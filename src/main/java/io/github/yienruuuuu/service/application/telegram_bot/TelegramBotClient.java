package io.github.yienruuuuu.service.application.telegram_bot;

import io.github.yienruuuuu.bean.entity.Bot;
import io.github.yienruuuuu.service.business.BotService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.AbstractTelegramClient;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.Serializable;

/**
 * @author Eric.Lee
 * Date: 2024/10/18
 */
@Component
@Slf4j
public class TelegramBotClient{
    @Getter
    private final TelegramClient telegramClient;
    private final BotService botService;

    // 使用依賴注入的 TelegramClient
    public TelegramBotClient(BotService botService) {
        this.botService = botService;
        this.telegramClient = createTelegramClient();
    }

    /**
     * 通用的 send 方法，支援所有 BotApiMethod 的子類別
     */
    public <T extends Serializable, Method extends BotApiMethod<T>> T send(Method method) {
        try {
            return telegramClient.execute(method);
        } catch (TelegramApiException e) {
            handleException(e, method.getMethod());
            return null;
        }
    }

    /**
     * 通用的 send 方法，支援所有 BotApiMethod 的子類別
     */
    public void send(SendAnimation animation) {
        try {
            telegramClient.execute(animation);
        } catch (TelegramApiException e) {
            handleException(e, "傳送GIF");
        }
    }


    /**
     * 取得文件的通用方法
     */
    public File getFile(GetFile getFile) {
        try {
            return telegramClient.execute(getFile);
        } catch (TelegramApiException e) {
            handleException(e, "getFile");
            return null;
        }
    }

    /**
     * 下載文件的通用方法
     */
    public java.io.File downloadFile(File file) {
        try {
            return telegramClient.downloadFile(file);
        } catch (TelegramApiException e) {
            handleException(e, "getFile");
            return null;
        }
    }



    /**
     * 將錯誤處理統一管理
     */
    private void handleException(TelegramApiException e, String action) {
        log.error("{} 操作失敗: ", action, e);
    }

    /**
     * 根據 botToken 創建 TelegramClient
     */
    private TelegramClient createTelegramClient() {
        Bot bot = botService.findBotById(1);
        return new OkHttpTelegramClient(bot.getBotToken());
    }
}
