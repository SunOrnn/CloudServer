package com.sso.service.impl;

import com.sso.entity.BO.GetUserInfoBO;
import com.sso.entity.DTO.GetUserInfoDTO;
import com.sso.entity.enums.GlobalCodeEnum;
import com.sso.entity.po.OauthUserDetailsPO;
import com.sso.service.UserResourcesService;
import com.sso.exception.ServiceException;
import com.sso.mapper.OauthUserDetailsMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserResourceServiceImpl implements UserResourcesService {

    @Autowired
    private OauthUserDetailsMapper oauthUserDetailsMapper;

    @Override
    public GetUserInfoBO getUserInfo(GetUserInfoDTO getUserInfoDTO) {
        // 查询用户信息
        OauthUserDetailsPO oauthUserDetailsPO = oauthUserDetailsMapper.getUserDetails(getUserInfoDTO.getUserName());

        if (ObjectUtils.isEmpty(oauthUserDetailsPO)) {
            throw new ServiceException(GlobalCodeEnum.BUSI_USER_NOT_EXIST.getCode(), GlobalCodeEnum.BUSI_USER_NOT_EXIST.getDesc());
        }

        return GetUserInfoBO.builder()
                .nickName(oauthUserDetailsPO.getNickname())
                .mobileNo(oauthUserDetailsPO.getMobile())
                .gender(oauthUserDetailsPO.getGender())
                .build();
    }
}
