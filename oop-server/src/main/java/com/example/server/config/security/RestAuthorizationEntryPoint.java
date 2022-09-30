package com.example.server.config.security;

import com.example.server.pojo.RespBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *  * 当未登录或者token失效时访问接口时，自定义的返回结果
 */


//request是遇到了认证异常authException用户请求，response是将要返回给客户的相应，
// 方法commence实现,也就是相应的认证方案逻辑会修改response并返回给用户引导用户进入认证流程。
@Component//将一个 类 标记为 Component，这种类将会作为候选类，当使用基于注解的配置和类路径扫描的时候将会被自动检测。
public class RestAuthorizationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");// 设置编码格式
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();//拿到输出
        RespBean bean = RespBean.error("Please login");
        bean.setCode(401);
        out.write(new ObjectMapper().writeValueAsString(bean));
        out.flush();
        out.close();
    }
}
