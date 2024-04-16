package com.heisyenberg.springcrmbot.commands;

import com.heisyenberg.springcrmbot.models.ChatState;
import com.heisyenberg.springcrmbot.models.Company;
import com.heisyenberg.springcrmbot.models.Product;
import com.heisyenberg.springcrmbot.services.ProductsService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Map;
import java.util.Optional;

public class GetProductCommand implements BotCommand {
    private final Map<Long, ChatState> chatStates;
    private final Map<Long, Company> companies;
    private final ProductsService productsService;

    public GetProductCommand(Map<Long, ChatState> chatStates,
                             Map<Long, Company> companies,
                             ProductsService productsService) {
        this.chatStates = chatStates;
        this.companies = companies;
        this.productsService = productsService;
    }

    @Override
    public SendMessage handleUpdate(Long chatId, String updateMessage) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        Optional<Product> product = productsService.getProductByVendorCode(
                companies.get(chatId), updateMessage);
        if (product.isPresent()) {
            sendMessage.setText(product.get().toString());
        } else {
            sendMessage.setText("Unable to remove product, check data " +
                    "validity");
        }
        chatStates.put(chatId, ChatState.LOGGED_IN);
        return sendMessage;
    }
}
