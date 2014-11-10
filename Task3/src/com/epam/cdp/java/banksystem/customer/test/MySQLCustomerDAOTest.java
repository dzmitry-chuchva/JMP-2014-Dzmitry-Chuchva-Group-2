package com.epam.cdp.java.banksystem.customer.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.epam.cdp.java.banksystem.customer.CustomerDAO;
import com.epam.cdp.java.banksystem.customer.MySQLCustomerDAO;
import com.epam.cdp.java.banksystem.dto.Conversion;
import com.ibatis.common.jdbc.ScriptRunner;

public class MySQLCustomerDAOTest {
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String JDBC_URL = "jdbc:mysql://localhost/banksystem";
	private static final String JDBC_URL_TEST = "jdbc:mysql://localhost/banksystem_test";
	private static final String USER = "root";
	private static final String PASSWORD = "root";

	private static Connection conn;
	private static Connection connTest;

	private static CustomerDAO customerDAO = new MySQLCustomerDAO();;

	@BeforeClass
	public static void createSchema() throws Exception {
		// init test schema
		Class.forName(JDBC_DRIVER);
		conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
		ScriptRunner runner = new ScriptRunner(conn, false, false);
		InputStreamReader reader = new InputStreamReader(new FileInputStream("src/resources/test/banksystem_test.sql"));
		runner.runScript(reader);
		reader.close();
		connTest = DriverManager.getConnection(JDBC_URL_TEST, USER, PASSWORD);
	}

	@AfterClass
	public static void releaseConnection() throws Exception {
		conn.close();
		connTest.close();
	}

	@Test
	public void testFetchConversionRate() throws Exception {
		IDataSet dataSet = readDataSet("src/resources/test/dataset/fetchConversionRate.xml");
		cleanlyInsert(dataSet);
		Conversion conversion = customerDAO.fetchConversionRate(1L, 2L, connTest);
		assertTrue(conversion != null);
	}

	@Test
	public void testUpdateAccountValue() throws Exception {
		IDataSet dataSet = readDataSet("src/resources/test/dataset/updateAccountValue.xml");
		cleanlyInsert(dataSet);
		int rowsAffected = customerDAO.updateAccountValue(1L, 50.0, connTest);
		assertTrue(rowsAffected == 1);
	}

	private IDataSet readDataSet(String fileName) throws Exception {
		return new FlatXmlDataSetBuilder().build(new File(fileName));
	}

	private void cleanlyInsert(IDataSet dataSet) throws Exception {
		IDatabaseTester databaseTester = new JdbcDatabaseTester(JDBC_DRIVER, JDBC_URL_TEST, USER, PASSWORD);
		databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
		databaseTester.setDataSet(dataSet);
		databaseTester.onSetup();
	}

}
