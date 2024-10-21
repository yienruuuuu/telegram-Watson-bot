package io.github.yienruuuuu.service.business.impl;

import io.github.yienruuuuu.bean.entity.Bot;
import io.github.yienruuuuu.repository.BotRepository;
import io.github.yienruuuuu.service.business.BotService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Eric.Lee Date: 2024/10/16
 */
@Service
public class BotServiceImpl implements BotService {

  private final BotRepository botRepository;

  public BotServiceImpl(BotRepository botRepository) {
    this.botRepository = botRepository;
  }

  @Override
  public List<Bot> findAll() {
    return botRepository.findAll();
  }

  @Override
  public Bot findBotById(Integer id) {
    return botRepository.findById(id)
        .orElse(null);
  }
}
