package com.sso.service;

import com.sso.entity.BO.CheckPassWordBO;
import com.sso.entity.DTO.CheckPassWordDTO;

public interface UserAuthService {
    /**
     * define the method of service of "login verification" interface
     */
    CheckPassWordBO checkPassWord(CheckPassWordDTO checkPassWordDTO);
}
