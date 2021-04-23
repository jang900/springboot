package com.xia.springbootshiro02.mapper;

import com.xia.springbootshiro02.pojo.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
//@Component
@Repository
@Mapper
public interface SysUserMapper  {

    SysUser findByusername(String username);
}
