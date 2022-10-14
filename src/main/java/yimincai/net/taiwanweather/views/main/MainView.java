package yimincai.net.taiwanweather.views.main;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import yimincai.net.taiwanweather.Forecast;
import yimincai.net.taiwanweather.service.CityService;
import yimincai.net.taiwanweather.service.WeatherService;

import java.util.List;

@PageTitle("Main")
@Route(value = "")
public class MainView extends HorizontalLayout {

    @Autowired
    private WeatherService weatherService;
    private Select<String> select;

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

        select.addValueChangeListener( e -> {
            JSONObject forecast = weatherService.getWeatherForecast(select.getValue());
            List<Forecast> newFakeForecast = List.of(new Forecast("fakeData", "fakeData", "fakeData", "fakeData", "fakeData", "fakeData"));
            grid.setItems(newFakeForecast);
            Notification.show("Data: " + forecast.toString());
        });




    }

}
