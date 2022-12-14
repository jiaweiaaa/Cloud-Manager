package com.example.server.service.impl;

import com.example.server.pojo.Department;
import com.example.server.mapper.DepartmentMapper;
import com.example.server.service.IDepartmentService;
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
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

}
