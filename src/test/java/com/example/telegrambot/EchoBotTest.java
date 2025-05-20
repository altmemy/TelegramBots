package com.example.telegrambot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class EchoBotTest {

    private EchoBot echoBot;
    private final String botUsername = "testuser";
    private final String botToken = "testtoken";

    @BeforeEach
    public void setUp() {
        echoBot = new EchoBot(botUsername, botToken);
    }

    @Test
    public void testOnWebhookUpdateReceived_validTextMessage() {
        // Mock Update object
        Update update = Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);
        Chat chat = Mockito.mock(Chat.class);

        when(update.hasMessage()).thenReturn(true);
        when(update.getMessage()).thenReturn(message);
        when(message.hasText()).thenReturn(true);
        when(message.getText()).thenReturn("Hello Bot");
        when(message.getChatId()).thenReturn(123L);
        when(message.getChat()).thenReturn(chat); // Although not directly used by current EchoBot, good for completeness
        when(chat.getId()).thenReturn(123L);


        SendMessage response = (SendMessage) echoBot.onWebhookUpdateReceived(update);

        assertNotNull(response);
        assertEquals("123", response.getChatId());
        assertEquals("Echo: Hello Bot", response.getText());
    }

    @Test
    public void testOnWebhookUpdateReceived_noMessage() {
        Update update = Mockito.mock(Update.class);
        when(update.hasMessage()).thenReturn(false);

        assertNull(echoBot.onWebhookUpdateReceived(update));
    }

    @Test
    public void testOnWebhookUpdateReceived_messageNoText() {
        Update update = Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);

        when(update.hasMessage()).thenReturn(true);
        when(update.getMessage()).thenReturn(message);
        when(message.hasText()).thenReturn(false);

        assertNull(echoBot.onWebhookUpdateReceived(update));
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
        assertNull(echoBot.getBotPath());
    }
}
