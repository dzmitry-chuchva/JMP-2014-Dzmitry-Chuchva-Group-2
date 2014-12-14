package com.epam.cdp.java.banksystem.auth;

import java.sql.Connection;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.epam.cdp.java.banksystem.dto.Role;
import com.epam.cdp.java.banksystem.dto.User;

@Transactional
@Repository("JPAAuthDAO")
public class JPAAuthDAO implements AuthDAO {

	@PersistenceContext
	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public User fetchUserByLoginAndPass(String login, String pass, Connection conn) throws SQLException {
		User user = null;
		try {
			Query query = entityManager.createQuery("select u from User u where u.login = :login and u.password = :password");
			query.setParameter("login", login);
			query.setParameter("password", pass);
			user = (User) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		return user;
	}

	@Override
	public int createUser(String firstName, String lastName, String login, String pass, Connection conn) throws SQLException {
		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setLogin(login);
		user.setPassword(pass);
		user.setRole(new Role());
		entityManager.persist(user);
		return 1;
	}
}
