package chapter1;

import java.util.Scanner;

/**
 *  快速查找并查集
 *  @auther Fighter Created on 2017/9/20.
 */

public class QuickFindUF {

    private int[] id;

    public QuickFindUF(int n) {

        id = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
        }
    }

    /**
     * 连通操作：将id[p]更新未id[q]
     * @param p 数组下标
     * @param q 数组下标
     */
    public void union(int p, int q) {

        int val = id[p];
        for (int i = 0; i < id.length; i++) {
            if (id[i] == val) {
                id[i] = id[q];
            }
        }
    }

    /**
     * @param p 数组下标
     * @return id[p]的值
     */
    public int find(int p) {
        return id[p];
    }

    /**
     * 判断两节点是否连通
     * @param p
     * @param q
     * @return
     */
    public boolean connected(int p, int q) {
        return id[p] == id[q];
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        QuickFindUF quickFindUF = new QuickFindUF(10);

        while (scanner.hasNext()) {

            int p = scanner.nextInt();
            int q = scanner.nextInt();

            if (!quickFindUF.connected(p, q)) {
                quickFindUF.union(p, q);
                System.out.printf("link id[%d] and id[%d]\n", p, q);
            }else{
                System.out.printf("id[%d] has connected id[%d]\n", p, q);
                quickFindUF.union(p, q);
            }
        }
    }
}
