package Graphs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

/**
 * Depth-first search vertex ordering in a digraph
 *
 * - 1. Preorder
 * - 2. Postorder
 * - 3. Reverse postorder
 *
 * Created by WanGe on 2017/3/31.
 */
public class DepthFirstOrder {
    private boolean[] marked;
    private int[] pre;                           // pre[v] = preorder number of v
    private int[] post;                          // post[v] = postorder number of v
    private ArrayList<Integer> preorder;         // vertices in preorder
    private ArrayList<Integer> postorder;        // vertices in postorder
    //private Stack<Integer> reversePost;          // vertices in reverse postorder
    private int preCounter;                      // counter or preorder numbering
    private int postCounter;                     // counter or postorder numbering

    public DepthFirstOrder(Digraph G) {
        pre = new int[G.V()];
        post = new int[G.V()];
        preorder = new ArrayList<>();
        postorder = new ArrayList<>();
        //reversePost = new Stack<>();
        marked = new boolean[G.V()];

        for (int v = 0; v <G.V(); v++)
            if (!marked[v]) dfs(G, v);
    }

    /**
     * Determines a depth-first order for the edge-weighted digraph {@code G}.
     * @param G the edge-weighted digraph
     */
    public DepthFirstOrder(EdgeWeightedDigraph G) {
        pre    = new int[G.V()];
        post   = new int[G.V()];
        postorder = new ArrayList<>();
        preorder  = new ArrayList<>();
        marked    = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++)
            if (!marked[v]) dfs(G, v);
    }

    private void dfs(Digraph G, int v) {
        marked[v] = true;
        pre[v] = preCounter++;
        preorder.add(v);
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                dfs(G, w);
            }
        }
        postorder.add(v);
        post[v] = postCounter++;
    }

    // run DFS in edge-weighted digraph G from vertex v and compute preorder/postorder
    private void dfs(EdgeWeightedDigraph G, int v) {
        marked[v] = true;
        pre[v] = preCounter++;
        preorder.add(v);
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if (!marked[w]) {
                dfs(G, w);
            }
        }
        postorder.add(v);
        post[v] = postCounter++;
    }

    /**
     *  1. preorder is order of dfs() calls
     *  2. postorder is order in which vertices are done
     * @return Iterable
     */
    public Iterable<Integer> pre() {return preorder;}
    public Iterable<Integer> post() {return postorder;}

    public Iterable<Integer> reversePost() {
        LinkedList<Integer> reverse = new LinkedList<>();
        for (int v : postorder)
            reverse.push(v);
        return reverse;
    }
}
