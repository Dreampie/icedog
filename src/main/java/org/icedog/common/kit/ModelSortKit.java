package org.icedog.common.kit;

import cn.dreampie.PinyinSortKit;
import cn.dreampie.web.model.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ice on 14-9-11.
 */
public class ModelSortKit extends PinyinSortKit {
  public static Map sort(List<? extends Model> list, String attr) {
    if (list == null)
      return null;
    Map<String, List<Model>> map = new HashMap<String, List<Model>>();
    List<Model> arraylist = new ArrayList<Model>();
    String[] alphatableb =
        {
            "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R",
            "S", "T", "U", "V", "W", "X", "Y", "Z"
        };
    for (String a : alphatableb) {
      for (Model m : list) {//为了排序都返回大写字母
        if (a.equals(String2AlphaFirst(m.get(attr).toString(), "b"))) {
          arraylist.add(m);
        }
      }
      map.put(a, arraylist);
      arraylist = new ArrayList<Model>();
    }
    return map;
  }
}
