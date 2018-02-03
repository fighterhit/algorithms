package algorithms.chapter2;

import edu.princeton.cs.algs4.StdRandom;

/**
 * Hoare 版本快排
 *
 * @auther Fighter Created on 2018/2/3.
 */
public class Quick extends Example {
    public static void sort(Comparable[] a) {
        //消除对输入的依赖
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) {
            return;
        }
        int j = partition(a, lo, hi);
        //将左半部分a[lo...j-1]排序
        sort(a, lo, j - 1);
        //将右半部分a[j+1...hi]排序
        sort(a, j + 1, hi);
    }

    private static int partition(Comparable[] a, int lo, int hi) {
        //将数组切分成a[lo...i-1], a[i], a[i+1...hi]
        //左右扫描指针
        int i = lo, j = hi + 1;
        //切分元素
        Comparable v = a[lo];
        while (true) {
            //扫描左右，检查扫描是否结束并交换元素
            while (less(a[++i], v)) {
                if (i == hi) {
                    break;
                }
            }

            while (less(v, a[--j])) {
                if (j == lo) {
                    break;
                }
            }

            if (i >= j) {
                break;
            }

            exch(a, i, j);
        }
        //将v = a[j]放入正确的位置
        exch(a, lo, j);
        //a[lo...j-1] <= a[j] <= a[j+1...hi]
        return j;
    }

    /**
     * 快排非递归版本
     * <br/>
     * 如果哨兵i先走，i和j相遇的时候，i和j所在位置的数会是一个大于基准数的数，这时基准数在最左边，这个数再和基准数位置交换，那么基准数左边就有一个大于基准数的数了，这样就达不到目的了（左边全部小于基准数，右边全部大于基准数）
     */
    public static void quickSort(Comparable[] a, int lo, int hi) {
        if (lo < hi) {
            Comparable temp = a[lo];
            int i = lo, j = hi;

            while (i < j) {
                //从右边扫描找到一个小于temp元素
                while (j > i && less(temp, a[j])) {
                    --j;
                }
                if (i < j) {
                    a[i] = a[j];
                    ++i;
                }
                while (i < j && less(a[i], temp)) {
                    //从左边扫描，找到一个大于temp元素
                    ++i;
                }
                if (i < j) {
                    a[j] = a[i];
                    --j;
                }
            }
            //temp放在最终位置
            a[i] = temp;
            //对temp左边元素进行扫描
            quickSort(a, lo, i - 1);
            //对temp右边元素进行扫描
            quickSort(a, i + 1, hi);
        }
    }

    public static void main(String[] args) {
        Integer[] a = new Integer[]{49, 38, 65, 97, 26, 13, 27, 49, 55, 4};
//        Integer[] a = new Integer[]{1,2,3,4,1,1,2,2,3,3};
//        sort(a);
        quickSort(a, 0, a.length - 1);
        assert isSort(a) : "Error Information...";
        show(a);
    }
}
