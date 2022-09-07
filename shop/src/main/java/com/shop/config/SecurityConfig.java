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

import org.springframework.security.config.annotation.web.builders.WebSecurity;

@Configuration
@EnableWebSecurity // WebSecurityConfigurerAdapter 상속 클래스에 선언하면 SpringSecurityFilterChain 자동 포함
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MemberService memberService;

  /*  @Override // http 요청에 대한 보안 설정
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/members/login") // 로그인 페이지 url
                .defaultSuccessUrl("/") // 로그인 성공시 이동
                .usernameParameter("email") // 로그인 시 사용할 파라미터
                .failureUrl("/members/login/error") // 로그인 실패시 이동
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout")) // 로그아웃 url
                .logoutSuccessUrl("/"); // 로그아웃 성공시 이동

        http.authorizeRequests()
                .mvcMatchers("/", "/members/**",
                        "/item/**", "/images/**").permitAll() // 모든 사용자가 인증없이 접속가능하도록 설정
                .mvcMatchers("/admin/**").hasRole("ADMIN") // /admin으로 시작하는 경로는 ADMIN역할만 접근가능
                .anyRequest().authenticated(); // 지정한 경로 제외 모두 인증요구 설정

        http.exceptionHandling() // 인증되지 않은 사용자 접근 시 수행되는 핸들러
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint());
    } */


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/members/login")
                .defaultSuccessUrl("/")
                .usernameParameter("email")
                .failureUrl("/members/login/error")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                .logoutSuccessUrl("/")
        ;

        http.authorizeRequests()
                .mvcMatchers("/", "/members/**", "/item/**", "/images/**").permitAll()
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
        ;

        http.exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
        ;
    }

    @Bean // 해시함수 이용하여 비밀번호 암호화
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception { // 인증 AuthenticationManagerBuilder가 AuthenticationManager 생성
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
        // userDetailsService 구현객체 지정/ 비밀번호 암호화 passwordEncoder
    }

    @Override // static 디렉토리 하위 파일은 인증무시 + 부트스트랩도..나중에 css폴더에 넣을까
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/**");
    }

}
