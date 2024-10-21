package io.github.yienruuuuu.service.application.telegram_bot;

import io.github.yienruuuuu.bean.entity.Bot;
import io.github.yienruuuuu.service.business.BotService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPaidMedia;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

/**
 * @author Eric.Lee
 * Date: 2024/10/18
 */
@Component
@Slf4j
public class TelegramBotClient {
    @Getter
    private final TelegramClient telegramClient;
    private final BotService botService;

    // 使用依賴注入的 TelegramClient
    public TelegramBotClient(BotService botService) {
        this.botService = botService;
        this.telegramClient = createTelegramClient();
    }

    /**
     * 傳送文字訊息
     */
    public void send(SendMessage sendMessage) {
        try {
            telegramClient.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("傳送訊息失敗 ", e);
        }
    }

    /**
     * 傳送圖片
     */
    public void send(SendPhoto sendPhoto) {
        try {
            telegramClient.execute(sendPhoto);
        } catch (TelegramApiException e) {
            log.error("傳送圖片訊息失敗 ", e);
        }
    }

    /**
     * 傳送付費圖片
     */
    public void send(SendPaidMedia paidMedia) {
        try {
            telegramClient.execute(paidMedia);
        } catch (TelegramApiException e) {
            log.error("傳送付費圖片訊息異常 ", e);
        }
    }

    /**
     * 編輯訊息的回覆鍵盤
     */
    public void send(EditMessageReplyMarkup editMessageReplyMarkup) {
        try {
            telegramClient.execute(editMessageReplyMarkup);
        } catch (TelegramApiException e) {
            log.error("回收訊息失敗 ", e);
        }
    }

    /**
     * 刪除訊息
     */
    public void send(DeleteMessage deleteMessage) {
        try {
            telegramClient.execute(deleteMessage);
        } catch (TelegramApiException e) {
            log.error("刪除訊息失敗 ", e);
        }
    }

    // 將創建 TelegramClient 的邏輯抽取到一個方法中
    private TelegramClient createTelegramClient() {
        Bot bot = botService.findBotById(1);
        return new OkHttpTelegramClient(bot.getBotToken());
    }
}
