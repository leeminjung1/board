package com.leeminjung1.domain.application.impl;

import com.leeminjung1.domain.model.article.Article;
import com.leeminjung1.domain.model.article.ArticleLike;
import com.leeminjung1.infrastructure.repository.ArticleLikeRepository;
import com.leeminjung1.infrastructure.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleLikeService {
    private final ArticleLikeRepository articleLikeRepository;
    private final ArticleRepository articleRepository;

    @Transactional
    public void likeArticle(Long articleId, Long memberId) {
        articleLikeRepository.likeArticle(articleId, memberId);
    }

    @Transactional
    public void unlikeArticle(Long articleId, Long memberId) {
        articleLikeRepository.unlikeArticle(articleId, memberId);
    }

    public List<ArticleLike> findAllByArticleId(Long articleId) {
        return articleLikeRepository.findAllByArticleId(articleId);
    }

    public List<Article> findArticleByMemberId(Long memberId) {
        return articleLikeRepository.findArticleByMemberId(memberId);
    }

}
