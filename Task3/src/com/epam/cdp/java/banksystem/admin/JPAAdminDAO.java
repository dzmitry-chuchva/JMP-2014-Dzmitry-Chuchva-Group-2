package com.epam.cdp.java.banksystem.admin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.epam.cdp.java.banksystem.dto.Account;
import com.epam.cdp.java.banksystem.dto.Currency;
import com.epam.cdp.java.banksystem.dto.User;

@Transactional
@Repository("JPAAdminDAO")
public class JPAAdminDAO implements AdminDAO {

	@PersistenceContext
	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

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
		entityManager.persist(acc);
		return 1;
	}

	@Override
	public Account fetchAccount(long accountId, Connection conn) throws SQLException {
		return entityManager.find(Account.class, accountId);
	}

	@Override
	public List<Account> fetchAccountList(Connection conn) throws SQLException {
		List<Account> accountList = entityManager.createQuery("select a from Account a").getResultList();
		return accountList;
	}

	@Override
	public List<Account> fetchUserAccountList(long userId, Connection conn) throws SQLException {
		Query query = entityManager.createQuery("select a from Account a where a.user.id = :userId");
		query.setParameter("userId", userId);
		List<Account> accountList = query.getResultList();
		return accountList;
	}

	@Override
	public List<Currency> fetchCurrencyTypeList(Connection conn) throws SQLException {
		List<Currency> currencyTypeList = entityManager.createQuery("select c from Currency c").getResultList();
		return currencyTypeList;
	}

	@Override
	public List<User> fetchUserList(Connection conn) throws SQLException {
		List<User> userList = entityManager.createQuery("select u from User u").getResultList();
		return userList;
	}

}
