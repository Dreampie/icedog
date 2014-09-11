package org.icedog.function.user;

import cn.dreampie.web.filter.ThreadLocalKit;
import cn.dreampie.ValidateKit;
import cn.dreampie.shiro.core.SubjectKit;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import org.icedog.function.user.model.Permission;
import org.icedog.function.user.model.Role;
import org.icedog.function.user.model.User;
import org.icedog.function.user.model.UserRole;

import java.util.List;

/**
 * Created by wangrenhui on 2014/6/10.
 */
public class AdminValidator {

  public static class deleteUserValidator extends Validator {

    @Override
    protected void validate(Controller c) {

      boolean idEmpty = ValidateKit.isNullOrEmpty(c.getPara("user.id"));
      if (idEmpty) addError("user_idMsg", "账户参数异常");
      if (!idEmpty && !ValidateKit.isPositiveNumber(c.getPara("user.id")))
        addError("user_idMsg", "账户参数异常");

      User u = User.dao.findFirstBy("`user`.id=" + c.getPara("user.id"));
      if (ValidateKit.isNullOrEmpty(u))
        addError("user_idMsg", "账户不存在");

      if (!ValidateKit.isNullOrEmpty(u)) {
        UserRole uRole = UserRole.dao.findFirstBy("`userRole`.user_id=" + u.get("id"));

        User user = SubjectKit.getUser();

        //查询当前用户的角色
        UserRole userRole = UserRole.dao.findFirstBy("`userRole`.user_id=" + user.get("id"));
        //当前用户的子集角色
        List<Long> roleIds = Role.dao.findChildrenIdsById("`role`.deleted_at is null", userRole.get("role_id"));

        if (!roleIds.contains(uRole.getLong("role_id"))) {
          addError("user_idMsg", "没有删除该用户的权限");
        }

      }
    }

    @Override
    protected void handleError(Controller c) {
      c.keepModel(User.class);
      c.keepPara();
      c.setAttr("state", "failure");
      if (ThreadLocalKit.isJson())
        c.renderJson();
      else
        c.forwardAction("/admin/user?" + c.getRequest().getQueryString());
    }
  }

  public static class UpdateRoleValidator extends Validator {

    @Override
    protected void validate(Controller c) {

      boolean idEmpty = ValidateKit.isNullOrEmpty(c.getPara("userRole.user_id"));
      if (idEmpty) addError("user_idMsg", "账户参数异常");
      if (!idEmpty && !ValidateKit.isPositiveNumber(c.getPara("userRole.user_id")))
        addError("user_idMsg", "账户参数异常");

      if (ValidateKit.isNullOrEmpty(User.dao.findFirstBy("`user`.id=" + c.getPara("userRole.user_id"))))
        addError("user_idMsg", "账户不存在");

      boolean roleidEmpty = ValidateKit.isNullOrEmpty(c.getPara("userRole.role_id"));
      if (roleidEmpty) addError("role_idMsg", "请选择一个角色");
      if (!roleidEmpty && !ValidateKit.isPositiveNumber(c.getPara("userRole.role_id")))
        addError("role_idMsg", "角色参数异常");

      if (!roleidEmpty) {
        Role newRole = Role.dao.findFirstBy("`role`.id='" + c.getPara("userRole.role_id") + "'");
        if (ValidateKit.isNullOrEmpty(newRole)) {
          addError("role_idMsg", "角色不存在");
        } else {
          User user = SubjectKit.getUser();

          //查询当前用户的角色
          UserRole userRole = UserRole.dao.findFirstBy("`userRole`.user_id=" + user.get("id"));
          //当前用户的子集角色
          List<Long> roleIds = Role.dao.findChildrenIdsById("`role`.deleted_at is null", userRole.get("role_id"));

          if (!roleIds.contains(newRole.getLong("id"))) {
            addError("role_idMsg", "没有修改该角色的权限");
          }
        }
      }
    }

    @Override
    protected void handleError(Controller c) {
      c.keepModel(UserRole.class);
      c.keepPara();
      c.setAttr("state", "failure");
      if (ThreadLocalKit.isJson())
        c.renderJson();
      else
        c.forwardAction("/admin/user?" + c.getRequest().getQueryString());
    }
  }

  public static class UpdatePwdValidator extends Validator {
    protected void validate(Controller c) {
      boolean idEmpty = ValidateKit.isNullOrEmpty(c.getPara("user.id"));
      if (idEmpty) addError("user_idMsg", "账户参数异常");
      if (!idEmpty && !ValidateKit.isPositiveNumber(c.getPara("user.id")))
        addError("user_idMsg", "账户参数异常");

      User u = User.dao.findFirstBy("`user`.id=" + c.getPara("user.id"));
      if (ValidateKit.isNullOrEmpty(u))
        addError("user_idMsg", "账户不存在");

      if (!ValidateKit.isNullOrEmpty(u)) {
        UserRole uRole = UserRole.dao.findFirstBy("`userRole`.user_id=" + u.get("id"));

        User user = SubjectKit.getUser();

        //查询当前用户的角色
        UserRole userRole = UserRole.dao.findFirstBy("`userRole`.user_id=" + user.get("id"));
        //当前用户的子集角色
        List<Long> roleIds = Role.dao.findChildrenIdsById("`role`.deleted_at is null", userRole.get("role_id"));

        if (!roleIds.contains(uRole.getLong("role_id"))) {
          addError("user_idMsg", "没有修改该用户的权限");
        }

      }

      boolean passwordEmpty = ValidateKit.isNullOrEmpty(c.getPara("user.password"));
      if (passwordEmpty) addError("user_passwordMsg", "密码不能为空");
      if (!passwordEmpty && !ValidateKit.isPassword(c.getPara("user.password")))
        addError("user_passwordMsg", "密码为英文字母 、数字和下划线长度为5-18");

    }

    protected void handleError(Controller c) {
      c.keepModel(User.class);
      c.keepPara();
      c.setAttr("state", "failure");
      if (ThreadLocalKit.isJson())
        c.renderJson();
      else
        c.forwardAction("/admin/user?" + c.getRequest().getQueryString());
    }
  }

  public static class RoleUpdateValidator extends Validator {
    protected void validate(Controller c) {
      boolean idEmpty = ValidateKit.isNullOrEmpty(c.getPara("role.id"));
      if (idEmpty) addError("role_idMsg", "角色编号异常");
      if (!idEmpty && !ValidateKit.isPositiveNumber(c.getPara("role.id")))
        addError("role_idMsg", "角色编号异常");

      if (!idEmpty) {
        if (ValidateKit.isNullOrEmpty(Role.dao.findFirstBy("`role`.id='" + c.getPara("role.id") + "'"))) {
          addError("role_idMsg", "角色不存在");
        }
      }

      boolean nameEmpty = ValidateKit.isNullOrEmpty(c.getPara("role.name"));
      if (nameEmpty) addError("role_nameMsg", "角色名称不能为空");
      if (!nameEmpty && !ValidateKit.isLength(c.getPara("role.name"), 2, 10))
        addError("role_nameMsg", "角色名称长度2-10");

      boolean valueEmpty = ValidateKit.isNullOrEmpty(c.getPara("role.value"));
      if (valueEmpty) addError("role_valueMsg", "角色名称不能为空");
      if (!valueEmpty && !ValidateKit.isLength(c.getPara("role.value"), 2, 20))
        addError("role_valueMsg", "角色名称长度2-20");

      if (!valueEmpty) {
        Role role = Role.dao.findFirstBy("`role`.value='" + c.getPara("role.value") + "'");
        if (role != null) addError("role_valueMsg", "角色标识已存在");
      }


      boolean introEmpty = ValidateKit.isNullOrEmpty(c.getPara("role.intro"));
      if (introEmpty) addError("role_introMsg", "角色描述不能为空");
      if (!introEmpty && !ValidateKit.isLength(c.getPara("role.intro"), 3, 240))
        addError("role_introMsg", "角色描述长度3-240");
    }

    protected void handleError(Controller c) {
      c.keepModel(Role.class);
      c.keepPara();

      c.setAttr("state", "failure");
      if (ThreadLocalKit.isJson())
        c.renderJson();
      else
        c.forwardAction("/admin/role");
    }
  }

  public static class RoleSaveValidator extends Validator {
    protected void validate(Controller c) {
      boolean pidEmpty = ValidateKit.isNullOrEmpty(c.getPara("role.pid"));
      if (pidEmpty) addError("role_pidMsg", "父级id不能为空");
      if (!pidEmpty && !ValidateKit.isPositiveNumber(c.getPara("role.pid")))
        addError("role_pidMsg", "父级id必须为整数");

      boolean nameEmpty = ValidateKit.isNullOrEmpty(c.getPara("role.name"));
      if (nameEmpty) addError("role_nameMsg", "角色名称不能为空");
      if (!nameEmpty && !ValidateKit.isLength(c.getPara("role.name"), 2, 10))
        addError("role_nameMsg", "角色名称长度2-10");

      boolean valueEmpty = ValidateKit.isNullOrEmpty(c.getPara("role.value"));
      if (valueEmpty) addError("role_valueMsg", "角色名称不能为空");
      if (!valueEmpty && !ValidateKit.isLength(c.getPara("role.value"), 2, 20))
        addError("role_valueMsg", "角色名称长度2-20");
      if (!valueEmpty) {
        Role role = Role.dao.findFirstBy("`role`.value='" + c.getPara("role.value") + "'");
        if (role != null) addError("role_valueMsg", "角色标识已存在");
      }

      boolean introEmpty = ValidateKit.isNullOrEmpty(c.getPara("role.intro"));
      if (introEmpty) addError("role_introMsg", "角色描述不能为空");
      if (!introEmpty && !ValidateKit.isLength(c.getPara("role.intro"), 3, 240))
        addError("role_introMsg", "角色描述长度3-240");
    }

    protected void handleError(Controller c) {
      c.keepModel(Role.class);
      c.keepPara();

      c.setAttr("state", "failure");
      if (ThreadLocalKit.isJson())
        c.renderJson();
      else
        c.forwardAction("/admin/role");
    }
  }

  public static class RolePermsValidator extends Validator {

    protected void validate(Controller c) {
      boolean idEmpty = ValidateKit.isNullOrEmpty(c.getPara("role.id"));
      if (idEmpty) addError("role_idMsg", "角色参数异常");
      boolean idNum = ValidateKit.isPositiveNumber(c.getPara("role.id"));
      if (!idEmpty && !idNum) addError("role_idMsg", "角色参数异常");
      if (!idEmpty && idNum) {
        Role role = Role.dao.findById(c.getPara("role.id"));
        if (ValidateKit.isNullOrEmpty(role)) addError("role_idMsg", "角色不存在");
      }

    }

    protected void handleError(Controller c) {
      c.keepModel(Role.class);
      c.keepPara();
      c.setAttr("state", "failure");
      if (ThreadLocalKit.isJson())
        c.renderJson();
      else
        c.forwardAction("/admin/role");
    }
  }


  public static class RoleDeleteValidator extends Validator {

    protected void validate(Controller c) {
      boolean idEmpty = ValidateKit.isNullOrEmpty(c.getPara("role.id"));
      if (idEmpty) addError("role_idMsg", "角色参数异常");
      boolean idNum = ValidateKit.isPositiveNumber(c.getPara("role.id"));
      if (!idEmpty && !idNum) addError("role_idMsg", "角色参数异常");
      if (!idEmpty && idNum) {
        Role role = Role.dao.findById(c.getPara("role.id"));
        boolean roleEmpty = ValidateKit.isNullOrEmpty(role);
        if (roleEmpty) addError("role_idMsg", "角色不存在");
        if (!roleEmpty) {

          if (SubjectKit.wasBaseRole(role.getStr("value"))) {
            addError("role_idMsg", "基础角色不能删除");
          } else {

            long childrenCount = Role.dao.countBy("`role`.pid=" + c.getPara("role.id"));
            if (childrenCount > 0) addError("role_idMsg", "删除当前角色，必须先删除子角色");

            List<String> userIds = UserRole.dao.findUserIds("`userRole`.role_id=" + c.getPara("role.id"));
            boolean userIdsEmpty = ValidateKit.isNullOrEmpty(userIds);
            if (!userIdsEmpty) addError("role_idMsg", "该角色下有用户存在");
          }
        }
      }
    }

    protected void handleError(Controller c) {
      c.keepModel(Role.class);
      c.keepPara();
      c.setAttr("state", "failure");
      if (ThreadLocalKit.isJson())
        c.renderJson();
      else
        c.forwardAction("/admin/role");
    }
  }


  public static class PermDeleteValidator extends Validator {

    protected void validate(Controller c) {
      boolean idEmpty = ValidateKit.isNullOrEmpty(c.getPara("permission.id"));
      if (idEmpty) addError("permission_idMsg", "权限id不能为空");
      boolean idNum = ValidateKit.isPositiveNumber(c.getPara("permission.id"));
      if (!idEmpty && !idNum) addError("permission_idMsg", "权限id必须为正整数");
      if (!idEmpty && idNum) {
        Permission permission = Permission.dao.findById(c.getPara("permission.id"));
        if (ValidateKit.isNullOrEmpty(permission)) addError("permission_idMsg", "权限不存在");
        long childrenCount = Permission.dao.countBy("`permission`.pid=" + c.getPara("permission.id"));
        if (childrenCount > 0) addError("permission_idMsg", "删除当前权限，必须先删除子权限");

      }

    }

    protected void handleError(Controller c) {
      c.keepModel(Permission.class);
      c.keepPara();
      c.setAttr("state", "failure");
      if (ThreadLocalKit.isJson())
        c.renderJson();
      else
        c.forwardAction("/admin/role");
    }
  }

  public static class PermSaveValidator extends Validator {
    protected void validate(Controller c) {
      boolean pidEmpty = ValidateKit.isNullOrEmpty(c.getPara("permission.pid"));
      if (pidEmpty) addError("permission_pidMsg", "父级id不能为空");
      if (!pidEmpty && !ValidateKit.isPositiveNumber(c.getPara("permission.pid")))
        addError("permission_pidMsg", "父级id必须为整数");
      boolean nameEmpty = ValidateKit.isNullOrEmpty(c.getPara("permission.name"));
      if (nameEmpty) addError("permission_nameMsg", "权限名称不能为空");
      if (!nameEmpty && !ValidateKit.isLength(c.getPara("permission.name"), 2, 10))
        addError("permission_nameMsg", "权限名称长度2-10");

      boolean valueEmpty = ValidateKit.isNullOrEmpty(c.getPara("permission.value"));
      if (valueEmpty) addError("permission_valueMsg", "权限名称不能为空");
      if (!valueEmpty && !ValidateKit.isLength(c.getPara("permission.value"), 2, 20))
        addError("permission_valueMsg", "权限名称长度2-20");

      if (!valueEmpty) {
        Permission permission = Permission.dao.findFirstBy("`permission`.value='" + c.getPara("permission.value") + "'");
        if (permission != null) addError("permission_valueMsg", "权限标识已存在");
      }

      boolean urlEmpty = ValidateKit.isNullOrEmpty(c.getPara("permission.url"));
      if (urlEmpty) addError("permission_urlMsg", "权限url不能为空");
      if (!urlEmpty && !ValidateKit.match("^[\\w/\\*]+$", c.getPara("permission.url")))
        addError("permission_urlMsg", "权限url必须英文字母 、数字、*、下划线和右斜线");

      boolean introEmpty = ValidateKit.isNullOrEmpty(c.getPara("permission.intro"));
      if (introEmpty) addError("permission_introMsg", "权限描述不能为空");
      if (!introEmpty && !ValidateKit.isLength(c.getPara("permission.intro"), 3, 240))
        addError("permission_introMsg", "权限描述长度3-240");
    }

    protected void handleError(Controller c) {
      c.keepModel(Permission.class);
      c.keepPara();

      c.setAttr("state", "failure");
      if (ThreadLocalKit.isJson())
        c.renderJson();
      else
        c.forwardAction("/admin/role");
    }
  }

  public static class PermUpdateValidator extends Validator {
    protected void validate(Controller c) {
      boolean idEmpty = ValidateKit.isNullOrEmpty(c.getPara("permission.id"));
      if (idEmpty) addError("permission_idMsg", "id不能为空");
      if (!idEmpty && !ValidateKit.isPositiveNumber(c.getPara("permission.id")))
        addError("permission_idMsg", "id必须为整数");

      boolean nameEmpty = ValidateKit.isNullOrEmpty(c.getPara("permission.name"));
      if (nameEmpty) addError("permission_nameMsg", "权限名称不能为空");
      if (!nameEmpty && !ValidateKit.isLength(c.getPara("permission.name"), 2, 10))
        addError("permission_nameMsg", "权限名称长度2-10");


      boolean valueEmpty = ValidateKit.isNullOrEmpty(c.getPara("permission.value"));
      if (valueEmpty) addError("permission_valueMsg", "权限名称不能为空");
      if (!valueEmpty && !ValidateKit.isLength(c.getPara("permission.value"), 2, 20))
        addError("permission_valueMsg", "权限名称长度2-20");

      if (!valueEmpty) {
        Permission permission = Permission.dao.findFirstBy("`permission`.value='" + c.getPara("permission.value") + "'");
        if (permission != null) addError("permission_valueMsg", "权限标识已存在");
      }

      boolean urlEmpty = ValidateKit.isNullOrEmpty(c.getPara("permission.url"));
      if (urlEmpty) addError("permission_urlMsg", "权限url不能为空");
      if (!urlEmpty && !ValidateKit.match("^[\\w/\\*]+$", c.getPara("permission.url")))
        addError("permission_urlMsg", "权限url必须英文字母 、数字、*、下划线和右斜线");

      boolean introEmpty = ValidateKit.isNullOrEmpty(c.getPara("permission.intro"));
      if (introEmpty) addError("permission_introMsg", "权限描述不能为空");
      if (!introEmpty && !ValidateKit.isLength(c.getPara("permission.intro"), 3, 240))
        addError("permission_introMsg", "权限描述长度3-240");
    }

    protected void handleError(Controller c) {
      c.keepModel(Permission.class);
      c.keepPara();

      c.setAttr("state", "failure");
      if (ThreadLocalKit.isJson())
        c.renderJson();
      else
        c.forwardAction("/admin/role");
    }
  }
}
