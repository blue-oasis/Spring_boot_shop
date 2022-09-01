package com.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.shop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity //WebSecurityConfigurerAdapter 상속 클래스에 선언하면 SpringSecurityFilterChain 자동 포함
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MemberService memberService;
    
    @Override //http 요청에 대한 보안 설정
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
            .loginPage("/members/login") //로그인 페이지 url
            .defaultSuccessUrl("/") //로그인 성공시 이동
            .usernameParameter("email") // 로그인 시 사용할 파라미터
            .failureUrl("/members/login/error") //로그인 실패시 이동
            .and()
            .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout")) //로그아웃 url
            .logoutSuccessUrl("/"); //로그아웃 성공시 이동
    }

    @Bean // 해시함수 이용하여 비밀번호 암호화
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
        throws Exception {  //인증 AuthenticationManagerBuilder가 AuthenticationManager 생성
            auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
            //userDetailsService 구현객체 지정/ 비밀번호 암호화 passwordEncoder
        }
    
}
