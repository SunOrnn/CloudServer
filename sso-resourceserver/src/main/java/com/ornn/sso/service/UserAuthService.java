package com.ornn.sso.service;

import com.ornn.sso.entity.BO.CheckPassWordBO;
import com.ornn.sso.entity.DTO.CheckPassWordDTO;

public interface UserAuthService {
    /**
     * define the method of service of "login verification" interface
     */
    CheckPassWordBO checkPassWordBO(CheckPassWordDTO checkPassWordDTO);
}
