package org.icedog.function.user.model;

import cn.dreampie.tablebind.TableBind;
import cn.dreampie.web.model.Model;
import com.jfinal.plugin.activerecord.Db;

import java.util.List;

/**
 * Created by wangrenhui on 14-4-22.
 */
@TableBind(tableName = "sec_role_menu")
public class RoleMenu extends Model<RoleMenu> {
  public static RoleMenu dao = new RoleMenu();

  public List<String> findMenuIds(String where, Object... paras) {
    return Db.query("SELECT DISTINCT `roleMenu`.menu_id " + getExceptSelectSql() + getWhere(where), paras);
  }
}
