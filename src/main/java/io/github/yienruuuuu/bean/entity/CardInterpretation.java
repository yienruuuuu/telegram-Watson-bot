package io.github.yienruuuuu.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "card_interpretation")
public class CardInterpretation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "position_id", nullable = false)
    @JsonIgnore
    private CardPosition cardPosition;

    @NotNull
    @Lob
    @Column(name = "content", nullable = false)
    private String content;

}