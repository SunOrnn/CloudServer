package com.ornn.sso.service;

import com.ornn.sso.entity.bo.GetUserInfoBO;
import com.ornn.sso.entity.dto.GetUserInfoDTO;
import org.springframework.stereotype.Service;

public interface UserResourcesService {
    /**
     * define the method of service of "user protected message query" interface
     */
    GetUserInfoBO getUserInfo(GetUserInfoDTO getUserInfoDTO);
}
