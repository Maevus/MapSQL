package mapsql.sql.core;

import java.util.HashMap;
import java.util.Map;
import mapsql.sql.condition.Equals;
import mapsql.util.LinkedList;
import mapsql.util.List;
import mapsql.util.Position;

import java.util.Iterator;

public class Table {
	private TableDescription description;
	private List<Row> rows = new LinkedList<Row>();

	public Table(TableDescription description) {
		this.description = description;
	}

	public TableDescription description() {
		return description;
	}

	public List<Row> select(Condition where) throws SQLException {
		List<Row> list = new LinkedList<Row>();
		for (Row row : rows) {
			if (row.satisfies(where, description))
				list.insertLast(row);
		}
		return list;
	}

	public void insert(String[] columns, String[] values) throws SQLException {
		Map<String, String> data = new HashMap<String, String>();

		for (int i = 0; i < columns.length; i++) {
			Field field = description.findField(columns[i]);
			if (field.isUnique()) {
				if (!select(new Equals(columns[i], values[i])).isEmpty()) {
					throw new SQLException(
							"Column '" + columns[i] + "' is UNIQUE - a row with '" + values[i] + "' already exists");
				}
			}
			data.put(columns[i], field.validate(values[i]));
		}

		Field[] fields = description.fields();
		for (int i = 0; i < fields.length; i++) {
			if (!data.containsKey(fields[i].name())) {
				String val = fields[i].defaultValue();

				if (fields[i].isUnique()) {
					if (!select(new Equals(fields[i].name(), val)).isEmpty()) {
						throw new SQLException("Column '" + fields[i].name() + "' is UNIQUE - a row with '" + val
								+ "' already exists");
					}
				}
				data.put(fields[i].name(), val);
			}
		}
		rows.insertLast(new Row(data));
	}

	@SuppressWarnings("unchecked")
	public void update(String[] columns, String[] values, Condition where) throws SQLException {
		Map<String, String> data = new HashMap<String, String>();
		Row matchedRow = null;
		Position<Row> pos = null;

		// Retrieve matching row.
		Iterator<Row> iter = rows.iterator();
		while (iter.hasNext()) {
			Row row = (Row) iter.next();
			if (row.satisfies(where, description)) {
				matchedRow = row;
			}
		}

		// Add new data to map.
		for (int i = 0; i < columns.length; i++) {
			Field field = description.findField(columns[i]);
			data.put(columns[i], field.validate(values[i]));
		}

		// Replace changed fields in matched row data map.
		Field[] fields = description.fields();
		for (int i = 0; i < fields.length; i++) {
			if (data.containsKey(fields[i].name())) {
				matchedRow.data.replace(fields[i].name(), data.get(fields[i].name()));
			}
		}
	}

	public void delete(Condition where) throws SQLException {
		// Delete row from linked list rows
		Iterator<Row> i = rows.iterator();

		while (i.hasNext()) {
			Row row = (Row) i.next();
			if (row != null) {
				if (row.satisfies(where, description)) {
					i.remove();
				}
			}
		}
	}
}
