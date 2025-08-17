package database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DataBase {
	
	// Declara uma variável estática conn do tipo Connection.
	// Inicialmente está como null → só será criada quando o método getConnection() for chamado.
	private static Connection conn = null;
	
	// Garante que só exista uma conexão ativa por vez.
	public static Connection getConnection() {
		
		if (conn == null) { // Verifica se a conexão ainda não foi criada. Se já existir, reutiliza a mesma.
			try {
				Properties props = loadProperties(); // Chama o método loadProperties(), que lê o arquivo db.properties
				String url = props.getProperty("dburl");
				conn = DriverManager.getConnection(url, props); // Cria a conexão com o banco:
			}
			catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
		return conn;
	}
	
	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			}
			catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	private static Properties loadProperties() {
		try (FileInputStream fs = new FileInputStream("db.properties")) {
			Properties props = new Properties();
			props.load(fs);
			return props;
		}
		catch (IOException e) {
			throw new DbException(e.getMessage());
		}
	}
	
	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

}
