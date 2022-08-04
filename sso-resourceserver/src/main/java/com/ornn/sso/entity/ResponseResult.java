package com.ornn.sso.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.ornn.sso.entity.enums.GlobalCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: CANHUI.WANG * @create: 2022-08-04
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonPropertyOrder({"code", "message", "data"})
public class ResponseResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 返回的业务数据对象
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    /**
     * 返回单位响应编码
     */
    private Integer code;

    /**
     * 返回的响应信息
     */
    private String message;

    /**
     * 返回的成功响应码
     * @return
     */
    public static ResponseResult<String> OK() {
        return packageObject("", GlobalCodeEnum.GL_SUCC_0000);
    }

    /**
     * 对返回的消息进行包装
     * @param data             返回的数据对象
     * @param globalCodeEnum   自定义的返回码枚举类型
     * @return                 返回的数据类型
     * @param <T>
     */
    public static<T> ResponseResult<T> packageObject(T data, GlobalCodeEnum globalCodeEnum) {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setCode(globalCodeEnum.getCode());
        responseResult.setMessage(globalCodeEnum.getMessage());
        responseResult.setData(data);
        return responseResult;
    }

    /**
     * 在系统发生异常不可用时返回的信息
     * @return 响应结果
     * @param <T>
     */
    public static <T> ResponseResult<T> systemException() {
        return packageObject(null, GlobalCodeEnum.GL_FAIL_9999);
    }

    /**
     * 在发生可感知的系统异常时返回的信息
     * @param globalCodeEnum
     * @return
     * @param <T>
     */
    public static <T> ResponseResult<T> systemException(GlobalCodeEnum globalCodeEnum) {
        return packageObject(null, globalCodeEnum);
    }
}
