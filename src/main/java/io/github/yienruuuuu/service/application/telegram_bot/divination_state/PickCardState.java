package io.github.yienruuuuu.service.application.telegram_bot.divination_state;

import io.github.yienruuuuu.bean.entity.Bot;
import io.github.yienruuuuu.bean.entity.CardPosition;
import io.github.yienruuuuu.bean.enums.DivinationBotStateEnum;
import io.github.yienruuuuu.bean.enums.PicType;
import io.github.yienruuuuu.bean.enums.TarotInterpretationType;
import io.github.yienruuuuu.service.application.telegram_bot.DivinationBot;
import io.github.yienruuuuu.service.application.telegram_bot.TelegramBotClient;
import io.github.yienruuuuu.service.business.CardPositionService;
import io.github.yienruuuuu.service.business.TextService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

/**
 * 初始狀態
 *
 * @author Eric.Lee
 * Date: 2024/10/21
 */
@Component
@Slf4j
public class PickCardState extends DivinationBaseState implements DivinationBotState {


    public PickCardState(TelegramBotClient telegramBotClient, TextService textService, CardPositionService cardPositionService) {
        super(telegramBotClient, textService, cardPositionService);
    }

    @Override
    public void handleMessage(DivinationBot bot, Update update, Bot botEntity) {
        bot.setState(String.valueOf(update.getMessage().getChatId()), DivinationBotStateEnum.INITIAL_STATE);
        bot.consume(update);
    }

    @Override
    public void handleCallbackQuery(DivinationBot bot, Update update, Bot botEntity) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        String chatId = String.valueOf(callbackQuery.getMessage().getChatId());
        int messageId = callbackQuery.getMessage().getMessageId();
        // 移除按鈕
        telegramBotClient.send(new DeleteMessage(chatId, messageId), botEntity);
        List<CardPosition> currentSituation = cardPositionService.findCardPositionsByInterpretationType(TarotInterpretationType.CURRENT_SITUATION);
        List<CardPosition> othersThought = cardPositionService.findCardPositionsByInterpretationType(TarotInterpretationType.OTHERS_THOUGHT);
        List<CardPosition> futureSituation = cardPositionService.findCardPositionsByInterpretationType(TarotInterpretationType.FUTURE_SITUATION);

        SendPhoto sendPhoto = SendPhoto.builder()
                .photo(new InputFile(randomPic(botEntity, PicType.DIVINATION_PIC)))
                .chatId(chatId)
                .caption(generateCompleteCardDisplay(currentSituation, othersThought, futureSituation))
                .build();

        // 這裡是傳送訊息的部分
        telegramBotClient.send(sendPhoto, botEntity);
    }
}
