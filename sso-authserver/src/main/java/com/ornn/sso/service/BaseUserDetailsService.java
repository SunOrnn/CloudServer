package com.ornn.sso.service;

import com.ornn.sso.client.ResourceServerClient;
import com.ornn.sso.entity.ResponseResult;
import com.ornn.sso.entity.bo.CheckPasswordBO;
import com.ornn.sso.entity.dto.CheckPasswordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: CANHUI.WANG * @create: 2022-07-29
 */
@Service
public class BaseUserDetailsService implements UserDetailsService {

    /**
     * 将资源微服务的FeignClient接口注入本实例
     * @param s
     * @reture
     */
    @Autowired
    ResourceServerClient resourceServerClient;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CheckPasswordDTO checkPassWordDTO = CheckPasswordDTO.builder().username(username).build();
        ResponseResult<CheckPasswordBO> responseResult = resourceServerClient.checkPassWord(checkPassWordDTO);
        CheckPasswordBO checkPassWordBO = responseResult.getData();
        List<GrantedAuthority> authorityList = new ArrayList<>();

        // 返回带有用户权限信息的User
        User user = new User(checkPassWordBO.getUsername(), checkPassWordBO.getPassword() + "," + checkPassWordBO.getSalt(), true, true, true, true, authorityList);
        return user;
    }
}
