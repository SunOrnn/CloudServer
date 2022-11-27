package com.pay.common.config;

import com.pay.modules.unionpay.util.SDKConfig;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
/**
 * 启动初始化银联参数
 * 创建者 小柒2012
 * 创建时间 2018年5月15日
 */
@Component
public class InitPay implements ApplicationRunner{
	
	@Override
    public void run(ApplicationArguments var){
		//初始化银联参数
		SDKConfig.getConfig().loadPropertiesFromSrc();
    }
}