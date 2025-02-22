package com.sanchit.WeatherApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Weather {

    private int id;
    private String description;
    private String main;
}
