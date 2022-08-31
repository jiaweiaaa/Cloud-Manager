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
    //Principal是一个包含用户的标识和用户的所属角色的对象。IPrincipal接口定义了Identity属性和IsInRole方法，
    // Identity属性返回Identity对象；在IsInRole方法中，可以验证用户是否是指定角色的一个成员。角色是有相同安全权限的用户集合，
    // 同时它是用户的管理单元。角色可以是Windows组或自己定义的一个字符串集合。
    //https://blog.csdn.net/lidew521/article/details/82253800?utm_medium=distribute.pc_relevant.none-task-blog-2~default~baidujs_baidulandingword~default-1-82253800-blog-83282439.pc_relevant_multi_platform_whitelistv3&spm=1001.2101.3001.4242.2&utm_relevant_index=4
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
