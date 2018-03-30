package algorithms.chapter3;

import edu.princeton.cs.algs4.SequentialSearchST;

/**
 * @auther Fighter Created on 2018/3/23.
 */
public class SeparateChainHashST<Key, Value> {
    //键值对总数
    private int N;
    private int M;
    private SequentialSearchST<Key, Value>[] st;

    public SeparateChainHashST() {
        this(997);
    }

    public SeparateChainHashST(int M) {
        this.M = M;
        st = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[M];
        for (int i = 0; i < M; i++) {
            st[i] = new SequentialSearchST<>();
        }
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    private Value get(Key key){
        return st[hash(key)].get(key);
    }

    private void put(Key key,Value val){
        st[hash(key)].put(key,val);
    }
}
