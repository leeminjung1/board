package com.leeminjung1.domain.application.impl;

import com.leeminjung1.domain.application.ArticleService;
import com.leeminjung1.domain.application.dtos.ArticleListDto;
import com.leeminjung1.domain.model.article.Article;
import com.leeminjung1.domain.model.category.Category;
import com.leeminjung1.infrastructure.repository.ArticleLikeRepository;
import com.leeminjung1.infrastructure.repository.ArticleRepository;
import com.leeminjung1.infrastructure.repository.CategoryRepository;
import com.leeminjung1.infrastructure.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;
    private final CommentRepository commentRepository;
    private final ArticleLikeRepository likeRepository;

    @Override
    public Optional<Article> findArticleById(long articleId) {
        Optional<Article> article = articleRepository.findById(articleId);
        return article;
    }

    @Override
    public List<ArticleListDto> findAllArticles() {
        List<Article> articles = articleRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        List<ArticleListDto> list = new ArrayList<>();
        for (Article article : articles) {
            list.add(new ArticleListDto(article));
        }
        return list;
    }

    @Override
    public List<ArticleListDto> findArticlesByCategory(Long categoryId) {
        List<Article> articles = articleRepository.findByCategoryIdOrderByIdDesc(categoryId);
        List<ArticleListDto> list = new ArrayList<>();
        for (Article article : articles) {
            list.add(new ArticleListDto(article));
        }
        return list;
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

    @Override
    public void save(Article article) {
        articleRepository.save(article);
    }

    public List<ArticleListDto> findArticlesByAuthorId(Long authorId) {
        List<Article> articles = articleRepository.findByAuthorId(authorId);
        List<ArticleListDto> list = new ArrayList<>();
        for (Article article : articles) {
            list.add(new ArticleListDto(article));
        }
        return list;
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

}
