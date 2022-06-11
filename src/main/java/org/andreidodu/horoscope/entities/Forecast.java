package org.andreidodu.horoscope.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tc001_forecast", uniqueConstraints = { @UniqueConstraint(columnNames = { "tc001_phrase", "tc001_category" }) })
@AttributeOverride(name = "active", column = @Column(name = "tc001_active"))
@AttributeOverride(name = "createdDate", column = @Column(name = "tc001_insert_date"))
@AttributeOverride(name = "lastModifiedDate", column = @Column(name = "tc001_update_date"))
@SQLDelete(sql = "UPDATE tc001_forecast SET tc001_active = 0 WHERE tc001_forecast_seq = ? AND tc001_version = ?")
@Where(clause = "tc001_active = 1")
public class Forecast extends TableCommons implements Serializable {

	private static final long serialVersionUID = 2602205456023526563L;

	@Id
	@Column(name = "tc001_forecast_seq")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "tc001_phrase", nullable = false)
	private String phrase;

	@Column(name = "tc001_category", length = 255, nullable = false)
	private String category;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "forecast")
	Set<ForecastSign> forecastsSigns = new HashSet<>();

}
