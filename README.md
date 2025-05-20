# Spring Boot Telegram Echo Bot

This is a simple Telegram Echo Bot built using Spring Boot. It uses a webhook to receive updates from Telegram.

## Prerequisites

- Java 11 or higher
- Maven 3.2 or higher
- A Telegram Bot Token (get one from BotFather)

## Configuration

1.  **Clone the repository:**
    ```bash
    git clone <repository_url>
    cd telegrambot
    ```

2.  **Update `src/main/resources/application.properties`:**

    Open the `application.properties` file and update the following properties:

    ```properties
    telegram.bot.username=YOUR_BOT_USERNAME
    telegram.bot.token=YOUR_TELEGRAM_BOT_TOKEN
    telegram.bot.webhook-url=YOUR_PUBLIC_WEBHOOK_URL
    ```

    -   `YOUR_BOT_USERNAME`: The username of your Telegram bot (e.g., `MyEchoBot`).
    -   `YOUR_TELEGRAM_BOT_TOKEN`: The token you received from BotFather.
    -   `YOUR_PUBLIC_WEBHOOK_URL`: The public URL where your Spring Boot application will be running and accessible by Telegram. This URL should end with a `/`. For example, if you deploy your application to a service like Heroku or use ngrok for local development, this will be the public URL provided by that service.

## Running the Application

1.  **Build the application:**
    ```bash
    mvn clean package
    ```

2.  **Run the application:**
    ```bash
    java -jar target/telegrambot-0.0.1-SNAPSHOT.jar
    ```
    The application will start, and Spring Boot will automatically try to set the webhook with Telegram using the `telegrambots-spring-boot-starter` library if you have configured `telegram.bot.webhook-url`.

## Setting the Webhook (Manual)

If the automatic webhook setup by the library doesn't work or if you prefer to set it manually, you can do so by sending a request to the Telegram Bot API.

Replace `YOUR_TELEGRAM_BOT_TOKEN` with your bot token and `YOUR_PUBLIC_WEBHOOK_URL` with your application's public URL.

You can make this request using `curl` or any HTTP client:

```bash
curl -F "url=YOUR_PUBLIC_WEBHOOK_URL" \
     -F "allowed_updates=[\"message\"]" \
     https://api.telegram.org/botYOUR_TELEGRAM_BOT_TOKEN/setWebhook
```

**Example:**

If your bot token is `12345:ABCDEF` and your webhook URL is `https://example.com/mybot/`, the command would be:

```bash
curl -F "url=https://example.com/mybot/" \
     -F "allowed_updates=[\"message\"]" \
     https://api.telegram.org/bot12345:ABCDEF/setWebhook
```

You should receive a JSON response like:
```json
{"ok":true,"result":true,"description":"Webhook was set"}
```

## How it Works

-   The bot uses `TelegramWebhookBot` from the `telegrambots` library.
-   `WebhookController` provides a POST endpoint (`/`) that Telegram will send updates to.
-   `EchoBot` contains the logic to process the `Update` and send back an echo message.
-   `BotConfig` configures the `EchoBot` bean with properties from `application.properties`.
-   The `telegrambots-spring-boot-starter` handles the registration of the bot and can set the webhook automatically if the `telegram.bot.webhook-url` property is set.

## Interacting with the Bot

Once the application is running and the webhook is set, you can send messages to your bot on Telegram, and it will echo them back.
