package io.github.yienruuuuu.service.application.telegram_bot.divination_state;

import io.github.yienruuuuu.bean.entity.*;
import io.github.yienruuuuu.bean.enums.GifType;
import io.github.yienruuuuu.bean.enums.LanguageType;
import io.github.yienruuuuu.bean.enums.PicType;
import io.github.yienruuuuu.bean.enums.TextType;
import io.github.yienruuuuu.service.application.telegram_bot.TelegramBotClient;
import io.github.yienruuuuu.service.business.CardPositionService;
import io.github.yienruuuuu.service.business.TextService;
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
public class DivinationBaseState {
    private static final Random random = new Random();
    protected final TelegramBotClient telegramBotClient;
    protected final TextService textService;
    protected final CardPositionService cardPositionService;

    public DivinationBaseState(TelegramBotClient telegramBotClient, TextService textService, CardPositionService cardPositionService) {
        this.telegramBotClient = telegramBotClient;
        this.textService = textService;
        this.cardPositionService = cardPositionService;
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

    protected String randomPicOrGif(Bot botEntity, GifType gifType) {
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
     * 從BOT關聯下，從指定的PIC類型中隨機選擇一個PIC並返回其telegram file id
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

    /**
     * 從指定的語言類型+文字內容類型中，隨機選擇一個Text並返回其內容(語言為null的話，預設為英語)
     */
    protected Text randomTextInDefaultLanguage(LanguageType languageType, TextType textType) {
        List<Text> textList = textService.findTextByLanguageTypeAndTextType(languageType == null ? LanguageType.ZH_TW : languageType, textType);
        if (textList.isEmpty()) {
            log.warn("textList in languageType: {} ,TextType: {} not exist", languageType, textType);
            return null;
        }
        // 從篩選結果中隨機選擇一個
        return textList.get(random.nextInt(textList.size()));
    }

    public String generateCompleteCardDisplay(List<CardPosition> currentSituation, List<CardPosition> othersThought, List<CardPosition> futureSituation) {
        // 分別從每個列表中隨機挑選一張卡牌
        CardPosition currentCard = currentSituation.get(random.nextInt(currentSituation.size()));
        CardPosition thoughtCard = othersThought.get(random.nextInt(othersThought.size()));
        CardPosition futureCard = futureSituation.get(random.nextInt(futureSituation.size()));

        // 將每張卡牌的相關資訊整理
        String currentCardText = formatCardInfo("現狀", currentCard);
        String thoughtCardText = formatCardInfo("對方的想法", thoughtCard);
        String futureCardText = formatCardInfo("未來", futureCard);

        // 組合成完整的訊息
        return String.format("戀愛塔羅運勢:%n%n%s%n%n%s%n%n%s", currentCardText, thoughtCardText, futureCardText);
    }

    private String formatCardInfo(String type, CardPosition cardPosition) {
        // 取得卡牌的基本資訊
        String cardName = cardPosition.getTarotCard().getName();
        String position = cardPosition.getPosition().getChineseDescription();  // 正位/逆位
        String interpretationContent = cardPosition.getInterpretations().get(0).getContent(); // 假設每張卡只有一個解釋

        // 組合每個卡牌的詳細訊息
        return String.format("%s%n牌名: %s%n位置: %s%n運勢分析: %s",
                type, cardName, position, interpretationContent
        );
    }
}
