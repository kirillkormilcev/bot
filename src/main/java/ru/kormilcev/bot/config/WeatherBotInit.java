package ru.kormilcev.bot.config;

import static lombok.AccessLevel.PRIVATE;

import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.kormilcev.bot.service.WeatherBotService;

@Component
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class WeatherBotInit {

  WeatherBotService bot;

  @Autowired
  public WeatherBotInit(WeatherBotService bot) {
    this.bot = bot;
  }

  @EventListener({ContextRefreshedEvent.class})
  public void init() throws TelegramApiException {
    TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
    try {
      telegramBotsApi.registerBot(bot);
    } catch (TelegramApiException e) {
      throw new RuntimeException(e);
    }
  }
}
