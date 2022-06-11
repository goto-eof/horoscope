package org.andreidodu.horoscope.entities;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "tc002_forecast_sign", uniqueConstraints = { @UniqueConstraint(columnNames = { "tc001_forecast_seq", "tp001_sign_seq" }) })
@AttributeOverride(name = "active", column = @Column(name = "tc002_active"))
@AttributeOverride(name = "createdDate", column = @Column(name = "tc002_insert_date"))
@AttributeOverride(name = "lastModifiedDate", column = @Column(name = "tc002_update_date"))
@SQLDelete(sql = "UPDATE tc002_forecats_sign SET tc002_active = 0 WHERE tc002_forecast_sign_seq = ? AND tc002_version = ?")
@Where(clause = "tc002_active = 1")
public class ForecastSign extends TableCommons {

	@Id
	@Column(name = "tc002_forecast_sign_seq")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "tc001_forecast_seq")
	private Forecast forecast;

	@ManyToOne
	@JoinColumn(name = "tp001_sign_seq")
	private Sign sign;

	@Column(name = "tc002_forecast_date", nullable = false)
	private Date forecastDate;

}
