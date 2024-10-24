package ru.kormilcev.bot.handler;

import static lombok.AccessLevel.PRIVATE;

import java.util.Map;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kormilcev.bot.service.Command;
import ru.kormilcev.bot.service.impl.StartCommand;
import ru.kormilcev.bot.util.Answers;

@Component
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CommandsHandler {
  Map<String, Command> commands;

  public CommandsHandler(StartCommand startCommand) {
    this.commands = Map.of(
        "/start", startCommand);
  }

  public SendMessage handleCommands(Update update) {
    String messageText = update.getMessage().getText();
    String commandText = messageText.split(" ")[0];
    long chatId = update.getMessage().getChatId();

    Command command = commands.get(commandText);

    if (command != null) {
      return command.execute(update);
    } else {
      return new SendMessage(String.valueOf(chatId), Answers.UNKNOWN_COMMAND.getMessage());
    }
  }
}
