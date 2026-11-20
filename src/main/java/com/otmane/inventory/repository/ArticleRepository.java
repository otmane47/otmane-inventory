package com.otmane.inventory.repository;

import com.otmane.inventory.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("select a from Article a where a.quantity <= a.alertThreshold order by a.quantity asc")
    List<Article> findLowStock();
}