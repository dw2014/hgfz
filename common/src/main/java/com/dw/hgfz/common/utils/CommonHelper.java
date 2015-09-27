package com.dw.hgfz.common.utils;

import java.util.List;

/**
 * Created by dw on 9/25/2015.
 */
public class CommonHelper {

    public static void printList(List<?> lists, String message) throws Exception {
        System.out.println(message);
        if (lists.size() < 1) {
            System.out.println("List<?> lists size is " + lists.size());
            return;
        }
        for (int i = 0; i < lists.size(); i++) {
            System.out.println(i + " " + GsonHelper.gsonSerializer(lists.get(i)));
        }
    }
}
