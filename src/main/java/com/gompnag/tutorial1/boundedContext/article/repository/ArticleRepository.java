package com.gompnag.tutorial1.boundedContext.article.repository;

import com.gompnag.tutorial1.boundedContext.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

// @Repository : 생략 가능
public interface ArticleRepository extends JpaRepository<Article, Long> {

}
