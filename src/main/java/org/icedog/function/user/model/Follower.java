package org.icedog.function.user.model;

import cn.dreampie.shiro.core.SubjectKit;
import cn.dreampie.sqlinxml.SqlKit;
import cn.dreampie.tablebind.TableBind;
import cn.dreampie.web.model.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * Created by wangrenhui on 14-1-3.
 */
@TableBind(tableName = "follower")
public class Follower extends Model<Follower> {
  public static Follower dao = new Follower();

  public boolean getFollowed() {
    User user = (User) SubjectKit.getUser();
    if (this.get("followed") == null) {
      Follower following = Follower.dao.findFirstBy("`follower`.user_id =" + user.get("id") + " AND `follower`.link_id =" + this.get("id"));
      if (following != null) {
        this.put("followed", true);
      } else
        this.put("followed", false);
    }
    return this.get("followed");
  }

  public Page<Follower> paginateFollowingInfoBy(int pageNumber, int pageSize, String where, Object... paras) {
    Page<Follower> result = dao.paginate(pageNumber, pageSize, SqlKit.sql("follower.findInfoBySelect"), SqlKit.sql("follower.findFollowingInfoByExceptSelect") + getWhere(where), paras);
    return result;
  }

  public Page<Follower> paginateFollowerInfoBy(int pageNumber, int pageSize, String where, Object... paras) {
    Page<Follower> result = dao.paginate(pageNumber, pageSize, SqlKit.sql("follower.findInfoBySelect"), SqlKit.sql("follower.findFollowerInfoByExceptSelect") + getWhere(where), paras);
    return result;
  }
}
