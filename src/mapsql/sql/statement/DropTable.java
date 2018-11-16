package mapsql.sql.statement;

import java.util.Map;

import mapsql.sql.condition.Equals;
import mapsql.sql.core.Row;
import mapsql.sql.core.SQLException;
import mapsql.sql.core.SQLResult;
import mapsql.sql.core.SQLStatement;
import mapsql.sql.core.Table;
import mapsql.sql.core.TableDescription;
import mapsql.util.List;

public class DropTable implements SQLStatement {
	private String name;
	
	public DropTable(String name) {
		this.name = name;
	}
	
	@Override
	public SQLResult execute(Map<String, Table> tables) throws SQLException {
		
		if (!tables.containsKey(name)) throw new SQLException ("No such table: " + name );
		
		if (name.equals("mapsql.tables")) {
			throw new SQLException ("Table ‘mapsql.tables’ cannot be modified."); 
			}
		else { 
			tables.remove(name);
			tables.get("mapsql.tables").delete(new Equals("table", name));
		}
		
		
		return new SQLResult() {
			public String toString() {
				return "OK";
			}

			@Override
			public TableDescription description() {
				return null;
			}

			@Override
			public List<Row> rows() {
				return null;
			}
		};
	}

}
