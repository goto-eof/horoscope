package org.andreidodu.horoscope.entities;

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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tp001_sign", uniqueConstraints = { @UniqueConstraint(columnNames = { "tp001_sign_name" }) })
@AttributeOverride(name = "active", column = @Column(name = "tp001_active"))
@AttributeOverride(name = "createdDate", column = @Column(name = "tp001_insert_date"))
@AttributeOverride(name = "lastModifiedDate", column = @Column(name = "tp001_update_date"))
@SQLDelete(sql = "UPDATE tp001_sign SET tp001_active = 0 WHERE tp001_sign_seq = ? AND tp001_version = ?")
@Where(clause = "tp001_active = 1")
public class Sign extends TableCommons {

	@Id
	@Column(name = "tp001_sign_seq")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "tp001_sign_name", length = 255, nullable = false)
	private String category;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sign")
	// @FilterJoinTable(name = "playerMinId", condition = "players_id >= :minId")
	Set<ForecastSign> forecastsSigns = new HashSet<>();

}
