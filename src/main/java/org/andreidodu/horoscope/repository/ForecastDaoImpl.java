package org.andreidodu.horoscope.repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.andreidodu.horoscope.entities.Forecast;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ForecastDaoImpl extends CommonDao implements ForecastDao {

	private static final Logger LOG = LoggerFactory.getLogger(ForecastDaoImpl.class);

	public Long getMaxId() {
		CriteriaBuilder builder = super.getSession().getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);

		Root<Forecast> root = criteria.from(Forecast.class);
		criteria.select(builder.max(root.get("id")));

		TypedQuery<Long> query = super.getSession().createQuery(criteria);
		Long result = query.getSingleResult();
		LOG.debug("maxId is {}", result);
		return result;
	}

	@Override
	public void addForecast(Forecast forecast) {
		Session session = this.entityManager.unwrap(Session.class);
		session.persist(forecast);
		LOG.debug("Saved successfully: {}", forecast);
	}

}
