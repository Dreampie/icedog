package org.icedog.function.common.model;

import cn.dreampie.tablebind.TableBind;
import cn.dreampie.web.model.Model;

/**
 * Created by wangrenhui on 14-4-16.
 */
@TableBind(tableName = "com_state")
public class State extends Model<State> {
  public static State dao = new State();

}
