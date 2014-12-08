package com.epam.cdp.java.banksystem.customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.epam.cdp.java.banksystem.dto.Conversion;
import com.epam.cdp.java.banksystem.dto.Currency;

public class HibernateCustomerDAO implements CustomerDAO {

    @Override
    public Conversion fetchConversionRate(long fromId, long toId,
	    Connection conn) throws SQLException {
	PreparedStatement st = conn
		.prepareStatement("select cur1.currency_id 'from_id', cur1.type 'from_type', cur2.currency_id 'to_id', cur2.type 'to_type', con.rate from banksystem.conversion con join banksystem.currency cur1 on con.from = cur1.currency_id join banksystem.currency cur2 on con.to = cur2.currency_id where cur1.currency_id = ? and cur2.currency_id = ?");
	st.setLong(1, fromId);
	st.setLong(2, toId);
	ResultSet rs = st.executeQuery();
	Conversion conversion = null;
	if (rs.next()) {
	    conversion = buildConversion(rs);
	}
	return conversion;
    }

    @Override
    public int updateAccountValue(long accountId, double value, Connection conn)
	    throws SQLException {
	PreparedStatement st = conn
		.prepareStatement("update banksystem.account set value = value + ? where account_id = ?");
	st.setDouble(1, value);
	st.setLong(2, accountId);
	int rowsAffected = st.executeUpdate();
	return rowsAffected;
    }

    private Conversion buildConversion(ResultSet rs) throws SQLException {
	Conversion conversion = new Conversion();
	conversion.setRate(rs.getDouble("rate"));
	Currency from = new Currency();
	from.setId(rs.getLong("from_id"));
	from.setType(rs.getString("from_type"));
	Currency to = new Currency();
	to.setId(rs.getLong("to_id"));
	to.setType(rs.getString("to_type"));
	conversion.setFrom(from);
	conversion.setTo(to);
	return conversion;
    }

}
