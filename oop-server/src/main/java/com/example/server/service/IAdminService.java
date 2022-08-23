package com.example.server.service;

import com.example.server.pojo.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.server.pojo.RespBean;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jiawei
 * @since 2022-02-23
 */
public interface IAdminService extends IService<Admin> {
    /**
     * Return token after login
     * @param username
     * @param password
     * @param request
     * @return
     */
    RespBean login(String username, String password, HttpServletRequest request);

    /**
     * get user by username
     * @param username
     * @return
     */
    Admin getAdminByUserName(String username);
}
