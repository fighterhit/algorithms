package algorithms.chapter2;

/**
 * @auther Fighter Created on 2018/1/30.
 */
public class Selection extends Example {
    public static void sort(Comparable[] a) {
        //将a[]按升序排列
        int N = a.length;
        for (int i = 0; i < N; i++) {
            //将a[i] 和 a[i+1...N]中的最小元素交换
            int min = i;//最小元素索引
            for (int j = i + 1; j < N; j++) {
                if (less(a[j], a[min])) {
                    min = j;
                }
            }
            exch(a, i, min);
        }
    }

    public static void main(String[] args) {
        Integer[] a = new Integer[]{144, 354, 55, 66, 7};
        sort(a);
        assert isSort(a) : "Error Information...";
        show(a);
    }
}
