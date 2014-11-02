package com.epam.cdp.java.banksystem.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.epam.cdp.java.banksystem.exception.ConPoolInitException;

public class ConnectionPool {
	private Connection conn;
	private Lock lock = new ReentrantLock();
	private static final ConnectionPool INSTANCE = new ConnectionPool();

	public static ConnectionPool getInstance() {
		return INSTANCE;
	}

	private ConnectionPool() throws ConPoolInitException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/banksystem");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		if (conn == null) {
			throw new ConPoolInitException("Connection pool was not properly initialized.");
		}
		lock.lock();
		return conn;
	}

	public void releaseConnection() {
		lock.unlock();
	}
}
