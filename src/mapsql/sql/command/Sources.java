package mapsql.sql.command;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import mapsql.shell.core.SQLVisitor;
import mapsql.shell.parser.MapSQL;
import mapsql.shell.parser.ParseException;
import mapsql.shell.parser.SimpleNode;
import mapsql.sql.core.SQLCommand;
import mapsql.sql.core.SQLException;
import mapsql.sql.core.SQLManager;
import mapsql.sql.core.SQLOperation;
import mapsql.util.List;

import java.util.StringTokenizer;

public class Sources implements SQLCommand {
	private String filename;

	public Sources(String filename) {
		this.filename = filename;
	}

	@Override
	public String execute(SQLManager manager) throws SQLException {
		File file = new File(filename);
		Scanner input;
		try {
			input = new Scanner(file);
			while (input.hasNextLine()) {
				String line = input.nextLine();
				try {
					SimpleNode n = new MapSQL(new ByteArrayInputStream(line.getBytes())).Start();

					@SuppressWarnings("unchecked")
					List<SQLOperation> operations = (List<SQLOperation>) n.jjtAccept(new SQLVisitor(), null);
					for (SQLOperation operation : operations) {
						System.out.println(manager.execute(operation));
					}
				} catch (ParseException e) {
					System.out.println(e.getMessage());
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return "";

	}
}
