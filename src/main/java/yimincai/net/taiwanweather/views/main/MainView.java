package yimincai.net.taiwanweather.views.main;

import com.jayway.jsonpath.JsonPath;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import yimincai.net.taiwanweather.Forecast;
import yimincai.net.taiwanweather.service.CityService;
import yimincai.net.taiwanweather.service.WeatherService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PageTitle("Main")
@Route(value = "")
public class MainView extends HorizontalLayout {

    @Autowired
    private WeatherService weatherService;
    private final Select<String> select;
    private List<String> parameter;
    private List<String> startTime;
    private List<String> endTime;


    public MainView() {
        setMargin(true);
        select = new Select<>();
        select.setLabel("City");
        List<String> cityList = CityService.getCityList();
        select.setItems(cityList);
        add(select);

        Grid<Forecast> grid = new Grid<>(Forecast.class, false);
        grid.addColumn(Forecast::getTime).setHeader("Time");
        grid.addColumn(Forecast::getWx).setHeader("Wx");
        grid.addColumn(Forecast::getPop).setHeader("PoP");
        grid.addColumn(Forecast::getMinT).setHeader("MinT");
        grid.addColumn(Forecast::getMaxT).setHeader("MaxT");
        grid.addColumn(Forecast::getCi).setHeader("CI");
        List<Forecast> nullForecast = List.of();
        grid.setItems(nullForecast);
        add(grid);

        select.addValueChangeListener(e -> {
            String jsonString = weatherService.getWeatherForecast(select.getValue()).toString();
            List<String> weatherElement = JsonPath.read(jsonString, "$.records.location[*].weatherElement[*]");
            List<String> elementName = JsonPath.read(jsonString, "$.records.location[*].weatherElement[*].elementName");
            startTime = JsonPath.read(jsonString, "$.records.location[*].weatherElement[0].time[*].startTime");
            endTime = JsonPath.read(jsonString, "$.records.location[*].weatherElement[0].time[*].endTime");
            int level = startTime.size();

            // get data
            Map<String, List<String>> parameterData = new HashMap<>();
            for (int i = 0; i < weatherElement.size(); i++) {
                parameter = JsonPath.read(jsonString, String.format("$.records.location[*].weatherElement[%d].time[*].parameter.parameterName", i));
                parameterData.put(elementName.get(i), parameter);
            }
            // set grid data
            List<Forecast> forecastsList = new java.util.ArrayList<>(List.of());
            if (startTime != null) {
                for (int i = 0; i < level; i++) {
                    List<String> wx = parameterData.get("Wx");
                    List<String> pop = parameterData.get("PoP");
                    List<String> minT = parameterData.get("MinT");
                    List<String> maxT = parameterData.get("MaxT");
                    List<String> ci = parameterData.get("CI");
                    forecastsList.add(new Forecast(startTime.get(i) + " ~ " + endTime.get(i), wx.get(i), pop.get(i), minT.get(i), maxT.get(i), ci.get(i)));
                }

            }
            grid.setItems(forecastsList);
        });
    }
}
