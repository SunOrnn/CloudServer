package com.ornn.sso.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

/**
 * @author: CANHUI.WANG * @create: 2022-08-04
 */

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    /**
     * 授权认证微服务的“令牌验证”接口的地址
     */
    @Value("${security.oauth2.checkTokenUrl}")
    private String checkTokenUrl;

    /**
     * 授权认证微服务中为资源微服务配置的客户端ID
     */
    @Value("${security.oauth2.clientId}")
    private String clientId;;

    /**
     * 在授权认证微服务中为资源微服务配置的客户端密钥
     */
    @Value("${security.oauth2.clientSecret}")
    private String clientSecret;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
        remoteTokenServices.setCheckTokenEndpointUrl(checkTokenUrl);
        remoteTokenServices.setClientId(clientId);
        remoteTokenServices.setClientSecret(clientSecret);
        resources.tokenServices(remoteTokenServices);
    }
}
