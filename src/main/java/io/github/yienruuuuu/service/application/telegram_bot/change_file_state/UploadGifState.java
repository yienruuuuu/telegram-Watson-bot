package io.github.yienruuuuu.service.application.telegram_bot.change_file_state;

import io.github.yienruuuuu.bean.entity.Bot;
import io.github.yienruuuuu.bean.entity.Gif;
import io.github.yienruuuuu.bean.enums.ChangeFileBotStateEnum;
import io.github.yienruuuuu.bean.enums.GifType;
import io.github.yienruuuuu.service.application.telegram_bot.ChangeFileBot;
import io.github.yienruuuuu.service.application.telegram_bot.TelegramBotClient;
import io.github.yienruuuuu.service.business.BotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * 初始狀態
 *
 * @author Eric.Lee
 * Date: 2024/10/21
 */
@Component
@Slf4j
public class UploadGifState extends ChangeFileBaseState implements ChangeFileBotState {
    private GifType gifType;

    public UploadGifState(TelegramBotClient telegramBotClient, BotService botService) {
        super(telegramBotClient, botService);
    }

    @Override
    public void handleMessage(ChangeFileBot bot, Update update, Bot botEntity) {
        bot.setState(ChangeFileBotStateEnum.INITIAL_STATE);
        bot.consume(update);
    }

    @Override
    public void handleCallbackQuery(ChangeFileBot bot, Update update, Bot botEntity, Bot mainBotEntity) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        String callbackData = callbackQuery.getData();
        String chatId = String.valueOf(callbackQuery.getMessage().getChatId());
        int messageId = callbackQuery.getMessage().getMessageId();
        // 移除按鈕
        telegramBotClient.send(new DeleteMessage(chatId, messageId), botEntity);
        gifType = GifType.valueOf(callbackData);
        telegramBotClient.send(new SendMessage(chatId, "請傳送GIF"), botEntity);
    }

    @Override
    public void handleFileUpdate(ChangeFileBot bot, Update update, Bot botEntity, Bot mainBotEntity) {
        String chatId = String.valueOf(update.getMessage().getChatId());
        if (!update.getMessage().hasAnimation()) {
            telegramBotClient.send(new SendMessage(chatId, "請傳送GIF"), botEntity);
            return;
        }
        //使用主BOT上傳文件並取得fileId
        String fileId = update.getMessage().getAnimation().getFileId();
        Message resMessage = getMainBotFileId(fileId, botEntity, mainBotEntity);

        Gif newGif = Gif.builder()
                .type(gifType)
                .telegramFileId(resMessage.getAnimation().getFileId())
                .fileBotFileId(fileId)
                .bot(mainBotEntity)
                .build();

        mainBotEntity.getGifList().add(newGif);
        botService.save(mainBotEntity);
        // 發送確認訊息
        telegramBotClient.send(new SendMessage(chatId, "GIF 已成功儲存"), botEntity);
    }

    private Message getMainBotFileId(String fileId, Bot botEntity, Bot mainBotEntity) {
        File file = telegramBotClient.getFile(new GetFile(fileId), botEntity);
        java.io.File downloadedFile = telegramBotClient.downloadFile(file, botEntity);

        // 在原文件路徑上使用新名稱
        String newFileName = UUID.randomUUID() + ".mp4";
        Path targetPath = downloadedFile.toPath().resolveSibling(newFileName);
        try {
            // 重命名文件
            Files.move(downloadedFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("檔案重命名過程發生異常", e);
        }
        // 新的文件對象
        java.io.File renamedFile = targetPath.toFile();
        Message resMessage = telegramBotClient.send(new SendAnimation("1513052214", new InputFile(renamedFile)), mainBotEntity);
        telegramBotClient.send(new DeleteMessage("1513052214", resMessage.getMessageId()), mainBotEntity);
        return resMessage;
    }
}
