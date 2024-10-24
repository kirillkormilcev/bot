package ru.kormilcev.bot.util;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public enum Answers {
  UNKNOWN_COMMAND("Не известная команда."),
  START("""
  Приветствую! Это бот погоды!
  Введите вашу локацию, чтобы получить текущую температуру.
  """),
  LOCATION("Введите название вашего местоположения.");
  String message;
}
