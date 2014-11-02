package com.epam.cdp.java.banksystem.auth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.epam.cdp.java.banksystem.dto.User;

public class MySQLAuthDAO implements AuthDAO {

	@Override
	public User fetchUserByLoginAndPass(String login, String pass, Connection conn) throws SQLException {
		PreparedStatement st = conn.prepareStatement("select * from banksystem.user where login = ? and password = ?");
		st.setString(1, login);
		st.setString(2, pass);
		ResultSet rs = st.executeQuery();
		User user = null;
		if (rs.next()) {
			user = new User();
			user.setId(rs.getLong("id"));
			user.setFirstName((rs.getString("first_name")));
			user.setLastName((rs.getString("last_name")));
		}
		return user;
	}

	@Override
	public int createUser(String firstName, String lastName, String login, String pass, Connection conn) throws SQLException {
		PreparedStatement st = conn.prepareStatement("insert into banksystem.user (first_name, last_name, login, password) values (?,?,?,?)");
		st.setString(1, firstName);
		st.setString(2, lastName);
		st.setString(3, login);
		st.setString(4, pass);
		int affectedRows = st.executeUpdate();
		return affectedRows;
	}
}
