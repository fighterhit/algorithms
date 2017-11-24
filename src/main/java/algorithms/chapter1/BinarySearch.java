package algorithms.chapter1;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @auther Fighter Created on 2017/9/24.
 */
public class BinarySearch {

    /**
     * 二分查找
     * 给定待查找的关键字，以及待查找的排好序的数组，如在[1,3,4,6,8,10,12]中查找8,返回其在数组中的索引值
     *
     * @param left
     * @param right
     * @param key
     * @param arr
     * @return
     */
    private static int BinarySearch(int left, int right, int key, int[] arr) {
        while (left <= right) {

            int mid = (left + right) / 2;

            if (key > arr[mid]) {
                left = mid + 1;
            } else if (key == arr[mid]) {
                return mid;
            } else {
                right = mid - 1;
            }

        }
        return -1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int num = scanner.nextInt();
        int[] arr = new int[num];

        for (int i = 0; i < num; i++) {
            arr[i] = scanner.nextInt();
        }

        Arrays.sort(arr);

        System.out.println(BinarySearch(0, num - 1, 8, arr));
    }
}
