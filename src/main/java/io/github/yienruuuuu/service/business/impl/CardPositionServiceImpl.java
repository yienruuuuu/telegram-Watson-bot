package io.github.yienruuuuu.service.business.impl;

import com.github.benmanes.caffeine.cache.Cache;
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
    private final Cache<TarotInterpretationType, List<CardPosition>> cardCache;

    public CardPositionServiceImpl(CardPositionRepository cardPositionRepository, Cache<TarotInterpretationType, List<CardPosition>> cardCache) {
        this.cardPositionRepository = cardPositionRepository;
        this.cardCache = cardCache;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CardPosition> findAll() {
        return cardPositionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CardPosition> findCardPositionsByInterpretationType(TarotInterpretationType interpretationType) {
        // 先檢查緩存是否存在
        List<CardPosition> cachedCardList = cardCache.getIfPresent(interpretationType);
        if (cachedCardList != null) {
            return cachedCardList;
        }

        // 若不存在則從資料庫查詢，並存入緩存
        List<CardPosition> cardList = cardPositionRepository.findCardPositionsByInterpretationType(interpretationType);
        if (cardList != null) {
            cardCache.put(interpretationType, cardList);
        }
        return cardList;
    }
}
