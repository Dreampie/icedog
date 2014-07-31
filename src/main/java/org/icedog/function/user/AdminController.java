package org.icedog.function.user;

import cn.dreampie.common.config.AppConstants;
import cn.dreampie.common.ehcache.CacheNameRemove;
import cn.dreampie.common.util.SortUtils;
import cn.dreampie.common.util.SubjectUtils;
import cn.dreampie.common.util.ValidateUtils;
import cn.dreampie.common.util.tree.TreeUtils;
import com.google.common.collect.Lists;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.plugin.ehcache.CacheName;
import org.icedog.common.web.controller.Controller;
import org.icedog.function.common.State;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wangrenhui on 14-1-3.
 */
public class AdminController extends Controller {
  public void index() {
    dynaRender("/view/admin/index.ftl");
  }

  @CacheName(AppConstants.DEFAULT_CACHENAME)
  public void user() {
    User user = SubjectUtils.me().getUser();
    keepPara("user_search");

    //查询当前用户的角色
    UserRole userRole = UserRole.dao.findFirstBy("`userRole`.user_id=" + user.get("id"));
    //当前用户的子集角色
    List<Role> roles = Role.dao.findChildrenById("`role`.deleted_at is null", userRole.get("role_id"));
    String roleIds = "";
    if (roles != null) {
      int size = roles.size();
      int i = 0;
      for (Role role : roles) {
        roleIds += role.get("id");
        if (i < size - 1) {
          roleIds += ",";
        }
        i++;
      }
    }
    //只能查询当前用户以下的角色
    String where = " `user`.id <> " + user.get("id") + " AND `userRole`.role_id in (" + roleIds + ")";
    String user_search = getPara("user_search");
    if (!ValidateUtils.me().isNullOrEmpty(user_search)) {
      where += " AND (INSTR(`user`.username,'" + user_search + "')>0 OR  INSTR(`user`.full_name,'" + user_search + "')>0 "
          + "OR  INSTR(`user`.mobile,'" + user_search + "')>0 OR  INSTR(`province`.name,'" + user_search + "')>0 "
          + "OR  INSTR(`city`.name,'" + user_search + "')>0 OR  INSTR(`county`.name,'" + user_search + "')>0 "
          + "OR INSTR(`userInfo`.street,'" + user_search + "')>0 OR INSTR(`userInfo`.zip_code,'" + user_search + "')>0 "
          + "OR INSTR(`user`.created_at,'" + user_search + "')>0 OR INSTR(`user`.email,'" + user_search + "')>0) ";
    }
//        String start_at = getPara("start_at");
//        if (ValidateUtils.me().isDateTime(start_at)) {
//            where += " AND `user`.created_at >= '" + start_at + "'";
//        }
//
//        String end_at = getPara("end_time");
//        if (ValidateUtils.me().isDateTime(end_at)) {
//            where += " AND `user`.created_at <= '" + end_at + "'";
//        }
//
//        Boolean deleted = getParaToBoolean("deleted");
//        if (!ValidateUtils.me().isNullOrEmpty(deleted) && deleted) {
//            where += " AND `user`.deleted_at is not null";
//        } else {
//            where += " AND `user`.deleted_at is null";
//        }


    Page<User> users = User.dao.paginateInfoBy(getParaToInt(0, 1), getParaToInt("pageSize", 15), where);
    Map userGroup = SortUtils.me().sort(users.getList(), "last_name");

    setAttr("roles", roles);
    setAttr("users", users);
    setAttr("userGroup", userGroup);
    setAttr("userStates", State.dao.findBy("`state`.type='user.state'"));
    dynaRender("/view/admin/user.ftl");
  }

  @CacheNameRemove(name = AppConstants.DEFAULT_CACHENAME)
  @Before({AdminValidator.deleteUserValidator.class, Tx.class})
  public void deleteUser() {
    keepModel(User.class);
    User user = getModel(User.class);

    if (user.getDate("deleted_at") != null) {
      user.set("deleted_at", new Date());
    } else {
      user.set("deleted_at", null);
    }

    if (user.update())
      setAttr("state", "success");
    else
      setAttr("state", "failure");
    dynaRender("/view/admin/user.ftl");
  }


  @CacheNameRemove(name = AppConstants.DEFAULT_CACHENAME)
  @Before({AdminValidator.UpdateRoleValidator.class, Tx.class})
  public void updateRole() {
    keepModel(UserRole.class);
    UserRole userRole = getModel(UserRole.class);

    boolean result = true;
    List<UserRole> aroles = UserRole.dao.findBy("`userRole`.user_id=" + userRole.get("user_id"));
    boolean mustAdd = true;
    if (!ValidateUtils.me().isNullOrEmpty(aroles)) {
      //delete
      for (UserRole ar : aroles) {
        if (ar.get("role_id") != userRole.get("role_id")) {
          ar.delete();
        } else {
          mustAdd = false;
        }
      }
    }
    //add
    if (mustAdd) {
      result = result && userRole.save();
    }


    if (result)
      setAttr("state", "success");
    else
      setAttr("state", "failure");
    dynaRender("/view/admin/user.ftl");
  }

  @CacheNameRemove(name = AppConstants.DEFAULT_CACHENAME)
  @Before({AdminValidator.UpdatePwdValidator.class, Tx.class})
  public void updatePwd() {
    keepModel(User.class);
    User user = getModel(User.class);
    cn.dreampie.common.plugin.shiro.hasher.HasherInfo passwordInfo = cn.dreampie.common.plugin.shiro.hasher.HasherUtils.me().hash(user.getStr("password"), cn.dreampie.common.plugin.shiro.hasher.Hasher.DEFAULT);
    user.set("password", passwordInfo.getHashResult());
    user.set("hasher", passwordInfo.getHasher().value());
    user.set("salt", passwordInfo.getSalt());

    if (user.update()) {
      setAttr("state", "success");
    } else
      setAttr("state", "failure");
    dynaRender("/view/admin/user.ftl");
  }

  @CacheName(AppConstants.DEFAULT_CACHENAME)
  public void role() {
    User user = SubjectUtils.me().getUser();
    keepPara("user_search");

    //查询当前用户的角色
    UserRole userRole = UserRole.dao.findFirstBy("`userRole`.user_id=" + user.get("id"));
    //当前用户的子集角色
    List<Role> roles = Role.dao.findChildrenById("`role`.deleted_at is null", userRole.get("role_id"));
    roles.add(0, user.getRole());
    if (!ValidateUtils.me().isNullOrEmpty(roles))
      setAttr("role", user.getRole());


    List<Permission> authories = Permission.dao.findBy("`permission`.deleted_at is NULL");
    setAttr("rolestree", TreeUtils.toTree(roles));
    setAttr("permissionestree", TreeUtils.toTreeLevel(authories, 2));

    dynaRender("/view/admin/role.ftl");
  }

  @CacheName(AppConstants.DEFAULT_CACHENAME)
  public void permIds() {
    Integer roleId = getParaToInt("role.id");
    if (roleId > 0) {
      List<String> permIds = RolePermission.dao.findPermissionIds("`rolePermission`.role_id=" + roleId);
      setAttr("permIds", permIds);
    }
    dynaRender("/view/admin/role.ftl");
  }

  @CacheNameRemove(name = AppConstants.DEFAULT_CACHENAME)
  @Before({AdminValidator.RoleSaveValidator.class, Tx.class})
  public void roleSave() {
    Role role = getModel(Role.class);
    Role parent = null;
    if (role.getParentId() == 0) {
      parent = Role.dao.findFirstBy("`role`.pid=0 ORDER BY `role`.right_code DESC");
    } else
      parent = Role.dao.findById(role.getParentId());
    boolean result = false;
    if (!ValidateUtils.me().isNullOrEmpty(parent)) {
      Role.dao.updateBy("`role`.left_code=`role`.left_code+2", "`role`.left_code>=" + parent.get("right_code"));
      Role.dao.updateBy("`role`.right_code=`role`.right_code+2", "`role`.right_code>=" + parent.get("right_code"));
      role.set("left_code", parent.getLong("right_code"));
      role.set("right_code", parent.getLong("right_code") + 1);
      role.set("created_at", new Date());
      if (ValidateUtils.me().isNullOrEmpty(role.get("id"))) {
        role.remove("id");
      }
      result = role.save();
    }

    if (result) {
      setAttr("state", "success");
    } else {
      setAttr("state", "failure");
    }
    dynaRender("/view/admin/role.ftl");
  }

  @CacheNameRemove(name = AppConstants.DEFAULT_CACHENAME)
  @Before({AdminValidator.RoleUpdateValidator.class, Tx.class})
  public void roleUpdate() {
    Role role = getModel(Role.class);
    if (ValidateUtils.me().isNullOrEmpty(role.get("pid"))) {
      role.remove("pid");
    }
    role.set("updated_at", new Date());
    if (role.update()) {
      setAttr("state", "success");
    } else {
      setAttr("state", "failure");
    }
    dynaRender("/view/admin/role.ftl");
  }

  @CacheNameRemove(name = AppConstants.DEFAULT_CACHENAME)
  @Before({AdminValidator.RoleDeleteValidator.class, Tx.class})
  public void roleDrop() {

    Integer id = getParaToInt("role.id");
    Role role = Role.dao.findById(id);
    boolean result = false;
    if (!ValidateUtils.me().isNullOrEmpty(role)) {
      Role.dao.updateBy("`role`.left_code=`role`.left_code-2", "`role`.left_code>=" + role.get("left_code"));
      Role.dao.updateBy("`role`.right_code=`role`.right_code-2", "`role`.right_code>=" + role.get("right_code"));

      result = role.delete();
      if (result) {
        RolePermission.dao.dropBy("role_id=" + role.get("id"));
      }
    }

    if (result) {
      setAttr("state", "success");
    } else {
      setAttr("state", "failure");
    }
    dynaRender("/view/admin/role.ftl");
  }

  @CacheNameRemove(name = AppConstants.DEFAULT_CACHENAME)
  @Before({AdminValidator.PermSaveValidator.class, Tx.class})
  public void permSave() {
    Permission permission = getModel(Permission.class);
    Permission parent = null;
    if (permission.getParentId() == 0) {
      parent = Permission.dao.findFirstBy("`permission`.pid=0 ORDER BY `permission`.right_code DESC");
    } else
      parent = Permission.dao.findById(permission.getParentId());
    boolean result = false;
    if (!ValidateUtils.me().isNullOrEmpty(parent)) {
      Permission.dao.updateBy("`permission`.left_code=`permission`.left_code+2", "`permission`.left_code>=" + parent.get("right_code"));
      Permission.dao.updateBy("`permission`.right_code=`permission`.right_code+2", "`permission`.right_code>=" + parent.get("right_code"));
      permission.set("left_code", parent.getLong("right_code"));
      permission.set("right_code", parent.getLong("right_code") + 1);
      permission.set("created_at", new Date());
      if (ValidateUtils.me().isNullOrEmpty(permission.get("id"))) {
        permission.remove("id");
      }
      result = permission.save();
    }
    if (result) {
      Role admin = Role.dao.findFirstBy("`role`.pid=0");
      admin.addPermission(permission);
      setAttr("state", "success");
    } else {
      setAttr("state", "failure");
    }
    dynaRender("/view/admin/role.ftl");
  }

  @CacheNameRemove(name = AppConstants.DEFAULT_CACHENAME)
  @Before({AdminValidator.PermUpdateValidator.class, Tx.class})
  public void permUpdate() {
    Permission permission = getModel(Permission.class);
    if (ValidateUtils.me().isNullOrEmpty(permission.get("pid"))) {
      permission.remove("pid");
    }
    permission.set("updated_at", new Date());
    if (permission.update()) {
      setAttr("state", "success");
    } else {
      setAttr("state", "failure");
    }
    dynaRender("/view/admin/role.ftl");
  }

  @CacheNameRemove(name = AppConstants.DEFAULT_CACHENAME)
  @Before({AdminValidator.PermDeleteValidator.class, Tx.class})
  public void permDrop() {

    Integer id = getParaToInt("permission.id");
    Permission permission = Permission.dao.findById(id);
    boolean result = false;
    if (!ValidateUtils.me().isNullOrEmpty(permission)) {
      Permission.dao.updateBy("`permission`.left_code=`permission`.left_code-2", "`permission`.left_code>=" + permission.get("left_code"));
      Permission.dao.updateBy("`permission`.right_code=`permission`.right_code-2", "`permission`.right_code>=" + permission.get("right_code"));
      result = permission.delete();
      if (result) {
        RolePermission.dao.dropBy("permission_id=" + permission.get("id"));
      }
    }
    if (result) {
      setAttr("state", "success");
    } else {
      setAttr("state", "failure");
    }
    dynaRender("/view/admin/role.ftl");
  }

  @CacheNameRemove(name = AppConstants.DEFAULT_CACHENAME)
  @Before({AdminValidator.RolePermsValidator.class, Tx.class})
  public void permsAdd() {
    String[] idsPara = getParaValues("permission.id");
    Integer roleId = getParaToInt("role.id");
    //需要添加的权限
    List<String> ids = Lists.newArrayList(idsPara);
    //已存在的权限
    List<String> permIds = RolePermission.dao.findPermissionIds("`rolePermission`.role_id=" + roleId);
    Integer id = null;
    //移除重复id
    for (int i = 0; i < ids.size(); i++) {
      id = new Integer(ids.get(i));
      if (permIds.contains(id)) {
        permIds.remove(id);
        ids.remove(i);
        i--;
      }
    }
    boolean result = true;
    //添加关系
    RolePermission rolePermission = null;
    for (int i = 0; i < ids.size(); i++) {
      rolePermission = new RolePermission();
      rolePermission.set("role_id", roleId);
      rolePermission.set("permission_id", ids.get(i));
      result = result && rolePermission.save();
    }
    //删除关系

    for (int i = 0; i < permIds.size(); i++) {
      result = result && RolePermission.dao.dropBy("role_id = ? AND permission_id = ?", roleId, permIds.get(i));
    }
    if (result) {
      setAttr("state", "success");
    } else {
      setAttr("state", "failure");
    }
    dynaRender("/view/admin/role.ftl");
  }
}
