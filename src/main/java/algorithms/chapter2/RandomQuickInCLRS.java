package algorithms.chapter2;

import java.util.Random;

/**
 * 快排随机化版本
 * @auther Fighter Created on 2018/2/3.
 */
public class RandomQuickInCLRS extends QuickInCLRS {

    private static Random random = new Random();

    private static void sort(Comparable[] a, int p, int r) {
        if (p < r) {
            int q = randomPartition(a, p, r);
            sort(a, p, q - 1);
            sort(a, q + 1, r);
        }
    }

    private static int randomPartition(Comparable[] a, int p, int r) {
        //随机选取主元，这里是获取其位置
        int j = random.nextInt(r) + p;
        //随机选出的主元与a[r]交换
        exch(a, j, r);
        return partition(a, p, r);
    }

    public static void main(String[] args) {
        Integer[] a = new Integer[]{49, 38, 65, 97, 26, 13, 27, 49, 55, 4};
        sort(a);
        assert isSort(a) : "Error Information...";
        show(a);
    }
}
