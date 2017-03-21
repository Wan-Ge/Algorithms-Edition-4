package Search;

import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by WanGe on 2017/3/21.
 */
public class Client {
    public static void unitTest() {
        //Just for test and draw one array
        int N = 40;
        Double[] a = new Double[N];
        /*for (int i = 0; i < N; i++)
            a[i] = StdRandom.uniform(2.0, 50.0);*/

        double randNum = StdRandom.uniform(2.0, 10.0);
        double pickNum = StdRandom.uniform(0, N-1);
        double key = -1;
        for (int i = 0; i < N; i++) {
            a[i] =  randNum + i;
            if (pickNum == i)
                key = a[i];
        }

        Search test = new Search(a, N-1);        //Modify the search method that you want to see
        double res = test.BinarySearchIt(key);
        assert res!=key;
        System.out.println("key: " + key);
        System.out.println("result code: " + res);

        Search.show(a);
    }

    public static void main(String[] args) {
        unitTest();
    }
}
