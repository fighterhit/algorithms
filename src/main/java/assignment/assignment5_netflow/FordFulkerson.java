package assignment.assignment5_netflow;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * @auther Fighter Created on 2018/1/10.
 */

class Graph {
    private Map<String, List<Edge>> edges = new HashMap<>();
    private Map<Edge, Integer> flow = new HashMap<>();

    public Map<String, List<Edge>> getEdgesMap() {
        return edges;
    }

    public List<Edge> getEdgeByNode(String node) {
        return edges.get(node);
    }

    public Map<Edge, Integer> getFlowMap() {
        return flow;
    }

    public void setNode(String node) {
        edges.put(node, new ArrayList<>());
    }

    public void setEdge(String src, String dst, int cap) {
        Edge edge = new Edge(src, dst, cap);
        Edge rEdge = new Edge(dst, src, 0);
        edge.setrEdge(rEdge);
        rEdge.setrEdge(edge);
        edges.get(src).add(edge);
        edges.get(dst).add(rEdge);
        flow.put(edge, 0);
        flow.put(rEdge, 0);
    }

    public List<Edge> augmentPath(String src, String dst, List<Edge> p) {
        if (src.equals(dst)) {
            return p;
        }
        for (Edge edge : edges.get(src)) {
            int residual = edge.getCap() - flow.get(edge);
            if (residual > 0 && !p.contains(edge)) {
                p.add(edge);
                List tmpPath = augmentPath(edge.getDst(), dst, p);
                if (tmpPath.size() > 0) {
                    return tmpPath;
                }
            }
        }
        return p;
    }


    public int getMaxFlow() {
        List<Edge> p = augmentPath("s", "t", new ArrayList<>());
        while (p.size() > 0) {
            List<Integer> resFlow = new ArrayList();
            for (Edge edge : p) {
                resFlow.add(edge.getCap() - flow.get(edge));
            }
            Collections.sort(resFlow);
            int min = resFlow.get(0);
            for (Edge edge : p) {
                flow.put(edge, flow.get(edge) + min);
            }
            p = augmentPath("s", "t", new ArrayList<>());
        }
        int maxFlow = 0;
        for (Edge e : edges.get("s")) {
            maxFlow += flow.get(e);
        }
        return maxFlow;
    }

    static class Edge {

        private String src;
        private String dst;
        private int cap;
        private Edge rEdge;

        public Edge(String src, String dst, int cap) {
            this.src = src;
            this.dst = dst;
            this.cap = cap;
        }

        public String getSrc() {
            return src;
        }

        public String getDst() {
            return dst;
        }

        public int getCap() {
            return cap;
        }

        public Edge getrEdge() {
            return rEdge;
        }

        public void setrEdge(Edge rEdge) {
            this.rEdge = rEdge;
        }

        @Override
        public String toString() {
            return String.format("\n 边：%s -> %s；容量：%d", src, dst, cap);
        }
    }
}

public class FordFulkerson {
    public static final String FILE_NAME = "F:\\之前\\信工所\\研一上\\计算机算法设计与分析\\作业\\第五次作业\\problem1.data";

    private static List<List> readAllSamples() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(FILE_NAME));
        List<List> samples = new ArrayList<>();
        List<String> sample = new ArrayList<>();
        int lineCount = 0;
        for (String line : lines) {
            lineCount++;
            if (line.startsWith("#")) {
                continue;
            } else if (!"".equals(line)) {
                sample.add(line);
            } else {
                samples.add(sample);
                sample = new ArrayList<>();
            }
        }
        samples.add(sample);
        return samples;
    }

    private static void testCase(List<List> samples) {


        System.out.println();
        for (List<String> sample : samples) {
            String[] firstLine = ((String) sample.get(0)).split(" ");
            int N_Jobs = 0, M_Computers = 0;
            N_Jobs = Integer.parseInt(firstLine[0]);
            M_Computers = Integer.parseInt(firstLine[1]);
            System.out.println(String.format("the number of jobs and computers: N = %d, M = %d", N_Jobs, M_Computers));

            Graph graph = new Graph();
            graph.setNode("s");
            graph.setNode("t");
            //加节点
            List<String> jNodes = new ArrayList<>();
            for (int i = 1; i <= N_Jobs; i++) {
                String node = "J" + i;
                jNodes.add(node);
                graph.setNode(node);
            }
            List<String> cNodes = new ArrayList<>();
            for (int i = 1; i <= M_Computers; i++) {
                String node = "C" + i;
                cNodes.add(node);
                graph.setNode(node);
            }
            //加边
            List<String[]> JCMap = new ArrayList<>();
            for (int i = 1; i <= N_Jobs; i++) {
                //s-j
                graph.setEdge("s", "J" + i, 1);
                String[] JCNodes = sample.get(i).split(" ");
                JCMap.add(JCNodes);
                for (String jcNode : JCNodes) {
                    //j-c
                    graph.setEdge("J" + i, "C" + jcNode, 1);
                }
            }
            for (int i = 1; i <= M_Computers; i++) {
                graph.setEdge("C" + i, "t", N_Jobs);
            }

            int maxFlow = graph.getMaxFlow();
            //二分
            if (maxFlow != N_Jobs) {
                System.out.println("Solution doesn't exist!");
            } else {
                int left = 1, right = N_Jobs, minMaxLoad = 0;
                System.out.println("ct cap: " + N_Jobs);
                System.out.println("max flow: " + maxFlow);
                while (left <= right) {

                    int cap = left + ((right - left ) >> 1);
                    Graph tmpGraph = new Graph();
                    tmpGraph.setNode("s");
                    tmpGraph.setNode("t");
                    for (String jNode : jNodes) {
                        tmpGraph.setNode(jNode);
                    }
                    for (String cNode : cNodes) {
                        tmpGraph.setNode(cNode);
                    }
                    for (int i = 1; i <= JCMap.size(); i++) {
                        tmpGraph.setEdge("s", "J" + i, 1);
                        String[] c = JCMap.get(i - 1);
                        for (String s : c) {
                            tmpGraph.setEdge("J" + i, "C" + s, 1);
                        }
                    }
                    for (int i = 1; i <= M_Computers; i++) {
                        tmpGraph.setEdge("C" + i, "t", cap);
                    }

                    if (cap * M_Computers < N_Jobs) {
                        left = cap + 1;
                        continue;
                    }

                    maxFlow = tmpGraph.getMaxFlow();
                    System.out.println("max flow: " + maxFlow);
//                    System.out.println(tmpGraph.getFlowMap());
                    if (maxFlow != N_Jobs) {
                        left = cap + 1;
                    } else {
                        right = cap - 1;
                        minMaxLoad = 0;
                        for (String cNode : cNodes) {
                            for (Graph.Edge edge : tmpGraph.getEdgeByNode(cNode)) {
                                if (minMaxLoad < tmpGraph.getFlowMap().get(edge)) {
                                    minMaxLoad = tmpGraph.getFlowMap().get(edge);
                                }
                            }
                        }
                        System.out.println("ComputerLoad: " + minMaxLoad);
                    }
                }
                System.out.println("minMaxLoad: " + minMaxLoad+"\n");
            }
        }
    }

    public static void main(String[] args) throws IOException {
        List<List> samples = readAllSamples();
        testCase(samples);
    }
}
