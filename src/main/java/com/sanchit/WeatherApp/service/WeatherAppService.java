package com.sanchit.WeatherApp.service;

import com.sanchit.WeatherApp.model.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Service
public class WeatherAppService {

    @Value("${api.key}")
    private String apiKey;

    public void getWeatherData(String city, Model model) {
        try {
            String c=city;
            System.out.println("City: "+c);
            String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric";

            System.out.println("URL: "+url);
            RestTemplate restTemplate = new RestTemplate();
            WeatherResponse weatherResponse = restTemplate.getForObject(url, WeatherResponse.class);

            //sunrise time calculation
            long unix_seconds_epoch = Long.parseLong(weatherResponse.getSys().getSunrise());
            long unix_seconds_epoch1 = Long.parseLong(weatherResponse.getSys().getSunset());


            Date date = new Date(unix_seconds_epoch*1000L);
            Date date11= new Date(unix_seconds_epoch1*1000L);
            SimpleDateFormat formatTime = new SimpleDateFormat("hh:mm:ss aa");
            String sunrisetime = formatTime.format(date);
            String sunsetTime= formatTime.format(date11);
            sunrisetime= sunrisetime.toUpperCase();
            sunsetTime=sunsetTime.toUpperCase();



            long epochTimesINSec = Long.parseLong(weatherResponse.getDt());
            ZonedDateTime currentTimeDt = Instant.ofEpochSecond(epochTimesINSec).atZone(ZoneId.of("UTC"));

            String utcTimeZone = "";
            int timeZone = weatherResponse.getTimezone();
            int hours = timeZone / 3600;
            int minutes = (Math.abs(timeZone) % 3600) / 60;
            int length = 2;
            if (timeZone >= 0) {
                utcTimeZone = "UTC : +" + String.format("%0" + length + "d", Math.abs(hours)) + ":" + String.format("%0" + length + "d", minutes);

            } else {
                utcTimeZone = "UTC : -" + String.format("%0" + length + "d", Math.abs(hours)) + ":" + String.format("%0" + length + "d", minutes);
            }

            System.out.println(weatherResponse.toString());
            if (weatherResponse != null) {
                model.addAttribute("city", weatherResponse.getName());
                model.addAttribute("country", weatherResponse.getSys().getCountry());
                model.addAttribute("weatherDescription", weatherResponse.getWeather().get(0).getDescription());
                model.addAttribute("temperature", weatherResponse.getMain().getTemp());
                model.addAttribute("humidity", weatherResponse.getMain().getHumidity());
                model.addAttribute("windSpeed", weatherResponse.getWind().getSpeed());
                String weatherIcon = "wi wi-owm-" + weatherResponse.getWeather().get(0).getId();

                model.addAttribute("weatherIcon", weatherIcon);
                model.addAttribute("longitude", weatherResponse.getCoord().getLon());
                model.addAttribute("latitude", weatherResponse.getCoord().getLat());
                model.addAttribute("main", weatherResponse.getWeather().get(0).getMain());
                model.addAttribute("feels_like", weatherResponse.getMain().getFeels_like());

                model.addAttribute("pressure", weatherResponse.getMain().getPressure());
                model.addAttribute("temp_min", weatherResponse.getMain().getTemp_min());
                model.addAttribute("temp_max", weatherResponse.getMain().getTemp_max());
                model.addAttribute("sea_level", weatherResponse.getMain().getSea_level());
                model.addAttribute("grnd_level", weatherResponse.getMain().getGrnd_level());
                model.addAttribute("gust", weatherResponse.getWind().getGust());
                model.addAttribute("deg", weatherResponse.getWind().getDeg());
                model.addAttribute("clouds", weatherResponse.getClouds().getAll());
                model.addAttribute("dt", currentTimeDt);
                model.addAttribute("sunrise", sunrisetime);
                model.addAttribute("sunset", sunsetTime);
                model.addAttribute("timezone", utcTimeZone);
                model.addAttribute("visibility", weatherResponse.getVisibility());
            }
        }
        catch (HttpClientErrorException e) {
            model.addAttribute("error", "City Not Found");
            System.out.println(model.getAttribute("error"));

        }
    }
}
