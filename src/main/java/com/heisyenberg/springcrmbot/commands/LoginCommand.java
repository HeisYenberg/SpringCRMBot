package com.heisyenberg.springcrmbot.commands;

import com.heisyenberg.springcrmbot.models.ChatState;
import com.heisyenberg.springcrmbot.models.Company;
import com.heisyenberg.springcrmbot.services.CompaniesService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Map;
import java.util.Optional;

public class LoginCommand implements BotCommand {
    private final CompaniesService companiesService;
    private final Map<Long, Company> companies;
    private final Map<Long, ChatState> chatStates;

    public LoginCommand(CompaniesService companiesService,
                        Map<Long, Company> companies,
                        Map<Long, ChatState> chatStates) {
        this.companiesService = companiesService;
        this.companies = companies;
        this.chatStates = chatStates;
    }

    @Override
    public SendMessage handleUpdate(Long chatId, String updateMessage) {
        String[] data = updateMessage.split(" ");
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        if (data.length != 2) {
            sendMessage.setText("Invalid input, enter email and password");
            return sendMessage;
        }
        Optional<Company> company = companiesService.login(data[0], data[1]);
        if (company.isPresent()) {
            companies.put(chatId, company.get());
            chatStates.put(chatId, ChatState.LOGGED_IN);
            sendMessage.setText("You successfully logged in "
                    + company.get().getName() + " company account");
        } else {
            sendMessage.setText("Unable to login");
        }
        return sendMessage;
    }
}
