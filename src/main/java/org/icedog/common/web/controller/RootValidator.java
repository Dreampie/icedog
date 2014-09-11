package org.icedog.common.web.controller;

import cn.dreampie.ValidateKit;
import cn.dreampie.shiro.core.SubjectKit;
import cn.dreampie.web.filter.ThreadLocalKit;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import org.icedog.common.config.AppConstants;
import org.icedog.function.user.model.User;

/**
 * Created by wangrenhui on 14-1-2.
 */
public class RootValidator {
  static String indexView = "view/index.html";

  public static class ForgetValidator extends Validator {
    protected void validate(Controller c) {
      boolean emailEmpty = ValidateKit.isNullOrEmpty(c.getPara("user.email"));
      if (!emailEmpty && !ValidateKit.isEmail(c.getPara("user.email"))) addError("emailMsg", "邮箱格式验证失败");

      User u = User.dao.findFirstBy("`user`.email=? AND `user`.deleted_at is null", c.getPara("user.email"));
      if (ValidateKit.isNullOrEmpty(u)) addError("emailMsg", "该邮箱不存在");

      boolean captchaEmpty = ValidateKit.isNullOrEmpty(c.getPara("captcha"));
      if (captchaEmpty) addError("captchaMsg", "验证码不能为空");
      if (!captchaEmpty && !SubjectKit.doCaptcha(AppConstants.CAPTCHA_NAME,c.getPara("captcha"))) addError("captchaMsg", "验证码验证失败");
    }

    protected void handleError(Controller c) {
      c.keepModel(User.class);

      if (ThreadLocalKit.isJson())
        c.renderJson();
      else
        c.render(indexView);
    }
  }

  public static class SignupEmailValidator extends Validator {
    protected void validate(Controller c) {


      boolean emailEmpty = ValidateKit.isNullOrEmpty(c.getPara("user.email"));
      if (!emailEmpty && !ValidateKit.isEmail(c.getPara("user.email"))) addError("emailMsg", "邮箱格式验证失败");

      User u = User.dao.findFirstBy("`user`.email=?", c.getPara("user.email"));
      if (!ValidateKit.isNullOrEmpty(u)) addError("emailMsg", "该邮箱已经注册");

      boolean captchaEmpty = ValidateKit.isNullOrEmpty(c.getPara("captcha"));
      if (captchaEmpty) addError("captchaMsg", "验证码不能为空");
      if (!captchaEmpty && !SubjectKit.doCaptcha(AppConstants.CAPTCHA_NAME,c.getPara("captcha"))) addError("captchaMsg", "验证码验证失败");
    }

    protected void handleError(Controller c) {
      c.keepModel(User.class);
      c.setAttr("state", "failure");

      if (ThreadLocalKit.isJson())
        c.renderJson();
      else
        c.render("/view/signup_email.ftl");
    }
  }

  public static class SignupValidator extends Validator {
    protected void validate(Controller c) {
      Object tmpU = SubjectKit.getSession().getAttribute(AppConstants.TEMP_USER);
      //ValidateKit.isEmail(((User) tmpU).getStr("email"))
      if (tmpU == null) {
        addError("usernameMsg", "邮箱已过期");
        c.setAttr("back", "/view/signup_email.ftl");
      } else {
        c.setAttr("email", ((User) tmpU).get("email"));
      }

      boolean usernameEmpty = ValidateKit.isNullOrEmpty(c.getPara("user.username"));
      if (usernameEmpty) addError("usernameMsg", "请输入用户名");
      if (!usernameEmpty && !ValidateKit.isUsername(c.getPara("user.username")))
        addError("usernameMsg", "用户名为5-18为字母,数字和下划线的组合");

      User u = User.dao.findFirstBy("`user`.username=?", c.getPara("user.username"));
      if (!ValidateKit.isNullOrEmpty(u)) addError("usernameMsg", "用户名已经存在");

      boolean passwordEmpty = ValidateKit.isNullOrEmpty(c.getPara("user.password"));
      if (passwordEmpty) addError("passwordMsg", "请输入密码");
      if (!passwordEmpty && !ValidateKit.isPassword(c.getPara("user.password")))
        addError("passwordMsg", "密码为5-18为字母,数字和下划线的组合");

      boolean repasswordEmpty = ValidateKit.isNullOrEmpty(c.getPara("repassword"));
      if (repasswordEmpty) addError("repasswordMsg", "请输入重复密码");
      if (!passwordEmpty && !repasswordEmpty && !c.getPara("user.password").equals(c.getPara("repassword")))
        addError("repasswordMsg", "重复密码不一致");


      boolean firstnameEmpty = ValidateKit.isNullOrEmpty(c.getPara("user.first_name"));
      if (firstnameEmpty) addError("firstnameMsg", "名字不能为空");

      boolean lastnameEmpty = ValidateKit.isNullOrEmpty(c.getPara("user.last_name"));
      if (lastnameEmpty) addError("lastnameMsg", "姓氏不能为空");

//            try {
//                if (!firstnameEmpty && c.getPara("user.first_name").getBytes("gbk").length >= 10)
//                    addError("firstnameMsg", "名字不能多于10位");
//
//                if (!lastnameEmpty && c.getPara("user.last_name").getBytes("gbk").length >= 10)
//                    addError("lastnameMsg", "姓氏不能多于10位");
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }


//            boolean captchaEmpty = ValidateKit.isNullOrEmpty(c.getPara("captcha"));
//            if (captchaEmpty) addError("captchaMsg", "验证码不能为空");
//            if (!captchaEmpty && !SubjectKit.doCaptcha(c.getPara("captcha"))) addError("captchaMsg", "验证码验证失败");
    }

    protected void handleError(Controller c) {
      c.keepModel(User.class);
      c.setAttr("email", c.getAttr("email"));
      c.setAttr("state", "failure");

      if (ThreadLocalKit.isJson())
        c.renderJson();
      else
        c.render(c.getAttr("back") != null ? c.getAttr("back").toString() : "/view/index.html");
    }
  }
}