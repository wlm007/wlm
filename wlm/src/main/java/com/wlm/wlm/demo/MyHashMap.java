package com.wlm.wlm.demo;

/**
 * 自定义hashMap
 * @author wuliming
 * @date 2021/11/29 15:23
 */
public class MyHashMap<K, V> {

    /**
     * 节点类
     * @param <K>
     * @param <V>
     */
    class Node<K, V> {
        private final K key;
        private V value;

        private Node<K, V> next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    /**
     * 默认桶数组长度
     */
    private final int DEFAULT_CAPACITY = 16;

    /**
     *
     */
    private final float LOAD_FACTOR = 0.75f;

    /**
     * 大小
     */
    private int size;

    Node<K, V>[] buckets;

    public MyHashMap() {
        buckets = new Node[DEFAULT_CAPACITY];
        size = 0;
    }

    public MyHashMap(int capacity) {
        buckets = new Node[capacity];
        size = 0;
    }

    /**
     * 散列函数 获取hashCode
     * @param key key
     * @param length value
     * @return hashCode
     */
    private int getIndex(K key, int length) {
        int hashCode = key.hashCode();
        int index = hashCode % length;
        return Math.abs(index);
    }

    /**
     * put方法
     *
     * @param key key
     * @param value value
     */
    public void put(K key, V value) {
        //判断是否需要进行扩容
        if (size >= buckets.length * LOAD_FACTOR) {
            resize();
        }
        putVal(key, value, buckets);
    }

    /**
     * 将元素存入指定的node数组
     *
     * @param key key
     * @param value value
     * @param table node数组
     */
    private void putVal(K key, V value, Node<K, V>[] table) {
        //获取位置
        int index = getIndex(key, table.length);
        Node<K, V> node = table[index];
        //插入的位置为空
        if (node == null) {
            table[index] = new Node<>(key, value);
            size++;
            return;
        }
        //插入位置不为空，说明发生冲突，使用链地址法,遍历链表
        while (node != null) {
            //如果key相同，就覆盖掉
            if (node.key.hashCode() == key.hashCode() && (node.key == key || node.key.equals(key))) {
                node.value = value;
                return;
            }
            node = node.next;
        }
        //当前key不在链表中，插入链表头部
        table[index] = new Node(key, value, table[index]);;
        size++;
    }

    /**
     * 扩容
     */
    private void resize() {
        //创建一个两倍容量的桶数组
        Node<K, V>[] newBuckets = new Node[buckets.length * 2];
        //将当前元素重新散列到新的桶数组
        rehash(newBuckets);
        buckets = newBuckets;
    }

    /**
     * 重新散列当前元素
     *
     * @param newBuckets 新桶数组
     */
    private void rehash(Node<K, V>[] newBuckets) {
        //map大小重新计算
        size = 0;
        //将旧的桶数组的元素全部刷到新的桶数组里
        for (Node<K, V> bucket : buckets) {
            //为空，跳过
            if (bucket == null) {
                continue;
            }
            Node<K, V> node = bucket;
            while (node != null) {
                //将元素放入新数组
                putVal(node.key, node.value, newBuckets);
                node = node.next;
            }
        }
    }

    /**
     * 获取元素
     *
     * @param key key
     * @return value
     */
    public V get(K key) {
        //获取key对应的地址
        int index = getIndex(key, buckets.length);
        if (buckets[index] == null) {
            return null;
        }
        Node<K, V> node = buckets[index];
        //查找链表
        while (node != null) {
            if (node.key.hashCode() == key.hashCode() && (node.key == key || node.key.equals(key))) {
                return node.value;
            }
            node = node.next;
        }
        return null;
    }
}
