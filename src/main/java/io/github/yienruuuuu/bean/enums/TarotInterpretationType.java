package io.github.yienruuuuu.bean.enums;

import lombok.Getter;

/**
 * @author Eric.Lee
 * Date:2024/10/25
 */
@Getter
public enum TarotInterpretationType {
    CURRENT_SITUATION("現狀"),
    OTHERS_THOUGHT("對方的想法"),
    FUTURE_SITUATION("未來");

    private final String chineseDescription;

    TarotInterpretationType(String chineseDescription) {
        this.chineseDescription = chineseDescription;
    }
}
