package io.github.yienruuuuu.service.application.telegram_bot;

import io.github.yienruuuuu.bean.entity.Bot;
import io.github.yienruuuuu.bean.enums.ChangeFileBotStateEnum;
import io.github.yienruuuuu.service.application.telegram_bot.change_file_state.ChangeFileBotState;
import io.github.yienruuuuu.service.business.BotService;
import io.github.yienruuuuu.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

/**
 * 上傳檔案機器人
 *
 * @author Eric.Lee
 * Date: 2024/10/23
 */
@Component
@Slf4j
public class ChangeFileBot implements LongPollingSingleThreadUpdateConsumer {
    private final Map<ChangeFileBotStateEnum, ChangeFileBotState> stateMap;
    private ChangeFileBotState currentState;
    private final BotService botService;

    public ChangeFileBot(Map<ChangeFileBotStateEnum, ChangeFileBotState> stateMap, BotService botService) {
        this.stateMap = stateMap;
        this.botService = botService;
        // 設置初始狀態
        this.currentState = stateMap.get(ChangeFileBotStateEnum.INITIAL_STATE);
    }

    /**
     * 切換狀態
     */
    public void setState(ChangeFileBotStateEnum state) {
        this.currentState = stateMap.get(state);
    }

    @Override
    public void consume(Update update) {
        Integer botNumber = 2;
        Bot bot = botService.findBotById(botNumber);
        JsonUtils.parseJsonAndPrintLog("收到訊息", update);
        if (!hasPermissions(bot, update)) {
            log.warn("無權限者試圖訪問BOT! update: {}", JsonUtils.parseJson(update));
            return;
        }

        if (update.hasMessage() && update.getMessage().hasText()) {
            currentState.handleMessage(this, update, bot);
        } else if (update.hasCallbackQuery()) {
            currentState.handleCallbackQuery(this, update, bot);
        } else {
            currentState.handleFileUpdate(this, update, bot);
        }
    }


    /**
     * 檢查權限
     */
    private boolean hasPermissions(Bot botEntity, Update update) {
        Long fromId = update.hasMessage() ? update.getMessage().getFrom().getId() : update.getCallbackQuery().getFrom().getId();
        return botEntity.getManagerList().stream()
                .anyMatch(t -> t.getTelegramId().equals(String.valueOf(fromId)));
    }
}
