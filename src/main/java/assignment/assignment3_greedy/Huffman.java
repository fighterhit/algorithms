package assignment.assignment3_greedy;

import java.io.*;
import java.util.*;

/**
 * @auther Fighter Created on 2017/11/18.
 */
public class Huffman {

    public static List<File> compressFileList;
    public static List<File> decompressFileList;

    /**
     * 初始化待压缩列表
     */
    static void initCompressFileList() {
        compressFileList.add(new File("assignment/assignment3_greedy/graph.txt"));
        compressFileList.add(new File("assignment/assignment3_greedy/Aesop_Fables.txt"));
    }

    /**
     * 初始化待压缩列表
     */
    static void initDecompressFileList() {
        decompressFileList.add(new File("assignment/assignment3_greedy/graph.huf"));
        decompressFileList.add(new File("assignment/assignment3_greedy/Aesop_Fables.huf"));
    }

    /**
     * 压缩
     *
     * @param inputFile  待压缩文件
     * @param outputFile 压缩后的文件
     */
    static void compress(File inputFile, File outputFile) throws IOException {

        int[] weight = new int[256];
        Arrays.fill(weight, 0);
        FileInputStream fi = new FileInputStream(inputFile);
        int tmpByte, fileLen = 0;
        while ((tmpByte = fi.read()) != -1) {
            ++weight[tmpByte];
            //文件长度
            ++fileLen;
        }
        fi.close();

        int leavesNodeNum = 0, totalNodeNum = 0;
        List<HuffmanNode> nodeList = new LinkedList<>();
        for (int i = 0; i < 256; i++) {
            if (weight[i] != 0) {
                HuffmanNode huffmanNode = new HuffmanNode();
                huffmanNode.setC((byte) i);
                huffmanNode.setWeight(weight[i]);
                nodeList.add(huffmanNode);
                leavesNodeNum++;
            }
        }
        totalNodeNum = leavesNodeNum * 2 - 1;
        Collections.sort(nodeList);

        int index = 0;
        PriorityQueue<HuffmanNode> nodeQueue = new PriorityQueue<>();
        //为叶子节点添加索引并加入优先队列准备建树
        for (HuffmanNode huffmanNode : nodeList) {
            huffmanNode.setIndex(index++);
            nodeQueue.add(huffmanNode);
        }

        //添加度为2的节点
        for (int i = leavesNodeNum; i < totalNodeNum; i++) {
            HuffmanNode node = new HuffmanNode();
            nodeList.add(node);
        }

        HuffmanNode[] nodes = nodeList.toArray(new HuffmanNode[totalNodeNum]);

        //创建哈弗曼树
        for (int i = leavesNodeNum; i < totalNodeNum; i++) {
            HuffmanNode leftNode = nodeQueue.remove();
            HuffmanNode rightNode = nodeQueue.remove();
            nodes[i].setIndex(i);
            nodes[i].setLeftNode(leftNode.getIndex());
            nodes[i].setRightNode(rightNode.getIndex());
            nodes[i].setWeight(leftNode.getWeight() + rightNode.getWeight());
            leftNode.setParentNode(i);
            rightNode.setParentNode(i);
            nodeQueue.add(nodes[i]);
        }

        int childIndex, parentIndex;
        Map<Byte, StringBuilder> codeMap = new HashMap<>();
        StringBuilder codeTmp;
        //编码
        for (int i = 0; i < leavesNodeNum; i++) {
            codeTmp = new StringBuilder();
            for (childIndex = i, parentIndex = nodes[childIndex].getParentNode();
                 parentIndex != -1;
                 childIndex = parentIndex, parentIndex = nodes[parentIndex].getParentNode()) {
                if (childIndex == nodes[parentIndex].getLeftNode()) {
                    codeTmp.append(0);
                } else {
                    codeTmp.append(1);
                }
            }
            codeTmp = codeTmp.reverse();
            nodes[i].setCode(codeTmp);
            codeMap.put(nodes[i].getC(), codeTmp);
        }

        FileOutputStream fo = new FileOutputStream(outputFile);
        ObjectOutputStream oo = new ObjectOutputStream(fo);
        oo.writeInt(leavesNodeNum);
        for (int i = 0; i < leavesNodeNum; i++) {
            oo.writeByte(nodes[i].getC());
            oo.writeInt(nodes[i].getWeight());
        }

        //文件的字节长度
        oo.writeInt(fileLen);
        //写入字符编码
        fi = new FileInputStream(inputFile);
        codeTmp = new StringBuilder();
        while ((tmpByte = fi.read()) != -1) {
            // map的get要转换成 byte，否则为 null
            codeTmp.append(codeMap.get((byte) tmpByte));
            //一字节一字节的写
            while (codeTmp.length() >= 8) {
                tmpByte = 0;
                for (int i = 0; i < 8; i++) {
                    // 先移位，后移位会将最高位移出去
                    tmpByte <<= 1;
                    if ('1' == codeTmp.charAt(i)) {
                        tmpByte |= 1;
                    }
                }
                oo.writeByte(tmpByte);
                codeTmp = codeTmp.delete(0, 8);
            }
        }

        //最后补齐8位
        if (codeTmp.length() > 0) {
            tmpByte = 0;
            for (int i = 0; i < codeTmp.length(); i++) {
                if (codeTmp.charAt(i) == '1') {
                    tmpByte |= 1;
                }
                tmpByte <<= 1;
            }
            tmpByte <<= (8 - codeTmp.length());
            oo.writeByte(tmpByte);
        }
        oo.close();
        fo.close();
        fi.close();

    }


    /**
     * 解压
     *
     * @param inputFile  待解压文件
     * @param outputFile 解压后文件
     * @return
     */
    static void deCompress(File inputFile, File outputFile) throws IOException {
        FileInputStream fi = new FileInputStream(inputFile);
        ObjectInputStream oi = new ObjectInputStream(fi);
        //叶子节点数量
        int leavesNodeNum = oi.readInt(), totalNodeNum = 2 * leavesNodeNum - 1;
        HuffmanNode[] nodes = new HuffmanNode[totalNodeNum];

        byte tmpByte;
        int tmpWeight;
        PriorityQueue<HuffmanNode> nodeQueue = new PriorityQueue<>();
        for (int i = 0; i < leavesNodeNum; i++) {
            tmpByte = oi.readByte();
            tmpWeight = oi.readInt();
            nodes[i] = new HuffmanNode();
            nodes[i].setC(tmpByte);
            nodes[i].setWeight(tmpWeight);
            nodes[i].setIndex(i);
            nodeQueue.add(nodes[i]);
        }

        //创建哈弗曼树
        for (int i = leavesNodeNum; i < totalNodeNum; i++) {
            HuffmanNode leftNode = nodeQueue.remove();
            HuffmanNode rightNode = nodeQueue.remove();
            nodes[i] = new HuffmanNode();
            nodes[i].setIndex(i);
            nodes[i].setLeftNode(leftNode.getIndex());
            nodes[i].setRightNode(rightNode.getIndex());
            nodes[i].setWeight(leftNode.getWeight() + rightNode.getWeight());
            leftNode.setParentNode(i);
            rightNode.setParentNode(i);
            nodeQueue.add(nodes[i]);
        }

        int fileLen = oi.readInt(), writeLen = 0, tmpIndex = totalNodeNum - 1;
        FileOutputStream fo = new FileOutputStream(outputFile);

        //解压，写文件
        while (writeLen != fileLen) {
//        while (true) {
            tmpByte = oi.readByte();
            // 一位一位编码看
            for (int i = 0; i < 8; i++) {
                // 1000 0000 与，若第一位为0则为左孩子
                if ((tmpByte & 128) == 0) {
                    tmpIndex = nodes[tmpIndex].getLeftNode();
                } else {
                    tmpIndex = nodes[tmpIndex].getRightNode();
                }

                //在叶子节点索引范围内
                if (tmpIndex < leavesNodeNum) {
                    fo.write(nodes[tmpIndex].getC());
                    if (++writeLen == fileLen) {
                        break;
                    }
                    //继续从根节点开始
                    tmpIndex = totalNodeNum - 1;
                }
                tmpByte <<= 1;
            }
        }
        fi.close();
        oi.close();
    }

    public static void main(String[] args) throws IOException {
        File file1 = new File("G:\\IdeaProjects\\algorithms\\src\\main\\java\\assignment\\assignment3_greedy\\Aesop_Fables.txt");
        File file1Huf = new File("G:\\IdeaProjects\\algorithms\\src\\main\\java\\assignment\\assignment3_greedy\\Aesop_Fables.huf");
        compress(file1, file1Huf);
        deCompress(file1Huf, new File("G:\\IdeaProjects\\algorithms\\src\\main\\java\\assignment\\assignment3_greedy\\Aesop_Fables-huf.txt"));

        File file2 = new File("G:\\IdeaProjects\\algorithms\\src\\main\\java\\assignment\\assignment3_greedy\\graph.txt");
        File file2Huf = new File("G:\\IdeaProjects\\algorithms\\src\\main\\java\\assignment\\assignment3_greedy\\graph.huf");
        compress(file2, file2Huf);
        deCompress(file2Huf, new File("G:\\IdeaProjects\\algorithms\\src\\main\\java\\assignment\\assignment3_greedy\\graph-huf.txt"));

        System.out.printf("Aesop_Fables compress ratio:%s;\n graph compress ratio:%s",
                (float)file1Huf.length() / file1.length(),(float)file2Huf.length()/file2.length());
    }
}

class HuffmanNode implements Comparable<HuffmanNode> {

    private byte c;
    private int weight;
    private int parentNode = -1, leftNode = -1, rightNode = -1;
    private int index;
    private StringBuilder code;


    public void setC(byte c) {
        this.c = c;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setParentNode(int parentNode) {
        this.parentNode = parentNode;
    }

    public void setLeftNode(int leftNode) {
        this.leftNode = leftNode;
    }

    public void setRightNode(int rightNode) {
        this.rightNode = rightNode;
    }

    public void setCode(StringBuilder code) {
        this.code = code;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public byte getC() {
        return c;
    }

    public int getWeight() {
        return weight;
    }

    public int getParentNode() {
        return parentNode;
    }

    public int getLeftNode() {
        return leftNode;
    }

    public int getRightNode() {
        return rightNode;
    }

    public int getIndex() {
        return index;
    }

    public StringBuilder getCode() {
        return code;
    }

    @Override
    public int compareTo(HuffmanNode huffmanNode) {
        if (weight > huffmanNode.weight) {
            return 1;
        } else if (weight == huffmanNode.weight) {
            return 0;
        }
        return -1;
    }

    @Override
    public String toString() {
        return String.format("\n char:%s; weight:%s; index:%s; leftChild:%s; rightChild:%s; code:%s", (char) c, weight, index, leftNode, rightNode, code);
    }
}

class HuffmanTree extends PriorityQueue<HuffmanNode> {
    @Override
    public boolean add(HuffmanNode huffmanNode) {
        return super.add(huffmanNode);
    }
}