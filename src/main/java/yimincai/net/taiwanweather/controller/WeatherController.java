package yimincai.net.taiwanweather.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yimincai.net.taiwanweather.service.WeatherService;

// @RestController 告知 Spring boot 此 Class 為一個 Controller
@RestController
// API 路徑前綴 將會套用到下方所有定義的 API endpoint
@RequestMapping("api/weather")
public class WeatherController {

    // WeatherService 透過 @Autowired 在 Spring IoC 掃描時用來依賴注入物件 並在此取得並使用
    @Autowired
    private WeatherService weatherService;

    // @RequestBody 是作用在形參列表上 用於將前端傳送過來固定格式的資料(json or xml)封裝為對應的 JavaBean 物件
    @ResponseBody
    /**
     * 定義此為 HTTP 請求方法中的 Get 方法
     * value 定義 API 路徑並與 @RequestMapping 組合
     * produces 定義回傳的 Content-Type 為 application/json ，charset=UTF-8 用於解決回傳的資料中若有中文時的亂碼問題
     * @RequestParam String city 代表此 Get 方法接受 Param 方式的參數輸入
     * 例如在此為 ?city=我的城市名稱 呼叫時就可以透過 String city 拿到 "我的城市名稱"
     * 綜上所述 因此要呼叫此方法時 路徑應為 http://127.0.0.1:8080/api/weather/forecast?city=我的城市名稱
     */
    @GetMapping(value ="forecast", produces = "application/json;charset=UTF-8")
    public String getCityForecast(@RequestParam String city){
        // 透過 WeatherService 的 getWeatherForecast 方法取得天氣預測結果 並轉換為 String
        String result = weatherService.getWeatherForecast(city).toString();
        // 在 Console (終端機) 印出結果
        System.out.println(result);
        // 回傳結果給使用者
        return  result;
    }

}
