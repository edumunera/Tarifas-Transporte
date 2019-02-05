package com.borman.myapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

	public static Connection Conectar() {
		
		final String msAccDB = "Prg_Borman_lib/TarifasTransporteBorman.accdb";
		final String dbURL = "jdbc:ucanaccess://" + msAccDB;
		Connection connection = null;
		
			try {
				Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				connection = DriverManager.getConnection(dbURL);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		return connection;
	}

}
