package algorithms.chapter3;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdIn;

import java.io.File;

/**
 * @auther Fighter Created on 2018/3/24.
 */
public class FileIndex {
    public static void main(String[] args) {
        ST<String, SET<File>> st = new ST<>();
        for (String filename : args) {
            File file = new File(filename);
            In in = new In();
            while (!in.isEmpty()) {
                String word = in.readString();
                if (!st.contains(word)) {
                    st.put(word, new SET<File>());
                }
                SET<File> set = st.get(word);
                set.add(file);
            }
        }
        while (!StdIn.isEmpty()) {
            String query = StdIn.readString();
            if (st.contains(query)) {
                for (File file : st.get(query)) {
                    System.out.println("   " + file.getName());
                }
            }
        }
    }
}
