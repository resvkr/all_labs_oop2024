import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {


        Map<String, City> cityMap = City.parseFile("C:\\Users\\User\\Desktop\\strefy.csv");

        for (Map.Entry<String, City> entry : cityMap.entrySet()) {
            String cityName = entry.getKey();
            City city = entry.getValue();
            System.out.println("Місто: " + cityName);
            System.out.println("Часовий пояс: " + city.getTimezone());
            System.out.println("Широта: " + city.getLatitude());
            System.out.println("Довгота: " + city.getLongitude());
            System.out.println();
        }


        City warsaw = new City("Warszawa", "+2", "52.2297 N", "21.0122 E");
        City kyiv = new City("Kijów", "+3", "50.4501 N", "30.5234 E");

        // Создаем объекты DigitalClock для 24-часового и 12-часового форматов
        DigitalClock clock24Hour = new DigitalClock(DigitalClock.ClockMode.TWENTY_FOUR_HOUR, warsaw);
        System.out.println(clock24Hour.toString());
        clock24Hour.setCity(kyiv);

        System.out.println(clock24Hour.toString());


        List<City> cities = Arrays.asList(warsaw, kyiv);
        AnalogClock analogClock = new AnalogClock(warsaw);
        City.generateAnalogClockSvg(cities, analogClock);
    }
}
