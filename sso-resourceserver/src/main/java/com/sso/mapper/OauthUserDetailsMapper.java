package com.sso.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sso.entity.po.OauthUserDetailsPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OauthUserDetailsMapper extends BaseMapper<OauthUserDetailsPO> {

    /**
     * 根据用户账号获取用户的详细信息
     */
    OauthUserDetailsPO getUserDetails(String username);
}
