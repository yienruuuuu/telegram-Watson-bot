package io.github.yienruuuuu.service.application.telegram_bot;

import io.github.yienruuuuu.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eric.Lee Date: 2024/10/16
 */

@Component
@Slf4j
public class DivinationTelegramBot implements LongPollingSingleThreadUpdateConsumer {
    private final TelegramBotClient telegramBotClient;

    public DivinationTelegramBot(TelegramBotClient telegramBotClient) {
        this.telegramBotClient = telegramBotClient;
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            JsonUtils.parseJsonAndPrintLog("收到訊息", update);
            String messageText = update.getMessage().getText();
            if (messageText.equals("/start")) {
                SendMessage message = new SendMessage(String.valueOf(chatId), "歡迎使用 Divination Bot!");
                // 創建 Inline Keyboard
                List<InlineKeyboardButton> rowInline = new ArrayList<>();
                List<InlineKeyboardRow> rows = new ArrayList<>();
                // 創建按鈕
                // 使用建造者模式來創建按鈕
                InlineKeyboardButton button1 = InlineKeyboardButton.builder()
                        .text("按鈕1")
                        .callbackData("button1_callback")
                        .build();
                InlineKeyboardButton button2 = InlineKeyboardButton.builder()
                        .text("按鈕2")
                        .callbackData("button2_callback")
                        .build();
                // 把按鈕加入行
                rowInline.add(button1);
                rowInline.add(button2);
                // 把行加入鍵盤
                rows.add(new InlineKeyboardRow(rowInline));
                // 設置鍵盤的按鈕
                InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup(rows);
                // 將 Inline Keyboard 設置為消息的回覆鍵盤
                message.setReplyMarkup(markupInline);
                telegramBotClient.send(message); // 發送消息
            }
            return;
        }

        if (update.hasCallbackQuery()) {
            handleCallbackQuery(update.getCallbackQuery());
        }
    }

    public void handleCallbackQuery(CallbackQuery callbackQuery) {
        String callbackData = callbackQuery.getData();
        long chatId = callbackQuery.getMessage().getChatId();
        int messageId = callbackQuery.getMessage().getMessageId();

        // 回覆使用者點擊了哪個按鈕
        String text = "";
        if ("button1_callback".equals(callbackData)) {
            text = "你點擊了按鈕1";
        } else if ("button2_callback".equals(callbackData)) {
            text = "你點擊了按鈕2";
        }

        // 移除按鈕
        DeleteMessage deleteMessage = DeleteMessage.builder()
                .chatId(chatId)
                .messageId(messageId)
                .build();
        // 更新訊息的按鈕狀態
        telegramBotClient.send(deleteMessage);

        SendMessage message = new SendMessage(String.valueOf(chatId), text);
        // 這裡是傳送訊息的部分
        telegramBotClient.send(message);
    }
}

