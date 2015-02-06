package com.epam.cdp.jmp.jms.banksystem.auth;

import java.sql.Connection;
import java.sql.SQLException;

import com.epam.cdp.jmp.jms.banksystem.dto.User;

public interface AuthDAO {
	public User fetchUserByLoginAndPass(String login, String pass, Connection conn) throws SQLException;

	public int createUser(String firstName, String lastName, String login, String pass, Connection conn) throws SQLException;
}
