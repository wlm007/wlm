package com.wlm.wlm.demo;

/**
 * 自定义链表
 * @author wuliming
 * @date 2021/11/30 11:07
 */
public class MyLinkList<K> {

    private static class LinkNode<K> {
        K data;
        LinkNode<K> next;
        LinkNode(K data) {
            this.data = data;
        }
    }

    private LinkNode<K> head;

    @Override
    public String toString() {
        LinkNode<K> cur = this.head;
        StringBuilder sb = new StringBuilder();
        while (cur != null) {
            sb.append(cur.data instanceof String ? (cur + "  ") : (cur.data.toString() + "  "));
            cur = cur.next;
        }
        return sb.toString();
    }

    /**
     * 判断是否包含该元素
     * @param data 元素数据
     * @return boolean
     */
    public boolean contains (K data) {
        LinkNode<K> cur = this.head;
        // 遍历链表查找数据是否存在
        while (cur != null) {
            if (cur.data == data || cur.data.equals(data)) {
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    public int size() {
        LinkNode<K> cur = this.head;
        int count = 0;
        while (cur != null) {
            count ++;
            cur = cur.next;
        }
        return count;
    }

    public void add(K data) {
        if (this.head == null) {
            this.addFirst(data);
        }
        else {
            this.addEnd(data);
        }
    }

    public void addFirst(K data) {
        LinkNode<K> node = new LinkNode<>(data);
        node.next = this.head;
        this.head = node;
    }

    public void addEnd(K data) {
        if (this.head == null) {
            this.addFirst(data);
            return;
        }
        LinkNode<K> node = new LinkNode<>(data);
        LinkNode<K> cur = this.head;
        while (cur.next != null) {
            cur = cur.next;
        }
        cur.next = node;
    }

    public void  addIndex(int index, K data) {
        if (index < 0 || index > size()) {
            System.out.println("添加数据的位置越界");
            return;
        }
        if (index == 0) {
            this.addFirst(data);
            return;
        }
        if (index == size()) {
            this.addEnd(data);
            return;
        }
        LinkNode<K> cur = this.head;
        while (index - 1 != 0) {
            cur = cur.next;
            index --;
        }
        LinkNode<K> node = new LinkNode<>(data);
        node.next = cur.next;
        cur.next = node;
    }

    public void remove(K key) {
        if (this.head == null) {
            System.out.println("链表为空 删除失败");
            return;
        }
        if (this.head.data == key || key.equals(this.head.data)) {
            this.head = this.head.next;
            return;
        }
        LinkNode<K> cur = this.head;
        while (cur.next != null) {
            if (cur.next.data == key || key.equals(cur.next.data)) {
                LinkNode<K> del = cur.next;
                cur.next = del.next;
                return;
            }
            cur = cur.next;
        }
        System.out.println("未找到该元素");
    }

    public void removeAllByKey(K key) {
        if (this.head == null) {
            return;
        }

        LinkNode<K> pre = this.head;
        LinkNode<K> cur = this.head.next;
        while (cur != null) {
            if (cur.data == key || key.equals(cur.data)) {
                pre.next = cur.next;
            } else {
                pre = cur;
            }
            cur = cur.next;
        }
        if (this.head.data == key || key.equals(this.head.data)) {
            this.head = this.head.next;
        }
    }

    public void clear() {
        while (this.head != null) {
            LinkNode<K> cur = this.head.next;
            this.head.next = null;
            this.head = cur;
        }
    }
}
