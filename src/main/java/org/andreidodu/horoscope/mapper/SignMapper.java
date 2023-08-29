package org.andreidodu.horoscope.mapper;

import org.andreidodu.horoscope.dto.SignDTO;
import org.andreidodu.horoscope.entities.Sign;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SignMapper {
    Sign toEntity(SignDTO dto);
    SignDTO toDTO(Sign dto);
}
