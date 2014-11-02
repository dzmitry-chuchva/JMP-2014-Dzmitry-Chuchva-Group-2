package com.epam.cdp.java.banksystem.auth;

import java.sql.Connection;
import java.sql.SQLException;

import com.epam.cdp.java.banksystem.dto.User;

public interface AuthDAO {
	public User fetchUserByLoginAndPass(String login, String pass, Connection conn) throws SQLException;

	public int createUser(String firstName, String lastName, String login, String pass, Connection conn) throws SQLException;
}
