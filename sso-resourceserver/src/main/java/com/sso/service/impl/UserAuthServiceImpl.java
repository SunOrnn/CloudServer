package com.sso.service.impl;

import com.sso.entity.BO.CheckPassWordBO;
import com.sso.entity.DTO.CheckPassWordDTO;
import com.sso.entity.enums.GlobalCodeEnum;
import com.sso.entity.po.OauthUserDetailsPO;
import com.sso.service.UserAuthService;
import com.sso.exception.ServiceException;
import com.sso.mapper.OauthUserDetailsMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAuthServiceImpl implements UserAuthService {

    @Autowired
    private OauthUserDetailsMapper oauthUserDetailsMapper;

    @Override
    public CheckPassWordBO checkPassWord(CheckPassWordDTO checkPassWordDTO) {
        // 获取用户信息
        OauthUserDetailsPO oauthUserDetailsPO = oauthUserDetailsMapper.getUserDetails(checkPassWordDTO.getUserName());
        if (ObjectUtils.isEmpty(oauthUserDetailsPO)) {
            throw new ServiceException(GlobalCodeEnum.BUSI_USER_NOT_EXIST.getCode(), GlobalCodeEnum.BUSI_USER_NOT_EXIST.getDesc());
        }
        return CheckPassWordBO.builder()
                .userName(oauthUserDetailsPO.getUsername())
                .passWord(oauthUserDetailsPO.getPassword())
                .salt(oauthUserDetailsPO.getSalt())
                .authorities(oauthUserDetailsPO.getAuthorities())
                .build();
    }
}
