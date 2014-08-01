package org.icedog.function.user.model;

import cn.dreampie.common.model.Model;
import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Db;

import java.util.List;

/**
 * Created by wangrenhui on 14-4-22.
 */
@TableBind(tableName = "sec_user_menu")
public class UserMenu extends Model<UserMenu> {
  public static UserMenu dao = new UserMenu();

  public List<String> findMenuIds(String where, Object... paras) {
    List<String> result = Db.query("SELECT DISTINCT `userMenu`.menu_id " + getExceptSelectSql() + getWhere(where), paras);
    return result;
  }
}
