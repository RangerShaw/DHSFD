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

        /* exp 2 */
        int dataset = 0;
        testCase.exp2_R(dataset);           //varying R
        testCase.exp2_r(dataset);           //varying r
        testCase.exp2_delta_r(dataset);     //varying delta r
        testCase.exp2_lambda(dataset);      //varying ratio lambda


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
