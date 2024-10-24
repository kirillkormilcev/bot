package ru.kormilcev.bot.service.impl;

import static lombok.AccessLevel.PRIVATE;

import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kormilcev.bot.model.ChatHistory;
import ru.kormilcev.bot.repository.ChatHistoryRepository;
import ru.kormilcev.bot.service.ChatHistoryService;

@Service
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatHistoryServiceImpl implements ChatHistoryService {

  ChatHistoryRepository chatHistoryRepository;

  @Override
  @Transactional
  public void save(Update update, SendMessage sendMessage) {
    ChatHistory chatHistory = ChatHistory.builder()
        .chatID(sendMessage.getChatId())
        .message(update.getMessage().getText())
        .answer(sendMessage.getText())
        .dateTime(Instant.now().getEpochSecond())
        .build();
    chatHistoryRepository.save(chatHistory);
  }
}
