package io.github.yienruuuuu.service.business.impl;

import io.github.yienruuuuu.bean.entity.CardPosition;
import io.github.yienruuuuu.bean.enums.TarotInterpretationType;
import io.github.yienruuuuu.repository.CardPositionRepository;
import io.github.yienruuuuu.service.business.CardPositionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Eric.Lee
 * Date:2024/10/26
 */
@Service
public class CardPositionServiceImpl implements CardPositionService {
    private final CardPositionRepository cardPositionRepository;

    public CardPositionServiceImpl(CardPositionRepository cardPositionRepository) {
        this.cardPositionRepository = cardPositionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CardPosition> findAll() {
        return cardPositionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CardPosition> findCardPositionsByInterpretationType(TarotInterpretationType interpretationType) {
        return cardPositionRepository.findCardPositionsByInterpretationType(interpretationType);
    }
}
