package ru.kormilcev.bot.service.impl;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kormilcev.bot.service.Command;
import ru.kormilcev.bot.util.Answers;

@Component
public class StartCommand implements Command {

  @Override
  public SendMessage execute(Update update) {
    Long chatId = update.getMessage().getChatId();
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(chatId);
    sendMessage.setText(Answers.START.getMessage());
    return sendMessage;
  }
}
