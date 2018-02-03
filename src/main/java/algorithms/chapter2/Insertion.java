package algorithms.chapter2;

/**
 * @auther Fighter Created on 2018/1/30.
 */
public class Insertion extends Example {

    public static void sort1(Comparable[] a) {
        int N = a.length;
        for (int i = 1; i < N; i++) {
            for (int j = i; j > 0 && less(a[j], a[j - 1]); j--) {
                exch(a, j, j - 1);
            }
        }
    }

    public static void sort2(Comparable[] a) {
        int N = a.length, j;
        for (int i = 1; i < N; i++) {
            Comparable key = a[i];
            j = i - 1;
            //注意 j >= 0
            while (j >= 0 && less(key, a[j])) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = key;
        }
    }

    public static void main(String[] args) {
        Integer[] a = new Integer[]{144,354,55,66,7};
        sort2(a);
        assert isSort(a):"Error Information...";
        show(a);
    }
}
