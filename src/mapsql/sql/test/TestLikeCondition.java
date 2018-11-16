package mapsql.sql.test;

import mapsql.sql.condition.Like;
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


public class TestLikeCondition {
	static SQLManager manager = new SQLManager();

	public static void main(String[] args) {
		createTableStatement();

		showTables();
		insertData();
		selectTable();
		selectColumnsWithWildcard("%aul");
		selectColumnsWithWildcard("Pau%");
		selectColumnsWithWildcard("%au%");
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


	public static void selectColumnsWithWildcard(String query) {
		executeStatement(new Select("PERSON", new String[] { "id", "name", "age" }, new Like("name", query)));
	}
	
}
