package io.github.yienruuuuu.repository;

import io.github.yienruuuuu.bean.entity.Bot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BotRepository extends JpaRepository<Bot, Integer> {
}