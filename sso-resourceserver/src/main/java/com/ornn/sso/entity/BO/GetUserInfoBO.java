package com.ornn.sso.entity.BO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetUserInfoBO {
    /**
     * 用户呢称
     */
    private String nickName;

    /**
     * phone number
     */
    private String mobileNo;

    /**
     * user gender:1-women; 2-male; 3-unknow
     */
    private Integer gender;

    /**
     * user description
     */
    private String desc;
}
