package com.heisyenberg.springcrmbot.commands;

import com.heisyenberg.springcrmbot.models.ChatState;
import com.heisyenberg.springcrmbot.models.Client;
import com.heisyenberg.springcrmbot.models.Company;
import com.heisyenberg.springcrmbot.services.ClientsService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Map;
import java.util.Optional;

public class GetClientCommand implements BotCommand {
    private final Map<Long, ChatState> chatStates;
    private final Map<Long, Company> companies;
    private final ClientsService clientsService;

    public GetClientCommand(Map<Long, ChatState> chatStates,
                            Map<Long, Company> companies,
                            ClientsService clientsService) {
        this.chatStates = chatStates;
        this.companies = companies;
        this.clientsService = clientsService;
    }

    @Override
    public SendMessage handleUpdate(Long chatId, String updateMessage) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        Optional<Client> client = clientsService.getClientByEmail(
                companies.get(chatId), updateMessage);
        if (client.isPresent()) {
            sendMessage.setText(client.get().toString());
        } else {
            sendMessage.setText("Unable to find client, check data validity");
        }
        chatStates.put(chatId, ChatState.LOGGED_IN);
        return sendMessage;
    }
}
