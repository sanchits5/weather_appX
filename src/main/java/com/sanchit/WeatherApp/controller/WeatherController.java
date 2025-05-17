package com.sanchit.WeatherApp.controller;

import com.sanchit.WeatherApp.model.WeatherResponse;
import com.sanchit.WeatherApp.service.WeatherAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.TimeZone;

@Controller
public class WeatherController {

    @Autowired
    private WeatherAppService weatherAppService;


    @GetMapping("/")
    public String index()
    {
        return "index";
    }

    @GetMapping("/weather")
    public String getWeather(@RequestParam("city") String city, Model model)
    {
        weatherAppService.getWeatherData(city,model);
        System.out.println(model.getAttribute("error"));

        if(model.getAttribute("error")=="City Not Found")
        {
        return "noDatafound";
        }
        return "weather";
    }
}
