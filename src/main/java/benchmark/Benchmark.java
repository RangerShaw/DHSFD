package benchmark;

public class Benchmark {

    public static void main(String[] args) {
        TestCase testCase = new TestCase();

        // available dataset indices for exp6: 0, 3, 5, 6, 7, 9, 10
        int dataset = 0;
        DataFp.genExp6FilePath();
        testCase.exp6Insert(dataset);
        testCase.exp6Remove(dataset);




//        testCase.testInsert(dataset);
//        testCase.testRemove(dataset);
        //testCase.testDiff(dataset);
        //testCase.testDiffBF1(dataset);

        //testCase.testMMCS(dataset);
        //testCase.testBHMMCS(dataset);

//        for (int d : datasets)
//            testCase.exp6insert(d);
    }

}
