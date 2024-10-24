package ru.kormilcev.bot.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.kormilcev.bot.model.ChatHistory;

public interface ChatHistoryRepository extends JpaRepository<ChatHistory, UUID> {


}
