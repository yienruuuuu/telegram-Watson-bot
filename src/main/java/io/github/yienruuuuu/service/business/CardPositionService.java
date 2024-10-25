package io.github.yienruuuuu.service.business;

import io.github.yienruuuuu.bean.entity.CardPosition;
import io.github.yienruuuuu.bean.enums.TarotInterpretationType;

import java.util.List;

/**
 * @author Eric.Lee Date: 2024/10/16
 */
public interface CardPositionService {
    List<CardPosition> findAll();

    List<CardPosition> findCardPositionsByInterpretationType(TarotInterpretationType interpretationType);
}
