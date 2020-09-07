package main.java.com.weibo.bop.pulse.query.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ganlu1
 * 2019-04-08
 */
public class FormatUtils {

    /**
     * 格式为：
     *     data中包含数组和object两种格式
     *
     * @param items 要封装起来的object名称
     * @param mapList 需要处理的数据列表，要求原数据是 XX-xx 形式
     * @return
     */
    public static Map rebuildMap(String[] items, Map<String, List<Map>> mapList) {
        Map data = new HashMap();
        Map<String, Map> map = new HashMap();

        //循环数据列表
        for (Map.Entry<String, List<Map>> entry : mapList.entrySet()) {
            //循环items
            for (String item : items) {
                int length = item.length();
                if (entry.getKey().length() >= length && item.equalsIgnoreCase(entry.getKey().substring(0, length))) {
                    if (!map.containsKey(item)) {
                        Map tmp = new HashMap();
                        tmp.put(entry.getKey().substring(length), entry.getValue());
                        map.put(item, tmp);
                    } else {
                        map.get(item).put(entry.getKey().substring(length), entry.getValue());
                    }
                    data.put(item.substring(0, length - 1), map.get(item));
                }
            }
            if (!Arrays.asList(items).contains(entry.getKey().toUpperCase().split("-")[0].concat("-"))) {
                data.put(entry.getKey(), entry.getValue());
            }
        }
        return data;
    }
}
