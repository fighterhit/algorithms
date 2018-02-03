package algorithms.chapter2;

/**
 * 快排普通版本
 * @auther Fighter Created on 2018/2/3.
 */
public class QuickInCLRS extends Example {
    public static void sort(Comparable[] a) {
        sort(a, 0, a.length - 1);
    }
    private static void sort(Comparable[] a, int p, int r) {
        if (p < r) {
            int q = partition(a, p, r);
            sort(a, p, q - 1);
            sort(a, q + 1, r);
        }
    }
    static int partition(Comparable[] a, int p, int r) {
        Comparable x = a[r];
        int i = p - 1;
        for (int j = p; j <= r - 1; j++) {
            if (less(a[j], x)) {
                i++;
                exch(a, i, j);
            }
        }
        exch(a, i + 1, r);
        return i + 1;
    }
    public static void main(String[] args) {
        Integer[] a = new Integer[]{144, 354, 55, 66, 7};
        sort(a);
        assert isSort(a) : "Error Information...";
        show(a);
    }
}
