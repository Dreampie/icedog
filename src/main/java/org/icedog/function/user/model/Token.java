package org.icedog.function.user.model;

import cn.dreampie.common.model.Model;
import com.jfinal.ext.plugin.tablebind.TableBind;

/**
 * Created by wangrenhui on 14-4-17.
 */
@TableBind(tableName = "sec_token", pkName = "uuid")
public class Token extends Model<Token> {
  public static Token dao = new Token();

}