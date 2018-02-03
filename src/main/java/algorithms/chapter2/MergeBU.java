package algorithms.chapter2;

/**
 * @auther Fighter Created on 2018/2/2.
 */
public class MergeBU extends Example {
    private static Comparable[] aux;

    public static void sort(Comparable[] a) {
        int N = a.length;
        aux = new Comparable[N];
        //sz 子数组大小
        for (int sz = 1; sz < N; sz += sz) {
            //lo 子数组索引
            for (int lo = 0; lo < N - sz; lo += sz + sz) {
                merge(a, lo, lo + sz - 1, Math.min(lo + sz + sz - 1, N - 1));
            }
        }
    }

    private static void merge(Comparable[] a, int lo, int mid, int hi) {
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = aux[j++];
            }else if(j > hi){
                a[k] = aux[i++];
            }else if(less(aux[i],aux[j])){
                a[k] = aux[i++];
            }else {
                a[k] = aux[j++];
            }
        }
    }

    public static void main(String[] args) {
        Integer[] a = new Integer[]{49, 38, 65, 97, 26, 13, 27, 49, 55, 4};
        sort(a);
        assert isSort(a) : "Error Information...";
        show(a);
    }
}
