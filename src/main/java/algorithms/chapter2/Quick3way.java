package algorithms.chapter2;

import edu.princeton.cs.algs4.StdRandom;

/**
 * 三向切分 快排
 * @auther Fighter Created on 2018/2/3.
 */
public class Quick3way extends Example {

    public static void sort(Comparable[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo){
            return;
        }
        int lt = lo, i = lo + 1, gt = hi;
        Comparable v = a[lo];
        while (i <= gt){
            int cmp = a[i].compareTo(v);
            if (cmp < 0){
                exch(a, lt++, i++);
            }else if (cmp > 0){
                exch(a, i , gt--);
            }else {
                i++;
            }
            sort(a, lo, lt - 1);
            sort(a, gt + 1, hi);
        }
    }

    public static void main(String[] args) {
        Integer[] a = new Integer[]{49, 38, 65, 97, 26, 13, 27, 49, 55, 4};
        sort(a);
        assert isSort(a) : "Error Information...";
        show(a);
    }
}
