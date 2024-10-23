package io.github.yienruuuuu.service.application.telegram_bot.change_file_state;

import io.github.yienruuuuu.bean.entity.Bot;
import io.github.yienruuuuu.bean.entity.Gif;
import io.github.yienruuuuu.bean.entity.Pic;
import io.github.yienruuuuu.bean.enums.GifType;
import io.github.yienruuuuu.bean.enums.PicType;
import io.github.yienruuuuu.service.application.telegram_bot.TelegramBotClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.List;
import java.util.Random;

/**
 * @author Eric.Lee
 * Date: 2024/10/22
 */
@Component
@Slf4j
public class ChangeFileBaseState {
    private static final Random random = new Random();
    protected final TelegramBotClient telegramBotClient;

    public ChangeFileBaseState(TelegramBotClient telegramBotClient) {
        this.telegramBotClient = telegramBotClient;
    }

    protected void addButtonText(InlineKeyboardRow rowInline, String sign, String callBackData) {
        // 遍歷每個語言類型並為每個語言創建一個按鈕
        InlineKeyboardButton button = InlineKeyboardButton.builder()
                .text(sign)  // 按鈕顯示語言名稱
                .callbackData(callBackData)  // callback data 用於區分
                .build();
        // 為每個按鈕創建一行並添加
        rowInline.add(button);
    }

    /**
     * 從BOT關聯下，從指定的gif類型中隨機選擇一個gif並返回其telegram file id
     */
    protected String randomGif(Bot botEntity, GifType gifType) {
        List<Gif> filteredGifList = botEntity.getGifList().stream()
                .filter(gif -> gifType.equals(gif.getType()))
                .toList();

        if (filteredGifList.isEmpty()) {
            log.warn("Gif type {} not exist", gifType);
            return null;
        }
        // 從篩選結果中隨機選擇一個
        Gif randomGif = filteredGifList.get(random.nextInt(filteredGifList.size()));

        // 假設你想返回 Gif 的某個屬性，比如 URL
        return randomGif.getTelegramFileId();
    }

    /**
     * 從BOT關聯下，從指定的gif類型中隨機選擇一個gif並返回其telegram file id
     */
    protected String randomPic(Bot botEntity, PicType picType) {
        List<Pic> filteredPicList = botEntity.getPicList().stream()
                .filter(pic -> picType.equals(pic.getType()))
                .toList();

        if (filteredPicList.isEmpty()) {
            log.warn("Pic type {} not exist", picType);
            return null;
        }
        // 從篩選結果中隨機選擇一個
        Pic randomPic = filteredPicList.get(random.nextInt(filteredPicList.size()));

        // 假設你想返回 Gif 的某個屬性，比如 URL
        return randomPic.getTelegramFileId();
    }
}
