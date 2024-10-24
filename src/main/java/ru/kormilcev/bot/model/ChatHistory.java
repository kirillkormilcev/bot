package ru.kormilcev.bot.model;

import static lombok.AccessLevel.PRIVATE;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "chat_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@FieldDefaults(level = PRIVATE)
public class ChatHistory {
  @Id
  @UuidGenerator
  @Column(name = "id", nullable = false)
  UUID id;

  @Column(name = "chat_id", length = 20)
  String chatID;

  @Column(name = "message", length = 100)
  String message;

  @Column(name = "answer", length = 100)
  String answer;

  @Column(name = "date_time")
  Long dateTime;
}
