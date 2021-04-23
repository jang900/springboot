package com.xia.springbootshiro02.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class MyShiroConfig {


    /**
     * 3： ShiroFilterFactoryBean 相当与是请求过来的"用户"
     * 需要查询这个用户的角色和权限 需要从defaultWebSecurityManager里面查看
     *
     * @param defaultWebSecurityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 拦截器机制
        /**
         *   shiro 拦截机制
         *     anon(AnonymousFilter.class), 无需认证 直接访问
         *     authc(FormAuthenticationFilter.class), 必须认证后才能访问
         *     authcBasic(BasicHttpAuthenticationFilter.class),
         *     logout(LogoutFilter.class),
         *     noSessionCreation(NoSessionCreationFilter.class),
         *     perms(PermissionsAuthorizationFilter.class), 拥有对某个资源的权限才能访问
         *     port(PortFilter.class),
         *     rest(HttpMethodPermissionFilter.class),
         *     roles(RolesAuthorizationFilter.class),  拥有某个角色权限才能访问
         *     ssl(SslFilter.class),
         *     user(UserFilter.class); 必须拥有记住我 的功能才能访问
         */


        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        Map<String, String> filterMap =new LinkedHashMap<>();
//        filterMap.put("/user/add","authc");
//        filterMap.put("/user/update","authc");
        // filterMap.put("/user/update","authc");
        filterMap.put("/user/add","perms[user:add]");
        filterMap.put("/user/update","perms[user:update]");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

        // 设置请求登陆页面
        shiroFilterFactoryBean.setLoginUrl("/tologin");
        shiroFilterFactoryBean.setUnauthorizedUrl("/Unauthorized");


        return shiroFilterFactoryBean;
    }

    /**
     * 2： defaultWebSecurityManager  验证身份的安全中心 ---》
     * 需要 realm 中获取
     * @Qualifier 有类似 @Autowired 的功能。将@Bean交给Spring的方法提出来。作为参数传入。
     * @param myRealm
     * @return
     */
    @Bean(name="securityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("myRealm") MyRealm myRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myRealm);
        return securityManager;
    }

    /**
     *  1： realm  从数据库中获取出用户的角色和权限
     * @return
     */
    @Bean
    public MyRealm myRealm(){
        return new MyRealm();
    }

    /**
     * shiro 和 thymeleaf 整合 通过权限角色的过滤 部分能看到，部分不能看到
     * @return
     */
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }


}
