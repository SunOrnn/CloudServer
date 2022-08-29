package com.ornn.sso.entity.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OauthUserDetailsPO {
    private String username;

    private String password;

    private String salt;

    private String nickname;

    private String mobile;

    private Integer gender;

    private String authorities;

    private Boolean nonExpired;

    private Boolean nonLocked;

    private Boolean credentialsNonExpired;

    private Timestamp createTime;

    private String createBy;

    private Timestamp updateTime;

    private String updateBy;
}
