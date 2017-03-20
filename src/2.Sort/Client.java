import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by WanGe on 2017/3/17.
 */
public class Client extends Sort{

    public static void unitTest() {
        //Just for test and draw one array
        DrawTrack t = new DrawTrack(){};
        int N = 40;
        Double[] a = new Double[N];
        for (int i = 0; i < N; i++)
            a[i] = StdRandom.uniform(2.0, 50.0);
        heapSort(a);                 //Modify the sort method that you want to see
        assert isSorted(a);
        show(a);
    }

    public static void comparisonTest() {
        SortCompare t = new SortCompare();
        double t1 = t.timeRandomInput("quickSort", 1000, 100000);
        System.out.println("quickSort: " + t1);

        double t2 = t.timeRandomInput("mergeSortBU", 1000, 100000);
        System.out.println("mergeSortBU: " + t2);

        System.out.printf("%.1f times faster than t1\n", t2/t1);
    }

    public static void main(String[] args) {
        unitTest();
        //comparisonTest();
    }
}
