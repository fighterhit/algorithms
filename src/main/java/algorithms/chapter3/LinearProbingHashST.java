package algorithms.chapter3;

/**
 * @auther Fighter Created on 2018/3/23.
 */
public class LinearProbingHashST<Key, Value> {
    private int N;
    private int M = 16;
    private Key[] keys;
    private Value[] vals;

    public LinearProbingHashST() {
        keys = (Key[]) new Object[M];
        vals = (Value[]) new Object[M];
    }

    public LinearProbingHashST(int M) {
        this.M = M;
        init(M);
    }

    private void init(int M) {
        keys = (Key[]) new Object[M];
        vals = (Value[]) new Object[M];
    }

    private void resize(int n) {
        LinearProbingHashST<Key, Value> t = new LinearProbingHashST<>(n);
        for (int i = 0; i < M; i++) {
            if (keys[i] != null) {
                t.put(keys[i], vals[i]);
            }
        }
        keys = t.keys;
        vals = t.vals;
        M = t.M;

    }

    private void put(Key key, Value val) {
        if (N >= M / 2) {
            resize(2 * M);
        }
        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % M) {
            if (keys[i].equals(key)) {
                vals[i] = val;
            }
        }
        keys[i] = key;
        vals[i] = val;
        N++;
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    private Value get(Key key) {
        for (int i = hash(key); keys[i] != null; i = (i + 1) % M) {
            if (keys[i].equals(key)) {
                return vals[i];
            }
        }
        return null;
    }

    private void delete(Key key) {
        if (!contains(key)) {
            return;
        }
        int i;
        for (i = hash(key); !key.equals(keys[i]); i = (i + 1) % M) {
        }

        keys[i] = null;
        vals[i] = null;

        i = (i + 1) % M;
        while (keys[i] != null) {
            Key tmpKey = keys[i];
            Value tmpVal = vals[i];
            keys[i] = null;
            vals[i] = null;
            N--;
            put(tmpKey, tmpVal);
            i = (i + 1) % M;
        }
        N--;
        if (N >= 0 && N == M / 8) {
            resize(M / 2);
        }
    }

    private boolean contains(Key key) {
        return true;
    }

}
