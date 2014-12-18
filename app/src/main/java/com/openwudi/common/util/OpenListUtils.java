package com.openwudi.common.util;

import java.util.ArrayList;
import java.util.List;

public final class OpenListUtils {
    /**
     * Create an array list.
     * @param elements initial elements
     * @param <T> element type
     * @return array list of type T
     */
    public static <T> ArrayList<T> newArrayList(T... elements) {
        ArrayList<T> list = new ArrayList<T>();
        for (T elem : elements) {
            list.add(elem);
        }
        return list;
    }

    /**
     * Whether a list is empty.
     * @param list a list
     * @param <T> element type
     * @return whether a list is empty
     */
    public static <T> boolean isEmpty(List<T> list) {
        return list == null || list.size() == 0;
    }

    private OpenListUtils() {}
}
