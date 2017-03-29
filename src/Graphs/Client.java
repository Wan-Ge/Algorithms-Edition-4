package Graphs;

import edu.princeton.cs.algs4.In;

/**
 * Ordinary test application
 *
 * Created by WanGe on 2017/3/28.
 */
public class Client {
    public static void main(String[] args) {
        Graph g = new Graph(new In(args[0]));
        DepathFirstSearch t = new DepathFirstSearch(g, 0);
        System.out.println(t.isBipartite());
    }
}
