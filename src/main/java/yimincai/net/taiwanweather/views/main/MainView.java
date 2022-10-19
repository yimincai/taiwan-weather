package yimincai.net.taiwanweather.views.main;

import com.jayway.jsonpath.JsonPath;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import yimincai.net.taiwanweather.classes.Forecast;
import yimincai.net.taiwanweather.service.WeatherService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 設定前端網頁的 pag tittle
@PageTitle("Weather APP")

// 網頁的進入點 "" 代表為 http://127.0.0.1:8080 (Spring boot 預設為 8080 port)
@Route(value = "")
public class MainView extends VerticalLayout {

    // WeatherService 透過 @Autowired 在 Spring IoC 掃描時用來依賴注入物件 並在此取得並使用
    @Autowired
    private WeatherService weatherService;

    // 前端畫面上用來呈現資料的表格
    private final Grid<Forecast> gridTable = new Grid<>(Forecast.class, false);
    // 前端畫面上用來選擇城市的選擇器
    private Select<String> selector;
    // 預測結果的值
    private List<String> forecastResult;
    // 預測的開始時間
    private List<String> startTime;
    // 預測的結束時間
    private List<String> endTime;


    public MainView() {
        setMargin(true);
        setUpSelector();
        setUpTable();
    }


    /**
     * 設定前端畫面上的選擇器
     */
    public void setUpSelector() {
        // 建立 select 物件
        selector = new Select<>();
        // 設定物件左上角顯示的選擇器名稱
        selector.setLabel("City");
        // // 定義所有可選的城市清單
        List<String> cityList = Arrays.asList("臺北市", "新北市", "桃園市", "臺中市", "臺南市", "高雄市", "宜蘭縣", "新竹縣", "苗栗縣", "彰化縣", "南投縣", "雲林縣", "嘉義縣", "屏東縣", "花蓮縣", "臺東縣", "澎湖縣", "基隆市", "新竹市", "嘉義市");
        // 將清單放進選擇器內
        selector.setItems(cityList);
        // 在畫面上新增此選擇器
        add(selector);
    }

    /**
     * 設定前端畫面上的表格
     */
    public void setUpTable() {
        // 表格寬度與畫面等寬
        gridTable.setWidthFull();
        // 設定Column欄位
        gridTable.addColumn(Forecast::getTime).setHeader("預報時段");
        gridTable.addColumn(Forecast::getWx).setHeader("天氣現象");
        gridTable.addColumn(Forecast::getPop).setHeader("降雨機率");
        gridTable.addColumn(Forecast::getMinT).setHeader("最低溫度");
        gridTable.addColumn(Forecast::getMaxT).setHeader("最高溫度");
        gridTable.addColumn(Forecast::getCi).setHeader("舒適度");
        // 定義一個空的 Forecast List 並設定給表格 所以表格會是空的 並會顯示 Column 名稱
        List<Forecast> nullForecast = List.of();
        // 空的 Forecast List 放進 gridTable
        gridTable.setItems(nullForecast);
        // 在畫面上新增此表格
        add(gridTable);

        /*
         * 建立一個 selector 監聽事件
         * 當 select 的 value 被改變時觸發此事件
         * 觸發時透過選擇器取得使用者選取的城市名稱 再透過 weatherService 取得對應城市的天氣預報
         * weatherService 回傳結果為 JSON 格式
         * 最後解析 JSON string 將資料放進表格中
         */
        selector.addValueChangeListener(e -> {
            // 透過 weatherService 與 selector 取得對應城市的天氣預報
            String jsonString = weatherService.getWeatherForecast(selector.getValue()).toString();
            // For debug: 給測試時使用 當取得資料時在前端畫面上使用 Notification 方法顯示透過 weather 取得的原始資料
            Notification.show(jsonString);
            List<String> weatherElement = JsonPath.read(jsonString, "$.records.location[*].weatherElement[*]");
            List<String> elementName = JsonPath.read(jsonString, "$.records.location[*].weatherElement[*].elementName");
            startTime = JsonPath.read(jsonString, "$.records.location[*].weatherElement[0].time[*].startTime");
            endTime = JsonPath.read(jsonString, "$.records.location[*].weatherElement[0].time[*].endTime");

            // 透過 startTime 的數量取得資料的筆數 預設應為 3
            int level = startTime.size();

            // 定義一個 Key-Value 的 Hash Map 儲存解析的資料
            Map<String, List<String>> parameterData = new HashMap<>();
            // weatherElement.size() 為 property 的筆數 例如這邊取到的會是 ["Wx", "PoP", "MinT", "CI", "MaxT"] 所以 .size() = 5
            for (int i = 0; i < weatherElement.size(); i++) {
            // 第一次解析時取得 Wx 的資料並放進 Hash Map ， Key 設定為 "Wx"
            // 第二次解析時去得 PoP 的資料並放進 Hash Map ， Key 設定為 "PoP"
            // ... 以此類推
                forecastResult = JsonPath.read(jsonString, String.format("$.records.location[*].weatherElement[%d].time[*].parameter.parameterName", i));
                parameterData.put(elementName.get(i), forecastResult);
            }

            // 定義一個空的 Forecast List 以存放要顯示在 gridTable 的資料
            List<Forecast> forecastsList = new java.util.ArrayList<>(List.of());

            // 如果 startTime 為空 代表沒有取得資料或解析資料錯誤 -> 不更新 gridTable 資料
            if (startTime != null) {
                // level 預設應為 3 (有3個時間段)
                // 跑 3 個迴圈依次透過 Hash Map 取得對應的顯示資料
                for (int i = 0; i < level; i++) {
                    List<String> wx = parameterData.get("Wx");
                    List<String> pop = parameterData.get("PoP");
                    List<String> minT = parameterData.get("MinT");
                    List<String> maxT = parameterData.get("MaxT");
                    List<String> ci = parameterData.get("CI");

                    // 設定該迴圈的資料進到 forecastsList
                    forecastsList.add(new Forecast(startTime.get(i) + " ~ " + endTime.get(i), wx.get(i), pop.get(i) + "%", minT.get(i) + "°C", maxT.get(i) + "°C", ci.get(i)));
                }
            }
            // 更新 gridTable 的資料 將 forecastsList 放進去
            gridTable.setItems(forecastsList);
        });
    }
}
