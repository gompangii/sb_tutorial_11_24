package com.gompnag.tutorial1.boundedContext.article.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Article {
  @Id // primary key가 id 칼럼에 속성 적용
  private long id;
  private String subject;
  private String content;
}
