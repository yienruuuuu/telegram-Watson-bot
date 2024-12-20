package io.github.yienruuuuu.bean.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * @author Eric.Lee
 * Date: 2024/10/23
 */
@Getter
public enum GifType {
    WELCOME_ANIMATION("歡迎gif"),
    CARD_DRAWING_ANIMATION("抽牌gif"),
    QUESTION_ANIMATION("疑問gif");

    private final String description;

    GifType(String description) {
        this.description = description;
    }

    // 靜態方法返回所有 ActiveType 的列表
    public static List<GifType> getAllTypes() {
        return Arrays.asList(GifType.values());
    }

}
