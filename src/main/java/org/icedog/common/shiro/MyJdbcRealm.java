package org.icedog.common.shiro;

import cn.dreampie.ValidateKit;
import cn.dreampie.shiro.core.SubjectKit;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.icedog.function.user.model.Permission;
import org.icedog.function.user.model.Role;
import org.icedog.function.user.model.User;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by wangrenhui on 14-1-3.
 */
public class MyJdbcRealm extends AuthorizingRealm {

  /**
   * 登录认证
   *
   * @param token
   * @return
   * @throws org.apache.shiro.authc.AuthenticationException
   */
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    UsernamePasswordToken userToken = (UsernamePasswordToken) token;
    User user = null;
//    if (userToken.getUsername().equalsIgnoreCase(MyAnonymousFilter.getUsername())) {
//      PasswordService passwordService = new DefaultPasswordService();
//      return new SimpleAuthenticationInfo(MyAnonymousFilter.getUsername(), passwordService.encryptPassword(MyAnonymousFilter.getPassword()), getName());
//    } else {
    String username = userToken.getUsername();
    if (ValidateKit.isEmail(username)) {
      user = User.dao.findFirstBy(" `user`.email =? AND `user`.deleted_at is null", username);
    } else if (ValidateKit.isMobile(username)) {
      user = User.dao.findFirstBy(" `user`.mobile =? AND `user`.deleted_at is null", username);
    } else {
      user = User.dao.findFirstBy(" `user`.username =? AND `user`.deleted_at is null", username);
    }
    if (user != null) {
//      Session session = SecurityUtils.getSubject().getSession();
//      session.setAttribute(AppConstants.TEMP_USER, user);
      SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getStr("password"), getName());
//      clearCachedAuthorizationInfo(info.getPrincipals());
      return info;
    } else {
      return null;
    }
//    }
  }

  /**
   * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
   *
   * @param principals 用户信息
   * @return
   */
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    String loginName = ((User) principals.fromRealm(getName()).iterator().next()).get("username");
    SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
    Set<String> roleSet = new LinkedHashSet<String>(); // 角色集合
    Set<String> permissionSet = new LinkedHashSet<String>();  // 权限集合
    List<Role> roles = null;
//    if (loginName.equalsIgnoreCase(MyAnonymousFilter.getUsername())) {
//      //遍历角色
//      roles = Role.me().findByRoleKey(MyAnonymousFilter.getRole());
//    } else {
    User user = User.dao.findFirstBy(" `user`.username =? AND `user`.deleted_at is null", loginName);
    if (user != null) {
      //遍历角色
      roles = Role.dao.findUserBy("", user.getLong("id"));
//        }
    } else {
      SubjectKit.getSubject().logout();
    }

    loadRole(roleSet, permissionSet, roles);
    info.setRoles(roleSet); // 设置角色
    info.setStringPermissions(permissionSet); // 设置权限
    return info;
  }

  /**
   * @param roleSet
   * @param permissionSet
   * @param roles
   */
  private void loadRole(Set<String> roleSet, Set<String> permissionSet, List<Role> roles) {
    List<Permission> permissions;
    for (Role role : roles) {
      //角色可用
      if (role.getDate("deleted_at") == null) {
        roleSet.add(role.getStr("value"));
        permissions = Permission.dao.findByRole("", role.getLong("id"));
        loadAuth(permissionSet, permissions);
      }
    }
  }

  /**
   * @param permissionSet
   * @param permissions
   */
  private void loadAuth(Set<String> permissionSet, List<Permission> permissions) {
    //遍历权限
    for (Permission permission : permissions) {
      //权限可用
      if (permission.getDate("deleted_at") == null) {
        permissionSet.add(permission.getStr("value"));
      }
    }
  }

  /**
   * 更新用户授权信息缓存.
   */

  public void clearCachedAuthorizationInfo(Object principal) {
    SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
    clearCachedAuthorizationInfo(principals);
  }

  /**
   * 清除所有用户授权信息缓存.
   */
  public void clearAllCachedAuthorizationInfo() {
    Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
    if (cache != null) {
      for (Object key : cache.keys()) {
        cache.remove(key);
      }
    }
  }
}