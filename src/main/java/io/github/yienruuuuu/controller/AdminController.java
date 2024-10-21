package io.github.yienruuuuu.controller;

import io.github.yienruuuuu.bean.entity.Bot;
import io.github.yienruuuuu.service.business.BotService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Eric.Lee
 * Date: 2024/10/21
 */
@Slf4j
@Tag(name = "Administrator controller", description = "Manager Interface")
@RestController
@RequestMapping("admin")
public class AdminController {
    private final BotService botService;

    public AdminController(BotService botService) {
        this.botService = botService;
    }

    @GetMapping(value = "bots")
    public List<Bot> bots() {
        return botService.findAll();
    }
}
