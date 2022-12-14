package com.ornn.schedule.sso.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
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

@Configuration
@EnableAuthorizationServer
public class AuthSeverConfiguration extends AuthorizationServerConfigurerAdapter {

    /**
     * JDBC???????????????
     */
    @Autowired
    private DataSource dataSource;

    /**
     * ????????????????????????
     */
    AuthenticationManager authenticationManager;

    /**
     * ????????????
     */
    public AuthSeverConfiguration(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * ??????JDBC???????????????????????????????????????????????????
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(new JdbcClientDetailsService(dataSource));
    }

    /**
     * ????????????????????????????????????????????????
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer clients) throws Exception {
        // ??????TokenService??????
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(getJdbcTokenStore());

        // ???????????????????????????
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setReuseRefreshToken(false);

        // ??????accessToken?????????????????????????????????30???
        tokenServices.setAccessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30));

        // ??????refreshToken?????????????????????????????????15???
        tokenServices.setRefreshTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(15));
        tokenServices.setClientDetailsService(getJdbcClientDetailsService());

        // ???????????????????????????
        clients.authenticationManager(this.authenticationManager)
                .accessTokenConverter(jwtAccessTokenConverter())
                .tokenStore(getJdbcTokenStore())
                .tokenServices(tokenServices)
                .authorizationCodeServices(getJdbcAuthorizationCodeServices())
                .approvalStore(getJdbcApprovalStore());
    }

    /**
     * ??????????????????
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("hasAuthority('ROLE_TRUSTED_CLIENT')")
                .allowFormAuthenticationForClients();
    }

    /**
     * ??????????????????????????????????????????
     * @return
     */
    @Bean
    public ApprovalStore getJdbcApprovalStore() {
        return new JdbcApprovalStore(dataSource);
    }

    /**
     * ?????????????????????????????????
     * @return
     */
    @Bean
    public AuthorizationCodeServices getJdbcAuthorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    /**
     * AccessToken????????????????????????????????????????????????Token????????????
     * @return
     */
    public AccessTokenConverter jwtAccessTokenConverter() {
        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        // ????????????
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("keystore.jks"), "mypass".toCharArray());
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("mytest"));
        return converter;
    }

    /**
     * ?????????????????????????????????
     * @return
     */
    @Bean
    public ClientDetailsService getJdbcClientDetailsService() {
        return new JdbcClientDetailsService(dataSource);
    }

    /**
     * ??????????????????Token??????
     * @return
     */
    @Bean
    public TokenStore getJdbcTokenStore() {
        return new JdbcTokenStore(dataSource);
    }
}
