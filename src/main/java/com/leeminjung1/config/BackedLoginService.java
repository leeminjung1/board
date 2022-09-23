package com.leeminjung1.config;

import com.leeminjung1.domain.model.member.Member;
import com.leeminjung1.domain.model.member.Privilege;
import com.leeminjung1.domain.model.member.Role;
import com.leeminjung1.infrastructure.repository.MemberRepository;
import com.leeminjung1.infrastructure.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.*;

@Service("userDetailsService")
@Transactional
@RequiredArgsConstructor
public class BackedLoginService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final HttpServletRequest request;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            final Member member = memberRepository.findByUsername(username).orElseThrow(IllegalArgumentException::new);
            if (member == null) {
//                return new User(" ", " ", true, true, true, true, getAuthorities(Arrays.asList(roleRepository.findByName("ROLE_USER"))));
                throw new UsernameNotFoundException("No member found with username: " + username);
            }
            return new org.springframework.security.core.userdetails.User(member.getUsername(), member.getPassword(), getAuthorities(member.getRoles()));
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(Collection<Role> roles) {
        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();
        for (Role role : roles) {
            privileges.add(role.getName());
            collection.addAll(role.getPrivileges());
        }
        for (Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

}