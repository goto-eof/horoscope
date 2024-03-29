package org.andreidodu.horoscope.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ForecastDTO {

    private String sign;
    List<ForecastByCategoryDTO> forecasts;

}
