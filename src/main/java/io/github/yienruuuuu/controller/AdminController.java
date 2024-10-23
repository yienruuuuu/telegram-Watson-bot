package io.github.yienruuuuu.controller;

import io.github.yienruuuuu.bean.entity.Bot;
import io.github.yienruuuuu.service.application.telegram_bot.TelegramBotClient;
import io.github.yienruuuuu.service.business.BotService;
import io.github.yienruuuuu.utils.JsonUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
    private final TelegramBotClient telegramBotClient;

    public AdminController(BotService botService, TelegramBotClient telegramBotClient) {
        this.botService = botService;
        this.telegramBotClient = telegramBotClient;
    }

    @Schema(description = "測試取得全部的bot")
    @GetMapping(value = "bots")
    public List<Bot> bots() {
        return botService.findAll();
    }

    @Schema(description = "測試傳送訊息給特定的chatId")
    @PostMapping(value = "bots/sendMessage")
    public void sendMessage() {
        String fileId = "CgACAgUAAxkBAAN_ZxceZdd_Lec6WQVxK9D4sWhwnCsAApQBAALiPhFUA836rU1-ARk2BA";
        SendAnimation msg = SendAnimation
                .builder()
                .chatId("7623887780")
                .animation(new InputFile(fileId))
                .caption("請選擇您的每日幸運卡牌(可能含有色情圖片，請確認是否已滿18歲)")
                .build();

        // 創建 Inline Keyboard 的行和按鈕
        List<InlineKeyboardRow> rows = new ArrayList<>();
        InlineKeyboardRow rowInline = new InlineKeyboardRow();
        addButtonText(rowInline, "♈️");
        addButtonText(rowInline, "☯️");
        addButtonText(rowInline, "☸");
        addButtonText(rowInline, "⚛");
        rows.add(rowInline);

        // 設置鍵盤的按鈕
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup(rows);
        msg.setReplyMarkup(markupInline);
        telegramBotClient.send(msg,botService.findBotById(1));
    }

    @Schema(description = "測試下載gif檔案")
    @PostMapping(value = "bots/getFile")
    public ResponseEntity<InputStreamResource> getFile() throws FileNotFoundException {
        String fileId = "CgACAgUAAxkBAAN_ZxceZdd_Lec6WQVxK9D4sWhwnCsAApQBAALiPhFUA836rU1-ARk2BA";
        File file = telegramBotClient.getFile(new GetFile(fileId),botService.findBotById(1));
        java.io.File downloadedFile = telegramBotClient.downloadFile(file,botService.findBotById(1));  // 返回下載的文件

        if (downloadedFile == null) {
            // 文件下載失敗時的處理
            return ResponseEntity.status(500).body(null);
        }
        // 使用 InputStreamResource 來返回文件流
        InputStreamResource resource = new InputStreamResource(new FileInputStream(downloadedFile));
        String newFileName = "downloaded.mp4";

        // 設定回應頭，讓瀏覽器觸發下載
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + newFileName) // 設置文件名
                .contentType(MediaType.APPLICATION_OCTET_STREAM)  // 設置為通用的二進制文件類型
                .contentLength(downloadedFile.length())  // 設置文件大小
                .body(resource);  // 返回文件流
    }

    @Schema(description = "測試傳送photo")
    @PostMapping(value = "bots/sendPhoto")
    public void sendPhoto() {
        String fileId = "AgACAgUAAxkBAAO9ZxhzfvQVvLPryXjwq_XjIhIb82IAAtbBMRvjc8lUnLZGmtbcCEQBAAMCAANzAAM2BA";
        SendPhoto msg = SendPhoto
                .builder()
                .chatId("1513052214")
                .photo(new InputFile(fileId))
                .caption("請選擇您的每日幸運卡牌(可能含有色情圖片，請確認是否已滿18歲)")
                .build();

        Message mss = telegramBotClient.send(msg,botService.findBotById(1));
        JsonUtils.parseJsonAndPrintLog("收到響應", mss);
    }


    private void addButtonText(InlineKeyboardRow rowInline, String sign) {
        // 遍歷每個語言類型並為每個語言創建一個按鈕
        InlineKeyboardButton button = InlineKeyboardButton.builder()
                .text(sign)  // 按鈕顯示語言名稱
                .callbackData("choose_card")  // callback data 用於區分
                .build();
        // 為每個按鈕創建一行並添加
        rowInline.add(button);
    }
}