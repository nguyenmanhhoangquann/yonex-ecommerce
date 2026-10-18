package com.yonex.repository;

import com.yonex.entity.NewsArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NewsArticleRepository extends JpaRepository<NewsArticle, Long> {
    Optional<NewsArticle> findBySlugAndIsPublishedTrue(String slug);
    List<NewsArticle> findTop6ByIsFeaturedTrueAndIsPublishedTrueOrderByPublishedAtDesc();
    Page<NewsArticle> findByIsPublishedTrueOrderByPublishedAtDesc(Pageable pageable);
    Page<NewsArticle> findBySportAndIsPublishedTrueOrderByPublishedAtDesc(String sport, Pageable pageable);
    boolean existsBySlug(String slug);
}
