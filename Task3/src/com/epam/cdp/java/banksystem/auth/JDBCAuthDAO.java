package com.epam.cdp.java.banksystem.auth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.epam.cdp.java.banksystem.dto.Role;
import com.epam.cdp.java.banksystem.dto.User;

public class JDBCAuthDAO implements AuthDAO {

	@Override
	public User fetchUserByLoginAndPass(String login, String pass, Connection conn) throws SQLException {
		PreparedStatement st = conn.prepareStatement("select * from banksystem.user u left join banksystem.role r on u.role_id = r.role_id where u.login = ? and u.password = ?");
		st.setString(1, login);
		st.setString(2, pass);
		ResultSet rs = st.executeQuery();
		User user = null;
		if (rs.next()) {
			user = new User();
			user.setId(rs.getLong("user_id"));
			user.setLogin((rs.getString("login")));
			user.setFirstName((rs.getString("first_name")));
			user.setLastName((rs.getString("last_name")));
			user.setRole(new Role((rs.getString("role"))));
		}
		return user;
	}

	@Override
	public int createUser(String firstName, String lastName, String login, String pass, Connection conn) throws SQLException {
		PreparedStatement st = conn.prepareStatement("insert into banksystem.user (role_id, first_name, last_name, login, password) values (?,?,?,?,?)");
		st.setInt(1, 2); // 2 is customer role (default)
		st.setString(2, firstName);
		st.setString(3, lastName);
		st.setString(4, login);
		st.setString(5, pass);
		int affectedRows = st.executeUpdate();
		return affectedRows;
	}
}
