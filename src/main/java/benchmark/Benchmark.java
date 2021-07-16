package benchmark;

public class Benchmark {

    public static void main(String[] args) {
        TestCase testCase = new TestCase();

        int dataset = 0;
        int[] datasets = new int[]{0, 3, 5, 6, 7, 8, 9, 10};


        testCase.testInsert(dataset);
        testCase.testRemove(dataset);
        //testCase.testDiff(dataset);

        //testCase.testMMCS(dataset);
        //testCase.testBHMMCS(dataset);

        //testCase.exp6insert(dataset);
//        testCase.exp6remove(dataset);
//        for (int d : datasets)
//            testCase.exp6insert(d);
    }

}
