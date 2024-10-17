package io.github.yienruuuuu.service.application.telegram_bot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author Eric.Lee Date: 2024/10/16
 */

@Component
public class DivinationTelegramBot implements LongPollingSingleThreadUpdateConsumer {

    private final ObjectMapper objectMapper;

    public DivinationTelegramBot(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void consume(Update update) {
        // 檢查更新是否有消息並且訊息有文本
        if (update.hasMessage() && update.getMessage().hasText()) {
            try {
                System.out.println(objectMapper.writeValueAsString(update));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }
}

