package com.sanchit.WeatherApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sys {

    private String country;
    private String sunrise;
    private String sunset;


}

