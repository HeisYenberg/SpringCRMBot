package com.heisyenberg.springcrmbot.commands;

import com.heisyenberg.springcrmbot.bot.Keyboards;
import com.heisyenberg.springcrmbot.models.ChatState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Map;

public class StartCommand implements BotCommand {
    private final Map<Long, ChatState> chatStates;

    public StartCommand(Map<Long, ChatState> chatStates) {
        this.chatStates = chatStates;
    }

    @Override
    public SendMessage handleUpdate(Long chatId, String updateMessage) {
        String message = "Welcome to SpringCRMBot! " +
                "Please login or register to your company account.";
        SendMessage sendMessage = new SendMessage(chatId.toString(), message);
        sendMessage.setReplyMarkup(Keyboards.authorizationKeyboard());
        chatStates.put(chatId, ChatState.AUTHENTICATION);
        return sendMessage;
    }
}
