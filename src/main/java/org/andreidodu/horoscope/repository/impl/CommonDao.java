package org.andreidodu.horoscope.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;

import lombok.Setter;

public abstract class CommonDao {

	@Setter
	@PersistenceContext
	protected EntityManager entityManager;

	protected Session getSession() {
		return this.entityManager.unwrap(Session.class);
	}

}
