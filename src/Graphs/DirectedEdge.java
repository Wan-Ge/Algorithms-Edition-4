package Graphs;

/**
 * The {@code DirectedEdge} class represents a weighted edge in an {@link EdgeWeightedDigraph}.
 * Each edge consists of two integers (naming the two vertices) and a real-value weight.
 *
 * Created by WanGe on 2017/4/1.
 */
public class DirectedEdge {
    private final int v;          // edge source
    private final int w;          // edge target
    private final double weight;  // edge weight

    public DirectedEdge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public double getWeight() {return weight;}

    public int from() {return v;}
    public int to() {return w;}
    public double weight() {return weight;}

    public String toString() {
        return v + "->" + w + " " + String.format("%5.2f", weight);
    }
}
