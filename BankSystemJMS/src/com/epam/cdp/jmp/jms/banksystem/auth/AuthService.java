package com.epam.cdp.jmp.jms.banksystem.auth;

import java.sql.Connection;
import java.sql.SQLException;

import com.epam.cdp.jmp.jms.banksystem.conn.ConnectionPool;
import com.epam.cdp.jmp.jms.banksystem.dto.User;
import com.epam.cdp.jmp.jms.banksystem.exception.TechnicalException;

public class AuthService {

	private AuthDAO authDAO;

	public AuthService(AuthDAO authDAO) {
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

}
