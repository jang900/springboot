package com.xia.springbootshiro02;

import com.xia.springbootshiro02.pojo.SysUser;
import com.xia.springbootshiro02.service.Impl.SysUserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Springbootshiro02ApplicationTests {

	@Autowired
	SysUserServiceImpl sysUserService;

	@Test
	void contextLoads() {
		SysUser admin = sysUserService.findByusername("admin");
		System.out.println(admin.getUsername());
	}

}
