package com.pay.common.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
@Configuration
@EnableSwagger2
public class Swagger2 {

	@Bean
	public Docket webApi() {
		return new Docket(DocumentationType.SWAGGER_2)
		        .groupName("支付后台API接口文档")  
		        .apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.pay.modules.web"))
				.paths(PathSelectors.any()).build();
	}

	@Bean
	public Docket aliPayApi() {
		return new Docket(DocumentationType.SWAGGER_2)
		        .groupName("支付宝API接口文档")  
		        .apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.pay.modules.aliPay"))
				.paths(PathSelectors.any()).build();
	}

	@Bean
	public Docket wxPayApi() {
		return new Docket(DocumentationType.SWAGGER_2)
		        .groupName("微信API接口文档")  
		        .apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.pay.modules.wxPay"))
				.paths(PathSelectors.any()).build();
	}

	@Bean
	public Docket unionPayApi() {
		return new Docket(DocumentationType.SWAGGER_2)
		        .groupName("银联API接口文档")  
		        .apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.pay.modules.unionPay"))
				.paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("支付系统 官网：https://pay.cloudbed.vip")
				.description("微信、支付宝、银联支付服务")
				.termsOfServiceUrl("http://blog.52itstyle.vip")
				.contact(new Contact("爪洼笔记 ", "http://blog.52itstyle.vip", "345849402@qq.com"))
				.version("1.0").build();
	}
}