package org.andreidodu.horoscope.repository;

import java.util.Date;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.andreidodu.horoscope.entities.ForecastSign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ForecastSignDaoImpl extends CommonDao implements ForecastSignDao {

	private static final Logger LOG = LoggerFactory.getLogger(ForecastSignDaoImpl.class);

	@Override
	public boolean thereAreRecordsForInterval(Date startDate, Date endDate) {

		CriteriaBuilder builder = super.getSession().getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);

		Root<ForecastSign> root = criteriaQuery.from(ForecastSign.class);
		criteriaQuery.where(builder.greaterThanOrEqualTo(root.get("forecastDate"), startDate));
		criteriaQuery.where(builder.lessThan(root.get("forecastDate"), endDate));
		criteriaQuery.select(builder.count(root.get("id")));

		TypedQuery<Long> query = super.getSession().createQuery(criteriaQuery);
		Long numRecords = query.getSingleResult();
		LOG.debug("For {}-{} I found {} records", startDate, endDate, numRecords);
		return numRecords > 0;
	}

}
