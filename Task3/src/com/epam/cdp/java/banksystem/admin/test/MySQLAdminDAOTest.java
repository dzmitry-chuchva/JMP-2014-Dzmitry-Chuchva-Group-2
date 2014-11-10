package com.epam.cdp.java.banksystem.admin.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.epam.cdp.java.banksystem.admin.AdminDAO;
import com.epam.cdp.java.banksystem.admin.MySQLAdminDAO;
import com.epam.cdp.java.banksystem.dto.Account;
import com.epam.cdp.java.banksystem.dto.Currency;
import com.epam.cdp.java.banksystem.dto.User;
import com.ibatis.common.jdbc.ScriptRunner;

public class MySQLAdminDAOTest {

	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String JDBC_URL = "jdbc:mysql://localhost/banksystem";
	private static final String JDBC_URL_TEST = "jdbc:mysql://localhost/banksystem_test";
	private static final String USER = "root";
	private static final String PASSWORD = "root";

	private static Connection conn;
	private static Connection connTest;

	private static AdminDAO adminDAO = new MySQLAdminDAO();;

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
	public void testFetchUserList() throws Exception {
		IDataSet dataSet = readDataSet("src/resources/test/dataset/fetchUserList.xml");
		cleanlyInsert(dataSet);
		List<User> userList = adminDAO.fetchUserList(connTest);
		assertTrue(userList.size() == 3);
	}

	@Test
	public void test—reateAccount() throws Exception {
		int rowsAffected = adminDAO.createAccount(1L, 1L, 300.0, connTest);
		assertTrue(rowsAffected == 1);
	}

	@Test
	public void testFetchAccountList() throws Exception {
		IDataSet dataSet = readDataSet("src/resources/test/dataset/fetchAccountList.xml");
		cleanlyInsert(dataSet);
		List<Account> accountList = adminDAO.fetchAccountList(connTest);
		assertTrue(accountList.size() == 3);
	}

	@Test
	public void testFetchUserAccountList() throws Exception {
		IDataSet dataSet = readDataSet("src/resources/test/dataset/fetchUserAccountList.xml");
		cleanlyInsert(dataSet);
		List<Account> accountList = adminDAO.fetchUserAccountList(1L, connTest);
		assertTrue(accountList.size() == 3);
	}

	@Test
	public void testFetchCurrencyTypeList() throws Exception {
		IDataSet dataSet = readDataSet("src/resources/test/dataset/fetchCurrencyTypeList.xml");
		cleanlyInsert(dataSet);
		List<Currency> currencyTypeList = adminDAO.fetchCurrencyTypeList(connTest);
		assertTrue(currencyTypeList.size() == 3);
	}

	@Test
	public void testFetchAccount() throws Exception {
		IDataSet dataSet = readDataSet("src/resources/test/dataset/fetchAccount.xml");
		cleanlyInsert(dataSet);
		Account account = adminDAO.fetchAccount(1L, connTest);
		assertTrue(account != null);
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