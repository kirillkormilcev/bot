package ru.kormilcev.bot.service.impl;

import static lombok.AccessLevel.PRIVATE;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kormilcev.bot.config.WeatherBotProps;
import ru.kormilcev.bot.service.MeteoService;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class MeteoServiceImpl implements MeteoService {

  RestTemplate restTemplate;
  WeatherBotProps props;

  @Override
  public SendMessage getWeather(Update update) {
    Long chatId = update.getMessage().getChatId();
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(chatId);

    String location = update.getMessage().getText();
    String key = props.getKey();

    String geoUrl = "http://api.openweathermap.org/geo/1.0/direct?q=" + location +
        "&limit=1&appid=" + key;

    try {
      String geo = restTemplate.getForObject(geoUrl, String.class);
      if (geo != null && geo.contains("lat")) {
        int beginLat = geo.indexOf("lat");
        int beginLon = geo.indexOf("lon");
        String lat = geo.substring(beginLat + 5, beginLat + 15);
        String lon = geo.substring(beginLon + 5, beginLon + 15);

        String weatherUrl = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat +
            "&lon=" + lon + "&units=metric&appid=" + key;

        String weather = restTemplate.getForObject(weatherUrl, String.class);

        if (weather != null && weather.contains("temp") && weather.contains("feels_like")) {
          int beginTemp = weather.indexOf("temp");
          int endTemp = weather.indexOf(",", beginTemp);
          int beginFeels = weather.indexOf("feels_like");
          int endFeels = weather.indexOf(",", beginFeels);

          String temp = weather.substring(beginTemp + 6, endTemp);
          String feels = weather.substring(beginFeels + 12, endFeels);

          sendMessage.setText("Текущая температура: " + temp + " С, ощущается как: " + feels + " C.");
        }
      } else {
        sendMessage.setText("Не удалось получить данные.");
      }
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
    return sendMessage;
  }
}
