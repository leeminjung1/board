package com.leeminjung1.domain.application.service.impl;

import com.leeminjung1.domain.model.article.Article;
import com.leeminjung1.domain.model.article.ArticleLike;
import com.leeminjung1.infrastructure.repository.ArticleLikeRepository;
import com.leeminjung1.infrastructure.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleLikeService {
    private final ArticleLikeRepository articleLikeRepository;
    private final ArticleRepository articleRepository;

    @Transactional
    public void likeArticle(Long articleId, Long memberId) {
        articleLikeRepository.like(articleId, memberId);
    }

    @Transactional
    public void unlikeArticle(Long articleId, Long memberId) {
        articleLikeRepository.unlike(articleId, memberId);
    }

    public List<ArticleLike> findAllByArticleId(Long articleId) {
        return articleLikeRepository.findAllByArticleId(articleId);
    }

    public Page<Article> findArticleByMemberId(Pageable pageable, Long memberId) {
        return articleLikeRepository.findArticleByMemberId(pageable, memberId);
    }

    public long countArticleLikeByMemberId(Long memberId) {
        return articleLikeRepository.countByMemberId(memberId);
    }

    public int isMemberLikeArticle(Long memberId, Long articleId) {
        if (articleLikeRepository.findByArticleIdAndMemberId(articleId, memberId).isEmpty()) {
            return 0;
        }
        return 1;
    }

}
