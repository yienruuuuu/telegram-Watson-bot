package io.github.yienruuuuu.service.application.telegram_bot.change_file_state;

import io.github.yienruuuuu.bean.entity.Bot;
import io.github.yienruuuuu.service.application.telegram_bot.ChangeFileBot;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author Eric.Lee
 * Date: 2024/10/21
 */
public interface ChangeFileBotState {
    void handleMessage(ChangeFileBot bot, Update update, Bot botEntity);

    void handleCallbackQuery(ChangeFileBot bot, Update update, Bot botEntity, Bot mainBotEntity);

    void handleFileUpdate(ChangeFileBot bot, Update update, Bot botEntity, Bot mainBotEntity);
}
