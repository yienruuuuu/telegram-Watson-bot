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
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;
import java.util.List;

/**
 * 初始狀態
 *
 * @author Eric.Lee
 * Date: 2024/10/21
 */
@Component
@Slf4j
public class DeletePicState extends ChangeFileBaseState implements ChangeFileBotState {


    public DeletePicState(TelegramBotClient telegramBotClient, BotService botService) {
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

        // 移除舊的按鈕消息
        telegramBotClient.send(new DeleteMessage(chatId, messageId), botEntity);

        if (isPicType(callbackData)) {
            PicType picType = PicType.valueOf(callbackData);
            handlePicTypeSelection(picType, chatId, botEntity, mainBotEntity);
        } else {
            handlePicDeletion(callbackData, chatId, botEntity, mainBotEntity);
        }
    }

    @Override
    public void handleFileUpdate(ChangeFileBot bot, Update update, Bot botEntity, Bot mainBotEntity) {
    }


    private void handlePicTypeSelection(PicType picType, String chatId, Bot botEntity, Bot mainBotEntity) {
        // 根據 GifType 過濾出對應的 Gif 列表
        List<Pic> filteredPicList = mainBotEntity.getPicList().stream()
                .filter(pic -> picType.equals(pic.getType()))
                .toList();

        if (filteredPicList.isEmpty()) {
            telegramBotClient.send(new SendMessage(chatId, "沒有可用的 Pic"), mainBotEntity);
            return;
        }

        // 構建帶有 Inline 按鈕的消息，每個按鈕的 callbackData 是 GIF 的 fileId
        SendMessage message = new SendMessage(chatId, "請選擇要刪除的 Pic:");
        telegramBotClient.send(message, botEntity);


        // 創建 Inline Keyboard 的行和按鈕
        for (Pic pic : filteredPicList) {
            SendPhoto sendPhoto = SendPhoto.builder()
                    .chatId(chatId)
                    .photo(new InputFile(pic.getTelegramFileId()))
                    .build();

            List<InlineKeyboardRow> rows = new ArrayList<>();
            InlineKeyboardButton button = InlineKeyboardButton.builder()
                    .text("刪除")
                    .callbackData(String.valueOf(pic.getId()))
                    .build();
            InlineKeyboardRow rowInline = new InlineKeyboardRow();
            rowInline.add(button);
            rows.add(rowInline);
            sendPhoto.setReplyMarkup(new InlineKeyboardMarkup(rows));
            // 發送包含按鈕的消息
            telegramBotClient.send(sendPhoto, botEntity);
        }
    }

    private void handlePicDeletion(String fileId, String chatId, Bot botEntity, Bot mainBotEntity) {
        List<Pic> picList = mainBotEntity.getPicList();
        boolean removed = picList.removeIf(pic -> String.valueOf(pic.getId()).equals(fileId));

        if (removed) {
            botService.save(mainBotEntity);
            telegramBotClient.send(new SendMessage(chatId, "PIC 已刪除"), botEntity);
        } else {
            telegramBotClient.send(new SendMessage(chatId, "找不到要刪除的 PIC"), botEntity);
        }
    }
}
