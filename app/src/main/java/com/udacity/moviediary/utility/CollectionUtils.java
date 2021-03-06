package com.udacity.moviediary.utility;

import java.util.List;
import java.util.Map;

/**
 * Created by Amardeep on 12/12/2016.
 */
public class CollectionUtils {
    public static <T> boolean isEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }

    public static <K, V> boolean isEmpty(Map<K, V> map) {
        return map == null || map.isEmpty();
    }
}
