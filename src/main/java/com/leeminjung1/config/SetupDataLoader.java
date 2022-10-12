package com.leeminjung1.config;

import com.leeminjung1.domain.model.article.Article;
import com.leeminjung1.domain.model.category.Category;
import com.leeminjung1.domain.model.comment.Comment;
import com.leeminjung1.domain.model.member.Member;
import com.leeminjung1.domain.model.member.Privilege;
import com.leeminjung1.domain.model.member.Role;
import com.leeminjung1.infrastructure.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    private final MemberRepository userRepository;
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;
    private final CategoryRepository categoryRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup)
            return;
        Privilege readPrivilege
                = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege
                = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        List<Privilege> adminPrivileges = Arrays.asList(
                readPrivilege, writePrivilege);
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));

        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        Member member = new Member("admin01", "hi@test.com", passwordEncoder.encode("pass"));
        member.setRoles(Arrays.asList(adminRole));
        member.setImgUrl("cafe_profile_363.png");
        userRepository.save(member);

        Category root = Category.getRoot();
        categoryRepository.save(root);

        Category category0 = Category.builder()
                .name("category0")
                .parent(root)
                .priority(0)
                .build();
        categoryRepository.save(category0);

        Category category00 = Category.builder()
                .name("category00")
                .parent(category0)
                .priority(0)
                .build();
        categoryRepository.save(category00);

        Category category01 = Category.builder()
                .parent(category0)
                .name("category01")
                .priority(1)
                .build();
        categoryRepository.save(category01);

        Category category1 = Category.builder()
                .name("category1")
                .parent(root)
                .priority(1)
                .build();
        categoryRepository.save(category1);

        Category category2 = Category.builder()
                .name("category2")
                .parent(root)
                .priority(2)
                .build();
        categoryRepository.save(category2);

        Category category20 = Category.builder()
                .name("category20")
                .parent(category2)
                .priority(0)
                .build();
        categoryRepository.save(category20);

        Article article = Article.builder()
                .author(member)
                .title("THIS IS TITLE")
                .content("<p><em><strong>hello!! this is example content:)</strong></em></p>")
                .category(category0)
                .createdAt(LocalDateTime.now())
                .voteCount(0)
                .viewCount(0)
                .build();
        articleRepository.save(article);

        Article article2 = Article.builder()
                .author(member)
                .title("test")
                .content("<p>hello</p><p>world</p>")
                .category(category0)
                .createdAt(LocalDateTime.now())
                .voteCount(0)
                .viewCount(0)
                .build();
        articleRepository.save(article2);

        Comment comment = Comment.builder()
                .parent(null)
                .commentLevel((byte) 0)
                .voteCount(0)
                .writer(member)
                .article(article2)
                .content("댓글!")
                .commentOrder(0)
                .build();
        commentRepository.save(comment);

        Comment comment0 = Comment.builder()
                .parent(null)
                .commentLevel((byte) 0)
                .voteCount(0)
                .writer(member)
                .article(article2)
                .content("댓글2")
                .commentOrder(0)
                .build();
        commentRepository.save(comment0);

        Comment comment1 = Comment.builder()
                .parent(comment)
                .commentLevel((byte) 1)
                .voteCount(0)
                .writer(member)
                .article(article2)
                .content("대댓글")
                .commentOrder(1)
                .build();
        commentRepository.save(comment1);

        Comment comment2 = Comment.builder()
                .parent(comment)
                .commentLevel((byte) 1)
                .voteCount(0)
                .writer(member)
                .article(article2)
                .content("대댓글2")
                .commentOrder(2)
                .build();
        commentRepository.save(comment2);

        alreadySetup = true;
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {

        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_ADMIN > ROLE_STAFF \n ROLE_STAFF > ROLE_USER";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

    @Bean
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy());
        return expressionHandler;
    }
}