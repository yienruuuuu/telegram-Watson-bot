package io.github.yienruuuuu.service.application.telegram_bot;

import io.github.yienruuuuu.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Comparator;
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
        if (!update.hasMessage()) {
            return;
        }
        long chatId = update.getMessage().getChatId();

        // 檢查更新是否有消息並且訊息有文本
        if (update.getMessage().hasText()) {
            JsonUtils.parseJsonAndPrintLog("收到訊息", update);
            String messageText = update.getMessage().getText();

            if (messageText.equals("/start")) {
                SendMessage message = SendMessage.builder()
                        .chatId(chatId)
                        .text(messageText)
                        .build();
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
        } else if (update.getMessage().hasPhoto()) {
            List<PhotoSize> photos = update.getMessage().getPhoto();
            String fileId = photos.stream().max(Comparator.comparing(PhotoSize::getFileSize))
                    .map(PhotoSize::getFileId)
                    .orElse("");
            // Know photo width
            int fileWidth = photos.stream().max(Comparator.comparing(PhotoSize::getFileSize))
                    .map(PhotoSize::getWidth)
                    .orElse(0);
            // Know photo height
            int fileHeight = photos.stream().max(Comparator.comparing(PhotoSize::getFileSize))
                    .map(PhotoSize::getHeight)
                    .orElse(0);
            // Set photo caption
            String caption = "file_id: " + fileId + "\nwidth: " + Integer.toString(fileWidth) + "\nheight: " + Integer.toString(fileHeight);
            SendPhoto msg = SendPhoto
                    .builder()
                    .chatId(chatId)
                    .photo(new InputFile(fileId))
                    .caption(caption)
                    .hasSpoiler(true)
                    .build();
//            InputPaidMedia med = new InputPaidMediaPhoto(fileId, false, "mediaaa", null, null);
//            SendPaidMedia msg2 = SendPaidMedia
//                    .builder()
//                    .chatId(chatId)
//                    .media(List.of(med,med))
//                    .caption(caption)
//                    .starCount(1)
//                    .build();
            telegramBotClient.sendPhotoMessage(msg);
        }
    }
}

