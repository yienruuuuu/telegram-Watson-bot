package io.github.yienruuuuu.service.application.telegram_bot.state;

import io.github.yienruuuuu.bean.enums.BotStateEnum;
import io.github.yienruuuuu.service.application.telegram_bot.TelegramBotClient;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.Map;

/**
 * @author Eric.Lee
 * Date: 2024/10/22
 */
@Component
public class BaseState {
    protected final TelegramBotClient telegramBotClient;

    public BaseState(TelegramBotClient telegramBotClient) {
        this.telegramBotClient = telegramBotClient;
    }


    protected void addButtonText(InlineKeyboardRow rowInline, String sign, String callBackData) {
        // 遍歷每個語言類型並為每個語言創建一個按鈕
        InlineKeyboardButton button = InlineKeyboardButton.builder()
                .text(sign)  // 按鈕顯示語言名稱
                .callbackData(callBackData)  // callback data 用於區分
                .build();
        // 為每個按鈕創建一行並添加
        rowInline.add(button);
    }
}
