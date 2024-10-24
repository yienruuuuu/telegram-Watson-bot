package io.github.yienruuuuu.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "bot", schema = "tg_bot")
public class Bot extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bot_id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "bot_username", nullable = false)
    private String botUsername;

    @Size(max = 512)
    @NotNull
    @Column(name = "bot_token", nullable = false, length = 512)
    @JsonIgnore
    private String botToken;

    @Size(max = 512)
    @Column(name = "description", length = 512)
    private String description;

    @OneToMany(mappedBy = "bot", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Language> languageSetting = new ArrayList<>();

    @OneToMany(mappedBy = "bot", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Gif> gifList = new ArrayList<>();

    @OneToMany(mappedBy = "bot", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Pic> picList = new ArrayList<>();

    @OneToMany(mappedBy = "bot", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Manager> managerList = new ArrayList<>();
}