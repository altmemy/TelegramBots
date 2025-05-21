package com.example.telegrambot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
// Attempting to import from potential sub-packages
import org.telegram.telegrambots.meta.api.objects.chat.Chat; 
import org.telegram.telegrambots.meta.api.objects.message.Message; 
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod; // For casting the result

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class EchoBotTest {

    private EchoBot echoBot;
    private final String botUsername = "testuser";
    private final String botToken = "testtoken";
    private final String botPath = "testpath"; // Added for new constructor

    @BeforeEach
    public void setUp() {
        // Updated constructor call
        echoBot = new EchoBot(botUsername, botToken, botPath); 
    }

    @Test
    // Renamed method from testOnWebhookUpdateReceived_validTextMessage to testConsumeUpdate_validTextMessage
    public void testConsumeUpdate_validTextMessage() { 
        // Mock Update object
        Update update = Mockito.mock(Update.class);
        org.telegram.telegrambots.meta.api.objects.message.Message message = Mockito.mock(org.telegram.telegrambots.meta.api.objects.message.Message.class);
        org.telegram.telegrambots.meta.api.objects.chat.Chat chat = Mockito.mock(org.telegram.telegrambots.meta.api.objects.chat.Chat.class);

        when(update.hasMessage()).thenReturn(true);
        when(update.getMessage()).thenReturn(message);
        when(message.hasText()).thenReturn(true);
        when(message.getText()).thenReturn("Hello Bot");
        when(message.getChatId()).thenReturn(123L);
        // when(message.getChat()).thenReturn(chat); // Not strictly needed if ChatId is directly used
        // when(chat.getId()).thenReturn(123L); // Not strictly needed

        // Renamed method call and cast to BotApiMethod then to SendMessage
        BotApiMethod<?> apiMethod = echoBot.consumeUpdate(update); 
        assertNotNull(apiMethod);
        assertEquals(SendMessage.class, apiMethod.getClass());
        SendMessage response = (SendMessage) apiMethod;

        assertNotNull(response);
        assertEquals("123", response.getChatId());
        assertEquals("Echo: Hello Bot", response.getText());
    }

    @Test
    // Renamed method from testOnWebhookUpdateReceived_noMessage to testConsumeUpdate_noMessage
    public void testConsumeUpdate_noMessage() { 
        Update update = Mockito.mock(Update.class);
        when(update.hasMessage()).thenReturn(false);

        // Renamed method call
        assertNull(echoBot.consumeUpdate(update)); 
    }

    @Test
    // Renamed method from testOnWebhookUpdateReceived_messageNoText to testConsumeUpdate_messageNoText
    public void testConsumeUpdate_messageNoText() { 
        Update update = Mockito.mock(Update.class);
        org.telegram.telegrambots.meta.api.objects.message.Message message = Mockito.mock(org.telegram.telegrambots.meta.api.objects.message.Message.class);

        when(update.hasMessage()).thenReturn(true);
        when(update.getMessage()).thenReturn(message);
        when(message.hasText()).thenReturn(false);

        // Renamed method call
        assertNull(echoBot.consumeUpdate(update)); 
    }
    
    @Test
    public void testGetBotUsername() {
        assertEquals(botUsername, echoBot.getBotUsername());
    }

    @Test
    public void testGetBotToken() {
        assertEquals(botToken, echoBot.getBotToken());
    }

    @Test
    public void testGetBotPath() {
        // Now expects the botPath provided in constructor
        assertEquals(botPath, echoBot.getBotPath()); 
    }
}
