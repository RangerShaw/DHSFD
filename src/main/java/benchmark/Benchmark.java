package benchmark;

public class Benchmark {

    public static void main(String[] args) {
        TestCase testCase = new TestCase();

        int dataset = 8;
        int[] datasets = new int[]{0, 3, 5, 6, 7, 8, 9};


        //testCase.testInsert(dataset);
        //testCase.testRemove(dataset);
        //testCase.testDiff(dataset);

        //testCase.testMMCS(dataset);
        //testCase.testBHMMCS(dataset);

        testCase.exp6(dataset);
//        for (int d : datasets)
//            testCase.exp6(d);
    }

}
