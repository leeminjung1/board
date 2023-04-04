package com.leeminjung1.domain.application.service.impl;

import com.leeminjung1.domain.application.dtos.ArticleListDto;
import com.leeminjung1.domain.application.dtos.ArticleRequestDto;
import com.leeminjung1.domain.model.article.Article;
import com.leeminjung1.domain.model.category.Category;
import com.leeminjung1.infrastructure.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {

    private final MemberRepository memberRepository;
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

    @Transactional(readOnly = true)
    public long countArticlesByCommentWriterId(Long writerId) {
        return articleRepository.countArticleCommentedByWriterId(writerId);
    }

    @Transactional(readOnly = true)
    public Optional<Article> findArticleById(long articleId) {
        return articleRepository.findById(articleId);
    }

    @Transactional(readOnly = true)
    public Page<ArticleListDto> findAllArticles(Pageable pageable) {
        List<ArticleListDto> list = articleRepository.findAllByOrderByIdDesc(pageable).stream()
                .map(ArticleListDto::new)
                .collect(Collectors.toList());

        return new PageImpl<>(list);
    }

    @Transactional(readOnly = true)
    public Page<ArticleListDto> findArticlesByCategory(Pageable pageable, Long categoryId) {
        return articleRepository.findByCategoryIdOrderByIdDesc(pageable, categoryId)
                .map(ArticleListDto::new);
    }

    @Transactional(readOnly = true)
    public Page<ArticleListDto> findArticlesThatCommentedByMemberId(Pageable pageable, Long memberId) {
        return commentRepository.findArticlesByWriterIdOrderByIdDesc(pageable, memberId)
                .map(ArticleListDto::new);
    }

    @Transactional(readOnly = true)
    public Page<ArticleListDto> findArticlesThatLikedByMemberId(Pageable pageable, Long memberId) {
        return likeRepository.findArticleByMemberId(pageable, memberId)
                .map(ArticleListDto::new);
    }

    @Transactional(readOnly = true)
    public Category findCategoryByCategoryId(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow();
    }

    @Transactional(readOnly = true)
    public Page<ArticleListDto> findArticlesByAuthorId(Pageable pageable, Long authorId) {
        return articleRepository.findByAuthorIdOrderByIdDesc(pageable, authorId)
                .map(ArticleListDto::new);
    }

    @Transactional(readOnly = true)
    public List<ArticleListDto> findNoticeArticleTop20() {
        return articleRepository.findTop20ByIsNoticeOrderByIdDesc((byte) 1).stream()
                .map(ArticleListDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long save(String userName, ArticleRequestDto dto) {
        return articleRepository.save(Article.builder()
                        .title(dto.getTitle())
                        .content(dto.getContent())
                        .category(categoryRepository.findById(dto.getCategoryId()).orElseThrow())
                        .author(memberRepository.findByUsername(userName).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 멤버")))
                        .viewCount(0)
                        .likeCount(0)
                        .createdAt(LocalDateTime.now())
                        .isNotice((byte) (dto.getIsNotice() ? 1 : 0))
                        .build())
                .getId();
    }

    @Transactional
    public void update(Long id, ArticleRequestDto dto) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        articleRepository.update(id, dto.getTitle(), dto.getContent(), dto.getCategoryId(), (byte) (dto.getIsNotice() ? 1 : 0));
    }

    @Transactional
    public void updateViewCount(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글 입니다"));
        article.updateViewCount();
    }

    @Transactional
    public void deleteById(Long id) {
        articleRepository.deleteById(id);
    }

    @Transactional
    public void updateLikeCount(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글 입니다"));
        article.updateLikeCount();
    }

    @Transactional(readOnly = true)
    public List<ArticleListDto> findNoticeArticleDtos() {
        return articleRepository.findByIsNotice((byte) 1).stream()
                .map(ArticleListDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Article> findNoticeArticles() {
        return articleRepository.findByIsNotice((byte) 1);
    }

    @Transactional
    public void deleteByIdIn(List<Long> ids) {
        articleRepository.deleteByIdIn(ids);
    }
}
