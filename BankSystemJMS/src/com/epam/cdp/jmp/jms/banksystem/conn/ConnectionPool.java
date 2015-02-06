package com.epam.cdp.jmp.jms.banksystem.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.epam.cdp.jmp.jms.banksystem.exception.ConPoolInitException;
import com.epam.cdp.jmp.jms.banksystem.exception.NoFreeConnectionsException;

public enum ConnectionPool {
	INSTANCE;

	private Queue<Connection> connQueue;

	private Lock lock = new ReentrantLock();

	private int poolSize = 16;

	private ConnectionPool() {
		try {
			connQueue = new LinkedList<Connection>();
			Class.forName("com.mysql.jdbc.Driver");
			for (int i = 0; i < poolSize; i++) {
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/banksystem", "root", "root");
				connQueue.add(conn);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() throws ConPoolInitException, NoFreeConnectionsException {
		lock.lock();
		try {
			if (connQueue == null) {
				throw new ConPoolInitException("Connection pool was not initialized.");
			}
			Connection conn = connQueue.poll();
			if (conn == null) {
				throw new NoFreeConnectionsException("No free connections.");
			}
			return conn;
		} finally {
			lock.unlock();
		}
	}

	public void releaseConnection(Connection conn) throws SQLException {
		lock.lock();
		try {
			if (connQueue.size() < poolSize) {
				if (conn != null && !conn.isClosed()) {
					connQueue.add(conn);
				} else {
					connQueue.add(DriverManager.getConnection("jdbc:mysql://localhost/banksystem"));
				}
			}
		} finally {
			lock.unlock();
		}
	}
}
