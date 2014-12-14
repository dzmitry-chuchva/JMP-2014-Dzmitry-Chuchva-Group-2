package com.epam.cdp.java.banksystem.customer;

import java.sql.Connection;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.epam.cdp.java.banksystem.dto.Conversion;

@Transactional
@Repository("JPACustomerDAO")
public class JPACustomerDAO implements CustomerDAO {

	@PersistenceContext
	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public Conversion fetchConversionRate(long fromId, long toId, Connection conn) throws SQLException {
		Query query = entityManager.createQuery("FROM Conversion c WHERE c.from.id = :fromId AND c.to.id = :toId");
		query.setParameter("fromId", fromId);
		query.setParameter("toId", toId);
		return (Conversion) query.getSingleResult();
	}

	@Override
	public int updateAccountValue(long accountId, double delta, Connection conn) throws SQLException {
		int rowsAffected = 0;
		Query query = entityManager.createQuery("UPDATE Account a SET a.value = a.value + :delta WHERE a.id = :accountId");
		query.setParameter("delta", delta);
		query.setParameter("accountId", accountId);
		rowsAffected = query.executeUpdate();
		return rowsAffected;
	}

}
