package com.heisyenberg.springcrmbot.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface BotCommand {
    SendMessage handleUpdate(Long chatId, String updateMessage);
}
