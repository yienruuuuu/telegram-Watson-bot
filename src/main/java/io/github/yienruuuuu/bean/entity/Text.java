package io.github.yienruuuuu.bean.entity;

import io.github.yienruuuuu.bean.enums.LanguageType;
import io.github.yienruuuuu.bean.enums.TextType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "text", schema = "tg_bot")
public class Text extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 50)
    private TextType type;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "language_type", nullable = false, length = 50)
    private LanguageType languageType;

    @NotNull
    @Column(name = "content", nullable = false)
    private String content;
}