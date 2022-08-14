package com.ornn.sso.service;

import com.ornn.sso.entity.BO.GetUserInfoBO;
import com.ornn.sso.entity.DTO.GetUserInfoDTO;

public interface UserResourcesService {
    /**
     * define the method of service of "user protected message query" interface
     */
    GetUserInfoBO getUserInfo(GetUserInfoDTO getUserInfoDTO);
}
