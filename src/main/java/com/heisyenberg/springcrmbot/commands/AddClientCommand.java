package com.heisyenberg.springcrmbot.commands;

import com.heisyenberg.springcrmbot.models.ChatState;
import com.heisyenberg.springcrmbot.models.Client;
import com.heisyenberg.springcrmbot.models.Company;
import com.heisyenberg.springcrmbot.services.ClientsService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Map;

public class AddClientCommand implements BotCommand {
    private final Map<Long, ChatState> chatStates;
    private final Map<Long, Company> companies;
    private final ClientsService clientsService;

    public AddClientCommand(Map<Long, ChatState> chatStates,
                            Map<Long, Company> companies,
                            ClientsService clientsService) {
        this.chatStates = chatStates;
        this.companies = companies;
        this.clientsService = clientsService;
    }

    @Override
    public SendMessage handleUpdate(Long chatId, String updateMessage) {
        String[] data = updateMessage.split("[\\s\\n]+");
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        if (data.length != 4) {
            sendMessage.setText("Invalid input, enter first name, last name, " +
                    "email and phone number");
            return sendMessage;
        }
        Client client = new Client(null, data[0], data[1], data[2], data[3],
                companies.get(chatId));
        if (clientsService.addClient(client)) {
            sendMessage.setText("Client was successfully added");
            chatStates.put(chatId, ChatState.LOGGED_IN);
        } else {
            sendMessage.setText("Unable to add client, check data validity");
        }
        return sendMessage;
    }
}
