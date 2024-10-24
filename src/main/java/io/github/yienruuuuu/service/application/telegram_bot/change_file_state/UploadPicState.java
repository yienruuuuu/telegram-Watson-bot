package io.github.yienruuuuu.service.application.telegram_bot.change_file_state;

import io.github.yienruuuuu.bean.entity.Bot;
import io.github.yienruuuuu.bean.entity.Pic;
import io.github.yienruuuuu.bean.enums.ChangeFileBotStateEnum;
import io.github.yienruuuuu.bean.enums.PicType;
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
public class UploadPicState extends ChangeFileBaseState implements ChangeFileBotState {
    private PicType picType;

    public UploadPicState(TelegramBotClient telegramBotClient, BotService botService) {
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
        picType = PicType.valueOf(callbackData);
        telegramBotClient.send(new SendMessage(chatId, "請傳送PIC"), botEntity);
    }

    @Override
    public void handleFileUpdate(ChangeFileBot bot, Update update, Bot botEntity, Bot mainBotEntity) {
        String chatId = String.valueOf(update.getMessage().getChatId());
        if (!update.getMessage().hasPhoto()) {
            telegramBotClient.send(new SendMessage(chatId, "請傳送PIC"), botEntity);
            return;
        }
        Pic newPic = Pic.builder()
                .type(picType)
                .telegramFileId(update.getMessage().getPhoto().get(1).getFileId())
                .bot(mainBotEntity)
                .build();

        mainBotEntity.getPicList().add(newPic);
        botService.save(mainBotEntity);
        // 發送確認訊息
        telegramBotClient.send(new SendMessage(chatId, "PIC 已成功儲存"), botEntity);
    }
}
