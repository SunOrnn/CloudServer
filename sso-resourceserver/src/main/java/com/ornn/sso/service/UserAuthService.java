package com.ornn.sso.service;

import com.ornn.sso.entity.bo.CheckPassWordBO;
import com.ornn.sso.entity.dto.CheckPassWordDTO;
import org.springframework.stereotype.Service;

public interface UserAuthService {
    /**
     * define the method of service of "login verification" interface
     */
    CheckPassWordBO checkPassWordBO(CheckPassWordDTO checkPassWordDTO);
}
