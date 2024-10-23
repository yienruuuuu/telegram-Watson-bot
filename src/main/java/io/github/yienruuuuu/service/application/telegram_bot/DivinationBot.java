package io.github.yienruuuuu.service.application.telegram_bot;

import io.github.yienruuuuu.bean.entity.Bot;
import io.github.yienruuuuu.bean.enums.DivinationBotStateEnum;
import io.github.yienruuuuu.service.application.telegram_bot.divination_state.DivinationBotState;
import io.github.yienruuuuu.service.business.BotService;
import io.github.yienruuuuu.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

/**
 * @author Eric.Lee Date: 2024/10/16
 */

@Component
@Slf4j
public class DivinationBot implements LongPollingSingleThreadUpdateConsumer {
    private final Map<DivinationBotStateEnum, DivinationBotState> stateMap;
    private DivinationBotState currentState;
    private final BotService botService;

    @Autowired
    public DivinationBot(Map<DivinationBotStateEnum, DivinationBotState> stateMap, BotService botService) {
        this.stateMap = stateMap;
        this.botService = botService;
        // 設置初始狀態
        this.currentState = stateMap.get(DivinationBotStateEnum.INITIAL_STATE);
    }

    /**
     * 切換狀態
     */
    public void setState(DivinationBotStateEnum state) {
        this.currentState = stateMap.get(state);
    }

    @Override
    public void consume(Update update) {
        Integer botNumber = 1;
        Bot bot = botService.findBotById(botNumber);
        JsonUtils.parseJsonAndPrintLog("收到訊息", update);

        if (update.hasMessage() && update.getMessage().hasText()) {
            currentState.handleMessage(this, update, bot);
        } else if (update.hasCallbackQuery()) {
            currentState.handleCallbackQuery(this, update, bot);
        }
    }

}

