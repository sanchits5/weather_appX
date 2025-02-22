package com.sanchit.WeatherApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Main {

    private String temp;
    private String feels_like;
    private String pressure;
    private String humidity;
    private String temp_min;
    private String temp_max;
    private String sea_level;
    private String grnd_level;

}
