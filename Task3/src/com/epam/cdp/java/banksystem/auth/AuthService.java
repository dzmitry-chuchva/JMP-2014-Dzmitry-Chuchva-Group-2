package com.epam.cdp.java.banksystem.auth;

import java.sql.Connection;
import java.sql.SQLException;

import com.epam.cdp.java.banksystem.conn.ConnectionPool;
import com.epam.cdp.java.banksystem.dto.User;
import com.epam.cdp.java.banksystem.exception.ServiceException;

public class AuthService {

	private AuthDAO authDAO;

	public AuthService(AuthDAO authDAO) {
		this.authDAO = authDAO;
	}

	public User signIn(String login, String pass) throws ServiceException {
		User user = null;
		ConnectionPool conPool = null;
		Connection conn = null;
		try {
			conPool = ConnectionPool.getInstance();
			conn = conPool.getConnection();
			conn.setAutoCommit(false);
			user = authDAO.fetchUserByLoginAndPass(login, pass, conn);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			throw new ServiceException();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		} finally {
			if (conn != null) {
				conPool.releaseConnection();
			}
		}
		return user;
	}

	public User signUp(String firstName, String lastName, String login, String pass) throws ServiceException {
		User user = null;
		ConnectionPool conPool = null;
		Connection conn = null;
		try {
			conPool = ConnectionPool.getInstance();
			conn = conPool.getConnection();
			conn.setAutoCommit(false);
			int rowsAffected = authDAO.createUser(firstName, lastName, login, pass, conn);
			if (rowsAffected == 1) {
				user = authDAO.fetchUserByLoginAndPass(login, pass, conn);
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			throw new ServiceException();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		} finally {
			if (conn != null) {
				conPool.releaseConnection();
			}
		}
		return user;
	}

}
