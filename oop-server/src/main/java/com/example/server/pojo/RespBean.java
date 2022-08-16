package com.example.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: RespBean
 * @Description: 公共返回对象
 * @version: v1.0.0
 * @author: Jiawei
 * @date: 2022/2/25 17:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespBean {
    private Long code;
    private String message;
    private Object obj;

    /**
     * 成功返回
     * @param message
     * @return
     */
    public static  RespBean success(String message){
        return new RespBean(code: 200,message,obj: null);

    }

    /**
     * 成功返回
     * @param message
     * @return
     */
    public static  RespBean success(String message,Object obj){
        return new RespBean(code: 200,message,obj);

    }

    public  static RespBean error(String message){

    }

}
