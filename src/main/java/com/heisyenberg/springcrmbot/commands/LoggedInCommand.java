package com.heisyenberg.springcrmbot.commands;

import com.heisyenberg.springcrmbot.bot.CSVWriter;
import com.heisyenberg.springcrmbot.bot.SpringCRMBot;
import com.heisyenberg.springcrmbot.models.*;
import com.heisyenberg.springcrmbot.services.ClientsService;
import com.heisyenberg.springcrmbot.services.ProductsService;
import com.heisyenberg.springcrmbot.services.SalesService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class LoggedInCommand implements BotCommand {
    private final Map<Long, ChatState> chatStates;
    private final Map<Long, Company> companies;
    private final ClientsService clientsService;
    private final ProductsService productsService;
    private final SalesService salesService;
    private final SpringCRMBot bot;

    public LoggedInCommand(Map<Long, ChatState> chatStates,
                           Map<Long, Company> companies,
                           ClientsService clientsService,
                           ProductsService productsService,
                           SalesService salesService, SpringCRMBot bot) {
        this.chatStates = chatStates;
        this.companies = companies;
        this.clientsService = clientsService;
        this.productsService = productsService;
        this.salesService = salesService;
        this.bot = bot;
    }

    @Override
    public SendMessage handleUpdate(Long chatId, String updateMessage) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        switch (updateMessage) {
            case "/addClient":
                chatStates.put(chatId, ChatState.ADD_CLIENT);
                sendMessage.setText("Please enter clients first name, " +
                        "last name, email and phone number");
                break;
            case "/getClients":
                sendMessage.setText(getClients(chatId));
                break;
            case "/getClient":
                chatStates.put(chatId, ChatState.GET_CLIENT);
                sendMessage.setText(
                        "Please enter clients email to get client data");
                break;
            case "/addProduct":
                chatStates.put(chatId, ChatState.ADD_PRODUCT);
                sendMessage.setText("Please enter vendor code, product name," +
                        " price and description");
                break;
            case "/getProducts":
                sendMessage.setText(getProducts(chatId));
                break;
            case "/getProduct":
                chatStates.put(chatId, ChatState.GET_PRODUCT);
                sendMessage.setText(
                        "Please enter vendor code to get product data");
                break;
            case "/addSale":
                chatStates.put(chatId, ChatState.ADD_SALE);
                sendMessage.setText("Please enter clients email, " +
                        "product vendor code and quantity");
                break;
            case "/getSales":
                sendMessage.setText(getSales(chatId));
                break;
            case "/getClientSales":
                chatStates.put(chatId, ChatState.GET_CLIENT_SALES);
                sendMessage.setText("Please enter clients email");
                break;
            case "/getProductSales":
                chatStates.put(chatId, ChatState.GET_PRODUCT_SALES);
                sendMessage.setText("Please enter products vendor code");
                break;
        }
        return sendMessage;
    }

    private String getClients(Long chatId) {
        List<Client> clients = clientsService
                .getAllByCompany(companies.get(chatId));
        return getItems(chatId, "clients", clients);
    }

    private String getProducts(Long chatId) {
        List<Product> products = productsService
                .getProducts(companies.get(chatId));
        return getItems(chatId, "products", products);
    }

    private String getSales(Long chatId) {
        List<Sale> products = salesService
                .getSales(companies.get(chatId));
        return getItems(chatId, "sales", products);
    }

    private String getItems(Long chatId, String name, List<?> items) {
        if (items.isEmpty()) {
            return "No " + name + " found";
        }
        try {
            bot.execute(CSVWriter.getDocument(chatId, "clients", items));
        } catch (IOException | TelegramApiException e) {
            return "Unable to write " + name + " data";
        }
        return "Successfully sent " + name + " data";
    }
}
