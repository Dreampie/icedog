package org.icedog.function.user;

import cn.dreampie.common.model.Model;
import com.jfinal.ext.plugin.tablebind.TableBind;
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
    List<String> result = Db.query("SELECT DISTINCT `userRole`.user_id " + cn.dreampie.common.plugin.sqlinxml.SqlKit.sql("userRole.findByExceptSelect") + " " + getWhere(where), paras);
    return result;
  }
}
