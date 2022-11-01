package com.ornn.schedule.sso.entity.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: CANHUI.WANG * @create: 2022-07-29
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckPasswordBO {
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 加盐值
     */
    private String salt;

    /**
     * 用户权限
     */
    private String authorities;
}
