package algorithms.chapter3;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @auther Fighter Created on 2018/3/8.
 */
public class BST<Key extends Comparable<Key>, Value> {

    private Node root;

    class Node {
        Key key;
        Value val;
        Node left, right;
        /**
         * 以该节点为根节点的子树中节点个数
         */
        int N;

        public Node(Key key, Value val, int n) {
            this.key = key;
            this.val = val;
            N = n;
        }
    }

    public int size() {
        return size(root);
    }

    int size(Node x) {
        if (x == null) {
            return 0;
        } else {
            return x.N;
        }
    }

    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        //在以x为根节点的子树中查找并返回key所对应的值
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            return get(x.left, key);
        } else if (cmp > 0) {
            return get(x.right, key);
        }
        return x.val;
    }

    public void put(Key key, Value val) {
        //查找key，找到则更新它的值，否则为它创建一个新节点
        root = put(root, key, val);
    }

    private Node put(Node x, Key key, Value val) {
        if (x == null) {
            return new Node(key, val, 1);
        }
        int cmp = key.compareTo(key);
        if (cmp < 0) {
            x.left = put(x.left, key, val);
        } else if (cmp > 0) {
            x.right = put(x.right, key, val);
        } else {
            x.val = val;
        }
        x.N = size(x.left) + size(x.right) + 1;
        System.out.println(x.key);
        return x;
    }

    public Key min() {
        return min(root).key;
    }

    private Node min(Node x) {
        if (x.left == null) {
            return x;
        }
        return min(x.left);
    }

    public Key floor(Key key) {
        Node x = floor(root, key);
        if (x == null) {
            return null;
        }
        return x.key;
    }

    private Node floor(Node x, Key key) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp > 0) {
            Node tmp = floor(x.right, key);
            if (tmp != null) {
                return tmp;
            } else {
                return x;
            }
        } else if (cmp < 0) {
            return floor(x.left, key);
        }
        return x;
    }

    public Key select(int k) {
        return select(root, k).key;
    }

    public Node select(Node x, int k) {
        if (x == null) {
            return null;
        } else {
            if (size(x.left) > k) {
                return select(x.left, k);
            } else if (size(x.left) + 1 == k) {
                return x;
            } else {
                return select(x.right, k - 1 - size(x.left));
            }
        }
    }

    public int rank(Key key) {
        return rank(root, key);
    }

    private int rank(Node x, Key key) {
        //返回以x为根节点的子树中小于x.key的键的数量
        if (x == null) {
            return 0;
        }
        int cmp = key.compareTo(x.key);
        if (cmp > 0) {
            return size(x.left) + 1 + rank(x.right, key);
        } else if (cmp < 0) {
            return rank(x.left, key);
        } else {
            return size(x.left);
        }
    }

    public void deleteMin() {
        root = deleteMin(root);
    }

    private Node deleteMin(Node x) {
        if (x.left == null) {
            return x.right;
        }
        x.left = deleteMin(x.left);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void delete(Key key) {
        root = delete(root, key);
    }

    private Node delete(Node x, Key key) {
        if (x == null) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp > 0) {
            x.right = delete(x.right, key);
        } else if (cmp < 0) {
            x.left = delete(x.left, key);
        } else {
            if (x.right == null) {
                return x.left;
            }
            if (x.left == null) {
                return x.right;
            }
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    private Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> queue = new LinkedBlockingQueue<>();
        keys(root, queue, lo, hi);
        return queue;
    }
    //类似中序遍历
    private void keys(Node x, Queue<Key> queue, Key lo, Key hi) {
        if (x == null) {
            return;
        }
        int cmplo = lo.compareTo(x.key);
        int cmphi = lo.compareTo(x.key);
        //如果下界小于当前节点key，则先在左子树中进行范围查找合适节点
        if (cmplo < 0) {
            keys(x.left, queue, lo, hi);
        }
        //如果当前节点在范围内，则加入队列
        if (cmplo <= 0 && cmphi >= 0) {
            queue.add(x.key);
        }
        //如果上界大于当前节点key，则在右子树中进行范围查找合适节点
        if (cmphi > 0){
            keys(x.right,queue,lo,hi);
        }
    }

    public Key max() {
        return null;
    }

    public static void main(String[] args) {
        BST<Character, Integer> bst = new BST<>();

        bst.put('F', 2);
        bst.put('A', 1);
        bst.put('G', 3);
        bst.put('B', 4);
        bst.put('C', 5);
        System.out.println(bst.root.N);
    }
}