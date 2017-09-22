package chapter1;

import java.util.Scanner;

/**
 * 带权并查集
 * @auther Fighter Created on 2017/9/22.
 */
public class WeightedQuickUnion {

    private int[] id;
    private int[] size;

    private WeightedQuickUnion(int n) {
        this.id = new int[n];
        this.size=new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
            size[i] = 1;
        }
    }

    /**
     * 优化：带权并查集(小树挂在大树下); 目的：降低树高，减少寻找根节点时间
     *
     * @param p
     * @param q
     */
    private void weightedUnion(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP != rootQ) {
            //小树挂在大树下
            if (size[p] > size[q]) {
                id[q] = rootP;
                size[p]+=size[q];
            } else {
                id[p] = rootQ;
                size[q]+=rootP;
            }
        }
    }

    /**
     * @param index 元素下标
     * @return 返回根节点下标
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
        WeightedQuickUnion weightedQuickUnion = new WeightedQuickUnion(10);

        while (scanner.hasNext()) {

            int p = scanner.nextInt();
            int q = scanner.nextInt();

            if (!weightedQuickUnion.connected(p, q)) {
                weightedQuickUnion.weightedUnion(p, q);
                System.out.printf("link id[%d] and id[%d]\n", p, q);
            } else {
                System.out.printf("id[%d] has connected id[%d]\n", p, q);
                weightedQuickUnion.weightedUnion(p, q);
            }
        }
    }
}
