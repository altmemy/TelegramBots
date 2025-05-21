package com.example.telegrambot;

import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod; // Corrected import
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.webhook.TelegramWebhookBot; // Corrected import - it's an interface

// Add @Component so Spring Boot auto-detects it. The starter should pick it up.
@org.springframework.stereotype.Component
public class EchoBot implements TelegramWebhookBot {

    private final String botUsername;
    private final String botToken;
    private final String botPath;

    // Constructor injection for properties
    public EchoBot(@Value("${telegram.bot.username}") String botUsername,
                   @Value("${telegram.bot.token}") String botToken,
                   @Value("${telegram.bot.webhook-path}") String botPath) { // Using webhook-path from properties
        this.botUsername = botUsername;
        this.botToken = botToken;
        this.botPath = botPath;
    }

    // Methods required by TelegramWebhookBot interface
    @Override
    public String getBotPath() {
        return botPath;
    }

    @Override
    public BotApiMethod<?> consumeUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            // Using constructor SendMessage(String chatId, String text)
            SendMessage response = new SendMessage(String.valueOf(chatId), "Echo: " + messageText);
            return response;
        }
        return null;
    }
    
    // These methods are not part of the TelegramWebhookBot interface, but commonly needed.
    // The starter might still expect these to be present on the bot bean.
    public String getBotUsername() {
        return botUsername;
    }

    public String getBotToken() {
        return botToken;
    }

    // These runDeleteWebhook and runSetWebhook methods are part of the interface
    // For now, they do nothing, assuming the starter handles webhook setup/deletion if configured.
    @Override
    public void runDeleteWebhook() {
        // Logic to delete webhook if needed, or leave to starter
        System.out.println("runDeleteWebhook called");
    }

    @Override
    public void runSetWebhook() {
        // Logic to set webhook if needed, or leave to starter
        System.out.println("runSetWebhook called, botPath: " + botPath);
    }
}
