package com.example.server.controller;

import com.example.server.pojo.Admin;
import com.example.server.pojo.AdminLoginParam;
import com.example.server.pojo.RespBean;
import com.example.server.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * Login
 *
 * @author Jiawei
 *
 */
@RestController
@Api(tags ="LoginController")

public class LoginController {
    @Autowired
    private IAdminService adminService;

    @ApiOperation(value = "Return token after login")
    @PostMapping("/login")
    public RespBean login(AdminLoginParam adminLoginParam, HttpServletRequest request){
        return  adminService.login(adminLoginParam.getUsername(),adminLoginParam.getPassword(),request);
    }

    @ApiOperation(value = "Obtain current user details")
    @GetMapping("/admin/info")
    public Admin getAdminInfo(Principal principal){
        if(null==principal){
            return null;
        }
        String username = principal.getName();
        Admin admin = adminService.getAdminByUserName(username);
        admin.setPassword(null);
        return admin;
    }

    @ApiOperation(value = "Logout")
    @PostMapping("/logout")
    public RespBean logout(){
        return RespBean.success("logout successful");
    }
}
