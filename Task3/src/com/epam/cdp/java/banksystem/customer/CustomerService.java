package com.epam.cdp.java.banksystem.customer;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.epam.cdp.java.banksystem.admin.AdminDAO;
import com.epam.cdp.java.banksystem.conn.ConnectionPool;
import com.epam.cdp.java.banksystem.dto.Account;
import com.epam.cdp.java.banksystem.dto.Conversion;
import com.epam.cdp.java.banksystem.exception.TechnicalException;

@Service("CustomerService")
public class CustomerService {

	@Autowired
	@Qualifier("JPACustomerDAO")
	private CustomerDAO customerDAO;

	@Autowired
	@Qualifier("JPAAdminDAO")
	private AdminDAO adminDAO;

	public CustomerDAO getCustomerDAO() {
		return customerDAO;
	}

	public void setCustomerDAO(CustomerDAO customerDAO) {
		this.customerDAO = customerDAO;
	}

	public AdminDAO getAdminDAO() {
		return adminDAO;
	}

	public void setAdminDAO(AdminDAO adminDAO) {
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
