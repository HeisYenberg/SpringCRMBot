package com.heisyenberg.springcrmbot.commands;

import com.heisyenberg.springcrmbot.bot.Keyboards;
import com.heisyenberg.springcrmbot.models.ChatState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Map;

public class AuthenticationCommand implements BotCommand {
    private final Map<Long, ChatState> chatStates;

    public AuthenticationCommand(Map<Long, ChatState> chatStates) {
        this.chatStates = chatStates;
    }

    @Override
    public SendMessage handleUpdate(Long chatId, String updateMessage) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        switch (updateMessage) {
            case "/restart":
                chatStates.put(chatId, ChatState.AUTHENTICATION);
                sendMessage.setText("Welcome to SpringCRMBot! " +
                        "Please login or register to your company account.");
                sendMessage.setReplyMarkup(Keyboards.authorizationKeyboard());
                break;
            case "/login":
                chatStates.put(chatId, ChatState.LOGIN);
                sendMessage.setText("Please enter your company email " +
                        "and password to login.");
                break;
            case "/registration":
                chatStates.put(chatId, ChatState.REGISTRATION);
                sendMessage.setText("Please enter your company name, " +
                        "email, and password to register.");
                break;
        }
        return sendMessage;
    }
}
