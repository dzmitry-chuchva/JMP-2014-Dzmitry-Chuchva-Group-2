package com.epam.cdp.java.banksystem.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.epam.cdp.java.banksystem.dto.Account;
import com.epam.cdp.java.banksystem.dto.Currency;
import com.epam.cdp.java.banksystem.dto.Role;
import com.epam.cdp.java.banksystem.dto.User;

public class JDBCAdminDAO implements AdminDAO {

    @Override
    public int createAccount(long userId, long currencyId, double value,
	    Connection conn) throws SQLException {
	PreparedStatement st = conn
		.prepareStatement("insert into banksystem.account (user_id, currency_id, value) values(?,?,?)");
	st.setLong(1, userId);
	st.setLong(2, currencyId);
	st.setDouble(3, value);
	int affectedRows = st.executeUpdate();
	return affectedRows;
    }

    @Override
    public Account fetchAccount(long accountId, Connection conn)
	    throws SQLException {
	PreparedStatement st = conn
		.prepareStatement("select * from banksystem.account a join banksystem.user u on a.user_id = u.user_id join banksystem.currency c on a.currency_id = c.currency_id join banksystem.role r on u.role_id = r.role_id where a.account_id = ?");
	st.setLong(1, accountId);
	ResultSet rs = st.executeQuery();
	Account account = null;
	if (rs.next()) {
	    account = buildAccount(rs);
	}
	return account;
    }

    @Override
    public List<Account> fetchAccountList(Connection conn) throws SQLException {
	PreparedStatement st = conn
		.prepareStatement("select * from banksystem.account a join banksystem.user u on a.user_id = u.user_id join banksystem.currency c on a.currency_id = c.currency_id join banksystem.role r on u.role_id = r.role_id");
	ResultSet rs = st.executeQuery();
	List<Account> accountList = new ArrayList<Account>();
	while (rs.next()) {
	    Account account = buildAccount(rs);
	    accountList.add(account);
	}
	return accountList;
    }

    @Override
    public List<Account> fetchUserAccountList(long userId, Connection conn)
	    throws SQLException {
	PreparedStatement st = conn
		.prepareStatement("select * from banksystem.account a join banksystem.user u on a.user_id = u.user_id join banksystem.currency c on a.currency_id = c.currency_id join banksystem.role r on u.role_id = r.role_id where u.user_id = ?");
	st.setLong(1, userId);
	ResultSet rs = st.executeQuery();
	List<Account> accountList = new ArrayList<Account>();
	while (rs.next()) {
	    Account account = buildAccount(rs);
	    accountList.add(account);
	}
	return accountList;
    }

    @Override
    public List<Currency> fetchCurrencyTypeList(Connection conn)
	    throws SQLException {
	PreparedStatement st = conn
		.prepareStatement("select * from banksystem.currency");
	ResultSet rs = st.executeQuery();
	List<Currency> currencyList = new ArrayList<Currency>();
	while (rs.next()) {
	    Currency currency = buildCurrency(rs);
	    currencyList.add(currency);
	}
	return currencyList;
    }

    @Override
    public List<User> fetchUserList(Connection conn) throws SQLException {
	PreparedStatement st = conn
		.prepareStatement("select * from banksystem.user u join banksystem.role r on u.role_id = r.role_id where r.role != 'admin'");
	ResultSet rs = st.executeQuery();
	List<User> userList = new ArrayList<User>();
	while (rs.next()) {
	    User user = buildUser(rs);
	    userList.add(user);
	}
	return userList;
    }

    private Account buildAccount(ResultSet rs) throws SQLException {
	Account account = new Account();
	account.setId(rs.getLong("account_id"));
	account.setValue(rs.getDouble("value"));
	User user = buildUser(rs);
	Currency currency = buildCurrency(rs);
	account.setUser(user);
	account.setCurrency(currency);
	return account;
    }

    private User buildUser(ResultSet rs) throws SQLException {
	User user = new User();
	user.setId(rs.getLong("user_id"));
	user.setLogin(rs.getString("login"));
	user.setRole(new Role(rs.getString("role")));
	user.setFirstName(rs.getString("first_name"));
	user.setLastName(rs.getString("Last_name"));
	return user;
    }

    private Currency buildCurrency(ResultSet rs) throws SQLException {
	Currency currency = new Currency();
	currency.setId(rs.getLong("currency_id"));
	currency.setType(rs.getString("type"));
	return currency;
    }

}
