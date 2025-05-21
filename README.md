# Spring Boot Telegram Echo Bot

This is a simple Telegram Echo Bot built using Spring Boot (version 3.x). It uses a webhook to receive updates from Telegram and requires Java 17.

## Prerequisites

- Java 17 or higher
- Maven 3.2 or higher
- A Telegram Bot Token (get one from BotFather)
- A publicly accessible base URL where your application will be running (e.g., `https://your-app-domain.com`)

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
    telegram.bot.webhook-path=your_bot_webhook_path # e.g., mybot or /mybot
    ```

    -   `YOUR_BOT_USERNAME`: The username of your Telegram bot (e.g., `MyEchoBot`).
    -   `YOUR_TELEGRAM_BOT_TOKEN`: The token you received from BotFather.
    -   `your_bot_webhook_path`: This is the specific path segment for your bot's webhook (e.g., `mybot` or `/mybot`). The `telegrambots-springboot-webhook-starter` will attempt to register the webhook with Telegram by combining your server's base URL with this path. For example, if your server runs at `https://your-app-domain.com` and you set `telegram.bot.webhook-path=mybot`, the full webhook URL registered will be `https://your-app-domain.com/mybot`.

## Running the Application

1.  **Build the application:**
    ```bash
    mvn clean package
    ```

2.  **Run the application:**
    ```bash
    java -jar target/telegrambot-0.0.1-SNAPSHOT.jar
    ```
    The application will start. The `telegrambots-springboot-webhook-starter` library will automatically attempt to set the webhook with Telegram. For this to succeed:
    - Your application must be running and publicly accessible.
    - The Spring Boot application needs to know its public base URL. This is often configured through server properties (e.g., `server.servlet.context-path` if running behind a reverse proxy, or specific cloud provider configurations). The starter typically derives the base URL from the incoming request when setting the webhook, or you might need to configure `server.forward-headers-strategy=native` if behind a proxy that sets `X-Forwarded-Host/Port/Proto`.

## Setting the Webhook (Manual)

If the automatic webhook setup doesn't work, or if you prefer to set it manually, you can do so by sending a request to the Telegram Bot API.

You'll need:
- `YOUR_TELEGRAM_BOT_TOKEN`: Your bot token.
- `YOUR_FULL_PUBLIC_WEBHOOK_URL`: This is your application's full public URL combined with your `telegram.bot.webhook-path`. For example, `https://your-app-domain.com/your_bot_webhook_path`.

You can make this request using `curl` or any HTTP client:

```bash
curl -F "url=YOUR_FULL_PUBLIC_WEBHOOK_URL" \
     -F "allowed_updates=[\"message\"]" \
     https://api.telegram.org/botYOUR_TELEGRAM_BOT_TOKEN/setWebhook
```

**Example:**

If your bot token is `12345:ABCDEF`, your application is hosted at `https://example.com`, and your `telegram.bot.webhook-path` is `mybot`, then `YOUR_FULL_PUBLIC_WEBHOOK_URL` is `https://example.com/mybot`. The command would be:

```bash
curl -F "url=https://example.com/mybot" \
     -F "allowed_updates=[\"message\"]" \
     https://api.telegram.org/bot12345:ABCDEF/setWebhook
```

You should receive a JSON response like:
```json
{"ok":true,"result":true,"description":"Webhook was set"}
```

## How it Works

-   The bot implements the `org.telegram.telegrambots.webhook.TelegramWebhookBot` interface from the `telegrambots` library (v8.x).
-   The `telegrambots-springboot-webhook-starter` auto-configures the bot, including setting up the webhook endpoint based on the `telegram.bot.webhook-path` property and the server's base address.
-   `EchoBot.java` is annotated with `@Component` and its dependencies (like bot token, username, path) are injected from `application.properties`. It contains the logic to process the `Update` (in the `consumeUpdate` method) and send back an echo message.
-   `BotConfig.java` is minimal as explicit bean creation for `EchoBot` is no longer required due to component scanning.

## Interacting with the Bot

Once the application is running and the webhook is correctly set with Telegram, you can send messages to your bot, and it will echo them back. Ensure your application is accessible from the public internet for Telegram to send updates.
