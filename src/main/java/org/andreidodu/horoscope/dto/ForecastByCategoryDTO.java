package org.andreidodu.horoscope.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ForecastByCategoryDTO {
    private String category;
    private String forecast;
    private Integer rating;
}
