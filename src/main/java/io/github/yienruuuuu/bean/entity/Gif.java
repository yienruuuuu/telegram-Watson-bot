package io.github.yienruuuuu.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.yienruuuuu.bean.enums.GifType;
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
    private String telegramFileId;

    @Lob
    @Column(name = "description")
    private String description;

}