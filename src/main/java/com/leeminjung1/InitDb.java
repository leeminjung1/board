/*
package com.leeminjung1;

import com.leeminjung1.domain.model.article.Article;
import com.leeminjung1.domain.model.category.Category;
import com.leeminjung1.domain.model.member.Member;
import com.leeminjung1.domain.model.member.MemberRole;
import com.leeminjung1.domain.model.member.Role;
import com.leeminjung1.domain.model.member.RoleName;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
//@RequiredArgsConstructor
public class InitDb {

    @Autowired
    private InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
//    @RequiredArgsConstructor
    static class InitService {
        @Autowired
        private EntityManager em;

        @Autowired
        private PasswordEncoder passwordEncoder;

        public static Role user;
        public static Role admin;

        private void makeRole() {
            user = new Role(RoleName.USER);
            admin = new Role(RoleName.ADMIN);
        }

        public void dbInit1() {
            makeRole();
            em.persist(user);
            em.persist(admin);

            Member member = createMember("user", "hello@gmail.com", passwordEncoder.encode("pass"));
            em.persist(member);

            MemberRole memberRole = new MemberRole();
            memberRole.setMember(member);
            memberRole.setRole(user);
            em.persist(memberRole);

            MemberRole memberRole2 = new MemberRole();
            memberRole2.setMember(member);
            memberRole2.setRole(admin);
            em.persist(memberRole2);

            ArrayList<MemberRole> memberRoles = new ArrayList<>();
            memberRoles.add(memberRole);
            memberRoles.add(memberRole2);
            member.setMemberRoles(memberRoles);
            em.persist(member);

            Category category = createCategory(null, "category0");
            em.persist(category);
            Category category1 = createCategory(category, "category01");
            em.persist(category1);

            Article article = createArticle(member, category1, "title00", "Initialized JPA EntityManagerFactory for persistence unit 'default'");
            em.persist(article);

        }

        public void dbInit2() {
            Member member = createMember("admin", "hello2@gmail.com", passwordEncoder.encode("pass"));
            em.persist(member);

            MemberRole memberRole = new MemberRole();
            memberRole.setMember(member);
            memberRole.setRole(admin);
            em.persist(memberRole);

            member.setMemberRoles(memberRole.getMember().getMemberRoles());
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
            Member member = Member.builder()
                    .username(username)
                    .email(email)
                    .password(password)
                    .build();

            return member;
        }

    }
}
*/
