package com.leeminjung1.config;

import com.leeminjung1.domain.model.article.Article;
import com.leeminjung1.domain.model.category.Category;
import com.leeminjung1.domain.model.member.Member;
import com.leeminjung1.domain.model.member.Privilege;
import com.leeminjung1.domain.model.member.Role;
import com.leeminjung1.infrastructure.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

        Category category0 = new Category(null, "category0");
        Category category00 = new Category(category0, "category00");
        Category category1 = new Category(null, "category1");
        categoryRepository.save(category0);
        categoryRepository.save(category00);
        categoryRepository.save(category1);

        Article article = Article.builder()
                .author(member)
                .title("ㅉ 돌고래상가 누네띠네빵집 가면 이거 꼭 먹어라")
                .content("<div><span>‘아몬드소보로’</span></div><div><span>겉은 소보로 때문에 바삭바삭하고 속은 슈같이 가벼워서 5개는 거뜬히 먹을수있을맛임ㅇㅇ</span></div><div><span>약간 예전에 유행하던 모카번처럼 속이 비어있고 버터리하면서 약간 짭짤해서 단짠단짠 개미쳤음</span></div><div><span>누네띠네도 크고 바삭바삭해서 맛있긴했는데 보닌은 이게 더 맘에 드누</span></div>")
                .category(category0)
                .build();
        articleRepository.save(article);

        Article article2 = Article.builder()
                .author(member)
                .title("보닌 팤콘 이벤트 할때")
                .content("<div><span>사람 괘많아서 디엠 보낼때 두번 보내져서</span></div><div><span>졸라 애처로워보임ㅋㅋㅋㅋㅋ</span></div>")
                .category(category0)
                .build();
        articleRepository.save(article2);

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