package io.github.yienruuuuu.service.application;

import io.github.yienruuuuu.bean.entity.Bot;
import io.github.yienruuuuu.service.application.telegram_bot.DivinationTelegramBot;
import io.github.yienruuuuu.service.business.BotService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * @author Eric.Lee Date: 2024/10/17
 */
@Service
@Slf4j
public class TelegramBotService {

    // TG長輪巡物件
    private TelegramBotsLongPollingApplication botsApplication;
    // 占卜機器人
    private final DivinationTelegramBot divinationTelegramBot;
    private final BotService botService;


    public TelegramBotService(DivinationTelegramBot divinationTelegramBot, BotService botService) {
        this.divinationTelegramBot = divinationTelegramBot;
        this.botService = botService;
    }

    @PostConstruct
    public void registerBot() {
        Bot bot = botService.findBotById(1);
        try {
            botsApplication = new TelegramBotsLongPollingApplication();
            botsApplication.registerBot(bot.getBotToken(), divinationTelegramBot);
        } catch (TelegramApiException e) {
            log.error("機器人註冊發生錯誤 , 錯誤訊息 : ", e);
        }
        log.info("機器人: {},註冊成功", bot.getBotUsername());
    }

    @PreDestroy
    public void shutdownBot() throws Exception {
        if (botsApplication != null) {
            log.info("關閉機器人並釋放資源");
            botsApplication.close();
        }
    }
}
