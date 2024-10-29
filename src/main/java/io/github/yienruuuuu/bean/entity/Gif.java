package io.github.yienruuuuu.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.yienruuuuu.bean.enums.GifType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@Table(name = "gif", schema = "tg_bot")
@NoArgsConstructor
@AllArgsConstructor
public class Gif extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bot_id", nullable = false)
    @JsonIgnore
    private Bot bot;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 50)
    private GifType type;

    @NotNull
    @Column(name = "telegram_file_id", nullable = false)
    @Schema(description = "主BOT所存的fieldId")
    private String telegramFileId;

    @NotNull
    @Column(name = "file_bot_file_id", nullable = false)
    @Schema(description = "資源管理BOT所存的fieldId")
    private String fileBotFileId;

    @Lob
    @Column(name = "description")
    private String description;

}