package com.epam.cdp.java.banksystem.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.epam.cdp.java.banksystem.dto.Account;
import com.epam.cdp.java.banksystem.dto.Currency;
import com.epam.cdp.java.banksystem.dto.User;
import com.epam.cdp.java.banksystem.util.HibernateUtil;

public class HibernateAdminDAO implements AdminDAO {

    @Override
    public int createAccount(long userId, long currencyId, double value,
	    Connection conn) throws SQLException {
	User user = new User();
	user.setId(userId);
	Currency cur = new Currency();
	cur.setId(currencyId);
	Account acc = new Account();
	acc.setCurrency(cur);
	acc.setUser(user);
	acc.setValue(value);

	Session session = HibernateUtil.getSessionFactory().openSession();
	try {
	    session.save(acc);
	} catch (HibernateException e) {
	    e.printStackTrace();
	    throw new SQLException(e);
	} finally {
	    session.close();
	}
	return 1;
    }

    @Override
    public Account fetchAccount(long accountId, Connection conn)
	    throws SQLException {
	Session session = HibernateUtil.getSessionFactory().openSession();
	Account account = null;
	try {
	    account = (Account) session.get(Account.class, accountId);
	} catch (HibernateException e) {
	    e.printStackTrace();
	    throw new SQLException(e);
	} finally {
	    session.close();
	}
	return account;
    }

    @Override
    public List<Account> fetchAccountList(Connection conn) throws SQLException {

	Session session = HibernateUtil.getSessionFactory().openSession();
	List<Account> accountList = new ArrayList<Account>();
	try {
	    Criteria crit = session.createCriteria(Account.class);
	    List<Account> list = crit.list();
	    Iterator<Account> itr = list.iterator();
	    while (itr.hasNext()) {
		accountList.add(itr.next());
	    }
	} catch (HibernateException e) {
	    e.printStackTrace();
	    throw new SQLException(e);
	} finally {
	    session.close();
	}
	return accountList;
    }

    @Override
    public List<Account> fetchUserAccountList(long userId, Connection conn)
	    throws SQLException {
	Session session = HibernateUtil.getSessionFactory().openSession();
	List<Account> accountList = new ArrayList<Account>();
	try {
	    User user = new User();
	    user.setId(userId);
	    Criteria crit = session.createCriteria(Account.class);
	    crit.add(Restrictions.eq("user", user));
	    List<Account> list = crit.list();
	    Iterator<Account> itr = list.iterator();
	    while (itr.hasNext()) {
		accountList.add(itr.next());
	    }
	} catch (HibernateException e) {
	    e.printStackTrace();
	    throw new SQLException(e);
	} finally {
	    session.close();
	}
	return accountList;
    }

    @Override
    public List<Currency> fetchCurrencyTypeList(Connection conn)
	    throws SQLException {
	Session session = HibernateUtil.getSessionFactory().openSession();
	List<Currency> currencyTypeList = new ArrayList<Currency>();
	try {
	    Criteria crit = session.createCriteria(Currency.class);
	    List<Currency> list = crit.list();
	    Iterator<Currency> itr = list.iterator();
	    while (itr.hasNext()) {
		currencyTypeList.add(itr.next());
	    }
	} catch (HibernateException e) {
	    e.printStackTrace();
	    throw new SQLException(e);
	} finally {
	    session.close();
	}
	return currencyTypeList;
    }

    @Override
    public List<User> fetchUserList(Connection conn) throws SQLException {
	Session session = HibernateUtil.getSessionFactory().openSession();
	List<User> userList = new ArrayList<User>();
	try {
	    Criteria crit = session.createCriteria(User.class);
	    List<User> list = crit.list();
	    Iterator<User> itr = list.iterator();
	    while (itr.hasNext()) {
		userList.add(itr.next());
	    }
	} catch (HibernateException e) {
	    e.printStackTrace();
	    throw new SQLException(e);
	} finally {
	    session.close();
	}
	return userList;
    }
}
