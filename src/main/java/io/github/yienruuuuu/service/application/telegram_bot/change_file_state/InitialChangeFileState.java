package io.github.yienruuuuu.service.application.telegram_bot.change_file_state;

import io.github.yienruuuuu.bean.entity.Bot;
import io.github.yienruuuuu.bean.enums.*;
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
public class InitialChangeFileState extends ChangeFileBaseState implements ChangeFileBotState {
    public InitialChangeFileState(TelegramBotClient telegramBotClient, BotService botService) {
        super(telegramBotClient, botService);
    }

    @Override
    public void handleMessage(ChangeFileBot bot, Update update, Bot botEntity) {
        sendHelloMessageAndChooseChangeType(update, botEntity);
    }

    @Override
    public void handleCallbackQuery(ChangeFileBot bot, Update update, Bot botEntity) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        String callbackData = callbackQuery.getData();
        String chatId = String.valueOf(callbackQuery.getMessage().getChatId());
        int messageId = callbackQuery.getMessage().getMessageId();
        // 移除按鈕
        telegramBotClient.send(new DeleteMessage(chatId, messageId), botEntity);
        // 解析 callbackData (格式應為 "UPLOAD_GIF" 或 "DELETE_PIC")
        String[] dataParts = callbackData.split("_");
        ActiveType activeType = ActiveType.valueOf(dataParts[0]);  // 取得 ActiveType
        FileType fileType = FileType.valueOf(dataParts[1]);  // 取得 FileType

        // 根據 ActiveType 和 FileType 切換到對應的狀態類別
        switch (activeType) {
            case UPLOAD:
                if (fileType == FileType.GIF) {
                    bot.setState(ChangeFileBotStateEnum.UPLOAD_GIF_STATE);
                } else if (fileType == FileType.PIC) {
                    bot.setState(ChangeFileBotStateEnum.UPLOAD_PIC_STATE);
                }
                break;
            case DELETE:
                if (fileType == FileType.GIF) {
                    bot.setState(ChangeFileBotStateEnum.DELETE_GIF_STATE);
                } else if (fileType == FileType.PIC) {
                    bot.setState(ChangeFileBotStateEnum.DELETE_PIC_STATE);
                }
                break;
            default:
                telegramBotClient.send(new SendMessage(chatId, "預料之外的操作，請重新開始"), botEntity);
                bot.setState(ChangeFileBotStateEnum.INITIAL_STATE);
                bot.consume(update);
        }
        sendTypeSelection(super.randomGif(botEntity, GifType.QUESTION_ANIMATION), chatId, botEntity, fileType);
    }

    @Override
    public void handleFileUpdate(ChangeFileBot bot, Update update, Bot botEntity) {
    }

    // private

    /**
     * 招呼語與傳送語系選項
     */
    private void sendHelloMessageAndChooseChangeType(Update update, Bot botEntity) {
        long chatId = update.getMessage().getChatId();
        String fileId = super.randomGif(botEntity, GifType.QUESTION_ANIMATION);

        SendAnimation message = SendAnimation
                .builder()
                .chatId(chatId)
                .animation(new InputFile(fileId))
                .caption("MASTER 歡迎回來，檔案異動模式 啟動!\n請問要異動哪種資源呢?")
                .build();

        // 創建 Inline Keyboard 的行和按鈕
        List<InlineKeyboardRow> rows = new ArrayList<>();
        // 遍歷每個 ActiveType 和 FileType 的組合並創建按鈕
        for (ActiveType activeType : ActiveType.getAllTypes()) {
            for (FileType fileType : FileType.getAllTypes()) {
                // 按鈕文字顯示: ActiveType + FileType 的 description
                String buttonText = activeType.getDescription() + " " + fileType.getDescription();

                // callbackData 可攜帶 ActiveType 和 FileType 的名稱
                String callbackData = activeType.name() + "_" + fileType.name();

                InlineKeyboardButton button = InlineKeyboardButton.builder()
                        .text(buttonText)
                        .callbackData(callbackData)
                        .build();

                // 為每個按鈕創建一行並添加
                InlineKeyboardRow rowInline = new InlineKeyboardRow();
                rowInline.add(button);
                rows.add(rowInline);
            }
        }
        // 將 Inline Keyboard 設置為消息的回覆鍵盤
        message.setReplyMarkup(new InlineKeyboardMarkup(rows));
        telegramBotClient.send(message, botEntity);
    }

    private void sendTypeSelection(String gifId, String chatId, Bot botEntity, FileType fileType) {
        SendAnimation msg = SendAnimation
                .builder()
                .chatId(chatId)
                .animation(new InputFile(gifId))
                .caption("請選擇類型")
                .build();

        // 創建 Inline Keyboard 的行和按鈕
        List<InlineKeyboardRow> rows = new ArrayList<>();
        // 動態選擇 GifType 或 PicType 列表
        List<? extends Enum<?>> types = (fileType.equals(FileType.GIF)) ? GifType.getAllTypes() : PicType.getAllTypes();
        for (Enum<?> type : types) {
            String description = "";
            if (type instanceof GifType gifType) {
                description = gifType.getDescription();
            } else if (type instanceof PicType picType) {
                description = picType.getDescription();
            }
            // 創建按鈕，顯示 description，callbackData 傳遞 name()
            InlineKeyboardButton button = InlineKeyboardButton.builder()
                    .text(description)
                    .callbackData(type.name())
                    .build();

            InlineKeyboardRow rowInline = new InlineKeyboardRow();
            rowInline.add(button);
            rows.add(rowInline);
        }
        // 設置鍵盤的按鈕
        msg.setReplyMarkup(new InlineKeyboardMarkup(rows));
        telegramBotClient.send(msg, botEntity);
    }
}
