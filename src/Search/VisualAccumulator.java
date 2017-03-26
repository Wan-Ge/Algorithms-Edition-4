package Search;

import edu.princeton.cs.algs4.StdDraw;

/**
 * A visual accumulator to view the cost of a search algorithm
 *
 * we draw the image by call "addDataValue" method in a loop
 *
 */

public class VisualAccumulator {
    private double total;
    private int N;
    private double interval;
    private double x_postion;

    /**
     *  provide a general way of initialization
     *  i always set trials(100), max(2000), interval(0.05)
     *
     * @param trials the length of x-axis
     * @param max the length of y-axis
     * @param interval  the interval between two points
     */
    public VisualAccumulator(int trials, double max, double interval) {
        StdDraw.setXscale(0, trials);
        StdDraw.setYscale(0, max);
        StdDraw.setPenRadius(.005);
        this.interval = interval;
    }

    public void addDataValue(double val) {
        N++;
        x_postion += interval;
        total += val;
        StdDraw.setPenColor(StdDraw.DARK_GRAY);
        StdDraw.point(x_postion, val);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.point(x_postion, total/N);
    }

    /**
     *
     * @return  average of all the data that have been added
     */
    public double mean() {return total / N;}

    @Override
    public String toString() {
        return "Mean (" + N + " values): " + String.format("%7.5f", mean());
    }
}
