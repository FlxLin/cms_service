package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

/**
 * @program: ServiceProject
 * @description:
 * @author: Linn
 * @create: 2020-02-20 20:32
 **/
public class ExceptionCast{
    public static void cast(ResultCode resultCode){
        throw new CustomException(resultCode);
    }
}
