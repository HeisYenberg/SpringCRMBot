package com.heisyenberg.springcrmbot.commands;

import com.heisyenberg.springcrmbot.models.ChatState;
import com.heisyenberg.springcrmbot.models.Company;
import com.heisyenberg.springcrmbot.models.Product;
import com.heisyenberg.springcrmbot.services.ProductsService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Map;
import java.util.StringJoiner;

public class AddProductCommand implements BotCommand {
    private final Map<Long, ChatState> chatStates;
    private final Map<Long, Company> companies;
    private final ProductsService productsService;

    public AddProductCommand(Map<Long, ChatState> chatStates,
                             Map<Long, Company> companies,
                             ProductsService productsService) {
        this.chatStates = chatStates;
        this.companies = companies;
        this.productsService = productsService;
    }

    @Override
    public SendMessage handleUpdate(Long chatId, String updateMessage) {
        String[] data = updateMessage.split("[\\s\\n]+");
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        if (data.length < 4) {
            sendMessage.setText("Invalid input, enter vendor code, " +
                    "product name, price and description");
            return sendMessage;
        }
        StringJoiner joiner = new StringJoiner(" ");
        for (int i = 3; i < data.length; ++i) {
            joiner.add(data[i]);
        }
        sendMessage.setText("Unable to add product, check data validity");
        try {
            Product product = new Product(null, data[0], data[1],
                    Double.valueOf(data[2]), joiner.toString(),
                    companies.get(chatId));
            if (productsService.addProduct(product)) {
                sendMessage.setText("Product was successfully added");
                chatStates.put(chatId, ChatState.LOGGED_IN);
            }
        } catch (NumberFormatException ignored) {
        }
        return sendMessage;
    }
}
