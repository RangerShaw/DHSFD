package benchmark;

public class Benchmark {

    public static void main(String[] args) {
        TestCase testCase = new TestCase();
        int dataset = 0;

        /* exp1 */
        //testCase.exp1(dataset);
//
//        /* exp 2 */
//        testCase.exp2_R(dataset);           // varying R
//        testCase.exp2_r(dataset);           // varying r
//        testCase.exp2_delta_r(dataset);     // varying delta r
//        testCase.exp2_lambda(dataset);      // varying ratio lambda

        /* exp 3 */
        //testCase.exp3_r(dataset);

        /* exp 4 */
        //testCase.exp4_delta_ri(dataset);


        // available dataset indices for exp6: 0, 3, 5, 6, 7, 9, 10
        testCase.exp6_insert(dataset);
        testCase.exp6_delete(dataset);


//        testCase.testInsert(dataset);
//        testCase.testRemove(dataset);
//        testCase.testDiff(dataset);
//        testCase.testDiffBF1(dataset);
//
//        testCase.testMMCS(dataset);
//        testCase.testBHMMCS(dataset);
//
//        for (int d : datasets)
//            testCase.exp6insert(d);
    }

}
