package io.github.yienruuuuu.bean.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "bots", schema = "tg_bot")
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
  private String botToken;

  @Size(max = 512)
  @Column(name = "description", length = 512)
  private String description;
}