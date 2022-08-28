package com.ornn.sso.entity.bo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckPassWordBO {
    /**
     * 用户账号
     */
    private String userName;

    /**
     * 密码
     */
    private String passWord;

    /**
     * 密码加密密钥
     */
    private String salt;

    /**
     * 用户权限
     */
    private String authorities;
}
