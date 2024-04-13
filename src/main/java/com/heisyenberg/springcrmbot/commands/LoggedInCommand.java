package com.heisyenberg.springcrmbot.commands;

import com.heisyenberg.springcrmbot.models.ChatState;
import com.heisyenberg.springcrmbot.models.Client;
import com.heisyenberg.springcrmbot.models.Company;
import com.heisyenberg.springcrmbot.models.Product;
import com.heisyenberg.springcrmbot.services.ClientsService;
import com.heisyenberg.springcrmbot.services.ProductsService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class LoggedInCommand implements BotCommand {
    private final Map<Long, ChatState> chatStates;
    private final Map<Long, Company> companies;
    private final ClientsService clientsService;
    private final ProductsService productsService;

    public LoggedInCommand(Map<Long, ChatState> chatStates,
                           Map<Long, Company> companies,
                           ClientsService clientsService,
                           ProductsService productsService) {
        this.chatStates = chatStates;
        this.companies = companies;
        this.clientsService = clientsService;
        this.productsService = productsService;
    }

    @Override
    public SendMessage handleUpdate(Long chatId, String updateMessage) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        switch (updateMessage) {
            case "/addClient":
                chatStates.put(chatId, ChatState.AWAITING_CLIENT_DATA);
                sendMessage.setText(
                        "Please enter clients first name, last name," +
                                "email and phone number");
                break;
            case "/getClients":
                sendMessage.setText(getClients(chatId));
                break;
            case "/addProduct":
                chatStates.put(chatId, ChatState.AWAITING_PRODUCT_DATA);
                sendMessage.setText(
                        "Please enter product name and description");
                break;
            case "/getProducts":
                sendMessage.setText(getProducts(chatId));
                break;
            case "/addSale":
                chatStates.put(chatId, ChatState.AWAITING_SALE_DATA);
                sendMessage.setText(
                        "Please enter clients first name, last name," +
                                "email and phone number");
                break;
        }
        return sendMessage;
    }

    private String getClients(Long chatId) {
        List<Client> clients = clientsService.getAllByCompany(
                companies.get(chatId));
        if (clients.isEmpty()) {
            return "No client found";
        }
        StringJoiner joiner = new StringJoiner("\n");
        for (Client client : clients) {
            joiner.add(client.toString());
        }
        return joiner.toString();
    }

    private String getProducts(Long chatId) {
        List<Product> products = productsService.getProducts(
                companies.get(chatId));
        if (products.isEmpty()) {
            return "No products found";
        }
        StringJoiner joiner = new StringJoiner("\n");
        for (Product product : products) {
            joiner.add(product.toString());
        }
        return joiner.toString();
    }
}
