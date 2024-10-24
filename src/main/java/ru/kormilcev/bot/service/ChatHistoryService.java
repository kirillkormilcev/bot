package ru.kormilcev.bot.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface ChatHistoryService {
  void save(Update update, SendMessage sendMessage);
}
