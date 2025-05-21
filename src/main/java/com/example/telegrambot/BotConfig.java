package com.example.telegrambot;

import org.springframework.context.annotation.Configuration;

// Since EchoBot is now a @Component and its dependencies (@Value properties)
// are injected via its constructor, this explicit configuration class
// might no longer be needed for defining the EchoBot bean.
// The telegrambots-springboot-webhook-starter should auto-configure the bot.
// If specific beans related to Telegram (like a custom SetWebhook) were needed,
// they could be defined here. For now, this class can be empty or removed if
// all configuration is handled by the starter and properties.
@Configuration
public class BotConfig {
    // No explicit bean definitions needed for EchoBot if it's a @Component
    // and the starter handles its registration and webhook setup.
}
