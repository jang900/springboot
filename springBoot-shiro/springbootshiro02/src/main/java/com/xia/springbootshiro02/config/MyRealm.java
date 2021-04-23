package com.xia.springbootshiro02.config;

import com.xia.springbootshiro02.pojo.SysUser;
import com.xia.springbootshiro02.service.Impl.SysUserServiceImpl;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MyRealm extends AuthorizingRealm {

    @Autowired
    SysUserServiceImpl sysUserService;

    Map<String,Object> userMap = new HashMap<>();
    {
        userMap.put("user","123456"); // 模拟从数据库取出的用户密码
        super.setName("userShiro");  // 自定义个一个shiro名字
    }

    /**
     * 授权 Authorization
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        // String primaryPrincipal = (String) principalCollection.getPrimaryPrincipal();
        //System.out.println("PrimaryPrincipal=1-0=>"+principalCollection.getPrimaryPrincipal());

        //Set<String> permission = getPermissionsByUserName(primaryPrincipal);  // 权限
        //Set<String> roles = getRolesByUserName(primaryPrincipal);  // 角色

        System.out.println("授权查询...");

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        SysUser principal = (SysUser) subject.getPrincipal();
        // perm 栏位应该是包含add update delete 等。可遍历
        String[] split = principal.getPerm().split(",");
        for (String s : split) {
            System.out.println(s);
            simpleAuthorizationInfo.addStringPermission(s);
        }


        // simpleAuthorizationInfo.addStringPermission(principal.getPerm());
        //simpleAuthorizationInfo.setRoles(roles);
        //simpleAuthorizationInfo.setStringPermissions(permission);

        //System.out.println("PrimaryPrincipal=1-2=>"+simpleAuthorizationInfo.getStringPermissions());
        //System.out.println("RealmNames=2-2=>"+simpleAuthorizationInfo.getRoles());

        return simpleAuthorizationInfo;

    }

    /**
     * 模拟从数据库中获取权限
     */
    private Set<String> getPermissionsByUserName(String primaryPrincipal) {
        Set<String> permissions = new HashSet<>();
        permissions.add("user:delete");
        permissions.add("admin:add");
        return permissions;
    }

    /**
     * 模拟从数据库中获取角色数据
     *
     * @param userName
     * @return
     */
    private Set<String> getRolesByUserName(String primaryPrincipal) {
        Set<String> roles = new HashSet<>();
        roles.add("admin");
        roles.add("user");
        return roles;
    }


    /**
     * 认证 Authentication
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // System.out.println("authenticationToken1=========>"+authenticationToken.getPrincipal());
        // System.out.println("authenticationToken2=========>"+authenticationToken.getCredentials());
        // 从token 获取用户名 Principals:身份,即用户名 Credentials:凭证,
        System.out.println("认证查询...");
        String principal = (String) authenticationToken.getPrincipal();
        System.out.println("principal==>"+principal);
        // String credentials =(String) userMap.get(principal);

//        if(credentials==null){
//            return null;
//        }
        SysUser sysUser = sysUserService.findByusername(principal);

        if(sysUser==null){
            return null;
        }
        // 将获取出来的user写入session中
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.setAttribute("loginuser",sysUser.getUsername());

        // new SimpleAuthenticationInfo 简单的认证 返回
        /// SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(principal,userMap.get(principal),"userShiro");
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(sysUser,sysUser.getPassword(),"userShiro");

        return simpleAuthenticationInfo;
        // return null;
    }
}
