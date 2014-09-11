package org.icedog.function.user.model;

import cn.dreampie.tablebind.TableBind;
import cn.dreampie.web.model.Model;


/**
 * Created by wangrenhui on 14-4-17.
 */
@TableBind(tableName = "sec_token", pkName = "uuid")
public class Token extends Model<Token> {
  public static Token dao = new Token();

}