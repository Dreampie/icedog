package org.icedog.function.user.model;

import cn.dreampie.sqlinxml.SqlKit;
import cn.dreampie.tablebind.TableBind;
import cn.dreampie.tree.TreeNode;
import cn.dreampie.web.model.Model;

import java.util.List;

/**
 * Created by wangrenhui on 14-1-3.
 */
@TableBind(tableName = "sec_permission")
public class Permission extends Model<Permission> implements TreeNode<Permission> {
  public static Permission dao = new Permission();

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
  public List<Permission> getChildren() {
    return this.get("children");
  }

  @Override
  public void setChildren(List<Permission> children) {
    this.put("children", children);
  }

  public List<Permission> findByRole(String where, Object... paras) {

    return find(getSelectSql() + SqlKit.sql("permission.findRoleByExceptSelect") + blank + getWhere(where), paras);
  }
}
