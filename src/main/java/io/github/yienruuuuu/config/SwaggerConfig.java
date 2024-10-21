package io.github.yienruuuuu.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SuppressWarnings("unused")
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Telegram bot專案 - OPEN API Document")
                        .description("Telegram BOT專案")
                        .license(new License().name("Yienruuuuu").url("https://github.com/yienruuuuu/telegram-Watson-bot")));
    }

    // Document Tab
    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("admin")  // 群組名稱
                .pathsToMatch("/admin/**")  // 定義包含的 API 路徑
                .build();
    }

    // Group API by another path (Example for "/public/**")
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")  // 群組名稱
                .pathsToMatch("/public/**")  // 定義另一組 API 路徑
                .build();
    }
}