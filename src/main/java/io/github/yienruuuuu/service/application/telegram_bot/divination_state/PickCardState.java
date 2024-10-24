package io.github.yienruuuuu.service.application.telegram_bot.divination_state;

import io.github.yienruuuuu.bean.entity.Bot;
import io.github.yienruuuuu.bean.enums.DivinationBotStateEnum;
import io.github.yienruuuuu.bean.enums.PicType;
import io.github.yienruuuuu.service.application.telegram_bot.DivinationBot;
import io.github.yienruuuuu.service.application.telegram_bot.TelegramBotClient;
import io.github.yienruuuuu.service.business.TextService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * 初始狀態
 *
 * @author Eric.Lee
 * Date: 2024/10/21
 */
@Component
@Slf4j
public class PickCardState extends DivinationBaseState implements DivinationBotState {

    public PickCardState(TelegramBotClient telegramBotClient, TextService textService) {
        super(telegramBotClient, textService);
    }

    @Override
    public void handleMessage(DivinationBot bot, Update update, Bot botEntity) {
        bot.setState(String.valueOf(update.getMessage().getChatId()), DivinationBotStateEnum.INITIAL_STATE);
        bot.consume(update);
    }

    @Override
    public void handleCallbackQuery(DivinationBot bot, Update update, Bot botEntity) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        String chatId = String.valueOf(callbackQuery.getMessage().getChatId());
        int messageId = callbackQuery.getMessage().getMessageId();
        // 移除按鈕
        telegramBotClient.send(new DeleteMessage(chatId, messageId), botEntity);
        String fileId = randomPic(botEntity, PicType.DIVINATION_PIC);
        log.warn("fileId: {}", fileId);
        File file = telegramBotClient.getFile(new GetFile(fileId), botEntity);
        log.warn("file: {}", file);

        SendPhoto sendPhoto = SendPhoto.builder()
                .photo(new InputFile(file.getFileId()))
                .chatId(chatId)
                .caption("今日運勢:吉")
                .build();

        // 這裡是傳送訊息的部分
        telegramBotClient.send(sendPhoto, botEntity);
    }
}
