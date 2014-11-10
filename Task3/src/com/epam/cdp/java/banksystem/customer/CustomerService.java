package com.epam.cdp.java.banksystem.customer;

import java.sql.Connection;
import java.sql.SQLException;

import com.epam.cdp.java.banksystem.admin.AdminDAO;
import com.epam.cdp.java.banksystem.conn.ConnectionPool;
import com.epam.cdp.java.banksystem.dto.Account;
import com.epam.cdp.java.banksystem.dto.Conversion;
import com.epam.cdp.java.banksystem.exception.TechnicalException;

public class CustomerService {
	private CustomerDAO customerDAO;
	private AdminDAO adminDAO;

	public CustomerService(CustomerDAO customerDAO, AdminDAO adminDAO) {
		this.customerDAO = customerDAO;
		this.adminDAO = adminDAO;
	}

	public void performExchange(long accFromId, long accToId, double exchangeValue) throws TechnicalException {
		if (accFromId == accToId) {
			throw new TechnicalException();
		}
		ConnectionPool conPool = null;
		Connection conn = null;

		try {

			try {
				conPool = ConnectionPool.INSTANCE;
				conn = conPool.getConnection();
				conn.setAutoCommit(false);
				Account accFrom = adminDAO.fetchAccount(accFromId, conn);
				Account accTo = adminDAO.fetchAccount(accToId, conn);
				long curFromId = accFrom.getCurrency().getId();
				long curToId = accTo.getCurrency().getId();
				Conversion conv = customerDAO.fetchConversionRate(curFromId, curToId, conn);
				double rate = conv.getRate();
				double remove = -exchangeValue;
				double add = exchangeValue * rate;
				int rowsAffected1 = customerDAO.updateAccountValue(accFromId, remove, conn);
				int rowsAffected2 = customerDAO.updateAccountValue(accToId, add, conn);
				if (rowsAffected1 == 0 || rowsAffected2 == 0) {
					throw new TechnicalException();
				}
				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
				if (conn != null) {
					conn.rollback();
				}
				throw new TechnicalException();
			} finally {
				if (conPool != null) {
					conPool.releaseConnection(conn);
				}
			}

		} catch (SQLException e) {
			throw new TechnicalException();
		}
	}
}
