package com.heisyenberg.springcrmbot.commands;

import com.heisyenberg.springcrmbot.models.ChatState;
import com.heisyenberg.springcrmbot.models.Company;
import com.heisyenberg.springcrmbot.models.Product;
import com.heisyenberg.springcrmbot.services.ProductsService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Map;

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
        if (data.length < 2) {
            sendMessage.setText(
                    "Invalid input, enter product name and description");
            return sendMessage;
        }
        Product product = new Product(null, data[0],
                updateMessage.substring(data[0].length()).trim(),
                companies.get(chatId));
        if (productsService.addProduct(product)) {
            sendMessage.setText("Product was successfully added");
            chatStates.put(chatId, ChatState.LOGGED_IN);
        } else {
            sendMessage.setText("Unable to add product, check data validity");
        }
        return sendMessage;
    }
}
