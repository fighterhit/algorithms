package assignment.assignment5_netflow;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @auther Fighter Created on 2018/1/10.
 */
public class PushRelabel {
    public static final String FILE_NAME = "F:\\之前\\信工所\\研一上\\计算机算法设计与分析\\作业\\第五次作业\\problem2.data";

    private static void readAllSamples() throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(FILE_NAME));
        while (true) {
            String line = bf.readLine();
            if (line == null) {
                break;
            } else if (line.startsWith("#")) {
                continue;
            } else if (line.split(" ").length == 2) {
                String[] matrixSize = line.split(" ");
                int rowNum = Integer.parseInt(matrixSize[0]);
                int colNum = Integer.parseInt(matrixSize[1]);
                String[] rowSumArr = bf.readLine().split(" ");
                String[] colSumArr = bf.readLine().split(" ");
                List<Integer> rowSumList = new ArrayList<>();
                int rowSum = 0;
                for (String i : rowSumArr) {
                    rowSumList.add(Integer.parseInt(i));
                    rowSum += Integer.parseInt(i);
                }

                List<Integer> colSumList = new ArrayList<>();
                int colSum = 0;
                for (String i : colSumArr) {
                    colSumList.add(Integer.parseInt(i));
                    colSum += Integer.parseInt(i);
                }

                int matrixShape = rowNum * colNum + 2;
                int[][] flow = new int[matrixShape][matrixShape];

                for (int i = 0; i < matrixShape; i++) {
                    Arrays.fill(flow[i], 0);
                }

                int[][] cap = new int[matrixShape][matrixShape];
                for (int i = 0; i < matrixShape; i++) {
                    Arrays.fill(cap[i], 0);
                }

                for (int i = 0; i < rowNum; i++) {
                    for (int j = 0; j < colNum; j++) {
                        cap[i][rowNum + j] = 1;
                    }
                }

                for (int i = 0; i < rowNum; i++) {
                    cap[matrixShape - 2][i] = rowSumList.get(i);
                }

                for (int i = 0; i < colNum; i++) {
                    cap[rowNum + i][matrixShape - 1] = colSumList.get(i);
                }

                int maxFlow = pushRelabel(matrixShape, cap, flow);
                if (maxFlow != colSum) {
                    System.out.println("Matrix doesn't exist!");
                } else {
                    int[][] matrix = new int[rowNum][colNum];
                    for (int i = 0; i < rowNum; i++) {
                        Arrays.fill(matrix[i], 0);
                    }

                    for (int i = 0; i < rowNum; i++) {
                        for (int j = 0; j < colNum; j++) {
                            matrix[i][j] = flow[i][rowNum + j];
                        }
                    }
                    for (int i = 0; i < matrix.length; i++) {
                        for (int j = 0; j < matrix[i].length; j++) {
                            System.out.print(matrix[i][j]);
                        }
                        System.out.println();
                    }
                    System.out.println("===================================");
                }

            } else {
                break;
            }
        }
    }

    private static int pushRelabel(int matrixShape, int[][] cap, int[][] flow) {
        int[] h = new int[matrixShape];
        int[] e = new int[matrixShape];
        int[] nei = new int[matrixShape];
        Arrays.fill(h, 0);
        Arrays.fill(e, 0);
        Arrays.fill(nei, 0);
        List<Integer> nodeList = new ArrayList<Integer>();
        int src = matrixShape - 2, dst = matrixShape - 1;
        for (int i = 0; i < matrixShape && i != src && i != dst; i++) {
            nodeList.add(i);
        }

        h[src] = matrixShape;
        e[src] = Integer.MAX_VALUE;
        for (int v = 0; v < matrixShape; v++) {
            int pFlow = Integer.min(e[src], cap[src][v] - flow[src][v]);
            flow[src][v] += pFlow;
            flow[v][src] -= pFlow;
            e[src] -= pFlow;
            e[v] += pFlow;
        }

        int i = 0;
        while (i < nodeList.size()) {
            int u = nodeList.get(i);
            int oldH = h[u];

            while (e[u] > 0) {
                if (nei[u] < matrixShape) {
                    int v = nei[u];
                    if (cap[u][v] - flow[u][v] > 0 && h[u] > h[v]) {

                        int pFlow = Integer.min(e[u], cap[u][v] - flow[u][v]);

                        flow[u][v] += pFlow;
                        flow[v][u] -= pFlow;

                        e[u] -= pFlow;
                        e[v] += pFlow;

                    } else {
                        nei[u] += 1;
                    }
                } else {
                    int minH = Integer.MAX_VALUE;
                    for (int v = 0; v < matrixShape; v++) {
                        if (cap[u][v] - flow[u][v] > 0) {
                            minH = Integer.min(minH, h[v]);
                            h[u] = minH + 1;
                        }
                    }
                    nei[u] = 0;
                }
            }


            if (h[u] > oldH) {
                List<Integer> tmpList = new ArrayList<>();
                tmpList.add(nodeList.remove(i));
                tmpList.addAll(nodeList);
                nodeList = tmpList;
                i = 0;
            } else {
                i += 1;
            }
        }

        int sum = 0;
        int[] l = flow[src];
        for (Integer integer : l) {
            sum += integer;
        }
        return sum;
    }

    public static void main(String[] args) throws IOException {
        readAllSamples();
    }
}
