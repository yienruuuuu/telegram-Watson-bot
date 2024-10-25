package io.github.yienruuuuu.bean.enums;

import lombok.Getter;

/**
 * @author Eric.Lee
 * Date:2024/10/25
 */
@Getter
public enum TarotPosition {
    UPRIGHT("正位"),
    REVERSED("逆位");

    private final String chineseDescription;

    TarotPosition(String chineseDescription) {
        this.chineseDescription = chineseDescription;
    }
}
