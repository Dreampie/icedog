package org.icedog.common.web.controller;

import cn.dreampie.web.filter.ThreadLocalKit;
import cn.dreampie.captcha.CaptchaRender;
import org.icedog.function.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller
 */
public class Controller extends com.jfinal.core.Controller {
  static String indexView = "view/index.html";
  protected Logger logger = LoggerFactory.getLogger(getClass());

  public void dynaRender() {
    if (ThreadLocalKit.isJson())
      super.renderJson();
    else
      super.render(indexView);
  }

  public void dynaRender(String view) {
    if (ThreadLocalKit.isJson())
      super.renderJson();
    else
      super.render(view);
  }

  /**
   * 根目录
   */
  public void index() {
    dynaRender(indexView);
  }


  /**
   * 验证码
   */
  public void patchca() {
    int width = 0, height = 0, minnum = 0, maxnum = 0, fontsize = 0, fontmin = 0, fontmax = 0;
    if (isParaExists("width")) {
      width = getParaToInt("width");
    }
    if (isParaExists("height")) {
      height = getParaToInt("height");
    }
    if (isParaExists("minnum")) {
      minnum = getParaToInt("minnum");
    }
    if (isParaExists("maxnum")) {
      maxnum = getParaToInt("maxnum");
    }

    if (isParaExists("fontsize")) {
      fontsize = getParaToInt("fontsize");
    }
    CaptchaRender captcha = new CaptchaRender(minnum, maxnum, width, height, fontsize);
    //透明度  全透明验证码
    captcha.setAlpha(0f);
    render(captcha);
  }

//  public void tosignup() {
//    String uuid = getPara("token");
//    if (uuid != null && ValidateKit.isUUID(uuid)) {
//
//      Token token = Token.dao.findFirstBy("`token`.uuid='" + uuid + "' AND `token`.expiration_at>'" + TimeUtils.me().toString(DateTime.now()) + "' AND `token`.used_to=0");
////            if (token == null) {
////                Token stoken = new Token();
////                stoken.set("uuid", uuid);
////                stoken.set("username", "302509116@qq.com");
////                DateTime now = DateTime.now();
////                stoken.set("created_at", now.toDate());
////                stoken.set("expiration_at", now.plusDays(1).toDate());
////                stoken.set("used_to", 0);
////                if (stoken.save()) {
////                    token = Token.dao.findFirstBy("`token`.uuid='" + uuid + "'  AND `token`.expiration_at>'" + TimeUtils.me().toString(DateTime.now()) + "' AND `token`.used_to = 0");
////                }
////            }
//      if (token != null) {
//        logger.info("tosignup:" + token.getStr("username") + ":" + token.getStr("uuid"));
//        User regUser = new User();
//        regUser.set("email", token.get("username"));
//        setAttr("email", regUser.get("email"));
//        SubjectKit.getSession().setAttribute(AppConstants.TEMP_USER, regUser);
//        dynaRender("/view/signup.ftl");
//        return;
//      }
//    }
//
//    dynaRender("/view/signup_email.ftl");
//  }
//
//  @Before({RootValidator.SignupEmailValidator.class, Tx.class})
//  public void signupEmail() {
//    User regUser = getModel(User.class);
//
//    Token token = new Token();
//    token.set("uuid", UUID.randomUUID().toString());
//    token.set("username", regUser.get("email"));
//    DateTime now = DateTime.now();
//    token.set("created_at", now.toDate());
//    token.set("expiration_at", now.plusDays(1).toDate());
//    token.set("used_to", 0);
//
//    if (token.save()) {
//      logger.info("signupEmail:" + token.getStr("username") + ":" + token.getStr("uuid"));
//      Mailer.me().sendHtml("Dreampie.cn-梦想派",
//          MailerTemplate.me().set("full_name", "先生/女士").set("safe_url", getAttr("_webRootPath") + "/tosignup?token=" + token.get("uuid"))
//              .getText("mails/signup_email.ftl"), regUser.getStr("email"));
//
//      setAttr("user", regUser);
//      dynaRender("/view/send_email_notice.ftl");
//    }
//  }
//
//  @Before({RootValidator.SignupValidator.class, Tx.class})
//  public void signup() {
//    User regUser = getModel(User.class);
////        Object u = SubjectKit.getSession().getAttribute(AppConstants.TEMP_USER);
//
////        regUser.set("email", ((User) u).get("email"));
//    regUser.set("email", getAttr("email"));
//    regUser.set("created_at", new Date());
//    regUser.set("providername", "dreampie");
//
//    regUser.set("full_name", regUser.get("first_name") + "·" + regUser.get("last_name"));
//
//    boolean autoLogin = getParaToBoolean("autoLogin") == null ? false : getParaToBoolean("autoLogin");
//
//    HasherInfo passwordInfo = HasherUtils.me().hash(regUser.getStr("password"), Hasher.DEFAULT);
//    regUser.set("password", passwordInfo.getHashResult());
//    regUser.set("hasher", passwordInfo.getHasher().value());
//    regUser.set("salt", passwordInfo.getSalt());
//
//    if (regUser.save()) {
//      //删除token
//      Token.dao.dropBy("username='" + regUser.get("email") + "' AND used_to＝0");
//      regUser.addUserInfo(null).addRole(null);
//      setAttr("state", "success");
//      if (autoLogin) {
//        if (SubjectKit.login(regUser.getStr("username"), passwordInfo.getHashText(), User.dao.findFirstBy("username=?", regUser.get("username")))) {
//          //添加到session
//          SubjectKit.getSession().setAttribute(AppConstants.CURRENT_USER, regUser);
//          dynaRender();
//          return;
//        }
//      }
//    } else {
//      setAttr("state", "failure");
//      dynaRender();
//      return;
//    }
//
//    dynaRender();
//  }

  /**
   * 
   */
  public void forget(){
    User u=getModel(User.class);
    User user = User.dao.findFirstBy(" `user`.email =? AND `user`.deleted_at is null", u.get("email"));
  }



}
