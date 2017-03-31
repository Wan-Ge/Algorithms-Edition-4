package Graphs;

/**
 * Topological Sort
 *
 * Created by WanGe on 2017/3/31.
 */
public class Topological {
    private Iterable<Integer> order;      //topological order

    public Topological(Digraph G) {
        DirectedDFS cyclefinder = new DirectedDFS(G, 0);
        if (!cyclefinder.hasCycle()) {
            DepthFirstOrder dfs = new DepthFirstOrder(G);
            order = dfs.reversePost();
        }
    }

    public Iterable<Integer> order() {return order;}

    public boolean isDAG() {return order == null;}

    /**
     * Unit tests the {@code Topological} data type
     * The results here has a little difference from the book, because the {@code Bag}
     * uses the "head insertion",but {@code ArrayList} uses the "tail interpolation".
     * and the results of topological sort is not unique.
     *
     * Dependencies: jobs.txt "/"
     *
     * @param args
     */
    public static void main(String[] args) {
        String filename = args[0];
        String separator = args[1];
        SymbolDigraph sg = new SymbolDigraph(filename, separator);

        Topological top = new Topological(sg.digraph());

        for (int v : top.order())
            System.out.println(sg.nameOf(v));
    }
}
