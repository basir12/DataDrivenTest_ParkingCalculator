package com.ParkingLibrary;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class DataBaseManager {
	final static Logger logger = Logger.getLogger(DataBaseManager.class);

	private String databaseServerName;
	private String databasePort;
	private String databaseName;
	private String userName;
	private String userPassword;

	private String connectionURL = null;

	private ResultSet resultSet = null;
	private Statement statement = null;
	private Connection connection = null;

	private void connectToOracleDB() throws Exception {
		databaseServerName = "localhost";
		databasePort = "1521";
		databaseName = "xe";
		userName = "hr";
		userPassword = "hr";

		connectionURL = "jdbc:oracle:thin:hr@//" + databaseServerName + ":" + databasePort + "/" + databaseName;

		Class.forName("oracle.jdbc.OracleDriver");
		connection = DriverManager.getConnection(connectionURL, userName, userPassword);
		statement = connection.createStatement();
	}

	private void connectToMySQLDB() throws Exception {
		// Homework
		// Step1: download microsoft MySQL database and install it
		// Step2: get the pom dependency for MySQL database driver
		// Step3: write here the connection code for MySQL database
	}

	public ResultSet runSQLQuery(String sqlQuery) throws Exception {
		connectToOracleDB();
		resultSet = statement.executeQuery(sqlQuery);
		return resultSet;
	}

	public static void main(String[] args) {
		DataBaseManager dbManager = new DataBaseManager();
		try {

			ResultSet result = dbManager.runSQLQuery("Select * From Countries");
			// logger.info("result set value : [ " + result + "]");

			System.out.println("");
			System.out.println("COUNTRY_ID" + "       " + "COUNTRY_NAME" + "       " + "REGION_ID");
			System.out.println("-------------------------------------------------------------------------");
			while (result.next()) {
				String countryID = result.getString("COUNTRY_ID");
				String countryName = result.getString("COUNTRY_NAME");
				int regionID = result.getInt("REGION_ID");
				System.out.println(countryID + "\t" + countryName + "\t \t \t \t" + regionID);
			}

		} catch (Exception e) {
			logger.error("Error: " + e);
		}
	}

}