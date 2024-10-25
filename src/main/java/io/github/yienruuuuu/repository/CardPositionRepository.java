package io.github.yienruuuuu.repository;

import io.github.yienruuuuu.bean.entity.CardPosition;
import io.github.yienruuuuu.bean.enums.TarotInterpretationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardPositionRepository extends JpaRepository<CardPosition, Integer> {
  List<CardPosition> findCardPositionsByInterpretationType(TarotInterpretationType interpretation);
}