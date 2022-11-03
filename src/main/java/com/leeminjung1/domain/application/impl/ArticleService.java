package com.leeminjung1.domain.application.impl;

import com.leeminjung1.domain.application.dtos.ArticleListDto;
import com.leeminjung1.domain.application.dtos.ArticleRequestDto;
import com.leeminjung1.domain.model.article.Article;
import com.leeminjung1.domain.model.category.Category;
import com.leeminjung1.infrastructure.repository.ArticleLikeRepository;
import com.leeminjung1.infrastructure.repository.ArticleRepository;
import com.leeminjung1.infrastructure.repository.CategoryRepository;
import com.leeminjung1.infrastructure.repository.CommentRepository;
import com.leeminjung1.infrastructure.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;
    private final CommentRepository commentRepository;
    private final ArticleLikeRepository likeRepository;

    public long countAll() {
        return articleRepository.count();
    }

    public long countAllByCategoryId(Long categoryId) {
        return articleRepository.countByCategoryId(categoryId);
    }

    public long countAllByAuthorId(Long authorId) {
        return articleRepository.countByAuthorId(authorId);
    }

    public Optional<Article> findArticleById(long articleId) {
        Optional<Article> article = articleRepository.findById(articleId);
        return article;
    }

    public Page<ArticleListDto> findAllArticles(Pageable pageable) {
        Page<Article> articles = articleRepository.findAllByOrderByIdDesc(pageable);
        return articles.map(ArticleListDto::new);
    }

    public Page<ArticleListDto> findArticlesByCategory(Pageable pageable, Long categoryId) {
        Page<Article> articles = articleRepository.findByCategoryIdOrderByIdDesc(pageable, categoryId);
        return articles.map(ArticleListDto::new);
    }

    public List<ArticleListDto> findArticlesThatCommentedByMemberId(Long memberId) {
        List<Article> articles = commentRepository.findAllArticleByWriterId(memberId);
        List<ArticleListDto> list = new ArrayList<>();
        for (Article article : articles) {
            list.add(new ArticleListDto(article));
        }
        return list;
    }

    public List<ArticleListDto> findArticlesThatLikedByMemberId(Long memberId) {
        List<Article> articles = likeRepository.findArticleByMemberId(memberId);
        List<ArticleListDto> list = new ArrayList<>();
        for (Article article : articles) {
            list.add(new ArticleListDto(article));
        }
        return list;
    }

    public Category findCategoryByCategoryId(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow();
    }

    public Long save(Article article) {
        Article save = articleRepository.save(article);
        return save.getId();
    }

    public Page<ArticleListDto> findArticlesByAuthorId(Pageable pageable, Long authorId) {
        Page<Article> articles = articleRepository.findByAuthorIdOrderByIdDesc(pageable, authorId);
        return articles.map(ArticleListDto::new);
    }

    @Transactional
    public void updateViewCount(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글 입니다"));
        article.setViewCount(article.getViewCount() + 1);
        articleRepository.save(article);
    }

    public void deleteById(Long id) {
        articleRepository.deleteById(id);
    }

    public void updateLikeCount(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글 입니다"));
        article.setLikeCount(likeRepository.findAllByArticleId(id).size());
        articleRepository.save(article);
    }

    public List<ArticleListDto> findNoticeArticleTop20() {
        return articleRepository.findTop20ByIsNoticeOrderByIdDesc((byte) 1).stream().map(ArticleListDto::new).collect(Collectors.toList());
    }

    public List<ArticleListDto> findNoticeArticleDtos() {
        List<Article> notices = articleRepository.findByIsNotice((byte) 1);
        List<ArticleListDto> list = new ArrayList<>();
        for (Article article : notices) {
            list.add(new ArticleListDto(article));
        }
        return list;
    }

    public List<Article> findNoticeArticles() {
        return articleRepository.findByIsNotice((byte) 1);
    }
}
