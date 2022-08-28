package com.ornn.sso.service.impl;

import com.ornn.sso.entity.bo.GetUserInfoBO;
import com.ornn.sso.entity.dto.GetUserInfoDTO;
import com.ornn.sso.entity.enums.GlobalCodeEnum;
import com.ornn.sso.entity.po.OauthUserDetailsPO;
import com.ornn.sso.exception.ServiceException;
import com.ornn.sso.mapper.OauthUserDetailsMapper;
import com.ornn.sso.service.UserResourcesService;
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
