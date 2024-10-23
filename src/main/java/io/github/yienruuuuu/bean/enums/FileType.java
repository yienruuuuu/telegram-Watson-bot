package io.github.yienruuuuu.bean.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * @author Eric.Lee
 * Date:2024/10/23
 */
@Getter
public enum FileType {
    GIF("動圖GIF"),
    PIC("靜態圖片");

    private final String description;

    FileType(String description) {
        this.description = description;
    }

    // 靜態方法返回所有 ActiveType 的列表
    public static List<FileType> getAllTypes() {
        return Arrays.asList(FileType.values());
    }
}
