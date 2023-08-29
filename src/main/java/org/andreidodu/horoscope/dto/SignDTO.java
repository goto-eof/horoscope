package org.andreidodu.horoscope.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.andreidodu.horoscope.entities.ForecastSign;
import org.andreidodu.horoscope.entities.TableCommons;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

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
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class SignDTO  {

	private Long id;
	private String signName;

}
