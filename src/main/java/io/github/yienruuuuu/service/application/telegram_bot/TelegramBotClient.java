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

    public void sendTextMessage(SendMessage sendMessage) {
        try {
            telegramClient.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("傳送訊息失敗 ", e);
        }
    }

    public void sendPhotoMessage(SendPhoto sendPhoto) {
        try {
            telegramClient.execute(sendPhoto);
        } catch (TelegramApiException e) {
            log.error("傳送圖片訊息失敗 ", e);
        }
    }

    public void sendPaidMediaMessage(SendPaidMedia paidMedia) {
        try {
            telegramClient.execute(paidMedia);
        } catch (TelegramApiException e) {
            log.error("傳送付費圖片訊息異常 ", e);
        }
    }

    // 將創建 TelegramClient 的邏輯抽取到一個方法中
    private TelegramClient createTelegramClient() {
        Bot bot = botService.findBotById(1);
        return new OkHttpTelegramClient(bot.getBotToken());
    }
}
