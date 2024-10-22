package io.github.yienruuuuu.service.application.telegram_bot.state;

import io.github.yienruuuuu.bean.entity.Bot;
import io.github.yienruuuuu.service.application.telegram_bot.DivinationTelegramBot;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author Eric.Lee
 * Date: 2024/10/21
 */
public interface BotState {
    void handleMessage(DivinationTelegramBot bot, Update update, Bot botEntity);

    void handleCallbackQuery(DivinationTelegramBot bot, Update update, Bot botEntity);
}
