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
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
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
public class DeleteGifState extends ChangeFileBaseState implements ChangeFileBotState {
    public DeleteGifState(TelegramBotClient telegramBotClient, BotService botService) {
        super(telegramBotClient, botService);
    }

    @Override
    public void handleMessage(ChangeFileBot bot, Update update, Bot botEntity) {
        bot.setState(ChangeFileBotStateEnum.INITIAL_STATE);
        bot.consume(update);
    }

    @Override
    public void handleCallbackQuery(ChangeFileBot bot, Update update, Bot botEntity) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        String callbackData = callbackQuery.getData();
        String chatId = String.valueOf(callbackQuery.getMessage().getChatId());
        int messageId = callbackQuery.getMessage().getMessageId();

        // 移除舊的按鈕消息
        telegramBotClient.send(new DeleteMessage(chatId, messageId), botEntity);

        if (isGifType(callbackData)) {
            GifType gifType = GifType.valueOf(callbackData);
            handleGifTypeSelection(gifType, chatId, botEntity);
        } else {
            handleGifDeletion(callbackData, chatId, botEntity);
        }
    }

    @Override
    public void handleFileUpdate(ChangeFileBot bot, Update update, Bot botEntity) {
    }

    private void handleGifTypeSelection(GifType gifType, String chatId, Bot botEntity) {
        // 根據 GifType 過濾出對應的 Gif 列表
        List<Gif> filteredGifList = botEntity.getGifList().stream()
                .filter(gif -> gifType.equals(gif.getType()))
                .toList();

        if (filteredGifList.isEmpty()) {
            telegramBotClient.send(new SendMessage(chatId, "沒有可用的 GIF"), botEntity);
            return;
        }

        // 構建帶有 Inline 按鈕的消息，每個按鈕的 callbackData 是 GIF 的 fileId
        SendMessage message = new SendMessage(chatId, "請選擇要刪除的 GIF:");
        telegramBotClient.send(message, botEntity);


        // 創建 Inline Keyboard 的行和按鈕
        for (Gif gif : filteredGifList) {
            SendAnimation sendAnimation = SendAnimation.builder()
                    .chatId(chatId)
                    .animation(new InputFile(gif.getTelegramFileId()))
                    .build();

            List<InlineKeyboardRow> rows = new ArrayList<>();
            InlineKeyboardButton button = InlineKeyboardButton.builder()
                    .text("刪除")
                    .callbackData(String.valueOf(gif.getId()))
                    .build();
            InlineKeyboardRow rowInline = new InlineKeyboardRow();
            rowInline.add(button);
            rows.add(rowInline);
            sendAnimation.setReplyMarkup(new InlineKeyboardMarkup(rows));
            // 發送包含按鈕的消息
            telegramBotClient.send(sendAnimation, botEntity);
        }
    }

    private void handleGifDeletion(String fileId, String chatId, Bot botEntity) {
        List<Gif> gifList = botEntity.getGifList();
        boolean removed = gifList.removeIf(gif -> String.valueOf(gif.getId()).equals(fileId));

        if (removed) {
            botService.save(botEntity);
            telegramBotClient.send(new SendMessage(chatId, "GIF 已刪除"), botEntity);
        } else {
            telegramBotClient.send(new SendMessage(chatId, "找不到要刪除的 GIF"), botEntity);
        }
    }

    // 判斷是否為有效的 GifType
    private boolean isGifType(String callbackData) {
        for (GifType gifType : GifType.values()) {
            if (gifType.name().equals(callbackData)) {
                return true;
            }
        }
        return false;
    }
}
