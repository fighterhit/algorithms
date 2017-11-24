package assignment.assignment2_dp;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @auther Fighter Created on 2017/10/24.
 */
public class LIS {

    /**
     * input: 输入数组
     */
    private static int[] input;

    private static int[] dp;

    /**
     * O(n*n)方法
     *
     * @return 返回最长递增子序列
     */
    private static List<Integer> getLIS_On2() {

        //初始化，全填1
        Arrays.fill(dp, 1);
        //保存每个元素所在子序列的前驱
        int[] pos = new int[input.length];
        Arrays.fill(pos, 0);
        for (int i = 1; i < input.length; i++) {

            for (int j = 0; j < i; j++) {
                if (input[j] < input[i] && dp[i] < dp[j] + 1) {
                    dp[i] = dp[j] + 1;
                    //记录前驱
                    pos[i] = j;
                }
            }
        }

        int max = 1, lastPos = 0;
        //找到最大子序列长度，并获取前驱
        for (int i = 0, len = dp.length; i < len; i++) {
            if (max < dp[i]) {
                max = dp[i];
                lastPos = i;
            }
        }

        List<Integer> res = new ArrayList<>();
        res.add(input[lastPos]);
        for (int i = 0; i < max - 1; i++) {
            lastPos = pos[lastPos];
            res.add(input[lastPos]);
        }
        Collections.reverse(res);

        return res;
    }

    /**
     * O(nlogn)方法
     *
     * @return 返回最长递增子序列
     */
    private static List<Integer> getLIS_Onlogn() {

        //初始化，全填1
        Arrays.fill(dp, 1);

        //子序列，下标为长度，值为该长度下子序列的最大元素的最小值
        Integer[] seq = new Integer[input.length + 1];
        //初始化，默认长度为1的子序列最大元素最小值为第一个元素
        seq[1] = input[0];

        //初始化，从长度位2的子序列开始
        int len = 2;
        //查找元素在seq中所应插入位置
        int pos;

        for (int i = 1, l = input.length; i < l; i++) {
            if (input[i] > seq[len - 1]) {
                seq[len] = input[i];
                len++;
            } else {
                pos = binarySearch(seq, len - 1, input[i]);
                seq[pos] = input[i];

            }
        }
        //过滤null值, 比较耗时，单纯求长度可直接返回len
        return Arrays.stream(seq).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private static int binarySearch(Integer[] arr, int len, int key) {
        int left = 1, right = len, mid;
        //找到直接返回位置
        while (left <= right) {
            mid = (left + right) / 2;
            if (arr[mid] > key) {
                right = mid - 1;
            } else if (arr[mid] < key) {
                left = mid + 1;
            } else {
                return mid;
            }
        }
        //没找到，则找到插入位置
        return left;
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        //数组大小
        int num = scanner.nextInt();

        input = new int[num];
        dp = new int[num];

        for (int i = 0; i < num; i++) {
            input[i] = scanner.nextInt();
        }

        long start, end;
        List<Integer> res;
        //O(n*n)方法
        start = System.currentTimeMillis();
        res = getLIS_On2();
        end = System.currentTimeMillis();
        System.out.printf("最长递增子序列为：%s, 耗时：%d ms\n", res, (end - start));

        //O(nlogn)方法
        start = System.currentTimeMillis();
        res = getLIS_Onlogn();
        end = System.currentTimeMillis();
        System.out.printf("最长递增子序列长度为：%s, 每个长度的递增子序列最大元素的最小值为：%s, 耗时：%d ms",res.size(), res, (end - start));

    }
}
/*
*
6
1
4
3
2
6
5
*
*
6
5
6
7
1
2
8
*
*
9
2
1
5
3
6
4
8
9
7
* */