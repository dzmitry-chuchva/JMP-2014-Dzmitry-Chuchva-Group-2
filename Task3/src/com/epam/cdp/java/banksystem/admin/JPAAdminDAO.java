package com.epam.cdp.java.banksystem.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.epam.cdp.java.banksystem.dto.Account;
import com.epam.cdp.java.banksystem.dto.Currency;
import com.epam.cdp.java.banksystem.dto.User;

public class JPAAdminDAO implements AdminDAO {

	private static final EntityManagerFactory enManfactory = Persistence.createEntityManagerFactory("Hibernate_Unit");

	@Override
	public int createAccount(long userId, long currencyId, double value, Connection conn) throws SQLException {
		User user = new User();
		user.setId(userId);
		Currency cur = new Currency();
		cur.setId(currencyId);
		Account acc = new Account();
		acc.setCurrency(cur);
		acc.setUser(user);
		acc.setValue(value);

		EntityManager em = enManfactory.createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(acc);
			em.getTransaction().commit();
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
		return 1;
	}

	@Override
	public Account fetchAccount(long accountId, Connection conn) throws SQLException {
		Account account = null;
		EntityManager em = enManfactory.createEntityManager();
		try {
			em.getTransaction().begin();
			account = em.find(Account.class, accountId);
			em.getTransaction().commit();
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
		return account;
	}

	@Override
	public List<Account> fetchAccountList(Connection conn) throws SQLException {
		List<Account> accountList = null;
		EntityManager em = enManfactory.createEntityManager();
		try {
			em.getTransaction().begin();
			accountList = em.createQuery("select a from Account a").getResultList();
			em.getTransaction().commit();
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
		return accountList;
	}

	@Override
	public List<Account> fetchUserAccountList(long userId, Connection conn) throws SQLException {
		List<Account> accountList = null;
		EntityManager em = enManfactory.createEntityManager();
		try {
			em.getTransaction().begin();
			Query query = em.createQuery("select a from Account a where a.user.id = :userId");
			query.setParameter("userId", userId);
			accountList = query.getResultList();
			em.getTransaction().commit();
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
		return accountList;
	}

	@Override
	public List<Currency> fetchCurrencyTypeList(Connection conn) throws SQLException {
		List<Currency> currencyTypeList = null;
		EntityManager em = enManfactory.createEntityManager();
		try {
			em.getTransaction().begin();
			currencyTypeList = em.createQuery("select c from Currency c").getResultList();
			em.getTransaction().commit();
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
		return currencyTypeList;
	}

	@Override
	public List<User> fetchUserList(Connection conn) throws SQLException {
		List<User> userList = null;
		EntityManager em = enManfactory.createEntityManager();
		try {
			em.getTransaction().begin();
			userList = em.createQuery("select u from User u").getResultList();
			em.getTransaction().commit();
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
		return userList;
	}

}
