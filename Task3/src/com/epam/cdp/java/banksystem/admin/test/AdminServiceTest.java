package com.epam.cdp.java.banksystem.admin.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.epam.cdp.java.banksystem.admin.AdminDAO;
import com.epam.cdp.java.banksystem.admin.AdminService;
import com.epam.cdp.java.banksystem.dto.Account;
import com.epam.cdp.java.banksystem.dto.Currency;
import com.epam.cdp.java.banksystem.dto.User;
import com.epam.cdp.java.banksystem.exception.TechnicalException;

public class AdminServiceTest {
	@Rule
	public JUnitRuleMockery context = new JUnitRuleMockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	private AdminService adminService;
	@Mock
	private AdminDAO mockedAdminDAO;

	@Before
	public void setUp() {
		adminService = new AdminService(mockedAdminDAO);
	}

	@Test
	public void testCreateAccount() throws SQLException, TechnicalException {
		final long userId = 1L;
		final long currencyId = 1L;
		final double value = 1.0;
		context.checking(new Expectations() {
			{
				oneOf(mockedAdminDAO).createAccount(with(equal(userId)), with(equal(currencyId)), with(equal(value)), with(any(Connection.class)));
				will(returnValue(1));
			}
		});
		adminService.createAccount(userId, currencyId, value);
	}

	@Test
	public void testReadAccountList() throws SQLException, TechnicalException {
		List<Account> expected = new ArrayList<Account>();
		context.checking(new Expectations() {
			{
				oneOf(mockedAdminDAO).fetchAccountList(with(any(Connection.class)));
				will(returnValue(new ArrayList<Account>()));
			}
		});
		List<Account> actual = adminService.readAccountList();
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testReadUserAccountList() throws SQLException, TechnicalException {
		final long userId = 1L;
		List<Account> expected = new ArrayList<Account>();
		context.checking(new Expectations() {
			{
				oneOf(mockedAdminDAO).fetchUserAccountList(with(equal(userId)), with(any(Connection.class)));
				will(returnValue(new ArrayList<Account>()));
			}
		});
		List<Account> actual = adminService.readUserAccountList(userId);
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testReadCurrencyTypeList() throws SQLException, TechnicalException {
		List<Currency> expected = new ArrayList<Currency>();
		context.checking(new Expectations() {
			{
				oneOf(mockedAdminDAO).fetchCurrencyTypeList(with(any(Connection.class)));
				will(returnValue(new ArrayList<Currency>()));
			}
		});
		List<Currency> actual = adminService.readCurrencyTypeList();
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testReadUserList() throws SQLException, TechnicalException {
		List<User> expected = new ArrayList<User>();
		context.checking(new Expectations() {
			{
				oneOf(mockedAdminDAO).fetchUserList(with(any(Connection.class)));
				will(returnValue(new ArrayList<User>()));
			}
		});
		List<User> actual = adminService.readUserList();
		Assert.assertEquals(expected, actual);
	}

}
