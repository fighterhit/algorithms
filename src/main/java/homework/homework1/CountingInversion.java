package homework.homework1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @auther Fighter Created on 2017/10/11.
 */
public class CountingInversion {
    //定义源文件路径
    private static String DATA_FILE_NAME = "G:\\IdeaProjects\\algorithms\\src\\main\\java\\homework\\Q8.txt";
    //定义逆序对数个数
    private static long inversionNum = 0L;
    //源数据
    private static List<Integer> srcDatas;
    //排序过程中的临时数组
    private static List<Integer> desDatas;

    //从Q8.txt文件读取点的横纵坐标——偶数行为x，奇数为y
    private static List<Integer> readPointFromFile() {
        List<Integer> datas = new ArrayList<>();
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE_NAME));
            while ((line = reader.readLine()) != null) {
                datas.add(Integer.valueOf(line));
            }
        } catch (IOException e) {
            System.err.println("read file error!");
        }
        return datas;
    }

    /**
     * 归并排序实现逆序对数计数
     *
     * @param left
     * @param right
     */
    //分割
    private static void mergeSort(int left, int right) {
        if (left < right) {
            int m = (left + right) / 2;
            mergeSort(left, m);
            mergeSort(m + 1, right);
            merge(left, m, right);
        }
    }

    //合并
    private static void merge(int left, int m, int right) {
        int i = left;
        int j = m + 1;
        int k = left;
        while (i <= m && j <= right) {
            //左边某个数大于右边某个数，则左边以后的数都大于该数
            if (srcDatas.get(i) > srcDatas.get(j)) {
                desDatas.set(k++, srcDatas.get(j++));
                inversionNum += (m - i + 1);
            } else {
                desDatas.set(k++, srcDatas.get(i++));
            }
        }
        //左边剩余的数填充临时数组
        while (i <= m)
            desDatas.set(k++, srcDatas.get(i++));
        //右边剩余的数填充临时数组
        while (j <= right)
            desDatas.set(k++, srcDatas.get(j++));
        //将排好序的部分覆盖到源数组
        for (int p = left; p <= right; p++)
            srcDatas.set(p, desDatas.get(p));
    }

    /**
     * 快速排序实现逆序对数计数
     */

    private static List<Integer> quickSort(List<Integer> datas) {
        if (datas.size() < 2)
            return datas;
        //取第一个元素为枢纽元
        int pivot = datas.get(0);
        List<Integer> biggerSet = new ArrayList<>();
        List<Integer> smallerSet = new ArrayList<>();
        //大元素放左边，小元素放右边
        for (int i = 1, len = datas.size(); i < len; i++) {
            if (pivot > datas.get(i)) {
                smallerSet.add(datas.get(i));
                inversionNum++;
                //遇到比pivot小的元素一定比大元素集合里的也小
                inversionNum += biggerSet.size();
            } else if (pivot < datas.get(i)) {
                biggerSet.add(datas.get(i));
            }
        }
        //递归，对大/小元素集合进行排序
        List<Integer> leftDatas = quickSort(smallerSet);
        List<Integer> rightDatas = quickSort(biggerSet);
        List<Integer> result = new ArrayList<>();
        result.addAll(leftDatas);
        result.add(pivot);
        result.addAll(rightDatas);
        return result;
    }

    public static void main(String[] args) {
        srcDatas = readPointFromFile();
        //定义临时数组和源数组大小一样
        desDatas = new ArrayList<>(srcDatas);
        //1、归并排序实现逆序对计数
        long start = System.currentTimeMillis();
        mergeSort(0, srcDatas.size() - 1);
        long end = System.currentTimeMillis();
        System.out.println("逆序对数：" + inversionNum + "，消耗时间：" + (end - start)+"ms");
        //2、快速排序实现逆序对计数
        srcDatas = readPointFromFile();
        inversionNum = 0;
        start = System.currentTimeMillis();
        quickSort(srcDatas);
        end = System.currentTimeMillis();
        //输出逆序对数
        System.out.println("逆序对数：" + inversionNum + "，消耗时间：" + (end - start)+"ms");
    }
}
