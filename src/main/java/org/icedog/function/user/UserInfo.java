package org.icedog.function.user;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Model;

/**
 * Created by wangrenhui on 14-4-22.
 */
@TableBind(tableName = "sec_user_info")
public class UserInfo extends Model<UserInfo> {
  public static UserInfo dao = new UserInfo();

}
