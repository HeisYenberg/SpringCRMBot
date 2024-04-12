package com.heisyenberg.springcrmbot.bot;

import com.heisyenberg.springcrmbot.commands.*;
import com.heisyenberg.springcrmbot.models.ChatState;
import com.heisyenberg.springcrmbot.models.Company;
import com.heisyenberg.springcrmbot.services.CompaniesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SpringCRMBot extends TelegramLongPollingBot {
    private final String botName;
    private final Map<Long, ChatState> chatStates;
    private final Map<Long, Company> companies;
    private final CompaniesService companiesService;
    private final List<BotCommand> botCommands;

    @Autowired
    SpringCRMBot(@Value("${bot.token}") String botToken,
                 @Value("${bot.name}") String botName,
                 CompaniesService companiesService) {
        super(botToken);
        this.botName = botName;
        chatStates = new HashMap<>();
        companies = new HashMap<>();
        this.companiesService = companiesService;
        this.botCommands = getBotCommands();
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }
        String updateMessage = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        if (updateMessage.equals("/start") || !chatStates.containsKey(chatId)) {
            chatStates.put(chatId, ChatState.START);
        }
        SendMessage sendMessage =
                botCommands.get(chatStates.get(chatId).ordinal())
                        .handleUpdate(chatId, updateMessage);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            System.out.println(e.getMessage());
        }
    }

    private List<BotCommand> getBotCommands() {
        List<BotCommand> botCommands = new ArrayList<>();
        botCommands.add(new StartCommand(chatStates));
        botCommands.add(new AwaitingAuthenticationCommand(chatStates));
        botCommands.add(new LoginCommand(
                companiesService, companies, chatStates));
        botCommands.add(new RegistrationCommand(
                companiesService, companies, chatStates));
        return botCommands;
    }
}
