package mapsql.sql.test;

import mapsql.sql.condition.GreaterThan;
import mapsql.sql.condition.LessThan;
import mapsql.sql.condition.GreaterThanOrEqual;
import mapsql.sql.condition.LessThanOrEqual;
import mapsql.sql.condition.NotEqual;
import mapsql.sql.core.Field;
import mapsql.sql.core.SQLException;
import mapsql.sql.core.SQLManager;
import mapsql.sql.core.SQLResult;
import mapsql.sql.core.SQLStatement;
import mapsql.sql.field.CHARACTER;
import mapsql.sql.field.INTEGER;
import mapsql.sql.statement.CreateTable;
import mapsql.sql.statement.Insert;
import mapsql.sql.statement.Select;


public class TestBasicConditions {
	static SQLManager manager = new SQLManager();

	public static void main(String[] args) {
		createTableStatement();

		showTables();
		insertData();
		selectTable();
		selectColumnsWithGreaterThanCondition();
		selectColumnsWithLessThanCondition();
		selectColumnsWithGreaterThanOrEqualCondition();
		selectColumnsNotEqualsCondition();
		
	}

	private static void executeStatement(SQLStatement statement) {
		try {
			SQLResult result = manager.execute(statement);
			System.out.println(result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void createTableStatement() {
		executeStatement(new CreateTable("PERSON", new Field[] { new INTEGER("id", true, false, true),
				new CHARACTER("name", 30, false, true), new INTEGER("age", false, false, false) }));
	}

	public static void showTables() {
		executeStatement(new Select("mapsql.tables", new String[] { "*" }));
	}

	public static void selectTable() {
		executeStatement(new Select("PERSON", new String[] { "*" }));
	}

	public static void insertData() {
		executeStatement(
				new Insert("PERSON", new String[] { "name", "age" }, new String[] { "Rem", "30" }));
		executeStatement(
				new Insert("PERSON", new String[] { "name", "age" }, new String[] { "Paul", "37" }));
		executeStatement(
				new Insert("PERSON", new String[] { "name", "age" }, new String[] { "Simon", "40" }));
		executeStatement(
				new Insert("PERSON", new String[] { "name", "age" }, new String[] { "Art", "28" }));
		}


	public static void selectColumnsWithGreaterThanCondition() {
		executeStatement(new Select("PERSON", new String[] { "id", "name", "age" }, new GreaterThan("age" ,"35")));
	}
	
	public static void selectColumnsWithLessThanCondition() {
		executeStatement(new Select("PERSON", new String[] { "id", "name", "age" }, new LessThan("age" ,"35")));
	}
	
	public static void selectColumnsWithLessThanOrEqualCondition() {
		executeStatement(new Select("PERSON", new String[] { "id", "name", "age" }, new LessThanOrEqual("age" ,"30")));
	}
	
	public static void selectColumnsWithGreaterThanOrEqualCondition() {
		executeStatement(new Select("PERSON", new String[] { "id", "name", "age" }, new GreaterThanOrEqual("age" ,"37")));
	}
	
	public static void selectColumnsNotEqualsCondition() {
		executeStatement(new Select("PERSON", new String[] { "id", "name", "age" }, new NotEqual("name" ,"Rem")));
	}
}
