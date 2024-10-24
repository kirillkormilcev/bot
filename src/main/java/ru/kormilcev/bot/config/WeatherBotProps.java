package ru.kormilcev.bot.config;

import static lombok.AccessLevel.PRIVATE;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "weather-bot")
@Getter
@Setter
@FieldDefaults(level = PRIVATE)
@PropertySource("classpath:application.yaml")
public class WeatherBotProps {
  String token;
  String name;
  String key;
}
