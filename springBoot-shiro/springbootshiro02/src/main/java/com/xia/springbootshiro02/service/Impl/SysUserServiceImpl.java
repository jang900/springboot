package com.xia.springbootshiro02.service.Impl;

import com.xia.springbootshiro02.mapper.SysUserMapper;
import com.xia.springbootshiro02.pojo.SysUser;
import com.xia.springbootshiro02.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.ParameterResolutionDelegate;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    public SysUser findByusername(String username) {
        return sysUserMapper.findByusername(username);
    }
}
