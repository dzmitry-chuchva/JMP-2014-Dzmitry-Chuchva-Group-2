package com.epam.cdp.jmp.jms.banksystem.customer;

import java.sql.Connection;
import java.sql.SQLException;

import com.epam.cdp.jmp.jms.banksystem.dto.Conversion;

public interface CustomerDAO {
	public Conversion fetchConversionRate(long fromId, long toId, Connection conn) throws SQLException;

	public int updateAccountValue(long accountId, double value, Connection conn) throws SQLException;
}
