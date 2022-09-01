package com.leeminjung1;

import com.leeminjung1.domain.model.article.Article;
import com.leeminjung1.domain.model.category.Category;
import com.leeminjung1.domain.model.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            Member member = createMember("member1", "hello@gmail.com", "password");
            em.persist(member);

            Category category = createCategory(null, "category0");
            em.persist(category);
            Category category1 = createCategory(category, "category01");
            em.persist(category1);

            Article article = createArticle(member, category1, "title00", "Initialized JPA EntityManagerFactory for persistence unit 'default'");
            em.persist(article);
        }

        public void dbInit2() {
            Member member = createMember("member2", "hello2@gmail.com", "password2");
            em.persist(member);

            Category category = createCategory(null, "category1");
            em.persist(category);

            Article article = createArticle(member, category, "title01", "Initialized JPA EntityManagerFactory for persistence unit 'default'");
            em.persist(article);
            Article article1 = createArticle(member, category, "title02", "Initialized JPA EntityManagerFactory for persistence unit 'default'");
            em.persist(article1);
        }

        private Article createArticle(Member member, Category category, String title, String content) {
            Article article = new Article();
            article.setAuthor(member);
            article.setCategory(category);
            article.setTitle(title);
            article.setContent(content);
            return article;
        }

        private Category createCategory(Category parent, String name) {
            Category category = new Category();
            category.setName(name);
            category.setParent(parent);
            return category;
        }

        private Member createMember(String username, String email, String password) {
            Member member = new Member();
            member.setUsername(username);
            member.setEmail(email);
            member.setPassword(password);
            member.setCreatedDate(LocalDateTime.now());
            member.setLastPasswordChanged(LocalDateTime.now());

            return member;
        }

    }
}
