package com.heisyenberg.springcrmbot.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.StringJoiner;

public class CSVWriter {
    public static SendDocument getDocument(Long chatId, String name,
                                           List<?> items) throws IOException {
        StringJoiner joiner = new StringJoiner("\n");
        for (Object item : items) {
            joiner.add(item.toString());
        }
        File file = new File(chatId + "_" + name + ".csv");
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(joiner.toString());
        fileWriter.close();
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(chatId);
        sendDocument.setDocument(new InputFile(file));
        file.delete();
        return sendDocument;
    }
}
