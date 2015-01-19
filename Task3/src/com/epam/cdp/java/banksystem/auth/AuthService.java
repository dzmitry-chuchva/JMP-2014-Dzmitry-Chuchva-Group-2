package com.epam.cdp.java.banksystem.auth;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.epam.cdp.java.banksystem.conn.ConnectionPool;
import com.epam.cdp.java.banksystem.dto.User;
import com.epam.cdp.java.banksystem.exception.TechnicalException;
import com.epam.cdp.java.banksystem.exception.UserAlreadyExistsException;

@Service("AuthService")
public class AuthService {

	@Autowired
	@Qualifier("JPAAuthDAO")
	private AuthDAO authDAO;

	public AuthDAO getAuthDAO() {
		return authDAO;
	}

	public void setAuthDAO(AuthDAO authDAO) {
		this.authDAO = authDAO;
	}

	public User signIn(String login, String pass) throws TechnicalException {
		User user = null;
		ConnectionPool conPool = null;
		Connection conn = null;

		try {

			try {
				conPool = ConnectionPool.INSTANCE;
				conn = conPool.getConnection();
				conn.setAutoCommit(false);
				user = authDAO.fetchUserByLoginAndPass(login, pass, conn);
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
			throw new TechnicalException(e);
		}

		return user;
	}

	public User signUp(String firstName, String lastName, String login, String pass) throws TechnicalException, UserAlreadyExistsException {
		User user = null;
		ConnectionPool conPool = null;
		Connection conn = null;

		try {

			try {
				conPool = ConnectionPool.INSTANCE;
				conn = conPool.getConnection();
				conn.setAutoCommit(false);
				user = authDAO.fetchUserByLoginAndPass(login, pass, conn);
				if (user != null) {
					throw new UserAlreadyExistsException("User is already exists.");
				}
				int rowsAffected = authDAO.createUser(firstName, lastName, login, pass, conn);
				if (rowsAffected == 0) {
					throw new TechnicalException();
				}
				user = authDAO.fetchUserByLoginAndPass(login, pass, conn);
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

		return user;
	}

}
