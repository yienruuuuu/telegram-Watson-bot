package io.github.yienruuuuu.repository;

import io.github.yienruuuuu.bean.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Integer> {
}