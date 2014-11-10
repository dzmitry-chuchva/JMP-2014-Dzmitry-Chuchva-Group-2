package com.epam.cdp.java.banksystem.auth.test;

import java.sql.Connection;
import java.sql.SQLException;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.epam.cdp.java.banksystem.auth.AuthDAO;
import com.epam.cdp.java.banksystem.auth.AuthService;
import com.epam.cdp.java.banksystem.dto.User;
import com.epam.cdp.java.banksystem.exception.TechnicalException;
import com.epam.cdp.java.banksystem.exception.UserAlreadyExistsException;

public class AuthServiceTest {
	@Rule
	public JUnitRuleMockery context = new JUnitRuleMockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	private AuthService authService;
	@Mock
	private AuthDAO mockedAuthDAO;

	@Before
	public void setUp() {
		authService = new AuthService(mockedAuthDAO);
	}

	@Test
	public void testSignIn() throws SQLException, TechnicalException {
		final String login = "login";
		final String pass = "pass";
		final User expected = new User();
		context.checking(new Expectations() {
			{
				oneOf(mockedAuthDAO).fetchUserByLoginAndPass(with(equal(login)), with(equal(pass)), with(any(Connection.class)));
				will(returnValue(expected));
			}
		});
		User actual = authService.signIn(login, pass);
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testSignUp() throws SQLException, TechnicalException, UserAlreadyExistsException {
		final String fName = "fName";
		final String lName = "lName";
		final String login = "login";
		final String pass = "pass";
		final User expected = new User();
		context.checking(new Expectations() {
			{
				oneOf(mockedAuthDAO).fetchUserByLoginAndPass(with(equal(login)), with(equal(pass)), with(any(Connection.class)));
				will(returnValue(null));
				oneOf(mockedAuthDAO).createUser(with(equal(fName)), with(equal(lName)), with(equal(login)), with(equal(pass)), with(any(Connection.class)));
				will(returnValue(1));
				oneOf(mockedAuthDAO).fetchUserByLoginAndPass(with(equal(login)), with(equal(pass)), with(any(Connection.class)));
				will(returnValue(expected));
			}
		});
		User actual = authService.signUp(fName, lName, login, pass);
		Assert.assertEquals(expected, actual);
	}

}
