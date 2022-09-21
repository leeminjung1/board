package com.leeminjung1.config;

import com.leeminjung1.domain.application.impl.MemberServiceImpl;
import com.leeminjung1.domain.model.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping(AuthRestController.URL_PREFIX)
public class AuthRestController {
    static final String URL_PREFIX = "/auth";
    static final String LOGIN = "/login";

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberServiceImpl userService;

    @RequestMapping(
            value = LOGIN,
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> login(
            final HttpServletRequest req,
            final HttpServletResponse res,
            @RequestBody Token.Request request) throws Exception {

        Member user = userService.findById(Long.parseLong(request.getId())).orElseThrow(() -> new IllegalArgumentException("없는 사용자입니다."));

        if(!request.getSecret().equals(user.getPassword())){
            throw new IllegalArgumentException("비밀번호를 확인하세요.");
        }

        Authentication authentication = new UserAuthentication(request.getId(), null, null);
        String token = JwtTokenProvider.generateToken(authentication);

        Token.Response response = Token.Response.builder().token(token).build();

        return ResponseEntity.ok(response);
    }

}
