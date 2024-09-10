import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class City {
    private String name;
    private String timezone;
    private String latitude;
    private String longitude;

    public City(String name, String timezone, String latitude, String longitude) {
        this.name = name;
        this.timezone = timezone;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    private static City parseLine(String line) {
        String[] parts = line.split(",");
        String name = parts[0].trim();
        String timezone = parts[1].trim();
        String latitude = parts[2].trim();
        String longitude = parts[3].trim();

        return new City(name, timezone, latitude, longitude);
    }


    public static Map<String, City> parseFile(String filepath) {
        Map<String, City> cityMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {

            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                City city = parseLine(line);
                String name = city.name;

                cityMap.put(name, city);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return cityMap;

    }

    public String getName() {
        return name;
    }

    public String getTimezone() {
        return timezone;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public LocalTime localMeanTime(LocalTime currentTime) {
        int timezoneOffSet = Integer.parseInt(timezone);

        // Отримання значення довготи
        // Видалення напрямку (E/W)
        String lon = longitude.split(" ")[0];
        double longitudeDegrees = Double.parseDouble(lon);

        // Розрахунок географічного зміщення (1 год = 15°)
        int geographicOffset = (int) (longitudeDegrees / 15);

        LocalTime localMeanTime = currentTime.plusHours(timezoneOffSet);
        return localMeanTime;

    }
    // Статичний метод-компаратор для сортування міст за найгіршим співвідношенням часового поясу

    public static Comparator<City> worstTimezoneFit() {
        return (city1, city2) -> {
            // Обчислення абсолютної різниці між місцевим часом і часовим поясом для кожного міста
            LocalTime now = LocalTime.now();
            double diference1 = Math.abs(city1.localMeanTime(now).toSecondOfDay() - now.toSecondOfDay());
            double diference2 = Math.abs(city2.localMeanTime(now).toSecondOfDay() - now.toSecondOfDay());

            // Порівняння цих різниць
            return Double.compare(diference2, diference1);
        };

    }

    public static void generateAnalogClockSvg(List<City> cities, AnalogClock clock) {
        // Створюємо каталог для збереження SVG-файлів
        String directoryName = clock.toString();
        File directory = new File(directoryName);

        if (!directory.exists()) {
            directory.mkdirs(); // Створюємо каталог для збереження SVG-файлів
        }
// Проходимося по всіх містах у списку
        for (City city : cities) {
            // Оновлюємо об'єкт AnalogClock для кожного міста
            clock.setCity(city);
            // Визначаємо шлях до файлу SVG для поточного міста
            String filepath = directoryName + File.separator + city.getName() + ".svg";
// Генеруємо SVG-код і зберігаємо його у файл
            clock.toSvg(filepath);
        }
    }

}
