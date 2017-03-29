package Graphs;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Breadth-first search to find paths in a graph
 *
 * Single-source shortest paths
 *
 * Created by WanGe on 2017/3/29.
 */
public class BreadthFirstPaths {
    private boolean[] marked;  //Is a shortest path to this vertex known?
    private int[] edgeTo;
    private final int s;       //source

    public BreadthFirstPaths(Graph G, int s) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        bfs(G, s);
    }

    private void bfs(Graph G, int s) {
        ArrayList<Integer> queue = new ArrayList<>(); // You can use your own queue
        marked[s] = true;        // Mark the source
        queue.add(s);            // put it on the queue
        while (!queue.isEmpty()) {
            int v = queue.remove(0);        // Remove next vertex from the queue
            for (int w : G.adj(v)) {              // For every unmarked adjacent vertex
                if (!marked[w]) {
                    edgeTo[w] = v;                // save last edge on a shortest path
                    marked[w] = true;             // mark it because path is known
                    queue.add(w);                 // add it to the queue
                }
            }
        }
    }

    public boolean hasPathTo(int v) {return marked[v];}
    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v))  return null;
        Stack<Integer> path = new Stack<>();
        for (int x = v; x != s; x = edgeTo[x])
            path.push(x);
        path.push(s);
        return path;
    }
}
