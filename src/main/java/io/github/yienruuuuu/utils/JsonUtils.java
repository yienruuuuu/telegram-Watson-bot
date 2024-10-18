package io.github.yienruuuuu.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Eric.Lee
 * Date: 2024/10/18
 */
@Slf4j
public class JsonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private JsonUtils() {
        // 拋出異常了防止透過反射呼叫私有建構子
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * 轉型 json 並打印 log
     */
    public static void parseJsonAndPrintLog(String objectDescription, Object obj) {
        try {
            log.info("{} : {} ", objectDescription, objectMapper.writeValueAsString(obj));
        } catch (JsonProcessingException e) {
            log.error("將更新物件解析為 json 字串時出錯", e);
        }
    }
}
