package com.wlm.wlm.demo;

import java.util.*;

/**
 * @author wuliming
 * @date 2021/11/30 15:31
 */
public class Test {


    public static void main(String[] args) {
        MyHashMap<String, String> map = new MyHashMap<>();
        map.put("you", "me");
        System.out.println(map.get("you"));
        System.out.println(groupStr(new String[]{"abc", "bca", "wa","aw"}));

        MyLinkList<Integer> list = new MyLinkList<>();
        for (int i = 1; i <= 10; i++) {
            list.add(i * 34);
        }
        System.out.println(list);
        System.out.println(list.size());
        System.out.println(list.contains(714));
        System.out.println(list.contains(102));
        list.addIndex(5, 666);
        list.addFirst(5555);
        list.addEnd(512);
        System.out.println(list);
        list.remove(666);
        System.out.println(list);
        list.removeAllByKey(68);
        System.out.println(list);
        list.clear();
        System.out.println(list);
        sort(new int[]{1, 22, 5, 66, 2});


        System.out.println(1 >> 1);
    }

    public static List<List<String>> groupStr(String[] strList) {
        Map<String, List<String>> mapList = new HashMap<>(strList.length);
        for (String s : strList) {
            char[] chars = s.toCharArray();
            Arrays.sort(chars);
            String key = String.valueOf(chars);
            if (mapList.containsKey(key)) {
                mapList.get(key).add(s);
            } else {
                List<String> stringList = new ArrayList<>();
                stringList.add(s);
                mapList.put(key, stringList);
            }
        }
        return new ArrayList<>(mapList.values());
    }

    public static void sort(int[]  arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(arr));
    }
}
