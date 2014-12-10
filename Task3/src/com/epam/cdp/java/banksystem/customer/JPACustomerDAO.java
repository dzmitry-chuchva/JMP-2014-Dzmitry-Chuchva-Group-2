package com.epam.cdp.java.banksystem.customer;

import java.sql.Connection;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.epam.cdp.java.banksystem.dto.Conversion;

public class JPACustomerDAO implements CustomerDAO {

	private static final EntityManagerFactory enManfactory = Persistence.createEntityManagerFactory("Hibernate_Unit");

	@Override
	public Conversion fetchConversionRate(long fromId, long toId, Connection conn) throws SQLException {
		Conversion conversion = null;
		EntityManager em = enManfactory.createEntityManager();
		try {
			em.getTransaction().begin();
			Query query = em.createQuery("FROM Conversion c WHERE c.from.id = :fromId AND c.to.id = :toId");
			query.setParameter("fromId", fromId);
			query.setParameter("toId", toId);
			conversion = (Conversion) query.getSingleResult();
			em.getTransaction().commit();
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
		return conversion;
	}

	@Override
	public int updateAccountValue(long accountId, double delta, Connection conn) throws SQLException {
		int rowsAffected = 0;
		EntityManager em = enManfactory.createEntityManager();
		try {
			em.getTransaction().begin();
			Query query = em.createQuery("UPDATE Account a SET a.value = a.value + :delta WHERE a.id = :accountId");
			query.setParameter("delta", delta);
			query.setParameter("accountId", accountId);
			rowsAffected = query.executeUpdate();
			em.getTransaction().commit();
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
		return rowsAffected;
	}

}
