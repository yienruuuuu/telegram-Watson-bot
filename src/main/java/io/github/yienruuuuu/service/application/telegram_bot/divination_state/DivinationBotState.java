package io.github.yienruuuuu.service.application.telegram_bot.divination_state;

import io.github.yienruuuuu.bean.entity.Bot;
import io.github.yienruuuuu.service.application.telegram_bot.DivinationBot;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author Eric.Lee
 * Date: 2024/10/21
 */
public interface DivinationBotState {
    void handleMessage(DivinationBot bot, Update update, Bot botEntity);

    void handleCallbackQuery(DivinationBot bot, Update update, Bot botEntity);
}
