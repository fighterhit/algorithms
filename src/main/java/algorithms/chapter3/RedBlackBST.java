package algorithms.chapter3;

/**
 * @auther Fighter Created on 2018/3/19.
 */
public class RedBlackBST<Key extends Comparable<Key>, Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private Node root;
    private class Node {
        Key key;
        Value value;
        Node left, right;
        //这课子树中结点总数
        int N;
        //由其父节点指向它的链接的颜色
        boolean color;
        public Node(Key key, Value value, int n, boolean color) {
            this.key = key;
            this.value = value;
            N = n;
            this.color = color;
        }
    }
    //结点和其父节点之间链接的颜色
    private boolean isRed(Node x) {
        if (x == null) {
            return false;
        }
        return x.color == RED;
    }
    int size(Node x) {
        if (x == null) {
            return 0;
        } else {
            return x.N;
        }
    }
    //左旋转
    Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = 1 + size(h.left) + size(h.right);
        return x;
    }
    Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = 1 + size(h.left) + size(h.right);
        return x;
    }
    private void flipColors(Node x) {
        x.left.color = x.right.color = BLACK;
        x.color = RED;
    }
    public void put(Key key, Value val) {
        root = put(root, key, val);
        root.color = BLACK;
    }
    private Node put(Node root, Key key, Value val) {
        //标准插入，和父节点用红链接相连
        if (root == null) {
            return new Node(key, val, 1, RED);
        }
        int cmp = key.compareTo(root.key);
        if (cmp < 0) {
            root.left = put(root.left, key, val);
        } else if (cmp > 0) {
            root.right = put(root.right, key, val);
        } else {
            root.value = val;
        }
        //左黑右红，左旋
        if (isRed(root.right) && !isRed(root.left)) {
            root = rotateLeft(root);
        }
        if (isRed(root.left) && isRed(root.left.left)) {
            root = rotateRight(root);
        }
        if (isRed(root.left) && isRed(root.right)) {
            flipColors(root);
        }
        root.N = 1 + size(root.left) + size(root.right);
        return root;
    }

}
