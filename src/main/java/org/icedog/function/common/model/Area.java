package org.icedog.function.common.model;

import cn.dreampie.tablebind.TableBind;
import cn.dreampie.tree.TreeNode;
import cn.dreampie.web.model.Model;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by wangrenhui on 14-4-17.
 */
@TableBind(tableName = "com_area")
public class Area extends Model<Area> implements TreeNode<Area> {
  private List<Area> children = Lists.newArrayList();

  public static Area dao = new Area();

  @Override
  public long getId() {
    return new Long(this.getInt("id"));
  }

  @Override
  public long getParentId() {
    return new Long(this.getInt("pid"));
  }

  @Override
  public List<Area> getChildren() {
    return this.get("children");
  }

  @Override
  public void setChildren(List<Area> children) {
    this.put("children", children);
  }

}