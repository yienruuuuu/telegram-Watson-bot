package io.github.yienruuuuu.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.yienruuuuu.bean.enums.LanguageType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "language", schema = "tg_bot")
public class Language{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bot_id", nullable = false)
    @JsonIgnore
    private Bot bot;

    @Size(max = 10)
    @NotNull
    @Column(name = "language_code", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    @Schema(description = "語言代碼")
    private LanguageType languageCode;

    @Size(max = 50)
    @NotNull
    @Column(name = "language_name", nullable = false, length = 50)
    private String languageName;

}