package org.icedog.function.user.model;

import cn.dreampie.ValidateKit;
import cn.dreampie.sqlinxml.SqlKit;
import cn.dreampie.tablebind.TableBind;
import cn.dreampie.web.model.Model;
import com.jfinal.plugin.activerecord.Page;

import java.util.Date;

/**
 * Created by wangrenhui on 14-1-3.
 */
@TableBind(tableName = "sec_user")
public class User extends Model<User> {
  public static User dao = new User();

  public User addUserInfo(UserInfo userInfo) {
    if (ValidateKit.isNullOrEmpty(userInfo)) {
      userInfo = new UserInfo();
      userInfo.set("user_id", this.get("id"));
    }
    userInfo.set("created_at", new Date());
    userInfo.save();
    return this;
  }

  public User addRole(Role role) {
    if (ValidateKit.isNullOrEmpty(role)) {
      role = Role.dao.findFirstBy("`role`.value='R_USER'");
      if (ValidateKit.isNullOrEmpty(role)) {
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
    return this.getBoolean("followed");
  }

  public Follower getFollowing() {
//    User user = SubjectKit.getUser();
    if (this.get("following") == null) {
      Follower following = Follower.dao.findFirstBy("`follower`.user_id =" + this.get("id") + " AND `follower`.link_id =" + this.get("id"));
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
    return dao.paginate(pageNumber, pageSize, SqlKit.sql("user.findInfoBySelect"), SqlKit.sql("user.findInfoByExceptSelect") + getWhere(where), paras);
  }

  public User findInfoBy(String where, Object... paras) {
    return dao.findFirst(SqlKit.sql("user.findInfoBySelect") + SqlKit.sql("user.findInfoByExceptSelect") + getWhere(where), paras);
  }
}
