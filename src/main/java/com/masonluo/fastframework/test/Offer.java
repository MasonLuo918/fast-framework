package com.masonluo.fastframework.test;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

/**
 * @author masonluo
 * @date 2020/7/3 10:43 AM
 */
public class Offer {
    public ArrayList<Integer> maxInWindows(int[] num, int size) {
        if (num == null || num.length == 0 || num.length < size || size == 0) {
            return new ArrayList<>();
        }
        Deque<Integer> deque = new LinkedList<>();
        ArrayList<Integer> list = new ArrayList<>();
        int i = 0;
        for (; i < size; i++) {
            while (!deque.isEmpty() && num[deque.getLast()] <= num[i]) {
                deque.removeLast();
            }
            if (deque.isEmpty() || num[deque.getFirst()] > num[i]) {
                deque.addLast(i);
            }
        }
        list.add(num[deque.getFirst()]);

        for (; i < num.length; i++) {
            if ((i - deque.getFirst()) >= size) {
                deque.removeFirst();
            }

            while (!deque.isEmpty() && num[deque.getLast()] <= num[i]) {
                deque.removeLast();
            }

            if (deque.isEmpty() || num[deque.getFirst()] > num[i]){
                deque.addLast(i);
            }
            list.add(num[deque.getFirst()]);
        }
        return list;
    }

    public static void main(String[] args) {
        Offer offer = new Offer();
        System.out.println(offer.maxInWindows(new int[]{2, 4, 3, 2, 6, 2, 5, 1}, 3));
    }
}
