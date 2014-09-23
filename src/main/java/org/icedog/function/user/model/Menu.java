package org.icedog.function.user.model;

import cn.dreampie.sqlinxml.SqlKit;
import cn.dreampie.tablebind.TableBind;
import cn.dreampie.tree.TreeNode;
import cn.dreampie.web.model.Model;

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
    return find(getSelectSql() + SqlKit.sql("menu.findRoleByExceptSelect") + blank + getWhere(where), paras);
  }
}
