package Graphs;

import java.util.ArrayList;
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

    private ArrayList<Integer> pre;         // vertices in preorder
    private ArrayList<Integer> post;        // vertices in postorder
    private Stack<Integer> reversePost;     // vertices in reverse postorder
    private Iterable<Integer> order;        // topological order

    public DepthFirstOrder(Digraph G) {
        pre = new ArrayList<>();
        post = new ArrayList<>();
        reversePost = new Stack<>();
        marked = new boolean[G.V()];

        for (int v = 0; v <G.V(); v++)
            if (!marked[v]) dfs(G, v);
    }

    private void dfs(Digraph G, int v) {
        pre.add(v);

        marked[v] = true;
        for (int w : G.adj(v))
            if (!marked[w]) dfs(G, w);

        post.add(v);
        reversePost.push(v);
    }

    /**
     *  1. preorder is order of dfs() calls
     *  2. postorder is order in which vertices are done
     * @return Iterable
     */
    public Iterable<Integer> pre() {return pre;}
    public Iterable<Integer> post() {return post;}
    public Iterable<Integer> reversePost() {return reversePost;}
}
