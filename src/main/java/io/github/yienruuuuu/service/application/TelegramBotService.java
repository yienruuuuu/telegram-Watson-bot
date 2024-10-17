package io.github.yienruuuuu.service.application;

import io.github.yienruuuuu.service.application.telegram_bot.DivinationTelegramBot;
import io.github.yienruuuuu.service.business.BotService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * @author Eric.Lee Date: 2024/10/17
 */
@Service
public class TelegramBotService {

    private final DivinationTelegramBot divinationTelegramBot;
    private final BotService botService;

    public TelegramBotService(DivinationTelegramBot divinationTelegramBot, BotService botService) {
        this.divinationTelegramBot = divinationTelegramBot;
        this.botService = botService;
    }

    @PostConstruct
    public void registerBot() throws TelegramApiException {
        String botToken = botService.findBotById(1).getBotToken();
        try {
            TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();
            botsApplication.registerBot(botToken, divinationTelegramBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        System.out.println("Bot " + divinationTelegramBot + " registered successfully.");
    }

}
