package com.example.demo.common;

/**
 * 返回工具类
 */
public class ResultUtils {
    public static <T> BaseResponse<T> success(T data){
        return new BaseResponse<>(0,data,"ok","");
    }
    public static BaseResponse error(ErrorCode errorCode){
        return new BaseResponse<>(errorCode);
    }

    public static BaseResponse error(ErrorCode errorCode ,String descrption){
        return new BaseResponse(errorCode.getCode(),descrption);
    }

    public static BaseResponse error(int code,String message ,String descrption){
        return new BaseResponse(code,null,message,descrption);
    }
}
