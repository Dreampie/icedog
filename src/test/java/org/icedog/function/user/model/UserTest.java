package org.icedog.function.user.model;


import com.jfinal.kit.PathKit;
import org.common.DBInit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserTest {

  @Before
  public void setUp() throws Exception {
//    new DBInit().exclude("com.shengmu.function.shop").init();
  }

  @Test
  public void testFindInfoBy() throws Exception {
//    User u = User.dao.findInfoBy("`user`.username='admin");
//    Assert.assertNotNull(u);
    System.out.println(PathKit.getRootClassPath());
  }
}