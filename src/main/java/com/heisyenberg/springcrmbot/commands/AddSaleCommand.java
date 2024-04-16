package com.heisyenberg.springcrmbot.commands;

import com.heisyenberg.springcrmbot.models.ChatState;
import com.heisyenberg.springcrmbot.models.Company;
import com.heisyenberg.springcrmbot.services.SalesService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Map;

public class AddSaleCommand implements BotCommand {
    private final Map<Long, ChatState> chatStates;
    private final Map<Long, Company> companies;
    private final SalesService salesService;

    public AddSaleCommand(Map<Long, ChatState> chatStates,
                          Map<Long, Company> companies,
                          SalesService salesService) {
        this.chatStates = chatStates;
        this.companies = companies;
        this.salesService = salesService;
    }

    @Override
    public SendMessage handleUpdate(Long chatId, String updateMessage) {
        String[] data = updateMessage.split("[\\s\\n]+");
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        if (data.length != 3) {
            sendMessage.setText("Invalid input, enter clients email, " +
                    "product vendor code and quantity");
            return sendMessage;
        }
        if (salesService.addSale(data[0], data[1], data[2],
                companies.get(chatId))) {
            sendMessage.setText("Sale was successfully added");
            chatStates.put(chatId, ChatState.LOGGED_IN);
        } else {
            sendMessage.setText("Unable to add sale, check data validity");
        }
        return sendMessage;
    }
}
