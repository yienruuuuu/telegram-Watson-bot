package io.github.yienruuuuu.service.application.telegram_bot.divination_state;

import io.github.yienruuuuu.bean.entity.Bot;
import io.github.yienruuuuu.bean.enums.DivinationBotStateEnum;
import io.github.yienruuuuu.service.application.telegram_bot.DivinationBot;
import io.github.yienruuuuu.service.application.telegram_bot.TelegramBotClient;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class PickCardState implements DivinationBotState {
    private final TelegramBotClient telegramBotClient;

    public PickCardState(TelegramBotClient telegramBotClient) {
        this.telegramBotClient = telegramBotClient;
    }


    @Override
    public void handleMessage(DivinationBot bot, Update update, Bot botEntity) {
        bot.setState(DivinationBotStateEnum.INITIAL_STATE);
        bot.consume(update);
    }

    @Override
    public void handleCallbackQuery(DivinationBot bot, Update update, Bot botEntity) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        String callbackData = callbackQuery.getData();
        long chatId = callbackQuery.getMessage().getChatId();
        int messageId = callbackQuery.getMessage().getMessageId();

        // 初始化回覆訊息
        String text = switch (callbackData) {
            case "lang_ZH_TW" -> "你選擇了繁體中文。";
            case "lang_EN" -> "You chose English.";
            default -> "你點擊了未知按鈕?____?\n 怎麼做到的???\n 算你繁體中文";
        };

        // 移除按鈕
        DeleteMessage deleteMessage = DeleteMessage.builder()
                .chatId(chatId)
                .messageId(messageId)
                .build();
        // 更新訊息的按鈕狀態
        telegramBotClient.send(deleteMessage,botEntity.getId());

        SendMessage message = new SendMessage(String.valueOf(chatId), text);
        // 這裡是傳送訊息的部分
        telegramBotClient.send(message,botEntity.getId());
    }

}
