package io.github.yienruuuuu.service.application.telegram_bot.state;

import io.github.yienruuuuu.bean.entity.Bot;
import io.github.yienruuuuu.service.application.telegram_bot.DivinationTelegramBot;
import io.github.yienruuuuu.service.application.telegram_bot.TelegramBotClient;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * 初始狀態
 *
 * @author Eric.Lee
 * Date: 2024/10/21
 */
@Component
public class PickCardState implements BotState {
    private final TelegramBotClient telegramBotClient;

    public PickCardState(TelegramBotClient telegramBotClient) {
        this.telegramBotClient = telegramBotClient;
    }


    @Override
    public void handleMessage(DivinationTelegramBot bot, Update update, Bot botEntity) {
    }

    @Override
    public void handleCallbackQuery(DivinationTelegramBot bot, Update update, Bot botEntity) {
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

}
