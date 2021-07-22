package benchmark;

public class Benchmark {

    public static void main(String[] args) {
        TestCase testCase = new TestCase();

        // available dataset indices for exp6: 0, 3, 5, 6, 7, 9, 10
//        int dataset = 0;
//        DataFp.genExp6FilePath();
//        testCase.exp6Insert(dataset);
//        testCase.exp6Remove(dataset);

        //  exp1
        for (int i = 0; i < 1; ++i) {
            testCase.testInsert(i);
            testCase.testRemove(i);
        }
        /**
         * varing R
         */
        for (int i = 3; i < 4; ++i) {
            //testCase.testInsert(i);
            testCase.testRemove(i);
        }
        /**
         * varing r
         */
        for (int i = 4; i < 5; ++i) {
            testCase.testInsert(i);
            testCase.testRemove(i);
        }
        /**
         * varing delta r
         */
        for (int i = 5; i < 6; ++i) {
            testCase.testInsert(i);
            testCase.testRemove(i);
        }
        /**
         * varing ratio
         */
        for (int i = 6; i < 7; ++i) {
            testCase.testInsert(i);
            testCase.testRemove(i);
        }

        /**
         * varing round
         */
        for (int i = 7; i < 8; ++i) {
            testCase.testInsert(i);
            testCase.testRemove(i);
        }


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
