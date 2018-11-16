package mapsql.sql.condition;

import java.util.Map;

import mapsql.sql.core.Field;
import mapsql.sql.core.SQLException;
import mapsql.sql.core.TableDescription;

public class Like extends AbstractCondition {
	private String column;
	private String value;

	public Like(String column, String value) {
		this.column = column;
		this.value = value;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean evaluate(TableDescription description, Map<String, String> data) throws SQLException {
		Field field = description.findField(column);
		String currFieldValue = (String) field.toValue(data.get(column));
		String valueEvalStr = value;
		String fieldEvalStr = currFieldValue;
		String wildcard = "%";

		if (value.substring(0, 1).equals(wildcard)
				&& value.substring(value.length() - 1, value.length()).equals(wildcard)) {
			valueEvalStr = value.substring(1, value.length()-1);
			fieldEvalStr = currFieldValue.substring(1, currFieldValue.length()-1);
			
		} else if (value.substring(0, 1).equals(wildcard)) {
			valueEvalStr = value.substring(1, value.length());
			fieldEvalStr = currFieldValue.substring(1, currFieldValue.length());

		} else if (value.substring(value.length() - 1, value.length()).equals(wildcard)) {
			valueEvalStr = value.substring(0, value.length() - 1);
			fieldEvalStr = currFieldValue.substring(0, currFieldValue.length() - 1);
		} else {
			new SQLException("Valid wildcard not found");
		}

		return comparator.compare(fieldEvalStr, field.toValue(valueEvalStr)) == 0;
	}
}
