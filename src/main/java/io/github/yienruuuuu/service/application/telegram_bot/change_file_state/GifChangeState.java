package io.github.yienruuuuu.service.application.telegram_bot.change_file_state;

import io.github.yienruuuuu.bean.entity.Bot;
import io.github.yienruuuuu.service.application.telegram_bot.ChangeFileBot;
import io.github.yienruuuuu.service.application.telegram_bot.TelegramBotClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * 初始狀態
 *
 * @author Eric.Lee
 * Date: 2024/10/21
 */
@Component
public class GifChangeState extends ChangeFileBaseState implements ChangeFileBotState {

    @Autowired
    public GifChangeState(TelegramBotClient telegramBotClient) {
        super(telegramBotClient);
    }

    @Override
    public void handleMessage(ChangeFileBot bot, Update update, Bot botEntity) {
    }

    @Override
    public void handleCallbackQuery(ChangeFileBot bot, Update update, Bot botEntity) {

    }
}
