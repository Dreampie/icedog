package org.icedog.function.user;

import cn.dreampie.ValidateKit;
import cn.dreampie.shiro.core.SubjectKit;
import cn.dreampie.shiro.hasher.Hasher;
import cn.dreampie.shiro.hasher.HasherKit;
import cn.dreampie.web.filter.ThreadLocalKit;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import org.icedog.function.user.model.Follower;
import org.icedog.function.user.model.User;

/**
 * Created by wangrenhui on 2014/6/10.
 */
public class UserValidator {

  public static class addFollowingValidator extends Validator {

    @Override
    protected void validate(Controller c) {

      boolean idEmpty = ValidateKit.isNullOrEmpty(c.getPara("follower.id"));
      if (idEmpty) addError("idMsg", "联系人参数异常");
      if (!idEmpty && !ValidateKit.isPositiveNumber(c.getPara("follower.id")))
        addError("idMsg", "联系人参数异常");
      if (!idEmpty) {
        Follower con = Follower.dao.findById(c.getPara("follower.id"));
        if (ValidateKit.isNullOrEmpty(con))
          addError("idMsg", "联系人不存在");
      }
      boolean introEmpty = ValidateKit.isNullOrEmpty(c.getPara("follower.intro"));
      if (introEmpty) addError("introMsg", "备注不能为空");
      if (!introEmpty && !ValidateKit.isLength(c.getPara("follower.intro"), 3, 240))
        addError("introMsg", "备注长度为3-240个字符");

    }

    @Override
    protected void handleError(Controller c) {
      c.keepModel(Follower.class);
      c.keepPara();
      c.setAttr("state", "failure");
      if (ThreadLocalKit.isJson())
        c.renderJson();
      else
        c.forwardAction("/user/follower?" + c.getRequest().getQueryString());
    }
  }

  public static class deleteFollowingValidator extends Validator {

    @Override
    protected void validate(Controller c) {

      boolean idEmpty = ValidateKit.isNullOrEmpty(c.getPara("follower.id"));
      if (idEmpty) addError("idMsg", "联系人参数异常");
      if (!idEmpty && !ValidateKit.isPositiveNumber(c.getPara("follower.id")))
        addError("idMsg", "联系人参数异常");
      if (!idEmpty) {
        Follower con = Follower.dao.findById(c.getPara("follower.id"));
        if (ValidateKit.isNullOrEmpty(con))
          addError("idMsg", "联系人不存在");
      }

    }

    @Override
    protected void handleError(Controller c) {
      c.keepModel(Follower.class);
      c.keepPara();
      c.setAttr("state", "failure");
      if (ThreadLocalKit.isJson())
        c.renderJson();
      else
        c.forwardAction("/user/following?" + c.getRequest().getQueryString());
    }
  }

  public static class UpdateIntroValidator extends Validator {

    @Override
    protected void validate(Controller c) {

      boolean idEmpty = ValidateKit.isNullOrEmpty(c.getPara("follower.id"));
      if (idEmpty) addError("idMsg", "联系人参数异常");
      if (!idEmpty && !ValidateKit.isPositiveNumber(c.getPara("follower.id")))
        addError("idMsg", "联系人参数异常");
      if (!idEmpty) {
        Follower con = Follower.dao.findById(c.getPara("follower.id"));
        if (ValidateKit.isNullOrEmpty(con))
          addError("idMsg", "联系人不存在");
      }
      boolean introEmpty = ValidateKit.isNullOrEmpty(c.getPara("follower.intro"));
      if (introEmpty) addError("introMsg", "备注不能为空");
      if (!introEmpty && !ValidateKit.isLength(c.getPara("follower.intro"), 3, 240))
        addError("introMsg", "备注长度为3-240个字符");

    }

    @Override
    protected void handleError(Controller c) {
      c.keepModel(Follower.class);
      c.keepPara();
      c.setAttr("state", "failure");
      if (ThreadLocalKit.isJson())
        c.renderJson();
      else
        c.forwardAction("/user/following?" + c.getRequest().getQueryString());
    }
  }


  public static class UpdatePwdValidator extends Validator {

    @Override
    protected void validate(Controller c) {

      boolean idEmpty = ValidateKit.isNullOrEmpty(c.getPara("user.id"));
      if (idEmpty) addError("user_idMsg", "账户编号丢失");
      if (!idEmpty && !ValidateKit.isPositiveNumber(c.getPara("user.id")))
        addError("user_idMsg", "账户编号必须为数字");

      if (ValidateKit.isNullOrEmpty(User.dao.findBy("`user`.id=" + c.getPara("user.id"))))
        addError("user_idMsg", "账户不存在");

      boolean userEmpty = ValidateKit.isNullOrEmpty(c.getPara("user.username"));
      if (userEmpty) addError("user_usernameMsg", "账户丢失");
      if (!userEmpty && !ValidateKit.isUsername(c.getPara("user.username")))
        addError("user_usernameMsg", "账户为英文字母 、数字和下划线长度为5-18");

      boolean passwordEmpty = ValidateKit.isNullOrEmpty(c.getPara("user.password"));
      if (passwordEmpty) addError("user_passwordMsg", "密码不能为空");
      if (!passwordEmpty && !ValidateKit.isPassword(c.getPara("user.password")))
        addError("user_passwordMsg", "密码为英文字母 、数字和下划线长度为5-18");

      if (!passwordEmpty && !c.getPara("user.password").equals(c.getPara("repassword")))
        addError("repasswordMsg", "重复密码不匹配");


      boolean oldpasswordEmpty = ValidateKit.isNullOrEmpty(c.getPara("oldpassword"));
      if (oldpasswordEmpty) addError("user_oldpasswordMsg", "原始密码不能为空");

      if (!oldpasswordEmpty && !ValidateKit.isPassword(c.getPara("oldpassword")))
        addError("user_oldpasswordMsg", "密码为英文字母 、数字和下划线长度为5-18");

      if (!oldpasswordEmpty) {
        User user = SubjectKit.getUser();

        if (user.getStr("hasher").equals(Hasher.DEFAULT.value())) {
          boolean match = HasherKit.match(c.getPara("oldpassword"), user.getStr("password"), Hasher.DEFAULT);

          if (!match) {
            addError("user_oldpasswordMsg", "原始密码不匹配");
          }
        } else {
          addError("user_oldpasswordMsg", "不支持的加密方式");
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
        c.forwardAction("/user/center");
    }
  }
}
