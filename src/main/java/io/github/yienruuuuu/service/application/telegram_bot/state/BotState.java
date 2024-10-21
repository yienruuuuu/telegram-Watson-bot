package io.github.yienruuuuu.service.application.telegram_bot.state;

import io.github.yienruuuuu.bean.entity.Bot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author Eric.Lee
 * Date: 2024/10/21
 */
public interface BotState {
    void handleMessage(LongPollingSingleThreadUpdateConsumer bot, Update update, Bot botEntity);

    void handleCallbackQuery(LongPollingSingleThreadUpdateConsumer bot, Update update, Bot botEntity);
}
