package io.github.yienruuuuu.service.application.telegram_bot;

import io.github.yienruuuuu.bean.entity.Bot;
import io.github.yienruuuuu.bean.enums.BotStateEnum;
import io.github.yienruuuuu.service.application.telegram_bot.state.BotState;
import io.github.yienruuuuu.service.application.telegram_bot.state.InitialState;
import io.github.yienruuuuu.service.business.BotService;
import io.github.yienruuuuu.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Eric.Lee Date: 2024/10/16
 */

@Component
@Slf4j
public class DivinationTelegramBot implements LongPollingSingleThreadUpdateConsumer {
    private final Map<BotStateEnum, BotState> stateMap;
    private BotState currentState;
    private final BotService botService;

    @Autowired
    public DivinationTelegramBot(Map<BotStateEnum, BotState> stateMap, BotService botService) {
        this.stateMap = stateMap;
        this.botService = botService;
        this.currentState = stateMap.get(BotStateEnum.INITIAL_STATE);  // 使用枚舉設置初始狀態
    }

    public void setState(BotStateEnum state) {
        this.currentState = stateMap.get(state);  // 使用枚舉來切換狀態
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

