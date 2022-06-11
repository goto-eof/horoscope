package org.andreidodu.horoscope.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class TableCommons {

	@JsonIgnore
	@CreatedDate
	@Column(name = "insert_date", updatable = false, insertable = false)
	protected LocalDateTime createdDate;
	
	@JsonIgnore
	@LastModifiedDate
	@Column(name = "update_date", updatable = true, insertable = false)
	protected LocalDateTime lastModifiedDate;

	@JsonIgnore
	@NotNull
	@Column(name = "active")
	protected Boolean active = Boolean.TRUE;

}
