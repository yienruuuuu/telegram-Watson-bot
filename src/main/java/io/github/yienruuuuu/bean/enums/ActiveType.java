package io.github.yienruuuuu.bean.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * @author Eric.Lee
 * Date:2024/10/23
 */
@Getter
public enum ActiveType {
    UPLOAD("上傳"),
    DELETE("刪除");

    private final String description;

    ActiveType(String description) {
        this.description = description;
    }

    // 靜態方法返回所有 ActiveType 的列表
    public static List<ActiveType> getAllTypes() {
        return Arrays.asList(ActiveType.values());
    }
}
