package io.github.yienruuuuu.repository;

import io.github.yienruuuuu.bean.entity.Text;
import io.github.yienruuuuu.bean.enums.LanguageType;
import io.github.yienruuuuu.bean.enums.TextType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TextRepository extends JpaRepository<Text, Integer> {
    List<Text> findTextByLanguageType(LanguageType languageType);

    List<Text> findTextByLanguageTypeAndType(LanguageType languageType, TextType type);
}