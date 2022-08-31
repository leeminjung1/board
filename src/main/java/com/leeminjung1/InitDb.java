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
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            Member member = new Member();
            member.setUsername("user0");
            member.setEmail("hello@gmail.com");
            member.setCreatedDate(LocalDateTime.now());
            member.setPassword("password");
            member.setLastPasswordChanged(LocalDateTime.now());
            em.persist(member);

            Category category = new Category();
            category.setName("category0");
            em.persist(category);
            Category category1 = new Category();
            category1.setName("category01");
            category1.setParent(category);
            em.persist(category1);

            Article article = new Article();
            article.setAuthor(member);
            article.setCategory(category1);
            article.setTitle("hello this is title");
            article.setContent("hello world! this is article!");
            em.persist(article);
        }

    }
}
