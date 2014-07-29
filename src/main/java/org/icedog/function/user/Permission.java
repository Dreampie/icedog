package org.icedog.function.user;

import cn.dreampie.common.model.Model;
import cn.dreampie.common.util.tree.TreeNode;
import com.jfinal.ext.plugin.tablebind.TableBind;

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

    List<Permission> result = find(getSelectSql() + cn.dreampie.common.plugin.sqlinxml.SqlKit.sql("permission.findRoleByExceptSelect") + blank + getWhere(where), paras);
    return result;
  }
}
