package io.github.yienruuuuu.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.yienruuuuu.bean.enums.ChangeFileBotStateEnum;
import io.github.yienruuuuu.bean.enums.DivinationBotStateEnum;
import io.github.yienruuuuu.service.application.telegram_bot.change_file_state.ChangeFileBotState;
import io.github.yienruuuuu.service.application.telegram_bot.change_file_state.GifChangeState;
import io.github.yienruuuuu.service.application.telegram_bot.change_file_state.InitialChangeFileState;
import io.github.yienruuuuu.service.application.telegram_bot.change_file_state.PicChangeState;
import io.github.yienruuuuu.service.application.telegram_bot.divination_state.DivinationBotState;
import io.github.yienruuuuu.service.application.telegram_bot.divination_state.InitialDivinationState;
import io.github.yienruuuuu.service.application.telegram_bot.divination_state.PickCardState;
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
    public Map<DivinationBotStateEnum, DivinationBotState> DivinationStateMap(InitialDivinationState initialDivinationState,
                                                                              PickCardState pickCardState) {
        Map<DivinationBotStateEnum, DivinationBotState> stateMap = new HashMap<>();
        stateMap.put(DivinationBotStateEnum.INITIAL_STATE, initialDivinationState);
        stateMap.put(DivinationBotStateEnum.PICK_CARD_STATE, pickCardState);
        return stateMap;
    }

    @Bean
    public Map<ChangeFileBotStateEnum, ChangeFileBotState> ChangeFileStateMap(InitialChangeFileState initialChangeFileState,
                                                                              PicChangeState picChangeState,
                                                                              GifChangeState gifChangeState) {
        Map<ChangeFileBotStateEnum, ChangeFileBotState> stateMap = new HashMap<>();
        stateMap.put(ChangeFileBotStateEnum.INITIAL_STATE, initialChangeFileState);
        stateMap.put(ChangeFileBotStateEnum.PIC_CHANGE_STATE, picChangeState);
        stateMap.put(ChangeFileBotStateEnum.GIF_CHANGE_STATE, gifChangeState);
        return stateMap;
    }
}
