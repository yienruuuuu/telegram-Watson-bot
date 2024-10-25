package io.github.yienruuuuu.bean.entity;

import io.github.yienruuuuu.bean.enums.TarotInterpretationType;
import io.github.yienruuuuu.bean.enums.TarotPosition;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "card_position")
public class CardPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "card_id", nullable = false)
    private TarotCard tarotCard;

    @Size(max = 50)
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "position", nullable = false, length = 50)
    private TarotPosition position;

    @Size(max = 50)
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "interpretation_type", nullable = false, length = 50)
    private TarotInterpretationType interpretationType;


    @OneToMany(mappedBy = "cardPosition", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<CardInterpretation> interpretations;
}