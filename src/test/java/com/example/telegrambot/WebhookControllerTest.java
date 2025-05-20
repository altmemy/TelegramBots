package com.example.telegrambot;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class WebhookControllerTest {

    @Mock
    private EchoBot echoBot;

    @InjectMocks
    private WebhookController webhookController;

    @Test
    public void testOnUpdateReceived() {
        Update mockUpdate = mock(Update.class);
        SendMessage mockResponse = new SendMessage(); // Or mock(SendMessage.class) if more interaction is needed

        // Configure the mock EchoBot to return a specific response when onWebhookUpdateReceived is called
        when(echoBot.onWebhookUpdateReceived(mockUpdate)).thenReturn(mockResponse);

        // Call the controller method
        SendMessage actualResponse = (SendMessage) webhookController.onUpdateReceived(mockUpdate);

        // Verify that the echoBot's method was called exactly once with the mockUpdate
        verify(echoBot, times(1)).onWebhookUpdateReceived(mockUpdate);

        // Assert that the response from the controller is the same as the response from the bot
        assertSame(mockResponse, actualResponse, "The controller should return the response from the bot.");
    }

    @Test
    public void testOnUpdateReceived_botReturnsNull() {
        Update mockUpdate = mock(Update.class);

        // Configure the mock EchoBot to return null
        when(echoBot.onWebhookUpdateReceived(mockUpdate)).thenReturn(null);

        // Call the controller method
        SendMessage actualResponse = (SendMessage) webhookController.onUpdateReceived(mockUpdate);

        // Verify that the echoBot's method was called
        verify(echoBot, times(1)).onWebhookUpdateReceived(mockUpdate);

        // Assert that the response is null
        assertNull(actualResponse, "The controller should return null if the bot returns null.");
    }
}
