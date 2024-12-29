package com.gompnag.tutorial1.boundedContext.article.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Builder
public class Article {
  @Id // primary key가 id 칼럼에 속성 적용
  @GeneratedValue(strategy = IDENTITY)  // AUTO_INCREMENT
  private long id;
  private LocalDateTime createDate;
  private LocalDateTime modifyDate;
  private String subject;
  private String content;
}
