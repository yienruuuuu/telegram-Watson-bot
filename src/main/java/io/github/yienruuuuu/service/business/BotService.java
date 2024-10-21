package io.github.yienruuuuu.service.business;

import io.github.yienruuuuu.bean.entity.Bot;

import java.util.List;

/**
 * @author Eric.Lee Date: 2024/10/16
 */
public interface BotService {
    List<Bot> findAll();

    Bot findBotById(Integer id);
}
