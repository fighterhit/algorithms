package course.Lecture1;

import java.util.Scanner;

/**
 * 求最大公约数
 *
 * @auther Fighter Created on 2017/9/22.
 */
public class Gcd {

    /**
     * 递归法求两个整数最大公约数（欧几里得）,时间复杂度O(n^2)
     *
     * @param p
     * @param q
     * @return 返回p和q最大公约数
     */
    private static int gcd(int p, int q) {

        if (q == 0) {
            return p;
        }
        return gcd(q, p % q);
    }


    /**
     * 非递归法求最大公约数（欧几里得）,时间复杂度O(logN)
     *
     * @param p
     * @param q
     * @return 返回p和q最大公约数
     */
    private static int gcd2(int p, int q) {
        int tmp;
        while (q > 0) {
            tmp = p % q;
            p = q;
            q = tmp;
        }
        return p;
    }

    /**
     * 扩展欧几里得算法是为了在计算最大公约数gcd(a,b)的同时，找到x，y，使得ax + by = d。
     *
     * @param a
     * @param b
     * @param obj 封装的返回值，包括 x,y
     */
    private static void exgcd(int a, int b, ExGcdRet obj) {
        if (b == 0) {
            obj.x = 1;
            obj.y = 0;
            return;
        }

        exgcd(b, a % b, obj);

        int tmpX = obj.x;
        int tmpY = obj.y;
        obj.x = tmpY;
//        obj.y = tmpX - tmpY * a / b;注意 a/b 与 tmpY 的前后顺序
        obj.y = tmpX - a / b * tmpY ;
    }

    /**
     * 封装扩展欧几里得算法的返回值（x,y）
     */
    static class ExGcdRet {
        int x, y;

        @Override
        public String toString() {
            return "x:" + x + "; y:" + y;
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {

            int p = scanner.nextInt();
            int q = scanner.nextInt();

            //若q>p，则第一次相当于p q交换位置
            System.out.println("最大公约数：");
            System.out.println(gcd(p, q));
            System.out.println(gcd2(p, q));

            System.out.println("x,y分别是：");
            ExGcdRet exGcdRet = new ExGcdRet();
            exgcd(p, q, exGcdRet);
            System.out.println(exGcdRet);

        }
    }


}
