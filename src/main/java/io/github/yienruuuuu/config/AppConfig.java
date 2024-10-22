package io.github.yienruuuuu.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.yienruuuuu.bean.enums.BotStateEnum;
import io.github.yienruuuuu.service.application.telegram_bot.state.BotState;
import io.github.yienruuuuu.service.application.telegram_bot.state.InitialState;
import io.github.yienruuuuu.service.application.telegram_bot.state.PickCardState;
import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Eric.Lee Date: 2024/10/17
 */
@Configuration
@Getter
public class AppConfig {

    //單例 ObjectMapper 物件
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    @Bean
    public Map<BotStateEnum, BotState> stateMap(InitialState initialState, PickCardState pickCardState) {
        Map<BotStateEnum, BotState> stateMap = new HashMap<>();
        stateMap.put(BotStateEnum.INITIAL_STATE, initialState);
        stateMap.put(BotStateEnum.PICK_CARD_STATE, pickCardState);
        return stateMap;
    }
}
