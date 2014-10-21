package org.icedog.function.user.model;


import cn.dreampie.mail.Mailer;
import cn.dreampie.mail.MailerTemplate;
import com.alibaba.fastjson.JSON;
import com.jfinal.kit.PathKit;
import org.common.DBInit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;

public class UserTest {

  @Before
  public void setUp() throws Exception {
    new DBInit().exclude("com.shengmu.function.shop").init();
  }

  @Test
  public void testFindInfoBy() throws Exception {
    User u = User.dao.findFirstBy("`user`.username='admin'");

//    System.out.println(JSON.toJSONString(u));
//    Assert.assertNotNull(u);
//    System.out.println(PathKit.getRootClassPath());
//    System.out.println(UserTest.class.getResource("application.properties"));

//    Enumeration<URL> urls = UserTest.class.getClassLoader().getResources("/quartz/quartz.properties");
//    while (urls.hasMoreElements()) {
//      URL url = urls.nextElement();
//      System.out.println(url.getPath());
//    }
//    System.out.println("--"+UserTest.class.getResource("/db/migration"));
//    Mailer.me().sendHtml("测试", "173956022@qq.com", "<a href='www.dreampie.cn'>Dreampie</a>");
//    Mailer.me().sendHtml("Dreampie.cn-梦想派",
//        MailerTemplate.me().set("full_name", "先生/女士").set("safe_url", "/aa")
//            .getText("mails/signup_email.ftl"), "173956022@qq.com");
//    System.out.println(PathKit.getWebRootPath());

    System.out.println(URLDecoder.decode("D:\\Program%20Files", "UTF-8"));

  }

}