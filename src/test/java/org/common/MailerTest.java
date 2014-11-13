package org.common;


import cn.dreampie.mail.AkkaMailer;
import cn.dreampie.mail.Mailer;
import cn.dreampie.mail.MailerConf;
import cn.dreampie.mail.MailerPlugin;
import cn.dreampie.template.freemarker.FreemarkerLoader;
import org.apache.commons.mail.*;
import org.junit.Before;
import org.junit.Test;

public class MailerTest {

  @Before
  public void setUp() throws Exception {
    new DBInit().init();
  }

  @Test
  public void testSendMail() throws Exception {
//    Runnable ra=getSendHtmlRunable("测试", "<a href='www.dreampie.cn'>Dreampie</a>", null, "173956022@qq.com");
//    Executors.newCachedThreadPool().execute(ra);
//    System.out.println("------------------------1");
    new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          System.out.println("begin");
          Email email = new SimpleEmail();
          email.setHostName("smtp.163.com");
          email.setAuthenticator(new DefaultAuthenticator("wangrenhui1990@163.com", "wangrenhui521"));
          email.setSSLOnConnect(true);
          email.setFrom("wangrenhui1990@163.com");
          email.setSubject("测试");
          email.setMsg("<a href='www.dreampie.cn'>Dreampie</a>");
          email.addTo("173956022@qq.com");
          email.send();
          System.out.println("end");
        } catch (EmailException e) {
          e.printStackTrace();
        }
      }
    }).start();
    System.out.println("------------------------2");
//    send("测试", "<a href='www.dreampie.cn'>Dreampie</a>", null, "173956022@qq.com");
//    System.out.println("------------------------3");

//    Mailer.sendHtml("测试", "<a href='www.dreampie.cn'>Dreampie</a>", "173956022@qq.com");
//    AkkaMailer.sendHtml("测试", "<a href='www.dreampie.cn'>Dreampie</a>", "173956022@qq.com");
//    System.out.println(new FreemarkerLoader("/template/index.ftl").setValue("doCaptcha", "sss").getHtml());
  }

  private static Runnable getSendHtmlRunable(final String subject, final String body, final EmailAttachment attachment, final String... recipients) {
    return new Runnable() {
      @Override
      public void run() {
        try {
          new MailerTest().send(subject, body, attachment, recipients);
        } catch (EmailException e) {
          e.printStackTrace();
        }
      }
    };
  }

  private void send(String subject, String body, EmailAttachment attachment, String... recipients) throws EmailException {
    System.out.println("begin");
    Email email = new SimpleEmail();
    email.setHostName("smtp.163.com");
    email.setAuthenticator(new DefaultAuthenticator("wangrenhui1990@163.com", "wangrenhui521"));
    email.setSSLOnConnect(true);
    email.setFrom("wangrenhui1990@163.com");
    email.setSubject(subject);
    email.setMsg(body);
    email.addTo(recipients);
    email.send();
    System.out.println("end");
  }

}