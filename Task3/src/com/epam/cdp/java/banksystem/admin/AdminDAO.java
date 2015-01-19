package com.epam.cdp.java.banksystem.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.epam.cdp.java.banksystem.dto.Account;
import com.epam.cdp.java.banksystem.dto.Currency;
import com.epam.cdp.java.banksystem.dto.User;

public interface AdminDAO {
	public int createAccount(long userId, long currencyId, double value, Connection conn) throws SQLException;

	public Account fetchAccount(long accountId, Connection conn) throws SQLException;

	public List<Account> fetchAccountList(Connection conn) throws SQLException;

	public List<Account> fetchUserAccountList(long userId, Connection conn) throws SQLException;

	public List<User> fetchUserList(Connection conn) throws SQLException;

	public List<Currency> fetchCurrencyTypeList(Connection conn) throws SQLException;

	public User fetchUserById(Connection conn, Long userId) throws SQLException;
}