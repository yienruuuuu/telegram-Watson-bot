package io.github.yienruuuuu.bean.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * @author Eric.Lee
 * Date: 2024/10/23
 */
@Getter
public enum PicType {
    DIVINATION_PIC("每日運勢靜態圖片");

    private final String description;

    PicType(String description) {
        this.description = description;
    }

    // 靜態方法返回所有 ActiveType 的列表
    public static List<PicType> getAllTypes() {
        return Arrays.asList(PicType.values());
    }

}
