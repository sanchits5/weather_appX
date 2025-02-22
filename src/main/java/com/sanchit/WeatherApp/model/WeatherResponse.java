package com.sanchit.WeatherApp.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WeatherResponse {

    private String name;
    private Coord coord;
    private Sys sys;
    private List<Weather> weather;
    private Main main;
    private Wind wind;
    private String visibility;
    private String dt;
    private int timezone;
    private Clouds clouds;


}
