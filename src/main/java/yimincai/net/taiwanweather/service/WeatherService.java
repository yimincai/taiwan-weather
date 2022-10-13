package yimincai.net.taiwanweather.service;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@Service
public class WeatherService {
    private OkHttpClient okHttpClient;
    private Response response;
    private String APIkey = "your_api_key";
    private String baseUrl = "https://opendata.cwb.gov.tw/api/v1/rest/datastore/F-C0032-001";

    // Getting data from open api
    public JSONObject getWeatherForecast(String locationName) {
        okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(baseUrl + "?Authorization=" + APIkey + "&locationName=" + locationName).build();
        try {
            response = okHttpClient.newCall(request).execute();
            return (new JSONObject(Objects.requireNonNull(response.body()).string()));
        }catch (IOException | JSONException e){
            e.printStackTrace();
        }
        return null;
    }
}
