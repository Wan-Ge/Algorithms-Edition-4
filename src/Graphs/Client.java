package Graphs;

import edu.princeton.cs.algs4.In;

/**
 * Ordinary test application
 *
 * Created by WanGe on 2017/3/28.
 */
public class Client {
    public static void main(String[] args) {
        Digraph g = new Digraph(new In(args[0]));
        KosarajuSCC t = new KosarajuSCC(g);
        boolean m = t.stronglyConnected(6, 12);
        System.out.println(m);
    }
}
