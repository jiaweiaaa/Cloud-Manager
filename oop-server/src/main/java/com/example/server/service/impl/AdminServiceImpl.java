package com.example.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.server.config.security.JwtTokenUtil;
import com.example.server.pojo.Admin;
import com.example.server.mapper.AdminMapper;
import com.example.server.pojo.RespBean;
import com.example.server.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jiawei
 * @since 2022-02-23
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    /**
     * Return token after login
     * @param username
     * @param password
     * @param request
     * @return
     */
    @Override
    public RespBean login(String username, String password, HttpServletRequest request) {
        //login
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);// 调用权限框架方法获取用户名
        if(null==userDetails||!passwordEncoder.matches(password,userDetails.getPassword())){
            return RespBean.error("Username or password incorrect");
        }
        if(!userDetails.isEnabled()){
            return RespBean.error("Account is blocked, please contact admin");
        }
        // Update Login User Object
        UsernamePasswordAuthenticationToken authenticationToken=new
        UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //generate token
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String,String>tokenMap =new HashMap<>();

        tokenMap.put("tokenHead",tokenHead);
        tokenMap.put("token",token);

        return RespBean.success("Login successful",tokenMap);
    }

    @Override
    public Admin getAdminByUserName(String username) {
        return adminMapper.selectOne(new QueryWrapper<Admin>().eq("username",username).eq
                ("enabled", true));
    }
}
