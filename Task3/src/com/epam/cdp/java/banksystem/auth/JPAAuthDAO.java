package com.epam.cdp.java.banksystem.auth;

import java.sql.Connection;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.epam.cdp.java.banksystem.dto.Role;
import com.epam.cdp.java.banksystem.dto.User;

public class JPAAuthDAO implements AuthDAO {

	private static final EntityManagerFactory enManfactory = Persistence.createEntityManagerFactory("Hibernate_Unit");

	@Override
	public User fetchUserByLoginAndPass(String login, String pass, Connection conn) throws SQLException {
		User user = null;
		EntityManager em = enManfactory.createEntityManager();
		try {
			em.getTransaction().begin();
			Query query = em.createQuery("select u from User u where u.login = :login and u.password = :password");
			query.setParameter("login", login);
			query.setParameter("password", pass);
			user = (User) query.getSingleResult();
			em.getTransaction().commit();
		} catch (NoResultException e) {
			return null;
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
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

		EntityManager em = enManfactory.createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(user);
			em.getTransaction().commit();
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
		return 1;
	}
}
