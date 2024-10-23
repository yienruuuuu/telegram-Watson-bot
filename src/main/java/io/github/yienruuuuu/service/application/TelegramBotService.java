package io.github.yienruuuuu.service.application;

import io.github.yienruuuuu.bean.entity.Bot;
import io.github.yienruuuuu.service.application.telegram_bot.DivinationBot;
import io.github.yienruuuuu.service.application.telegram_bot.ChangeFileBot;
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
    // 每日運勢機器人
    private final DivinationBot divinationBot;
    // 上傳檔案機器人
    private final ChangeFileBot changeFileBot;
    private final BotService botService;


    public TelegramBotService(DivinationBot divinationBot, ChangeFileBot changeFileBot, BotService botService) {
        this.divinationBot = divinationBot;
        this.changeFileBot = changeFileBot;
        this.botService = botService;
    }

    @PostConstruct
    public void registerBot() {
        Bot divinationBot = botService.findBotById(1);
        Bot changeFileBot = botService.findBotById(2);
        try {
            botsApplication = new TelegramBotsLongPollingApplication();
            botsApplication.registerBot(divinationBot.getBotToken(), this.divinationBot);
            botsApplication.registerBot(changeFileBot.getBotToken(), this.changeFileBot);
        } catch (TelegramApiException e) {
            log.error("機器人註冊發生錯誤 , 錯誤訊息 : ", e);
        }
    }

    @PreDestroy
    public void shutdownBot() throws Exception {
        if (botsApplication != null) {
            log.info("關閉機器人並釋放資源");
            botsApplication.close();
        }
    }
}
