package org.andreidodu.horoscope.repository;

import org.andreidodu.horoscope.entities.Sign;
import org.springframework.data.repository.CrudRepository;

public interface SignDao extends CrudRepository<Sign, Long> {

}
