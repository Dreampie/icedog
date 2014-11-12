package org.icedog.function.user.model;


import cn.dreampie.PropertiesKit;
import cn.dreampie.mail.Mailer;
import cn.dreampie.mail.MailerConf;
import cn.dreampie.mail.MailerPlugin;
import cn.dreampie.mail.MailerTemplate;
import com.alibaba.fastjson.JSON;
import com.jfinal.kit.PathKit;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.common.DBInit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.concurrent.Executors;

public class UserTest {

  @Before
  public void setUp() throws Exception {
    new DBInit().exclude("com.shengmu.function.shop").init();
  }

  @Test
  public void testFindInfoBy() throws Exception {
//    User u = User.dao.findFirstBy("`user`.username='admin'");
//    PropertiesKit.me().loadPropertyFile("application.properties");
//    PropertiesKit.me().loadPropertyFile("application.properties");
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
//    Mailer.sendHtml("测试", "<a href='www.dreampie.cn'>Dreampie</a>", "173956022@qq.com");
//    Mailer.me().sendHtml("Dreampie.cn-梦想派",
//        MailerTemplate.me().set("full_name", "先生/女士").set("safe_url", "/aa")
//            .getText("mails/signup_email.ftl"), "173956022@qq.com");
//    System.out.println(PathKit.getWebRootPath());

//    System.out.println(URLDecoder.decode("D:\\Program%20Files", "UTF-8"));

//    Runnable ra=getSendHtmlRunable("测试", "<a href='www.dreampie.cn'>Dreampie</a>", null, "173956022@qq.com");
//    Executors.newCachedThreadPool().execute(ra);

    new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          send("测试", "<a href='www.dreampie.cn'>Dreampie</a>", null, "173956022@qq.com");
        } catch (EmailException e) {
          e.printStackTrace();
        }
      }
    }).start();
//    send("测试", "<a href='www.dreampie.cn'>Dreampie</a>", null, "173956022@qq.com");
  }

  private static Runnable getSendHtmlRunable(final String subject, final String body, final EmailAttachment attachment, final String... recipients) {
    return new Runnable() {
      @Override
      public void run() {
        try {
          send(subject, body, attachment, recipients);
        } catch (EmailException e) {
          e.printStackTrace();
        }
      }
    };
  }

  private static void send(String subject, String body, EmailAttachment attachment, String... recipients) throws EmailException {
    System.out.println("begin");
    MailerConf mailerConf = MailerPlugin.mailerConf;
    HtmlEmail htmlEmail = new HtmlEmail();
    htmlEmail.setCharset(mailerConf.getCharset());
    htmlEmail.setSocketTimeout(mailerConf.getTimeout());
    htmlEmail.setCharset(mailerConf.getEncode());
    htmlEmail.setHostName(mailerConf.getHost());
    if (!mailerConf.getSslport().isEmpty())
      htmlEmail.setSslSmtpPort(mailerConf.getSslport());
    if (!mailerConf.getPort().isEmpty())
      htmlEmail.setSmtpPort(Integer.parseInt(mailerConf.getPort()));
    htmlEmail.setSSLOnConnect(mailerConf.isSsl());
    htmlEmail.setStartTLSEnabled(mailerConf.isTls());
    htmlEmail.setDebug(mailerConf.isDebug());
    htmlEmail.setAuthenticator(new DefaultAuthenticator(mailerConf.getUser(), mailerConf.getPassword()));

    htmlEmail.setFrom(mailerConf.getFrom(), mailerConf.getName());
    htmlEmail.setSubject(subject);
    htmlEmail.addTo(recipients);
    htmlEmail.setHtmlMsg(body);
    // set the alternative message
    htmlEmail.setTextMsg("Your email client does not support HTML messages");
    if (attachment != null)
      htmlEmail.attach(attachment);
    htmlEmail.send();
    System.out.println("end");
  }

}