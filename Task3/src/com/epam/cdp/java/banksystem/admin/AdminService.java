package com.epam.cdp.java.banksystem.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.epam.cdp.java.banksystem.conn.ConnectionPool;
import com.epam.cdp.java.banksystem.dto.Account;
import com.epam.cdp.java.banksystem.dto.Currency;
import com.epam.cdp.java.banksystem.dto.User;
import com.epam.cdp.java.banksystem.exception.TechnicalException;

@Service("AdminService")
public class AdminService {

	@Autowired
	@Qualifier("JPAAdminDAO")
	private AdminDAO adminDAO;

	public AdminDAO getAdminDAO() {
		return adminDAO;
	}

	public void setAdminDAO(AdminDAO adminDAO) {
		this.adminDAO = adminDAO;
	}

	public void createAccount(long userId, long currencyId, double value) throws TechnicalException {
		ConnectionPool conPool = null;
		Connection conn = null;

		try {

			try {
				conPool = ConnectionPool.INSTANCE;
				conn = conPool.getConnection();
				conn.setAutoCommit(false);
				int rowsAffected = adminDAO.createAccount(userId, currencyId, value, conn);
				if (rowsAffected == 0) {
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

	public List<Account> readAccountList() throws TechnicalException {
		ConnectionPool conPool = null;
		Connection conn = null;
		List<Account> accountList = null;
		try {

			try {
				conPool = ConnectionPool.INSTANCE;
				conn = conPool.getConnection();
				conn.setAutoCommit(false);
				accountList = adminDAO.fetchAccountList(conn);
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

		return accountList;
	}

	public List<Account> readUserAccountList(long userId) throws TechnicalException {
		ConnectionPool conPool = null;
		Connection conn = null;
		List<Account> accountList = null;
		try {

			try {
				conPool = ConnectionPool.INSTANCE;
				conn = conPool.getConnection();
				conn.setAutoCommit(false);
				accountList = adminDAO.fetchUserAccountList(userId, conn);
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

		return accountList;
	}

	public List<Currency> readCurrencyTypeList() throws TechnicalException {
		ConnectionPool conPool = null;
		Connection conn = null;
		List<Currency> currencyTypeList = null;
		try {

			try {
				conPool = ConnectionPool.INSTANCE;
				conn = conPool.getConnection();
				conn.setAutoCommit(false);
				currencyTypeList = adminDAO.fetchCurrencyTypeList(conn);
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

		return currencyTypeList;
	}

	public List<User> readUserList() throws TechnicalException {
		ConnectionPool conPool = null;
		Connection conn = null;
		List<User> userList = null;
		try {

			try {
				conPool = ConnectionPool.INSTANCE;
				conn = conPool.getConnection();
				conn.setAutoCommit(false);
				userList = adminDAO.fetchUserList(conn);
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

		return userList;
	}

	public User readUserById(Long userId) throws TechnicalException {
		User user = null;
		try {
			user = adminDAO.fetchUserById(null, userId);
		} catch (SQLException e) {
			throw new TechnicalException();
		}
		return user;
	}
}
