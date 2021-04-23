package com.xia.springbootshiro02.service;

import com.xia.springbootshiro02.pojo.SysUser;

public interface SysUserService {
    SysUser findByusername(String username);
}
