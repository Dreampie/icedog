package org.icedog.function.user.model;

import cn.dreampie.ValidateKit;
import cn.dreampie.sqlinxml.SqlKit;
import cn.dreampie.tablebind.TableBind;
import cn.dreampie.tree.TreeNode;
import cn.dreampie.web.model.Model;
import com.jfinal.plugin.activerecord.Db;

import java.util.List;

/**
 * Created by wangrenhui on 14-1-3.
 */
@TableBind(tableName = "sec_role")
public class Role extends Model<Role> implements TreeNode<Role> {
  public static Role dao = new Role();

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
  public List<Role> getChildren() {
    return this.get("children");
  }

  @Override
  public void setChildren(List<Role> children) {
    this.put("children", children);
  }


  public void initPermissiones() {
    this.put("permissiones", Permission.dao.findByRole("", this.getInt("id")));
  }

  public List<Permission> getPermissiones() {
    return this.get("permissiones");
  }

  public Role addPermission(Permission permission) {
    if (ValidateKit.isNullOrEmpty(permission)) {
      throw new NullPointerException("操作权限不存在");
    }
    RolePermission rolePermission = new RolePermission();
    rolePermission.set("role_id", this.get("id"));
    rolePermission.set("permission_id", permission.get("id"));
    rolePermission.save();

    return this;
  }

  public List<Role> findUserBy(String where, Object... paras) {
    return find(getSelectSql() + SqlKit.sql("role.findUserByExceptSelect") + blank + getWhere(where), paras);
  }

  public List<Role> findChildrenById(String where, Object... paras) {
    return find(getSelectSql() + SqlKit.sql("role.findChildrenByExceptSelect") + blank + getWhere(where), paras);
  }

  public List<Long> findChildrenIdsById(String where, Object... paras) {
    return Db.query("SELECT `role`.id " + SqlKit.sql("role.findChildrenByExceptSelect") + blank + getWhere(where), paras);
  }

}
