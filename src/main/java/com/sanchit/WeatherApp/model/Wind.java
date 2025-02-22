package com.sanchit.WeatherApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wind {
    private String speed;
    private String deg;
    private String gust;

}
