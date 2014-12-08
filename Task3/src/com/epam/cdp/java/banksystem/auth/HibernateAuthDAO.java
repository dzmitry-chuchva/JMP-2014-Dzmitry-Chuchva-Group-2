package com.epam.cdp.java.banksystem.auth;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.epam.cdp.java.banksystem.dto.Role;
import com.epam.cdp.java.banksystem.dto.User;
import com.epam.cdp.java.banksystem.util.HibernateUtil;

public class HibernateAuthDAO implements AuthDAO {

    @Override
    public User fetchUserByLoginAndPass(String login, String pass,
	    Connection conn) throws SQLException {
	Session session = HibernateUtil.getSessionFactory().openSession();
	User user = null;
	try {
	    Criteria crit = session.createCriteria(User.class);
	    crit.add(Restrictions.eq("login", login));
	    crit.add(Restrictions.eq("password", pass));
	    List<User> list = crit.list();
	    Iterator<User> itr = list.iterator();
	    if (itr.hasNext()) {
		user = itr.next();
	    }
	} catch (HibernateException e) {
	    e.printStackTrace();
	    throw new SQLException(e);
	} finally {
	    session.close();
	}
	return user;
    }

    @Override
    public int createUser(String firstName, String lastName, String login,
	    String pass, Connection conn) throws SQLException {
	Session session = HibernateUtil.getSessionFactory().openSession();
	User user = new User();
	user.setFirstName(firstName);
	user.setLastName(lastName);
	user.setLogin(login);
	user.setPassword(pass);
	user.setRole(new Role());
	try {
	    session.save(user);
	} catch (HibernateException e) {
	    e.printStackTrace();
	    throw new SQLException(e);
	} finally {
	    session.close();
	}
	return 1;
    }
}
