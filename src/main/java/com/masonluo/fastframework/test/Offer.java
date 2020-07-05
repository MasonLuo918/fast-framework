package com.masonluo.fastframework.test;

import java.util.*;

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

            if (deque.isEmpty() || num[deque.getFirst()] > num[i]) {
                deque.addLast(i);
            }
            list.add(num[deque.getFirst()]);
        }
        return list;
    }

    private List<Integer> list = new ArrayList<>();

    private PriorityQueue<Integer> maxHeap = new PriorityQueue<>((o1, o2) -> o2 - o1);

    private PriorityQueue<Integer> minHeap = new PriorityQueue<>();

    public void Insert(Integer num) {
        if (list.size() % 2 == 0) {
            evenInsert(num);
        } else {
            oddInsert(num);
        }
        list.add(num);
    }

    private void oddInsert(Integer num) {
        if (minHeap.size() > 0 && minHeap.element() < num) {
            int newNum = minHeap.remove();
            minHeap.add(num);
            maxHeap.add(newNum);
        } else {
            maxHeap.add(num);
        }
    }

    private void evenInsert(Integer num) {
        if (maxHeap.size() > 0 && maxHeap.element() > num) {
            int newNUm = maxHeap.remove();
            maxHeap.add(num);
            minHeap.add(newNUm);
        } else {
            minHeap.add(num);
        }
    }

    public Double GetMedian() {
        if (list.size() % 2 == 0) {
            return (minHeap.element() + maxHeap.element()) / 2.0;
        }
        return minHeap.size() > maxHeap.size() ? (double) minHeap.element() : (double) maxHeap.element();
    }
    // ----------------------------------------------------------------------------------------------------//

    static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;

        }

        @Override
        public String toString() {
            return "TreeNode{" +
                    "val=" + val +
                    ", left=" + (left != null ? left.val : null) +
                    ", right=" + (right != null ? right.val : null) +
                    '}';
        }
    }

    static class Result {
        int count;

        TreeNode result;

        public Result(int count) {
            this.count = count;
        }

        public Result(int count, TreeNode result) {
            this.count = count;
            this.result = result;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "count=" + count +
                    ", result=" + result +
                    '}';
        }
    }

   /* TreeNode create() {
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(3);
        root.left.left = new TreeNode(2);
        root.left.right = new TreeNode(4);
        root.right = new TreeNode(7);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(8);
        return root;
    }*/

    TreeNode KthNode(TreeNode pRoot, int k) {
        Result result = new Result(0, null);
        doFindKth(pRoot, k, result);
        return result.result;
    }

    private void doFindKth(TreeNode pRoot, int k, Result result) {
        if (pRoot == null) {
            return;
        }
        doFindKth(pRoot.left, k, result);
        if (result.count == k && result.result != null) {
            return;
        }
        result.count += 1;
        if (k - result.count == 0) {
            result.result = pRoot;
        }
        doFindKth(pRoot.right, k, result);
    }
    // ----------------------------------------------------------------------------------------------------//
}
