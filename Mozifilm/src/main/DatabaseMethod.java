package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class DatabaseMethod {

	private Statement statement = null;
	private Connection connection = null;
	private ResultSet resultSet = null;

	public void Reg() {
		try {
			Class.forName("org.sqlite.JDBC");
//			 SM("Sikeres driver regisztr�ci�" ,1);
		} catch (ClassNotFoundException e) {
			SM("Hib�s driver regisztr�ci�!" + e.getMessage(), 0);
		}
	}

	public void SM(String msg, int tipus) {
		JOptionPane.showMessageDialog(null, msg, "Program �zenet", tipus);
	}

	public void connect() {
		try {
			String url = "jdbc:sqlite:D:/sqlite/plazadb";
			connection = DriverManager.getConnection(url);
			// SM("Connection OK",1);
		} catch (SQLException e) {
			SM("JDBC Connect: " + e.getMessage(), 0);

		}
	}

	public void DisConnect() {
		try {
			connection.close();
			// SM("DisConnection OK!", 1);
		} catch (SQLException e) {
			SM(e.getMessage(), 0);
		}
	}

	public MovieTableModel ReadData() {
		Object emptmn[] = { "Jel", "Sorsz�m", "C�m", "M�faj", "Kezd� id�pont", "Teremsz�m" };
		MovieTableModel etm = new MovieTableModel(emptmn, 0);
		String cim = "", mufaj = "", kezdoIdopont = "";
		int id = 0, terem = 0;
		String sqlp = "select sorszam,cim,mufaj,kezdo_idopont,terem from mozifilm";
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sqlp);
			while (resultSet.next()) {
				id = resultSet.getInt("sorszam");
				cim = resultSet.getString("cim");
				mufaj = resultSet.getString("mufaj");
				kezdoIdopont = resultSet.getString("kezdo_idopont");
				terem = resultSet.getInt("terem");
				etm.addRow(new Object[] { false, id, cim, mufaj, kezdoIdopont, terem });
			}
			resultSet.close();
		} catch (SQLException e) {
			SM(e.getMessage(), 0);
		}
		return etm;
	}

	public void InsertData(String id, String cim, String mufaj, String kezdoIdopont, String terem) {
		String sqlp = "insert into mozifilm values(" + id + ",'" + cim + "', '" + mufaj + "', '" + kezdoIdopont + "', "
				+ terem + ")";
		try {
			statement = connection.createStatement();
			statement.execute(sqlp);
			SM("�j film sikeresen hozz�ad�sa!", 1);
		} catch (SQLException e) {
			SM("JDBC insert: " + e.getMessage(), 0);
		}
	}

	public void DeleteData(String id) {
		String sqlp = "delete from mozifilm where sorszam =" + id;
		try {
			statement = connection.createStatement();
			statement.execute(sqlp);
		} catch (SQLException e) {
			SM("JDBC Delete: " + e.getMessage(), 0);
		}
	}

	public void UpdateData(String id, String mnev, String madat) {
		String sqlp = "update mozifilm set " + mnev + "='" + madat + "' where sorszam=" + id;
		try {
			statement = connection.createStatement();
			statement.execute(sqlp);
		} catch (SQLException e) {
			SM("JDBC Update: " + e.getMessage(), 0);
		}
	}
}
