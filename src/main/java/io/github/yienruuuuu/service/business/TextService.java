package io.github.yienruuuuu.service.business;

import io.github.yienruuuuu.bean.entity.Text;
import io.github.yienruuuuu.bean.enums.LanguageType;
import io.github.yienruuuuu.bean.enums.TextType;

import java.util.List;

/**
 * @author Eric.Lee
 * Date: 2024/10/24
 */
public interface TextService {
    List<Text> findTextByLanguageType(LanguageType languageType);

    List<Text> findTextByLanguageTypeAndTextType(LanguageType languageType, TextType textType);
}
