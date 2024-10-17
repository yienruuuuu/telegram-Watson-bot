package io.github.yienruuuuu.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Eric.Lee Date: 2024/10/17
 */
@Configuration
@Getter
public class AppConfig {

  //單例 ObjectMapper 物件
  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }
}
