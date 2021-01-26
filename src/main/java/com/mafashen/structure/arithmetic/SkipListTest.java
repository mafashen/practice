package com.mafashen.structure.arithmetic;

/**
 * 跳表
 * @author mafashen
 * @since 2021-01-23.
 */
public class SkipListTest {

    /**
     * 索引间隔
     */
    int interval;

    Node head = new Node(-1);


    public void insert(int data){

    }

    public Node find(int data){
        Node p = head;
        while(p != null && p.data < data){
            p = p.next;
        }
        p = p.down;
        return null;
    }

    static class Node{
        int data;

        /**
         * 索引下驱节点
         */
        Node down;

        /**
         * 后继节点
         */
        Node next;

        /**
         * 0-叶子节点, 1-索引节点
         */
        int type = 0;

        /**
         * 节点层级
         */
        int level;

        public Node() {
        }

        public Node(int data) {
            this.data = data;
        }
    }
}
