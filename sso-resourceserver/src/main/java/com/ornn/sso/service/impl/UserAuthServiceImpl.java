package com.ornn.sso.service.impl;

import com.ornn.sso.entity.bo.CheckPassWordBO;
import com.ornn.sso.entity.dto.CheckPassWordDTO;
import com.ornn.sso.entity.enums.GlobalCodeEnum;
import com.ornn.sso.entity.po.OauthUserDetailsPO;
import com.ornn.sso.exception.ServiceException;
import com.ornn.sso.mapper.OauthUserDetailsMapper;
import com.ornn.sso.service.UserAuthService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAuthServiceImpl implements UserAuthService {

    @Autowired
    private OauthUserDetailsMapper oauthUserDetailsMapper;

    @Override
    public CheckPassWordBO checkPassWordBO(CheckPassWordDTO checkPassWordDTO) {
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
