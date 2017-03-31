package Graphs;

/**
 * Kosaraju's algorithm of computing strong components
 *
 * 1. Given a digraph G, use DepthFirstOrder to compute the reverse postorder of its reverser,G(R)
 * 2. Run standard DFS on G, but consider the unmarked vertices in the order just computed instead
 *    of the standard numerical order.
 *
 * Created by WanGe on 2017/3/31.
 */
public class KosarajuSCC {
    private boolean[] marked;          // reached vertices
    private int[] id;                  // component identifiers
    private int count;                 // number of strong components

    public KosarajuSCC(Digraph G) {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        // Create G(R).
        DepthFirstOrder order = new DepthFirstOrder(G.reverse());
        // check unmarked vertices in reversePost order
        for (int s : order.reversePost()) {
            if (!marked[s]) {
                dfs(G, s);
                count++;
            }
        }
    }

    /**
     * Determine a connected component (They have the same {@code count} in id[])
     * @param G The original graph
     * @param v next vertex
     */
    private void dfs(Digraph G, int v) {
        marked[v] = true;
        id[v] = count;
        for (int w : G.adj(v))
            if (!marked[w]) dfs(G, w);
    }

    /**
     * If two vertices have the same number in id[], it said that they are both
     * in a common strongly connected.
     *
     * @return true if there exists a path from v to w, and from w to v.
     */
    public boolean stronglyConnected(int v, int w) {return id[v] == id[w];}
    public int id(int v) {return id[v];}
    public int count() {return count;}
}
