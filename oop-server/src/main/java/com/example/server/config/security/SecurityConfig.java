package com.example.server.config.security;

/**
 * Security 配置类
 */

import com.example.server.pojo.Admin;
import com.example.server.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private IAdminService adminService;
    @Autowired
    private RestAuthorizationEntryPoint restAuthorizationEntryPoint;// 未登录 token 失效时自定义处理结果
    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;// 无权访问时自定义处理结果

    //让spring security 走登录逻辑的时候走自己写的user details方法
    //AuthenticationManagerBuilder是一个SecurityBuilder,其目的是根据所设置的属性构建一个AuthenticationManager(实现类使用ProviderManager)。

    //重写user details方法
    @Override
    @Bean
    public UserDetailsService userDetailsService(){
      return  username -> {
          Admin admin = adminService.getAdminByUserName(username);
          if(admin!= null){
              return admin;

          }
          return null;
      };
    }
    // 2、让 Security 走我们重写的 UserDetailsService ，通过 getAdminByUserName 获取用户名
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //放行一些路径，不走拦截链
        web.ignoring().antMatchers(
                "/login",
                "/logout",
                "/css/**",
                "/js/**",
                "/index.html",
                "favicon.ico",
                "/doc.html",
                "/webjars/**", //**表示这个路径后面的所有
                "/swagger-resources/**",
                "/v2/api-docs/**"
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Cross-site request forgery
        http.csrf()
                .disable()
                .sessionManagement()//基于token，不需要session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //除了登录，所有请求都要认证
                .anyRequest()
                .authenticated()
                .and()
                //禁用缓存
                .headers()
                .cacheControl();
        //添加jwt 登录授权过滤
        http.addFilterBefore(jwtAuthencationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        //添加未授权和未登录结果返回
        http.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthorizationEntryPoint);

    }

    // 5、JWT 登录授权过滤器
    @Bean
    public JwtAuthencationTokenFilter jwtAuthencationTokenFilter(){
        return new JwtAuthencationTokenFilter();
    }



}



















