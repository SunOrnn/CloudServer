package com.ornn.sso.filter;

import com.ornn.sso.filter.provider.UserNameAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author: CANHUI.WANG * @create: 2022-07-29
 */

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 处理授权用户信息的Service类
     */
    @Autowired
    UserDetailsService baseUserDetailsService;

    /**
     * 放开部分授权认证入口服务的访问限制
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatchers()
                .antMatchers("/login", "/oauth/authorize", "/oauth/check_token")
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login-error")
                .permitAll();

        http.csrf().disable();

    }

    /**
     * 授权认证管理配置
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(detailsAuthenticationProvider());
    }

    /**
     * 安全路径过滤
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/css/**", "/js/**", "/images/**", "/fonts/**", "/icons/**", "/favicons/**");
    }

    /**
     * 授权用户信息数据库提供者的对象和身份验证逻辑配置
     */
    @Bean
    public AbstractUserDetailsAuthenticationProvider detailsAuthenticationProvider() {
        UserNameAuthenticationProvider authProvider = new UserNameAuthenticationProvider();

        // 设置userDetailsService
        authProvider.setUserDetailsService(baseUserDetailsService);

        // 禁止隐藏未被发现的异常
        authProvider.setHideUserNotFoundExceptions(false);

        // 使用BCrypt进行密码的Hash运算
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder(6));

        return authProvider;
    }
}
