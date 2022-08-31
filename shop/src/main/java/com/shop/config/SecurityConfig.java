package com.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity //WebSecurityConfigurerAdapter 상속 클래스에 선언하면 SpringSecurityFilterChain 자동 포함
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Override //http 요청에 대한 보안 설정
    protected void configure(HttpSecurity http) throws Exception {

    }

    @Bean // 해시함수 이용하여 비밀번호 암호화
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
