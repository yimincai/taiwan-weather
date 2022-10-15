package yimincai.net.taiwanweather.service;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

/**
 * 告知 Spring boot 此 class 為一個 Service
 * 必需標註為 @Service 否則 Spring IoC 掃描時不會註冊此依賴 會導致 @Autowired 時找不到Service
 */
@Service
public class WeatherService {
    // 用來對 Open API 呼叫的 http client
    private OkHttpClient okHttpClient;
    // http client 回傳的物件
    private Response response;
    // Open API 的使用金鑰 請記得替換成你自己的鑰匙
    private String API_KEY = "CWB-18DF70B5-67BF-4345-94B0-EB5AC35F7490";
    // 請求預測的 Open API 的基本網址 後續會與 API_KEY 組合成請求的 URL
    private String baseUrl = "https://opendata.cwb.gov.tw/api/v1/rest/datastore/F-C0032-001";

    // 從 Open API 取得天氣預測結果 此方法回傳 JSONObject
    public JSONObject getWeatherForecast(String locationName) {
        // 定義一個 okHttpClient 物件
        okHttpClient = new OkHttpClient();
        // 定義一個請求 並設定請求的 URL 這邊還沒發送請求哦 只有定義而已
        Request request = new Request.Builder().url(baseUrl + "?Authorization=" + API_KEY + "&locationName=" + locationName).build();
        try {
            // 發送請求到定義的 URL
            response = okHttpClient.newCall(request).execute();
            // 將 response 轉換為 JSONObject 並回傳
            return (new JSONObject(Objects.requireNonNull(response.body()).string()));
        }catch (IOException | JSONException e){
            e.printStackTrace();
        }
        // Response body 為空，回傳 null
        return null;
    }
}
