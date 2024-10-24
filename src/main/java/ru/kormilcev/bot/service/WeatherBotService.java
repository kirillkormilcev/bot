package ru.kormilcev.bot.service;

import static lombok.AccessLevel.PRIVATE;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.kormilcev.bot.config.WeatherBotProps;
import ru.kormilcev.bot.handler.CommandsHandler;

@Service
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class WeatherBotService extends TelegramLongPollingBot{

  WeatherBotProps props;
  CommandsHandler commandsHandler;
  MeteoService meteoService;

  @Override
  public String getBotToken() {
    return props.getToken();
  }

  @Override
  public String getBotUsername() {
    return props.getName();
  }

  @Override
  public void onUpdateReceived(Update update) {
    if (update.hasMessage() && update.getMessage().hasText()) {
      String chatId = update.getMessage().getChatId().toString();
      if (update.getMessage().getText().startsWith("/")) {
        sendMessage(commandsHandler.handleCommands(update));
      } else {
        sendMessage(meteoService.getWeather(update));
      }
    }
  }

  private void sendMessage(SendMessage sendMessage) {
    try {
      execute(sendMessage);
    } catch (TelegramApiException e) {
      throw new RuntimeException(e);
    }
  }
}
