package algorithms.chapter2;

/**
 * @auther Fighter Created on 2018/2/2.
 */
public class Shell extends Example {
    public static void sort1(Comparable[] a) {
        int N = a.length;
        int h = 1;
        while (h < N / 3) {
            h = 3 * h + 1;
        }

        while (h >= 1) {
            for (int i = h; i < N; i++) {
                //注意 j >= h
                for (int j = i; j >= h && less(a[j], a[j - h]); j -= h) {
                    exch(a, j, j - h);
                }
            }
            h /= 3;
        }
    }

    public static void sort2(Comparable[] a) {
        int N = a.length;
        //初始化gap，逐步缩小gap，直到1
        for (int gap = N / 2; gap >= 1; gap /= 2) {
            //每组都从第gap个元素开始进行直接插入排序
            for (int i = gap; i < N; i++) {
                //插入排序
                Comparable key = a[i];
                int j = i - gap;
                //注意 j >= 0
                while (j >= 0 && less(key,a[j])){
                    a[j + gap] = a[j];
                    j -= gap;
                }
                a[j + gap] = key;
            }
        }
    }

    public static void main(String[] args) {
//        Integer[] a = new Integer[]{144, 354, 55, 66, 7};
        Integer[] a = new Integer[]{49, 38, 65, 97, 26, 13, 27, 49, 55, 4};
//        sort1(a);
        sort2(a);
        assert isSort(a) : "Error Information...";
        show(a);
    }
}
