package com.billow.tools.utlis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MathUtils {

    /**
     * list 求差集
     *
     * @param n
     * @param m
     * @param <T>
     * @return
     */
    public static <T> List<T> getDifferenceSet(List<T> n, List<T> m) {
        //转化最长列表
        Set<T> set = new HashSet<>(n.size() > m.size() ? n : m);
        //循环最短列表
        for (T t : n.size() > m.size() ? m : n) {
            if (set.contains(t)) {
                set.remove(t);
            } else {
                set.add(t);
            }
        }
        return new ArrayList(set);
    }

    /**
     * list 求交集
     *
     * @param n
     * @param m
     * @param <T>
     * @return
     */
    public static <T> List<T> getIntersection(List<T> n, List<T> m) {
        Set<T> setN = new HashSet<>(n);
        Set<T> setM = new HashSet<>(m);
        setN.retainAll(setM);
        return new ArrayList(setN);
    }

    /**
     * list 集合并集
     *
     * @param n
     * @param m
     * @param <T>
     * @return
     */
    public static <T> List<T> getUnion(List<T> n, List<T> m) {
        Set<T> setN = new HashSet<>(n);
        Set<T> setM = new HashSet<>(m);
        setN.addAll(setM);
        return new ArrayList(setN);
    }

    /**
     * 数组求差集
     *
     * @param n
     * @param m
     * @param <T>
     * @return
     */
    public static <T> T[] getDifferenceSet(T[] n, T[] m) {
        List<T> list = MathUtils.getDifferenceSet(Arrays.asList(n), Arrays.asList(m));
        return list.toArray(Arrays.copyOf(n, list.size()));
    }

    /**
     * 数组求交集
     *
     * @param n
     * @param m
     * @param <T>
     * @return
     */
    public static <T> T[] getIntersection(T[] n, T[] m) {
        List<T> list = MathUtils.getIntersection(Arrays.asList(n), Arrays.asList(m));
        return list.toArray(Arrays.copyOf(n, list.size()));
    }

    /**
     * 数组并集
     *
     * @param n
     * @param m
     * @param <T>
     * @return
     */
    public static <T> T[] getUnion(T[] n, T[] m) {
        List<T> list = MathUtils.getUnion(Arrays.asList(n), Arrays.asList(m));
        return list.toArray(Arrays.copyOf(n, list.size()));
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        List<Integer> list1 = new ArrayList<>(Arrays.asList(3, 4, 5, 6));
        System.out.println("list 差集" + getDifferenceSet(list, list1));
        System.out.println("list 并集" + getUnion(list, list1));
        System.out.println("list 交集" + getIntersection(list, list1));
        Integer[] array = new Integer[]{1, 2, 3, 4};
        Integer[] array1 = new Integer[]{3, 4, 5, 6};
        //差集[1, 2, 5, 6]
        System.out.println("array 差集" + Arrays.toString(getDifferenceSet(array, array1)));
        //并集[1, 2, 3, 4, 5, 6]
        System.out.println("array 并集" + Arrays.toString(getUnion(array, array1)));
        //交集[3, 4]
        System.out.println("array 交集" + Arrays.toString(getIntersection(array, array1)));


    }

}