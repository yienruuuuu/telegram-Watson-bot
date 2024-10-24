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
        if (!update.getMessage().hasAnimation()){
            telegramBotClient.send(new SendMessage(chatId, "請傳送GIF"), botEntity);
            return;
        }
        Gif newGif = Gif.builder()
                .type(gifType)
                .telegramFileId(update.getMessage().getAnimation().getFileId())
                .bot(mainBotEntity)
                .build();

        mainBotEntity.getGifList().add(newGif);
        botService.save(mainBotEntity);
        // 發送確認訊息
        telegramBotClient.send(new SendMessage(chatId, "GIF 已成功儲存"), botEntity);
    }
}
