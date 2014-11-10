package com.epam.cdp.java.banksystem.customer.test;

import java.sql.Connection;
import java.sql.SQLException;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.epam.cdp.java.banksystem.admin.AdminDAO;
import com.epam.cdp.java.banksystem.customer.CustomerDAO;
import com.epam.cdp.java.banksystem.customer.CustomerService;
import com.epam.cdp.java.banksystem.dto.Account;
import com.epam.cdp.java.banksystem.dto.Conversion;
import com.epam.cdp.java.banksystem.dto.Currency;
import com.epam.cdp.java.banksystem.exception.TechnicalException;

public class CustomerServiceTest {
	@Rule
	public JUnitRuleMockery context = new JUnitRuleMockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	private CustomerService customerService;
	@Mock
	private CustomerDAO mockedCustomerDAO;
	@Mock
	private AdminDAO mockedAdminDAO;

	@Before
	public void setUp() {
		customerService = new CustomerService(mockedCustomerDAO, mockedAdminDAO);
	}

	@Test
	public void testPerformExchange() throws SQLException, TechnicalException {
		final double exchangeValue = 1.0;

		final Account accFrom = new Account();
		accFrom.setId(1L);
		final Currency curFrom = new Currency();
		curFrom.setId(2L);
		accFrom.setCurrency(curFrom);

		final Account accTo = new Account();
		accTo.setId(3L);
		final Currency curTo = new Currency();
		curTo.setId(4L);
		accTo.setCurrency(curTo);

		final Conversion conversion = new Conversion();
		conversion.setRate(2.0);
		conversion.setFrom(curFrom);
		conversion.setTo(curTo);

		context.checking(new Expectations() {
			{
				oneOf(mockedAdminDAO).fetchAccount(with(equal(accFrom.getId())), with(any(Connection.class)));
				will(returnValue(accFrom));
				oneOf(mockedAdminDAO).fetchAccount(with(equal(accTo.getId())), with(any(Connection.class)));
				will(returnValue(accTo));
				oneOf(mockedCustomerDAO).fetchConversionRate(with(equal(conversion.getFrom().getId())), with(equal(conversion.getTo().getId())), with(any(Connection.class)));
				will(returnValue(conversion));
				oneOf(mockedCustomerDAO).updateAccountValue(with(equal(accFrom.getId())), with(equal(-exchangeValue)), with(any(Connection.class)));
				will(returnValue(1));
				oneOf(mockedCustomerDAO).updateAccountValue(with(equal(accTo.getId())), with(equal(exchangeValue * conversion.getRate())), with(any(Connection.class)));
				will(returnValue(1));
			}
		});
		customerService.performExchange(accFrom.getId(), accTo.getId(), exchangeValue);
	}

}
