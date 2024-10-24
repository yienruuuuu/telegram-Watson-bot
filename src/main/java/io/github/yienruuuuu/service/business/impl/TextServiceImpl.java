package io.github.yienruuuuu.service.business.impl;

import io.github.yienruuuuu.bean.entity.Text;
import io.github.yienruuuuu.repository.TextRepository;
import io.github.yienruuuuu.bean.enums.LanguageType;
import io.github.yienruuuuu.bean.enums.TextType;
import io.github.yienruuuuu.service.business.TextService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Eric.Lee
 * Date: 2024/10/24
 */
@Service
public class TextServiceImpl implements TextService {
    private final TextRepository textRepository;

    public TextServiceImpl(TextRepository textRepository) {
        this.textRepository = textRepository;
    }

    @Override
    public List<Text> findTextByLanguageType(LanguageType languageType) {
        return textRepository.findTextByLanguageType(languageType);
    }

    @Override
    public List<Text> findTextByLanguageTypeAndTextType(LanguageType languageType, TextType textType) {
        return textRepository.findTextByLanguageTypeAndType(languageType, textType);
    }
}
