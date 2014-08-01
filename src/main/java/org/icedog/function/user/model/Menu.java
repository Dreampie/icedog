package org.icedog.function.user.model;

import cn.dreampie.common.model.Model;
import cn.dreampie.common.util.tree.TreeNode;
import com.jfinal.ext.plugin.sqlinxml.SqlKit;
import com.jfinal.ext.plugin.tablebind.TableBind;

import java.util.List;

/**
 * Created by wangrenhui on 14-1-3.
 */
@TableBind(tableName = "sec_menu")
public class Menu extends Model<Menu> implements TreeNode<Menu> {
  public static Menu dao = new Menu();

  @Override
  public long getId() {
//        return this.id;
    return this.getLong("id");
  }

  @Override
  public long getParentId() {
//        return this.parentId;
    return this.getLong("pid");
  }

  @Override
  public List<Menu> getChildren() {
    return this.get("children");
  }

  @Override
  public void setChildren(List<Menu> children) {
    this.put("children", children);
  }

  public List<Menu> findByRole(String where, Object... paras) {

    List<Menu> result = find(getSelectSql() + SqlKit.sql("menu.findRoleByExceptSelect") + blank + getWhere(where), paras);
    return result;
  }
}
