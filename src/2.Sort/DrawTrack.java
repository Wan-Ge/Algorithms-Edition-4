/****************************************************************************
 *
 *  Use the function provided by algs4 to draw a array
 *
 *  See API of StdDraw for specific parameters
 *
 **************************************************************************/

import edu.princeton.cs.algs4.StdDraw;

/**
 * Created by WanGe on 2017/3/16.
 */
public abstract class DrawTrack {
    public void traceSortTrack(Comparable[] a, int ...args) {
        int N = a.length;
        StdDraw.clear();
        for (int k = 0; k < N; k++) {
            for (int e : args) {
                if (k == e) {
                    StdDraw.setPenColor(StdDraw.RED);
                    break;
                }
                else    StdDraw.setPenColor(StdDraw.GRAY);
            }
            double x = 1.0*k/N;
            double y = (double)a[k]/2000;
            double rw = 0.3/N;
            double rh = (double)a[k]/80;
            StdDraw.filledRectangle(x, y, rw, rh);
        }
        StdDraw.pause(200);
    }
}
