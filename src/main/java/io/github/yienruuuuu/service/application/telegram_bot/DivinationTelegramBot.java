package io.github.yienruuuuu.service.application.telegram_bot;

import io.github.yienruuuuu.bean.entity.Bot;
import io.github.yienruuuuu.service.application.telegram_bot.state.BotState;
import io.github.yienruuuuu.service.business.BotService;
import io.github.yienruuuuu.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author Eric.Lee Date: 2024/10/16
 */

@Component
@Slf4j
public class DivinationTelegramBot implements LongPollingSingleThreadUpdateConsumer {
    // 當前狀態
    private BotState currentState;
    private final BotService botService;

    public DivinationTelegramBot(BotState currentState, BotService botService) {
        this.currentState = currentState;
        this.botService = botService;
    }

    public void setState(BotState state) {
        this.currentState = state;
    }

    @Override
    public void consume(Update update) {
        Integer botNumber = 1;
        Bot bot = botService.findBotById(botNumber);
        if (update.hasMessage() && update.getMessage().hasText()) {
            JsonUtils.parseJsonAndPrintLog("收到訊息", update);
            currentState.handleMessage(this, update, bot);
        } else if (update.hasCallbackQuery()) {
            JsonUtils.parseJsonAndPrintLog("收到訊息", update);
            currentState.handleCallbackQuery(this, update, bot);
        }
    }
}

