package io.github.yienruuuuu.service.application.telegram_bot;

import io.github.yienruuuuu.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

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
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            log.warn("無文字訊息存在, update: {}", update);
            return;
        }

        long chatId = update.getMessage().getChatId();
        JsonUtils.parseJsonAndPrintLog("收到訊息", update);
        String messageText = update.getMessage().getText();

        if (messageText.equals("/start")) {
            SendMessage message = SendMessage
                    .builder()
                    .chatId(chatId)
                    .text("歡迎使用yuki的每日占卜機器人，請輸入 /pic 來查看今日占卜結果。")
                    .build();

            // 將鍵盤加入訊息中
            message.setReplyMarkup(ReplyKeyboardMarkup
                    .builder()
                    .keyboardRow(new KeyboardRow("/pic"))
                    .build());

            telegramBotClient.sendTextMessage(message);
        } else if (messageText.equals("/pic")) {
            SendPhoto message = SendPhoto
                    .builder()
                    .chatId(chatId)
                    .photo(new InputFile("https://png.pngtree.com/background/20230519/original/pngtree-this-is-a-picture-of-a-tiger-cub-that-looks-straight-picture-image_2660243.jpg"))
                    .caption("This is a little cat :)")
                    .showCaptionAboveMedia(true)
                    .build();
            telegramBotClient.sendPhotoMessage(message);
        } else {
            SendMessage message = SendMessage
                    .builder()
                    .chatId(chatId)
                    .text("Unknown command")
                    .build();
            telegramBotClient.sendTextMessage(message); // Sending our message object to user
        }
    }
}

