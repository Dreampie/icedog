package org.icedog.function.user;

import cn.dreampie.common.util.SubjectUtils;
import cn.dreampie.common.util.ValidateUtils;
import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Page;

import java.util.Date;

/**
 * Created by wangrenhui on 14-1-3.
 */
@TableBind(tableName = "sec_user")
public class User extends cn.dreampie.common.model.User<User> {
  public static User dao = new User();

  public User addUserInfo(UserInfo userInfo) {
    if (ValidateUtils.me().isNullOrEmpty(userInfo)) {
      userInfo = new UserInfo();
      userInfo.set("user_id", this.get("id"));
    }
    userInfo.set("created_at", new Date());
    userInfo.save();
    return this;
  }

  public User addRole(Role role) {
    if (ValidateUtils.me().isNullOrEmpty(role)) {
      role = Role.dao.findFirstBy("`role`.value='R_USER'");
      if (ValidateUtils.me().isNullOrEmpty(role)) {
        throw new NullPointerException("角色不存在");
      }
    }
    UserRole userRole = new UserRole();
    userRole.set("user_id", this.get("id"));
    userRole.set("role_id", role.get("id"));
    userRole.save();
    return this;
  }

  public boolean getFollowed() {
    if (getFollowing() != null) {
      this.put("followed", true);
    } else
      this.put("followed", false);
    return this.get("followed");
  }

  public Follower getFollowing() {
    User user = (User) SubjectUtils.me().getUser();
    if (this.get("following") == null) {
      Follower following = Follower.dao.findFirstBy("`follower`.user_id =" + user.get("id") + " AND `follower`.link_id =" + this.get("id"));
      if (following != null) {
        this.put("following", following);
      }
    }
    return this.get("following");
  }


  public Role getRole() {
    return Role.dao.findById(UserRole.dao.findFirstBy("`userRole`.user_id=" + this.get("id")).get("role_id"));
  }

  public Page<User> paginateInfoBy(int pageNumber, int pageSize, String where, Object... paras) {
    Page<User> result = dao.paginate(pageNumber, pageSize, cn.dreampie.common.plugin.sqlinxml.SqlKit.sql("user.findInfoBySelect"), cn.dreampie.common.plugin.sqlinxml.SqlKit.sql("user.findInfoByExceptSelect") + getWhere(where), paras);
    return result;
  }

}
