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
    Map map = new HashMap();
    ArrayList arraylist = new ArrayList();
    String[] alphatableb =
        {
            "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R",
            "S", "T", "U", "V", "W", "X", "Y", "Z"
        };
    for (String a : alphatableb) {
      for (int i = 0; i < list.size(); i++) {//为了排序都返回大写字母
        if (a.equals(String2AlphaFirst(list.get(i).get(attr).toString(), "b"))) {
          arraylist.add(list.get(i));
        }
      }
      map.put(a, arraylist);
      arraylist = new ArrayList();
    }
    return map;
  }
}
