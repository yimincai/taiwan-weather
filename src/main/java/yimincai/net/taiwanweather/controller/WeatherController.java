package yimincai.net.taiwanweather.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yimincai.net.taiwanweather.service.WeatherService;

@RestController
@RequestMapping("api/weather")
public class WeatherController {

    private final WeatherService weatherService;

    @Autowired
    public  WeatherController(WeatherService weatherService){
        this.weatherService = weatherService;
    }

    @ResponseBody
    @GetMapping(value ="forecast", produces = "application/json;charset=UTF-8")
    public String getCityForecast(@RequestParam String cityName){
        JSONObject result = weatherService.getWeatherForecast(cityName);
        return  result.toString();
    }

}
