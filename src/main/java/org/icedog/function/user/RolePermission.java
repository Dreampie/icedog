package org.icedog.function.user;

import cn.dreampie.common.model.Model;
import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;

import java.util.List;

/**
 * Created by wangrenhui on 14-4-22.
 */
@TableBind(tableName = "sec_role_permission")
public class RolePermission extends Model<RolePermission> {
  public static RolePermission dao = new RolePermission();

  public List<String> findPermissionIds(String where, Object... paras) {
    List<String> result = Db.query("SELECT DISTINCT `rolePermission`.permission_id " + getExceptSelectSql() + getWhere(where), paras);
    return result;
  }
}
