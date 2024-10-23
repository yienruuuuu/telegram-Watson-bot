package io.github.yienruuuuu.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.yienruuuuu.bean.enums.ChangeFileBotStateEnum;
import io.github.yienruuuuu.bean.enums.DivinationBotStateEnum;
import io.github.yienruuuuu.service.application.telegram_bot.change_file_state.*;
import io.github.yienruuuuu.service.application.telegram_bot.divination_state.DivinationBotState;
import io.github.yienruuuuu.service.application.telegram_bot.divination_state.InitialDivinationState;
import io.github.yienruuuuu.service.application.telegram_bot.divination_state.PickCardState;
import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
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
    public Map<DivinationBotStateEnum, DivinationBotState> divinationStateMap(InitialDivinationState initialDivinationState,
                                                                              PickCardState pickCardState) {
        Map<DivinationBotStateEnum, DivinationBotState> stateMap = new EnumMap<>(DivinationBotStateEnum.class);
        stateMap.put(DivinationBotStateEnum.INITIAL_STATE, initialDivinationState);
        stateMap.put(DivinationBotStateEnum.PICK_CARD_STATE, pickCardState);
        return stateMap;
    }

    @Bean
    public Map<ChangeFileBotStateEnum, ChangeFileBotState> changeFileStateMap(InitialChangeFileState initialChangeFileState,
                                                                              UploadPicState uploadPicState,
                                                                              UploadGifState uploadGifState,
                                                                              DeletePicState deletePicState,
                                                                              DeleteGifState deleteGifState) {
        Map<ChangeFileBotStateEnum, ChangeFileBotState> stateMap = new EnumMap<>(ChangeFileBotStateEnum.class);
        stateMap.put(ChangeFileBotStateEnum.INITIAL_STATE, initialChangeFileState);
        stateMap.put(ChangeFileBotStateEnum.UPLOAD_PIC_STATE, uploadPicState);
        stateMap.put(ChangeFileBotStateEnum.DELETE_PIC_STATE, deletePicState);
        stateMap.put(ChangeFileBotStateEnum.UPLOAD_GIF_STATE, uploadGifState);
        stateMap.put(ChangeFileBotStateEnum.DELETE_GIF_STATE, deleteGifState);
        return stateMap;
    }
}
