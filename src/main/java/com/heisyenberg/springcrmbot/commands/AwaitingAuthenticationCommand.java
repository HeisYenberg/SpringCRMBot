package com.heisyenberg.springcrmbot.commands;

import com.heisyenberg.springcrmbot.models.ChatState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Map;

public class AwaitingAuthenticationCommand implements BotCommand {
    private final Map<Long, ChatState> chatStates;

    public AwaitingAuthenticationCommand(Map<Long, ChatState> chatStates) {
        this.chatStates = chatStates;
    }

    @Override
    public SendMessage handleUpdate(Long chatId, String updateMessage) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        if (updateMessage.equals("/registration")) {
            chatStates.put(chatId, ChatState.AWAITING_REGISTRATION);
            sendMessage.setText("Please enter your company name, " +
                    "email, and password to register.");
        } else if (updateMessage.equals("/login")) {
            chatStates.put(chatId, ChatState.AWAITING_LOGIN);
            sendMessage.setText("Please enter your company email " +
                    "and password to login.");
        }
        return sendMessage;
    }
}
