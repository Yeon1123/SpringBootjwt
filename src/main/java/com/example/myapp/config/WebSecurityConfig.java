package com.example.myapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    //보안 설정: url별로 접근 권한을 줄 수 있다. 루트 페이지(기본홈) 을 제외한 모든 페이지는
    //루트 페이지 이외의 모든 사이트는 .anyRequest()authenticated(): 로그인이 되어야 한다.
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                .defaultSuccessUrl("/")
                    .and()
                .logout();
    }
}
