package chapter1;

import java.util.Scanner;

/**
 * 快速合并并查集（构造树）
 * @auther Fighter Created on 2017/9/22.
 */
public class QuickUnion {
    private int[] id;

    private QuickUnion(int n) {
        this.id = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
        }
    }

    /**
     * 合并操作，合并两组根节点即可
     *
     * @param p
     * @param q
     */
    private void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP != rootQ)
            id[p] = rootQ;
    }

    /**
     * @param index the element of index
     * @return return the root of the element which in the given index
     */
    private int find(int index) {
        while (index != id[index]) {
            index = id[index];
        }
        return index;
    }

    /**
     * 判断两节点是否连通
     *
     * @param p
     * @param q
     * @return
     */
    private boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        QuickUnion quickUnion = new QuickUnion(10);

        while (scanner.hasNext()) {

            int p = scanner.nextInt();
            int q = scanner.nextInt();

            if (!quickUnion.connected(p, q)) {
                quickUnion.union(p, q);
                System.out.printf("link id[%d] and id[%d]\n", p, q);
            } else {
                System.out.printf("id[%d] has connected id[%d]\n", p, q);
                quickUnion.union(p, q);
            }
        }
    }


}
