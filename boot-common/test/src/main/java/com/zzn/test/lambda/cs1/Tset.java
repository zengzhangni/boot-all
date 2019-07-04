package com.zzn.test.lambda.cs1;

import com.zzn.test.lambda.model.User;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zengzhangni
 * @date 2019/6/28
 */
public class Tset {


    public static void main(String[] args) {
        List<User> list = getList();
        List<Integer> intList = getIntList();
        //排序
        sort(list);
        //连接
        join(list);
        //转换流
        map(list);

        intSort(intList);
    }

    private static void intSort(List<Integer> list) {
        System.out.println(list);
        list.sort((a,b)->b-a);
        System.out.println(list);
        list.sort((a,b)->a-b);
        System.out.println(list);

    }


    private static void sort(List<User> list) {
        List<User> collect = list.stream().filter(x -> x.getId() != null).sorted(Comparator.comparing(User::getId).reversed()).collect(Collectors.toList());
        collect.forEach(v -> {
            System.out.println(v.getId() + ":" + v.getName());
        });


    }

    private static void join(List<User> list) {
        String collect1 = list.stream().map(User::getName).collect(Collectors.joining("=="));
        System.out.println(collect1);
    }

    private static void map(List<User> list) {
        List<String> collect2 = list.stream().map(User::getName).collect(Collectors.toList());
        System.out.println(collect2);
        List<Integer> collect3 = list.stream().filter(x -> x.getId() != null ? x.getId() != 1 : true).map(User::getId).collect(Collectors.toList());
        System.out.println(collect3);
    }


    private static List<User> getList() {
        User user1 = new User();
        user1.setId(1);
        user1.setName("张三");
        User user2 = new User();
        user2.setId(2);
        user2.setName("李四");
        User user3 = new User();
        user3.setName("王五");
        User user4 = new User();
        user4.setId(4);
        user4.setName("赵六");
        User user5 = new User();
        user5.setId(5);
        user5.setName("七七");
        return Arrays.asList(user1, user2, user3, user4, user5);
    }

    private static List<Integer> getIntList() {
        return Arrays.asList(1, 20, 24, 15, 36, 11, 40, 54, 15, 26);
    }


}
