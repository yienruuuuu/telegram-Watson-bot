package io.github.yienruuuuu.service.business.impl;

import com.github.benmanes.caffeine.cache.Cache;
import io.github.yienruuuuu.bean.entity.Bot;
import io.github.yienruuuuu.repository.BotRepository;
import io.github.yienruuuuu.service.business.BotService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Eric.Lee Date: 2024/10/16
 */
@Service
public class BotServiceImpl implements BotService {

    private final BotRepository botRepository;
    private final Cache<String, Bot> botCache;


    public BotServiceImpl(BotRepository botRepository, Cache<String, Bot> botCache) {
        this.botRepository = botRepository;
        this.botCache = botCache;
    }

    @Override
    public List<Bot> findAll() {
        return botRepository.findAll();
    }

    @Override
    public Bot findBotById(Integer id) {
        String cacheKey = "bot_" + id;

        // 先檢查緩存是否存在
        Bot cachedBot = botCache.getIfPresent(cacheKey);
        if (cachedBot != null) {
            return cachedBot;
        }

        // 若不存在則從資料庫查詢，並存入緩存
        Bot bot = botRepository.findById(id).orElse(null);
        if (bot != null) {
            botCache.put(cacheKey, bot);
        }
        return bot;
    }

    @Override
    @Transactional
    public Bot save(Bot bot) {
        // 保存實體到資料庫
        Bot savedBot = botRepository.save(bot);

        // 清理對應的緩存
        String cacheKey = "bot_" + savedBot.getId();
        botCache.invalidate(cacheKey);  // 清理緩存中的舊數據

        return savedBot;
    }
}
