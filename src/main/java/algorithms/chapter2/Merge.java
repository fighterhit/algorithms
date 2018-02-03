package algorithms.chapter2;

/**
 * @auther Fighter Created on 2018/2/2.
 */
public class Merge extends Example {

    //归并所需辅助数组
    private static Comparable[] aux;

    public static void merge(Comparable[] a, int lo, int mid, int hi) {
        int i = lo, j = mid + 1;
        //将a[]复制到aux[]
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }
        //注意：比较元素都用aux[]
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = aux[j++];
            } else if (j > hi) {
                a[k] = aux[i++];
            } else if (less(aux[j], aux[i])) {
                a[k] = aux[j++];
            } else {
                a[k] = aux[i++];
            }
        }
    }

    public static void sort(Comparable[] a) {
        aux = new Comparable[a.length];
        sort(a, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) {
            return;
        }
        int mid = lo + (hi - lo) / 2;
        //左半边排序
        sort(a, lo, mid);
        //右半边排序
        sort(a, mid + 1, hi);
        //归并结果
        merge(a, lo, mid, hi);
    }

    public static void main(String[] args) {
        Integer[] a = new Integer[]{49, 38, 65, 97, 26, 13, 27, 49, 55, 4};
        sort(a);
        assert isSort(a) : "Error Information...";
        show(a);
    }
}
