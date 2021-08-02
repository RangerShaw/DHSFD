package benchmark;

import static benchmark.DataFp.*;

public class Benchmark {

    public static void main(String[] args) {
        TestCase testCase = new TestCase();
        int dataset = 0;                    // one index in different experiments may refer to different datasets

        /* exp 1 */
        testCase.exp1(dataset);

        /* exp 2 */
        testCase.exp2_R(dataset);           // varying R
        testCase.exp2_delta_r(dataset);     // varying delta r
        testCase.exp2_r(dataset);           // varying r
        testCase.exp2_lambda(dataset);      // varying ratio lambda

        /* exp 3 */
        testCase.exp3_r(dataset);

        /* exp 4 */
       testCase.exp4_delta_ri(dataset);
        testCase.exp4_round(dataset);

        /* exp 6 */
        testCase.exp6_insert(dataset);
        testCase.exp6_delete(dataset);


        /* general execution of DHSFD */
        testCase.runDHSFD(BASE_DATA[dataset], RMVD_DATA[dataset], ISRT_DATA[dataset],
                true, BASE_DIFF_OUTPUT[dataset], UPDATED_DIFF_OUTPUT[dataset],
                true, BASE_FD_OUTPUT[dataset], UPDATED_FD_OUTPUT[dataset]);
    }

}
