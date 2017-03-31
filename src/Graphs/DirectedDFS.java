package Graphs;

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Stack;

/**
 * - 1. Reachability in digraphs
 * - 2. Is a given digraph a DAG (Directed acyclic graph)
 * - 3. All-pairs reachability
 *
 * Created by WanGe on 2017/3/29.
 */
public class DirectedDFS {
    private boolean[] marked;
    private boolean[] cycle_marked;
    private int[] edgeTo;
    private Stack<Integer> cycle;     // For hasCycle(), vertices on a cycle (if one exists)
    private boolean[] onStack;        // vertices on recursive call stack
    private Digraph G;
    private DirectedDFS[] all;        // For transitiveClosure()

    public DirectedDFS(Digraph G, int s) {
        this.G = G;
        marked = new boolean[G.V()];
        dfs(G, s);
    }

    public DirectedDFS(Digraph G, Iterable<Integer> sources) {
        this.G = G;
        marked = new boolean[G.V()];
        for (int s : sources)
            if (!marked[s]) dfs(G, s);
    }

    private void dfs(Digraph G, int v) {
        marked[v] = true;
        for (int w : G.adj(v))
            if (!marked[w]) dfs(G, w);
    }

    public boolean marked(int v) {return marked[v];}

    /**
     * Judge if G is acyclic. (Digraph)
     *
     * In fact, this method like DepthFirstSearch->hasCycle() method,
     * you can try to only call directedCycle() ont time to reduce cost.
     *
     * @return true if it has cycle.
     */
    public boolean hasCycle() {
        directedCycle(G);
        return cycle != null;
    }

    private void directedCycle(Digraph G) {
        onStack = new boolean[G.V()];
        edgeTo = new int[G.V()];
        cycle_marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++)
            if (!cycle_marked[v])   dfsCycle(G, v);
    }

    private void dfsCycle(Digraph G, int v) {
        onStack[v] = true;
        cycle_marked[v] = true;
        for (int w : G.adj(v)) {
            if (cycle != null)    return;
            else if (!cycle_marked[w]) {edgeTo[w] = v; dfsCycle(G, w);}
            else if (onStack[w]) {
                cycle = new Stack<>();
                for (int x = v; x != w; x = edgeTo[x])
                    cycle.push(x);

                cycle.push(w);
                cycle.push(v);
            }
        }
        onStack[v] = false;
    }

    public Iterable<Integer> cycle() {return cycle;}

    public boolean reachable(int v, int w) {
        if (all == null) {
            transitiveClosure();
        }
        return all[v].marked(w);
    }

    /**
     * The transitive closure of a digraph G is another digraph with the same set of
     * vertices, but with an edge from v to w in the transitive closure if and only if
     * w is reachable from v in G.
     *
     * Since transitive closures are typically dense, instead of explicitly computing
     * the transitive closure, we use DFS to implement it.
     *
     * Essentially, TransitiveClosure computes and stores the transitive closure of G,
     * to support constant-time queries.
     */
    private void transitiveClosure() {
        all = new DirectedDFS[G.V()];
        for (int v = 0; v < G.V(); v++)
            all[v] = new DirectedDFS(G, v);
    }


    /**
     * Test
     *
     * @param args tinyDG.txt 2 6 8 (all the vertices this $args[i] can reach)
     *
     * Sample:   input: java DirectedDFS tinyDG.txt 1
     *            output: 1
     */
    public static void main(String[] args) {
        Digraph G = new Digraph(new In(args[0]));

        ArrayList<Integer> sources = new ArrayList<>();
        for (int i = 1; i < args.length; i++)
            sources.add(Integer.parseInt(args[i]));

        DirectedDFS dbfs = new DirectedDFS(G, sources);

        for (int v = 0; v < G.V(); v++)
            if (dbfs.marked(v)) System.out.print(v + " ");
        System.out.println();

        System.out.println(dbfs.reachable(12, 5));
    }
}
