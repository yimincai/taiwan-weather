package yimincai.net.taiwanweather.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yimincai.net.taiwanweather.service.WeatherService;

@RestController
@RequestMapping("api/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @ResponseBody
    @GetMapping(value ="forecast", produces = "application/json;charset=UTF-8")
    public String getCityForecast(@RequestParam String city){
        String result = weatherService.getWeatherForecast(city).toString();
        System.out.println(result);
        return  result;
    }

}
