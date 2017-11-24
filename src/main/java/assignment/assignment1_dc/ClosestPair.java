package assignment.assignment1_dc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @auther Fighter Created on 2017/10/9.
 */
//自定义坐标
class Point {
    private double x, y;

    public Point() {
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getDistance(Point p) {
        //两点间距离公式
        return Math.sqrt(Math.pow(this.x - p.getX(), 2) + Math.pow(this.y - p.getY(), 2));
    }

    //比较两个点是否相同
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (Double.compare(point.x, x) != 0) return false;
        return Double.compare(point.y, y) == 0;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", x, y);
    }
}

public class ClosestPair {

    private static String POINT_FILE_NAME =
            "assignment/assignment1_dc/Q8.txt";
    private static int POINTS_NUM_LOW_BOUND = 10;
    private static List<Point> sortXPoints;
    private static List<Point> sortYPoints;

    //从Q8.txt文件读取点的横纵坐标——偶数行为x，奇数为y
    private static List<Point> readPointFromFile() {
        List<Point> points = new ArrayList<>();
        try {
            //读取所有行
            List<String> strList = Files.readAllLines(Paths.get(POINT_FILE_NAME));
            for (int i = 0; i < strList.size(); i++) {
                Point point = new Point(Double.parseDouble(strList.get(i)),
                        Double.parseDouble(strList.get(++i)));
                points.add(point);
            }
        } catch (IOException e) {
            System.err.println("read file error!");
        }
        return points;
    }

    //筛选带内的点
    private static List<Point> getPointsInBelt(double split, double distance, List<Point>... lrPoints) {
        List<Point> pointsInBelt = new ArrayList<>();
        for (List<Point> points : lrPoints) {
            for (Point point : points) {
                if (Math.abs(split - point.getX()) < distance)
                    pointsInBelt.add(point);
            }
        }
        return pointsInBelt;
    }

    //获取最近点对
    private static List<Point> getClosestPair(List<Point> points) {

        //递归出口
        if (points.size() <= 2) {
            return points;
        }

        //中线左边点集
        List<Point> leftPoints = points.subList(0, points.size() / 2);
        //中线右边点集
        List<Point> rightPoints = points.subList(points.size() / 2 , points.size());

        //查找左边集合最近点对
        List<Point> leftResult = getClosestPair(leftPoints);
        //查找右边集合最近点对
        List<Point> rightResult = getClosestPair(rightPoints);

        //两边最近点对距离最小值
        List<Point> result;
        double leftMinDistance = leftResult.size() == 1 ?
                Double.MAX_VALUE : leftResult.get(0).getDistance(leftResult.get(1)),
                rightMinDistance = rightResult.size() == 1 ?
                        Double.MAX_VALUE : rightResult.get(0).getDistance(rightResult.get(1)),
                minDistance;
        if (leftMinDistance <= rightMinDistance) {
            result = leftResult;
            minDistance = leftMinDistance;
        } else {
            result = rightResult;
            minDistance = rightMinDistance;
        }

        //中线的x坐标
        double split = points.get(points.size() / 2).getX();
        //筛选带内的点
        List<Point> pointsInBelt = getPointsInBelt(split, minDistance, leftPoints, rightPoints);
        pointsInBelt.sort((point, t1) -> (int) (point.getY() - t1.getY()));
        //求带内最短距离
        double tmpDis;
        int index;
        for (int i = 0; i < pointsInBelt.size(); i++) {
            Point point = pointsInBelt.get(i);
            //只算该点后的7个点
            for (int j = 1; j < 8; j++) {
                index = i + j;
                if (index == pointsInBelt.size())
                    break;
                tmpDis = point.getDistance(pointsInBelt.get(index));
                if (tmpDis < minDistance) {
                    result.set(0, point);
                    result.set(1, pointsInBelt.get(i + j));
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {

        List<Point> pointList = readPointFromFile();
        //按x坐标排序
        pointList.sort((p1, p2) -> (int) (p1.getX() - p2.getX()));
        sortXPoints = new ArrayList<>(pointList);
        //按y坐标排序
        pointList.sort((p1, p2) -> (int) (p1.getY() - p2.getY()));
        sortYPoints = new ArrayList<>(pointList);
        List<Point> closestPair = getClosestPair(sortXPoints);
        System.out.println(String.format("the ClosestPair are: %s and %s, the distance is: %f",
                closestPair.get(0), closestPair.get(1),closestPair.get(0).getDistance(closestPair.get(1))));

    }
}
