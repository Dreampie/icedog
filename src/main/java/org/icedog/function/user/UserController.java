package org.icedog.function.user;

import cn.dreampie.ValidateKit;
import cn.dreampie.shiro.core.SubjectKit;
import cn.dreampie.shiro.hasher.Hasher;
import cn.dreampie.shiro.hasher.HasherInfo;
import cn.dreampie.shiro.hasher.HasherKit;
import cn.dreampie.web.cache.CacheRemove;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;
import org.icedog.common.config.AppConstants;
import org.icedog.common.kit.ModelSortKit;
import org.icedog.common.web.controller.Controller;
import org.icedog.function.user.model.Follower;
import org.icedog.function.user.model.User;

import java.util.Date;
import java.util.Map;

/**
 * Created by wangrenhui on 14-1-3.
 */
public class UserController extends Controller {

  public void index() {
    dynaRender("/view/user/index.ftl");
  }

  public void search() {
    User user = SubjectKit.getUser();
    keepPara("user_search");

    String where = " `user`.deleted_at is null AND `user`.id <> " + user.get("id");
    String user_search = getPara("user_search");
    if (!ValidateKit.isNullOrEmpty(user_search)) {
      where += " AND (INSTR(`user`.username,'" + user_search + "')>0 OR  INSTR(`user`.full_name,'" + user_search + "')>0 "
          + " OR  INSTR(`province`.name,'" + user_search + "')>0 "
          + " OR  INSTR(`city`.name,'" + user_search + "')>0 OR  INSTR(`county`.name,'" + user_search + "')>0 "
          + " OR INSTR(`userInfo`.zip_code,'" + user_search + "')>0 "
          + " OR INSTR(`user`.created_at,'" + user_search + "')>0) ";
    }
//        String start_at = getPara("start_at");
//        if (ValidateKit.isDateTime(start_at)) {
//            where += " AND `user`.created_at >= '" + start_at + "'";
//        }
//
//        String end_at = getPara("end_time");
//        if (ValidateKit.isDateTime(end_at)) {
//            where += " AND `user`.created_at <= '" + end_at + "'";
//        }
//
//        Boolean deleted = getParaToBoolean("deleted");
//        if (!ValidateKit.isNullOrEmpty(deleted) && deleted) {
//            where += " AND `user`.deleted_at is not null";
//        } else {
//            where += " AND `user`.deleted_at is null";
//        }

    Page<User> users = User.dao.paginateInfoBy(getParaToInt(0, 1), getParaToInt("pageSize", 15), where);
    Map userGroup = ModelSortKit.sort(users.getList(), "last_name");

    setAttr("users", users);
    setAttr("userGroup", userGroup);
    dynaRender("/view/user/search.ftl");
  }

  public void following() {
    User user = SubjectKit.getUser();
    keepPara("user_search");

    //只能查询当前用户以下的角色
    String where = " `follower`.user_id = " + user.get("id");
    String user_search = getPara("user_search");
    if (!ValidateKit.isNullOrEmpty(user_search)) {
      where += " AND (INSTR(`follower`.intro,'" + user_search + "')>0 "
          + "OR INSTR(`follower`.created_at,'" + user_search + "')>0 OR INSTR(`user`.full_name,'" + user_search + "')>0 "
          + "OR  INSTR(`user`.mobile,'" + user_search + "')>0 OR  INSTR(`province`.name,'" + user_search + "')>0 "
          + "OR  INSTR(`city`.name,'" + user_search + "')>0 OR  INSTR(`county`.name,'" + user_search + "')>0 "
          + "OR INSTR(`userInfo`.street,'" + user_search + "')>0 OR INSTR(`userInfo`.zip_code,'" + user_search + "')>0) ";
    }
//        String start_at = getPara("start_at");
//        if (ValidateKit.isDateTime(start_at)) {
//            where += " AND `user`.created_at >= '" + start_at + "'";
//        }
//
//        String end_at = getPara("end_time");
//        if (ValidateKit.isDateTime(end_at)) {
//            where += " AND `user`.created_at <= '" + end_at + "'";
//        }
//
//        Boolean deleted = getParaToBoolean("deleted");
//        if (!ValidateKit.isNullOrEmpty(deleted) && deleted) {
//            where += " AND `user`.deleted_at is not null";
//        } else {
//            where += " AND `user`.deleted_at is null";
//        }

    Page<Follower> followers = Follower.dao.paginateFollowingInfoBy(getParaToInt(0, 1), getParaToInt("pageSize", 15), where);
    Map userGroup = ModelSortKit.sort(followers.getList(), "last_name");

    setAttr("users", followers);
    setAttr("userGroup", userGroup);
    dynaRender("/view/user/following.ftl");
  }

  public void follower() {
    User user = SubjectKit.getUser();
    keepPara("user_search");

    //只能查询当前用户以下的角色
    String where = " `follower`.link_id = " + user.get("id");
    String user_search = getPara("user_search");
    if (!ValidateKit.isNullOrEmpty(user_search)) {
      where += " AND (INSTR(`follower`.intro,'" + user_search + "')>0 "
          + "OR INSTR(`follower`.created_at,'" + user_search + "')>0 OR INSTR(`user`.full_name,'" + user_search + "')>0 "
          + "OR  INSTR(`user`.mobile,'" + user_search + "')>0 OR  INSTR(`province`.name,'" + user_search + "')>0 "
          + "OR  INSTR(`city`.name,'" + user_search + "')>0 OR  INSTR(`county`.name,'" + user_search + "')>0 "
          + "OR INSTR(`userInfo`.street,'" + user_search + "')>0 OR INSTR(`userInfo`.zip_code,'" + user_search + "')>0) ";
    }
//        String start_at = getPara("start_at");
//        if (ValidateKit.isDateTime(start_at)) {
//            where += " AND `user`.created_at >= '" + start_at + "'";
//        }
//
//        String end_at = getPara("end_time");
//        if (ValidateKit.isDateTime(end_at)) {
//            where += " AND `user`.created_at <= '" + end_at + "'";
//        }
//
//        Boolean deleted = getParaToBoolean("deleted");
//        if (!ValidateKit.isNullOrEmpty(deleted) && deleted) {
//            where += " AND `user`.deleted_at is not null";
//        } else {
//            where += " AND `user`.deleted_at is null";
//        }

    Page<Follower> followers = Follower.dao.paginateFollowerInfoBy(getParaToInt(0, 1), getParaToInt("pageSize", 15), where);
    Map userGroup = ModelSortKit.sort(followers.getList(), "last_name");

    setAttr("users", followers);
    setAttr("userGroup", userGroup);
    dynaRender("/view/user/follower.ftl");
  }

  @CacheRemove(name = AppConstants.DEFAULT_CACHENAME,keys = {"search"})
  public void addFollowing() {
    keepModel(Follower.class);

    User user = SubjectKit.getUser();
    Follower follower = getModel(Follower.class);
    follower.set("user_id", user.get("id"));
    follower.set("created_at", new Date());

    if (follower.save())
      setAttr("state", "success");
    else
      setAttr("state", "failure");
    dynaRender("/view/user/follower.ftl");
  }

  @CacheRemove(name = AppConstants.DEFAULT_CACHENAME)
  @Before({UserValidator.deleteFollowingValidator.class, Tx.class})
  public void deleteFollowing() {
    keepModel(Follower.class);
    Follower follower = getModel(Follower.class);

    if (follower.delete())
      setAttr("state", "success");
    else
      setAttr("state", "failure");
    dynaRender("/view/user/following.ftl");
  }


  @CacheRemove(name = AppConstants.DEFAULT_CACHENAME)
  @Before({UserValidator.UpdateIntroValidator.class, Tx.class})
  public void updateIntro() {
    keepModel(Follower.class);
    Follower follower = getModel(Follower.class);

    if (follower.update())
      setAttr("state", "success");
    else
      setAttr("state", "failure");
    dynaRender("/view/user/following.ftl");
  }


  @CacheRemove(name = AppConstants.DEFAULT_CACHENAME)
  @Before({UserValidator.UpdatePwdValidator.class, Tx.class})
  public void updatePwd() {
    keepModel(User.class);
    User upUser = getModel(User.class);

    HasherInfo passwordInfo = HasherKit.hash(upUser.getStr("password"), Hasher.DEFAULT);
    upUser.set("password", passwordInfo.getHashResult());
    upUser.set("hasher", passwordInfo.getHasher().value());
    upUser.set("salt", passwordInfo.getSalt());

    if (upUser.update()) {
      SubjectKit.getSubject().logout();
      setAttr("username", upUser.get("username"));
      setAttr("state", "success");
    } else
      setAttr("state", "failure");
    dynaRender("/view/user/center.ftl");
  }

  public void center() {
    User user = SubjectKit.getUser();
    if (!ValidateKit.isNullOrEmpty(user)) {
      setAttr("user", user);
    }
    dynaRender("/view/user/center.ftl");
  }
}
