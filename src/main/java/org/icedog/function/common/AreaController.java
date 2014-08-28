package org.icedog.function.common;

import cn.dreampie.common.config.AppConstants;
import cn.dreampie.common.util.tree.TreeUtils;
import com.jfinal.plugin.ehcache.CacheName;
import org.icedog.common.web.controller.Controller;
import org.icedog.function.common.model.Area;

/**
 * Created by wangrenhui on 14-1-3.
 */
public class AreaController extends Controller {

  @CacheName(AppConstants.DEFAULT_CACHENAME)
  public void query() {
    String where = "";
    Integer pid = getParaToInt("pid");
    if (pid != null && pid > 0) {
      where += " `area`.pid =" + pid;
    }
    Boolean isdelete = getParaToBoolean("isdelete");
    if (isdelete != null && isdelete) {
      where += " AND `area`.deleted_at is NULL";
    }

    Boolean istree = getParaToBoolean("istree");
    if (istree != null && istree) {
      setAttr("areas", TreeUtils.toTree(Area.dao.findBy(where)));
    } else {
      setAttr("areas", Area.dao.findBy(where));
    }
  }
}
