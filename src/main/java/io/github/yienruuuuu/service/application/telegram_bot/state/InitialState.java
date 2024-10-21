package io.github.yienruuuuu.service.application.telegram_bot.state;

import io.github.yienruuuuu.bean.entity.Bot;
import io.github.yienruuuuu.bean.entity.Language;
import io.github.yienruuuuu.bean.enums.LanguageType;
import io.github.yienruuuuu.service.application.telegram_bot.TelegramBotClient;
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
 * 初始狀態
 *
 * @author Eric.Lee
 * Date: 2024/10/21
 */
@Component
public class InitialState implements BotState {
    private final TelegramBotClient telegramBotClient;

    public InitialState(TelegramBotClient telegramBotClient) {
        this.telegramBotClient = telegramBotClient;
    }


    @Override
    public void handleMessage(LongPollingSingleThreadUpdateConsumer bot, Update update, Bot botEntity) {
        if (update.getMessage().getText().equals("/start")) {
            sendMessage(update, botEntity);
        }
    }

    @Override
    public void handleCallbackQuery(LongPollingSingleThreadUpdateConsumer bot, Update update, Bot botEntity) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        String callbackData = callbackQuery.getData();
        long chatId = callbackQuery.getMessage().getChatId();
        int messageId = callbackQuery.getMessage().getMessageId();

        // 初始化回覆訊息
        String text = switch (callbackData) {
            case "lang_ZH_TW" -> "你選擇了繁體中文";
            case "lang_EN" -> "你選擇了英文";
            default -> "你點擊了未知按鈕";
        };

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


    /**
     * 招呼語與傳送語系選項
     */
    private void sendMessage(Update update, Bot bot) {
        long chatId = update.getMessage().getChatId();
        SendMessage message = new SendMessage(String.valueOf(chatId), "歡迎使用 Divination Bot!");
        List<LanguageType> typeList = bot.getLanguageSetting().stream()
                .map(Language::getLanguageCode)
                .toList();
        // 創建 Inline Keyboard

        // 創建 Inline Keyboard 的行和按鈕
        List<InlineKeyboardRow> rows = new ArrayList<>();

        // 遍歷每個語言類型並為每個語言創建一個按鈕
        for (LanguageType type : typeList) {
            InlineKeyboardButton button = InlineKeyboardButton.builder()
                    .text("選擇語言：" + type.getType())  // 按鈕顯示語言名稱
                    .callbackData("lang_" + type.name())  // callback data 用於區分
                    .build();

            // 為每個按鈕創建一行並添加
            InlineKeyboardRow rowInline = new InlineKeyboardRow();
            rowInline.add(button);
            rows.add(rowInline);
        }
        // 設置鍵盤的按鈕
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup(rows);
        // 將 Inline Keyboard 設置為消息的回覆鍵盤
        message.setReplyMarkup(markupInline);
        telegramBotClient.send(message); // 發送消息
    }
}
