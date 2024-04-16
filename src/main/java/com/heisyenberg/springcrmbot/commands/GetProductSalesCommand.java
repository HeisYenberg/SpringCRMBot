package com.heisyenberg.springcrmbot.commands;

import com.heisyenberg.springcrmbot.bot.CSVWriter;
import com.heisyenberg.springcrmbot.bot.SpringCRMBot;
import com.heisyenberg.springcrmbot.models.ChatState;
import com.heisyenberg.springcrmbot.models.Company;
import com.heisyenberg.springcrmbot.models.Sale;
import com.heisyenberg.springcrmbot.services.SalesService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GetProductSalesCommand implements BotCommand {
    private final Map<Long, ChatState> chatStates;
    private final Map<Long, Company> companies;
    private final SalesService salesService;
    private final SpringCRMBot bot;

    public GetProductSalesCommand(Map<Long, ChatState> chatStates,
                                  Map<Long, Company> companies,
                                  SalesService salesService, SpringCRMBot bot) {
        this.chatStates = chatStates;
        this.companies = companies;
        this.salesService = salesService;
        this.bot = bot;
    }

    @Override
    public SendMessage handleUpdate(Long chatId, String updateMessage) {
        List<Sale> sales = salesService
                .getProductSales(companies.get(chatId), updateMessage);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        if (sales.isEmpty()) {
            sendMessage.setText("No product sales found");
            return sendMessage;
        }
        try {
            bot.execute(CSVWriter.getDocument(chatId, "productSales", sales));
            sendMessage.setText("Successfully sent product sales data");
        } catch (IOException | TelegramApiException e) {
            sendMessage.setText("Unable to write product sales data");
        }
        chatStates.put(chatId, ChatState.LOGGED_IN);
        return sendMessage;
    }
}
