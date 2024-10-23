package io.github.yienruuuuu.service.application.telegram_bot.divination_state;

import io.github.yienruuuuu.bean.entity.Bot;
import io.github.yienruuuuu.bean.entity.Language;
import io.github.yienruuuuu.bean.enums.DivinationBotStateEnum;
import io.github.yienruuuuu.bean.enums.GifType;
import io.github.yienruuuuu.bean.enums.LanguageType;
import io.github.yienruuuuu.service.application.telegram_bot.DivinationBot;
import io.github.yienruuuuu.service.application.telegram_bot.TelegramBotClient;
import org.springframework.beans.factory.annotation.Autowired;
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
public class InitialDivinationState extends DivinationBaseState implements DivinationBotState {

    @Autowired
    public InitialDivinationState(TelegramBotClient telegramBotClient) {
        super(telegramBotClient);
    }

    @Override
    public void handleMessage(DivinationBot bot, Update update, Bot botEntity) {
        if (update.getMessage().getText().equals("/start")) {
            sendHelloMessageAndChooseLanguage(update, botEntity);
        }
    }

    @Override
    public void handleCallbackQuery(DivinationBot bot, Update update, Bot botEntity) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        String callbackData = callbackQuery.getData();
        String chatId = String.valueOf(callbackQuery.getMessage().getChatId());
        int messageId = callbackQuery.getMessage().getMessageId();
        // 移除按鈕
        telegramBotClient.send(new DeleteMessage(chatId, messageId), botEntity);

        // 初始化回覆訊息
        String text = switch (callbackData) {
            case "lang_ZH_TW" -> "你選擇了繁體中文。";
            case "lang_EN" -> "You chose English.";
            default -> "你點擊了未知按鈕?____?\n 怎麼做到的???\n 算你繁體中文";
        };

        SendMessage message = new SendMessage(chatId, text);
        // 這裡是傳送訊息的部分
        telegramBotClient.send(message, botEntity);

        sendPickCardAnimate(
                super.randomPicOrGif(botEntity, GifType.CARD_DRAWING_ANIMATION),
                chatId,
                "請選擇您的每日幸運卡牌",
                botEntity
        );
        bot.setState(DivinationBotStateEnum.PICK_CARD_STATE);
    }

    // private

    /**
     * 招呼語與傳送語系選項
     */
    private void sendHelloMessageAndChooseLanguage(Update update, Bot bot) {
        long chatId = update.getMessage().getChatId();
        SendMessage message = new SendMessage(String.valueOf(chatId), "歡迎使用 每日運勢測試!");
        List<LanguageType> typeList = bot.getLanguageSetting().stream()
                .map(Language::getLanguageCode)
                .toList();
        // 創建 Inline Keyboard

        // 創建 Inline Keyboard 的行和按鈕
        List<InlineKeyboardRow> rows = new ArrayList<>();

        // 遍歷每個語言類型並為每個語言創建一個按鈕
        for (LanguageType type : typeList) {
            InlineKeyboardButton button = InlineKeyboardButton.builder()
                    .text(type.getType())  // 按鈕顯示語言名稱
                    .callbackData("lang_" + type.name())  // callback data 用於區分
                    .build();

            // 為每個按鈕創建一行並添加
            InlineKeyboardRow rowInline = new InlineKeyboardRow();
            rowInline.add(button);
            rows.add(rowInline);
        }
        // 設置鍵盤的按鈕
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup(rows);
        // 將 Inline Keyboard 設置為消息的回覆鍵盤
        message.setReplyMarkup(markupInline);
        telegramBotClient.send(message, bot); // 發送消息
    }

    private void sendPickCardAnimate(String gifId, String chatId, String caption, Bot botEntity) {
        SendAnimation msg = SendAnimation
                .builder()
                .chatId(chatId)
                .animation(new InputFile(gifId))
                .caption(caption)
                .build();

        // 創建 Inline Keyboard 的行和按鈕
        List<InlineKeyboardRow> rows = new ArrayList<>();
        InlineKeyboardRow rowInline = new InlineKeyboardRow();
        addButtonText(rowInline, "♈️", "choose_card");
        addButtonText(rowInline, "☯️", "choose_card");
        addButtonText(rowInline, "☸", "choose_card");
        addButtonText(rowInline, "⚛", "choose_card");
        rows.add(rowInline);

        // 設置鍵盤的按鈕
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup(rows);
        msg.setReplyMarkup(markupInline);
        telegramBotClient.send(msg, botEntity);
    }
}
