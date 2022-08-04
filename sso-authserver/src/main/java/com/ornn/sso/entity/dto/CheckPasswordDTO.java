package com.ornn.sso.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: CANHUI.WANG * @create: 2022-07-29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckPasswordDTO {
    /**
     * 登录账号
     */
    private String username;

}
