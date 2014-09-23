package org.icedog.function.user.model;

import cn.dreampie.sqlinxml.SqlKit;
import cn.dreampie.tablebind.TableBind;
import cn.dreampie.web.model.Model;
import com.jfinal.plugin.activerecord.Db;

import java.util.List;

/**
 * Created by wangrenhui on 14-4-22.
 */
@TableBind(tableName = "sec_user_role")
public class UserRole extends Model<UserRole> {
  public static UserRole dao = new UserRole();

  public Role getRole() {
    return Role.dao.findById(this.get("role_id"));
  }


  public List<String> findUserIds(String where, Object... paras) {
    return Db.query("SELECT DISTINCT `userRole`.user_id " + SqlKit.sql("userRole.findByExceptSelect") + " " + getWhere(where), paras);
  }
}
