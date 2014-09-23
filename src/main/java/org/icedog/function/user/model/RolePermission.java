package org.icedog.function.user.model;

import cn.dreampie.tablebind.TableBind;
import cn.dreampie.web.model.Model;
import com.jfinal.plugin.activerecord.Db;

import java.util.List;

/**
 * Created by wangrenhui on 14-4-22.
 */
@TableBind(tableName = "sec_role_permission")
public class RolePermission extends Model<RolePermission> {
  public static RolePermission dao = new RolePermission();

  public List<String> findPermissionIds(String where, Object... paras) {
    return Db.query("SELECT DISTINCT `rolePermission`.permission_id " + getExceptSelectSql() + getWhere(where), paras);
  }
}
