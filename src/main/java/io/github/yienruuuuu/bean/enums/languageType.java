package io.github.yienruuuuu.bean.enums;

import lombok.Getter;

/**
 * @author Eric.Lee
 * Date: 2024/10/21
 */
@Getter
public enum languageType {
    //繁體中文
    ZH_TW("繁體中文"),
    //英文
    EN("English");

    private final String type;

    languageType(String type) {
        this.type = type;
    }
}