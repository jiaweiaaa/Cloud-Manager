package com.example.server.service.impl;

import com.example.server.pojo.Menu;
import com.example.server.mapper.MenuMapper;
import com.example.server.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jiawei
 * @since 2022-02-23
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

}
