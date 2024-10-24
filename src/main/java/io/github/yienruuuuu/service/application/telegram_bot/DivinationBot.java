package io.github.yienruuuuu.service.application.telegram_bot;

import io.github.yienruuuuu.bean.entity.Bot;
import io.github.yienruuuuu.bean.enums.DivinationBotStateEnum;
import io.github.yienruuuuu.bean.enums.LanguageType;
import io.github.yienruuuuu.service.application.telegram_bot.divination_state.DivinationBotState;
import io.github.yienruuuuu.service.business.BotService;
import io.github.yienruuuuu.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Eric.Lee Date: 2024/10/16
 */

@Component
@Slf4j
public class DivinationBot implements LongPollingSingleThreadUpdateConsumer {
    private final Map<DivinationBotStateEnum, DivinationBotState> stateMap;
    private final BotService botService;
    private final Map<String, DivinationBotState> userStates = new HashMap<>();  // 存放每個使用者的狀態
    private final Map<String, LanguageType> userLanguages = new HashMap<>(); // 保存用戶語言設置


    @Autowired
    public DivinationBot(Map<DivinationBotStateEnum, DivinationBotState> stateMap, BotService botService) {
        this.stateMap = stateMap;
        this.botService = botService;
    }

    /**
     * 設置使用者的狀態
     */
    public void setState(String chatId, DivinationBotStateEnum stateEnum) {
        userStates.put(chatId, stateMap.get(stateEnum));
    }

    /**
     * 根據 chatId 獲取當前使用者的狀態，若無狀態則設置為初始狀態
     */
    private DivinationBotState getState(String chatId) {
        return userStates.getOrDefault(chatId, stateMap.get(DivinationBotStateEnum.INITIAL_STATE));
    }

    // 設置用戶的語言設置
    public void setUserLanguage(String chatId, LanguageType language) {
        userLanguages.put(chatId, language);
    }

    // 根據 chatId 獲取用戶的語言設置
    public Optional<LanguageType> getUserLanguage(String chatId) {
        return Optional.ofNullable(userLanguages.get(chatId));
    }

    @Override
    public void consume(Update update) {
        Integer botNumber = 1;
        Bot bot = botService.findBotById(botNumber);
        JsonUtils.parseJsonAndPrintLog("收到訊息", update);

        String chatId = getChatId(update)
                .orElseThrow(() -> new IllegalArgumentException("無法取得 chatId"));

        DivinationBotState currentState = getState(chatId);
        if (update.hasMessage() && update.getMessage().hasText()) {
            currentState.handleMessage(this, update, bot);
        } else if (update.hasCallbackQuery()) {
            currentState.handleCallbackQuery(this, update, bot);
        }
    }


    private Optional<String> getChatId(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            return Optional.of(update.getMessage().getChatId().toString());
        }
        if (update.hasCallbackQuery()) {
            return Optional.of(update.getCallbackQuery().getMessage().getChatId().toString());
        }
        return Optional.empty();
    }
}

