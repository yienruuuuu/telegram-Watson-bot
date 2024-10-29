package io.github.yienruuuuu.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.yienruuuuu.bean.enums.PicType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;


@Getter
@Setter
@Entity
@Builder
@Table(name = "pic", schema = "tg_bot")
@NoArgsConstructor
@AllArgsConstructor
public class Pic extends BaseEntity {
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
    @Column(name = "type", nullable = false)
    private PicType type;

    @NotNull
    @Column(name = "telegram_file_id", nullable = false)
    @Schema(description = "主BOT所存的fieldId")
    private String telegramFileId;

    @NotNull
    @Column(name = "file_bot_file_id", nullable = false)
    @Schema(description = "資源管理BOT所存的fieldId")
    private String fileBotFileId;

    @Column(name = "description")
    private String description;

}