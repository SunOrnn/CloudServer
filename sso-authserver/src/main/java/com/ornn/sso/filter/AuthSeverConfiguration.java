package com.ornn.sso.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

/**
 * @author: CANHUI.WANG * @create: 2022-07-28
 */

public class AuthSeverConfiguration extends AuthorizationServerConfigurerAdapter {

    /**
     * JDBC数据源依赖
     */
    @Autowired
    private DataSource dataSource;

    /**
     * 授权认证管理接口
     */
    AuthenticationManager authenticationManager;

    /**
     * 构造方法
     */
    public AuthSeverConfiguration(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * 通过JDBC操作数据库，实现对客户端信息的管理
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(new JdbcClientDetailsService(dataSource));
    }

    /**
     * 配置授权认证微服务相关的服务端点
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer clients) throws Exception {
        // 配置TokenService参数
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(getJdbcTokenStore());

        // 支持访问令牌的刷新
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setReuseRefreshToken(false);

        // 设置accessToken的有效时间，这里设置为30天
        tokenServices.setAccessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30));

        // 设置refreshToken的有效时间，这里设置为15天
        tokenServices.setRefreshTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(15));
        tokenServices.setClientDetailsService(getJdbcClientDetailsService());

        // 数据库管理授权信息
        clients.authenticationManager(this.authenticationManager)
                .accessTokenConverter(jwtAccessTokenConverter())
                .tokenStore(getJdbcTokenStore())
                .tokenServices(tokenServices)
                .authorizationCodeServices(getJdbcAuthorizationCodeServices())
                .approvalStore(getJdbcApprovalStore());


    }

    /**
     * 安全约束配置
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("hasAuthority('Role_TRUSTED_CLIENT')")
                .allowFormAuthenticationForClients();
    }

    @Bean
    /**
     * 数据库管理的用户授权确认记录
     * @return
     */
    public ApprovalStore getJdbcApprovalStore() {
        return new JdbcApprovalStore(dataSource);
    }

    @Bean
    /**
     * 数据库管理的授权码信息
     * @return
     */
    public AuthorizationCodeServices getJdbcAuthorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    /**
     * AccessToken颁发管理（使用非对称加密算法来对Token进行签名
     * @return
     */
    public AccessTokenConverter jwtAccessTokenConverter() {
        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        // 导入证书
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("keystore.jks"), "mypass".toCharArray());
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("mytest"));
        return converter;
    }

    /**
     * 数据库管理的客户端信息
     * @return
     */
    @Bean
    public ClientDetailsService getJdbcClientDetailsService() {
        return new JdbcClientDetailsService(dataSource);
    }

    /**
     * 数据库管理的Token实例
     * @return
     */
    @Bean
    public TokenStore getJdbcTokenStore() {
        return new JdbcTokenStore(dataSource);
    }
}
