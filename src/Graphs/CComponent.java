package Graphs;

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;

/**
 * The full name of this class is ConnectedComponent.
 *
 * One of depth-first's application is to find the connected components of a graph.
 *
 * Created by WanGe on 2017/3/29.
 */
public class CComponent {
    private boolean[] marked;
    private int[] id;
    private int count;

    public CComponent(Graph G) {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        for (int s = 0; s < G.V(); s++)
            if (!marked[s]) {
                dfs(G, s);
                count++;
            }
    }

    private void dfs(Graph G, int v) {
        marked[v] = true;
        id[v] = count;
        for (int w : G.adj(v))
            if (!marked[w])
                dfs(G, w);
    }

    public boolean connected(int v, int w) {return id[v] == id[w];}

    public int id(int v) {return id[v];}

    public int getCount() {return count;}

    /**
     * Test client for connected components API
     * @param args tinyG.txt
     */
    public static void main(String[] args) {
        Graph G = new Graph(new In(args[0]));
        CComponent cc = new CComponent(G);

        int M = cc.getCount();
        System.out.println(M + " components");

        ArrayList<Integer>[] components;
        components = (ArrayList<Integer>[]) new ArrayList[M];
        for (int i = 0; i < M; i++)
            components[i] = new ArrayList<Integer>();
        for (int v = 0; v < G.V(); v++)
            components[cc.id(v)].add(v);
        for (int i = 0; i < M; i++) {
            for (int v : components[i])
                System.out.print(v + " ");
            System.out.println();
        }
    }
}
