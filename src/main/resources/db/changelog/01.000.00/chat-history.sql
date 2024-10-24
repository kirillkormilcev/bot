--liquibase formatted sql
--changeset kormilcev:weather_bot logicalFilePath:01.000.00/chat-history.sql
CREATE TABLE IF NOT EXISTS chat_history
(
    id UUID not null,
    chat_id VARCHAR(20),
    message VARCHAR(100),
    answer VARCHAR(100),
    date_time BIGINT,
    constraint pk_chat_history PRIMARY KEY (id)
);