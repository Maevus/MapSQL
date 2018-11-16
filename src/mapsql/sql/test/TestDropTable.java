package mapsql.sql.test;

import mapsql.sql.statement.DropTable;
import mapsql.sql.core.Field;
import mapsql.sql.core.SQLException;
import mapsql.sql.core.SQLManager;
import mapsql.sql.core.SQLResult;
import mapsql.sql.core.SQLStatement;
import mapsql.sql.field.CHARACTER;
import mapsql.sql.field.INTEGER;
import mapsql.sql.statement.CreateTable;
import mapsql.sql.statement.Select;


public class TestDropTable {
	static SQLManager manager = new SQLManager();

	public static void main(String[] args) {
		createTableStatement();

		showTables();
		selectTable();
		dropTable();
		showTables();
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
		executeStatement(new CreateTable("CONTACT", new Field[] { new INTEGER("id", true, false, true),
				new CHARACTER("Email", 30, false, true) }));
	}

	public static void showTables() {
		executeStatement(new Select("mapsql.tables", new String[] { "*" }));
	}

	public static void selectTable() {
		executeStatement(new Select("PERSON", new String[] { "*" }));
	}

	
	public static void dropTable() {
		executeStatement(new DropTable("PERSON"));
	}
}
