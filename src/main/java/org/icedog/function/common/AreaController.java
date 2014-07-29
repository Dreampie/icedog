package org.icedog.function.common;

import cn.dreampie.common.config.AppConstants;
import com.jfinal.plugin.ehcache.CacheName;
import org.icedog.common.web.controller.Controller;

/**
 * Created by wangrenhui on 14-1-3.
 */
public class AreaController extends Controller {

  public void index() {
    dynaRender("/view/index.ftl");
  }

  @CacheName(AppConstants.DEFAULT_CACHENAME)
  public void own() {
    setAttr("areas", Area.dao.paginateBy(getParaToInt(0, 1), 15, "`area`.deleted_at is NULL"));
    dynaRender("/view/index.ftl");
  }

  @CacheName(AppConstants.DEFAULT_CACHENAME)
  public void whole() {
    setAttr("areas", Area.dao.findBy("`area`.deleted_at is NULL"));
    dynaRender("/view/index.ftl");
  }

  public void children() {
    setAttr("areas", Area.dao.findBy("`area`.deleted_at is NULL AND `area`.pid =" + getParaToInt(0, 1)));
    dynaRender("/view/area/index.ftl");
  }
}
