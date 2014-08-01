package org.icedog.common.web.controller;

import cn.dreampie.common.config.AppConstants;
import cn.dreampie.common.util.SubjectUtils;
import cn.dreampie.common.util.ValidateUtils;
import cn.dreampie.common.web.thread.ThreadLocalUtil;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import org.icedog.function.user.model.User;

/**
 * Created by wangrenhui on 14-1-2.
 */
public class RootValidator {

  public static class RegisterEmailValidator extends Validator {
    protected void validate(Controller c) {


      boolean emailEmpty = ValidateUtils.me().isNullOrEmpty(c.getPara("user.email"));
      if (!emailEmpty && !ValidateUtils.me().isEmail(c.getPara("user.email"))) addError("emailMsg", "邮箱格式验证失败");

      User u = User.dao.findFirstBy("`user`.email=?", c.getPara("user.email"));
      if (!ValidateUtils.me().isNullOrEmpty(u)) addError("emailMsg", "该邮箱已经注册");

      boolean captchaEmpty = ValidateUtils.me().isNullOrEmpty(c.getPara("captcha"));
      if (captchaEmpty) addError("captchaMsg", "验证码不能为空");
      if (!captchaEmpty && !SubjectUtils.me().doCaptcha(c.getPara("captcha"))) addError("captchaMsg", "验证码验证失败");
    }

    protected void handleError(Controller c) {
      c.keepModel(User.class);
      c.setAttr("state", "failure");

      if (ThreadLocalUtil.isJson())
        c.renderJson();
      else
        c.render("/view/signup_email.ftl");
    }
  }

  public static class RegisterValidator extends Validator {
    protected void validate(Controller c) {
      Object tmpU = SubjectUtils.me().getSession().getAttribute(AppConstants.TEMP_USER);
      //ValidateUtils.me().isEmail(((User) tmpU).getStr("email"))
      if (tmpU == null) {
        addError("usernameMsg", "邮箱已过期");
        c.setAttr("back", "/view/signup_email.ftl");
      } else {
        c.setAttr("email", ((User) tmpU).get("email"));
      }

      boolean usernameEmpty = ValidateUtils.me().isNullOrEmpty(c.getPara("user.username"));
      if (usernameEmpty) addError("usernameMsg", "请输入用户名");
      if (!usernameEmpty && !ValidateUtils.me().isUsername(c.getPara("user.username")))
        addError("usernameMsg", "用户名为5-18为字母,数字和下划线的组合");

      User u = User.dao.findFirstBy("`user`.username=?", c.getPara("user.username"));
      if (!ValidateUtils.me().isNullOrEmpty(u)) addError("usernameMsg", "用户名已经存在");

      boolean passwordEmpty = ValidateUtils.me().isNullOrEmpty(c.getPara("user.password"));
      if (passwordEmpty) addError("passwordMsg", "请输入密码");
      if (!passwordEmpty && !ValidateUtils.me().isPassword(c.getPara("user.password")))
        addError("passwordMsg", "密码为5-18为字母,数字和下划线的组合");

      boolean repasswordEmpty = ValidateUtils.me().isNullOrEmpty(c.getPara("repassword"));
      if (repasswordEmpty) addError("repasswordMsg", "请输入重复密码");
      if (!passwordEmpty && !repasswordEmpty && !c.getPara("user.password").equals(c.getPara("repassword")))
        addError("repasswordMsg", "重复密码不一致");


      boolean firstnameEmpty = ValidateUtils.me().isNullOrEmpty(c.getPara("user.first_name"));
      if (firstnameEmpty) addError("firstnameMsg", "名字不能为空");

      boolean lastnameEmpty = ValidateUtils.me().isNullOrEmpty(c.getPara("user.last_name"));
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


//            boolean captchaEmpty = ValidateUtils.me().isNullOrEmpty(c.getPara("captcha"));
//            if (captchaEmpty) addError("captchaMsg", "验证码不能为空");
//            if (!captchaEmpty && !SubjectUtils.me().doCaptcha(c.getPara("captcha"))) addError("captchaMsg", "验证码验证失败");
    }

    protected void handleError(Controller c) {
      c.keepModel(User.class);
      c.setAttr("email", c.getAttr("email"));
      c.setAttr("state", "failure");

      if (ThreadLocalUtil.isJson())
        c.renderJson();
      else
        c.render(c.getAttr("back") != null ? c.getAttr("back").toString() : "/view/signup.ftl");
    }
  }
}