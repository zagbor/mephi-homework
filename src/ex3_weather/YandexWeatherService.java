package ex3_weather;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

public class YandexWeatherService {

    private static final String API_URL = "https://api.weather.yandex.ru/v2/forecast";
    private static final String API_KEY = "ceb73baa-e941-4277-88e2-3670ff3046d0"; // вставьте ваш ключ сюда

    // Метод для получения и вывода всей информации о погоде по координатам
    public static void getWeatherData(double lat, double lon) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "?lat=" + lat + "&lon=" + lon))
                .header("X-Yandex-Weather-Key", API_KEY)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();

        // Вывод полного JSON ответа
        System.out.println("Полный ответ от сервера:");
        System.out.println(responseBody);

        // Парсинг JSON и вывод температуры
        JSONObject jsonObject = new JSONObject(responseBody);
        int temperature = jsonObject.getJSONObject("fact").getInt("temp");
        System.out.println("Текущая температура: " + temperature + "°C");
    }

    // Метод для вычисления средней температуры за определенный период
    public static double getAverageTemperature(double lat, double lon, int limit) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + "?lat=" + lat + "&lon=" + lon + "&limit=" + limit))
                .header("X-Yandex-Weather-Key", API_KEY)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject jsonObject = new JSONObject(response.body());
        JSONArray forecasts = jsonObject.getJSONArray("forecasts");

        double sumTemperature = 0;
        int count = 0;

        // Проход по всем дням и суммирование температур
        for (int i = 0; i < forecasts.length(); i++) {
            JSONObject day = forecasts.getJSONObject(i);
            int temp = day.getJSONObject("parts").getJSONObject("day").getInt("temp_avg");
            sumTemperature += temp;
            count++;
        }

        return count > 0 ? sumTemperature / count : 0;
    }

    public static void main(String[] args) {
        try {
            double lat = 29.55805;
            double lon = 34.57149;
            int limit = 3;

            getWeatherData(lat, lon);

            double averageTemp = getAverageTemperature(lat, lon, limit);
            System.out.println("Средняя температура за " + limit + " дня: " + averageTemp + "°C");

        } catch (IOException | InterruptedException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}