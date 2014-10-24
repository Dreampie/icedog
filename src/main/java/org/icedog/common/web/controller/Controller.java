package org.icedog.common.web.controller;

import cn.dreampie.captcha.CaptchaRender;
import cn.dreampie.quartz.QuartzFactory;
import cn.dreampie.shiro.core.SubjectKit;
import cn.dreampie.web.filter.ThreadLocalKit;
import cn.dreampie.web.route.ControllerKey;
import cn.dreampie.web.websocket.Message;
import cn.dreampie.web.websocket.MessageServer;
import org.icedog.function.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.Date;

/**
 * Controller
 */
@ControllerKey(value = "/x", path = "/view/")
public class Controller extends com.jfinal.core.Controller {
  static String indexView = "index.html";
  protected Logger logger = LoggerFactory.getLogger(getClass());

  public void dynaRender() {
    dynaRender(indexView);
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
    QuartzFactory.me().startJobOnce(new Date(), 1, "test", "test", DemoJob.class);
//    if (SubjectKit.isAuthed())
//      MessageServer.send(new Message(SubjectKit.getUser().get("id").toString(), "message"));
    dynaRender(indexView);
  }


  /**
   * 验证码
   */
  public void captcha() {
    int width = 0, height = 0, minnum = 0, maxnum = 0, fontsize = 0, fontmin = 0, fontmax = 0;
    CaptchaRender captcha = new CaptchaRender();
    if (isParaExists("width")) {
      width = getParaToInt("width");
    }
    if (isParaExists("height")) {
      height = getParaToInt("height");
    }
    if (width > 0 && height > 0)
      captcha.setImgSize(width, height);
    if (isParaExists("minnum")) {
      minnum = getParaToInt("minnum");
    }
    if (isParaExists("maxnum")) {
      maxnum = getParaToInt("maxnum");
    }
    if (minnum > 0 && maxnum > 0)
      captcha.setFontNum(minnum, maxnum);
    if (isParaExists("fontsize")) {
      fontsize = getParaToInt("fontsize");
    }
    if (fontsize > 0)
      captcha.setFontSize(fontsize, fontsize);
    //干扰线数量 默认0
    captcha.setLineNum(2);
    //噪点数量 默认50
    captcha.setArtifactNum(30);
    //使用字符  去掉0和o  避免难以确认
//    captcha.setCode("ABCDEFGHIJKLMNPQRSTUVWXYZ123456789");
    //验证码在session里的名字 默认 captcha,创建时间为：名字_time
//    captcha.setCaptchaName("captcha");
    //验证码颜色 默认黑色
//    captcha.setDrawColor(new Color(255,0,0));
    //背景干扰物颜色  默认灰
//    captcha.setDrawBgColor(new Color(0,0,0));
    //背景色+透明度 前三位数字是rgb色，第四个数字是透明度  默认透明
//    captcha.setBgColor(new Color(225, 225, 0, 100));
    //滤镜特效 默认随机特效 //曲面Curves //大理石纹Marble //弯折Double //颤动Wobble //扩散Diffuse
//    captcha.setFilter(CaptchaRender.FilterFactory.Curves);
    //随机色  默认黑验证码 灰背景元素
    captcha.setRandomColor(true);
    render(captcha);
  }

  public void authed() {
    setAttr("isAuthed", SubjectKit.isAuthed());
    dynaRender();
  }

  /**
   *
   */
  public void forget() {
    User u = getModel(User.class);
    User user = User.dao.findFirstBy(" `user`.email =? AND `user`.deleted_at is null", u.get("email"));

  }


}
