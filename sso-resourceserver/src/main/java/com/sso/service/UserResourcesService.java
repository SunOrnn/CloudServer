package com.sso.service;

import com.sso.entity.DTO.GetUserInfoDTO;
import com.sso.entity.BO.GetUserInfoBO;

public interface UserResourcesService {
    /**
     * define the method of service of "user protected message query" interface
     */
    GetUserInfoBO getUserInfo(GetUserInfoDTO getUserInfoDTO);
}
