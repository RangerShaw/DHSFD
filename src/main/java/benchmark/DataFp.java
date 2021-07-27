package benchmark;

/**
 * Data File Path
 */
public class DataFp {

    /*  experiment index
        1   DHSFD VS batch & dynamic methods
        2   scalability
        3   time decomposition
        4   a series of updates
        5   auxiliary structures
        6   DynHS VS MMCS
     */


    /* exp1: DHSFD runtimes */

    static String[][] EXP1_INSERT_BASE_DATA = new String[][]{{
            "dataFiles/exp1/horse/horse_240.csv",
            "dataFiles/exp1/horse/horse_240.csv",
            "dataFiles/exp1/horse/horse_240.csv",
    },
    };

    static String[][] EXP1_INSERT_BASE_DIFF = new String[][]{{
            "dataFiles/exp1/horse/horse_240.txt",
            "dataFiles/exp1/horse/horse_240.txt",
            "dataFiles/exp1/horse/horse_240.txt",
    },
    };

    static String[][] EXP1_INSERT_ISRT_DATA = new String[][]{{
            "dataFiles/exp1/horse/horse_241-256.csv",
            "dataFiles/exp1/horse/horse_241-272.csv",
            "dataFiles/exp1/horse/horse_241-288.csv",
    },
    };

    static String[][] EXP1_REMOVE_RMVD_DATA = new String[][]{{
            "dataFiles/exp1/horse/horse_249-256_del.csv",
            "dataFiles/exp1/horse/horse_257-272_del.csv",
            "dataFiles/exp1/horse/horse_265-288_del.csv",
    }
    };


    /* exp 2: scalability */

    // varying R
    static String[][] EXP2_R_INSERT_BASE_DATA = new String[][]{{
            "dataFiles/exp2/CAB/CAB_34_10000.csv",
            "dataFiles/exp2/CAB/CAB_38_10000.csv",
            "dataFiles/exp2/CAB/CAB_42_10000.csv",
            "dataFiles/exp2/CAB/CAB_46_10000.csv",
            "dataFiles/exp2/CAB/CAB_50_10000.csv",
            "dataFiles/exp2/CAB/CAB_54_10000.csv",
    },
    };

    static String[][] EXP2_R_INSERT_BASE_DIFF = new String[][]{{
            "dataFiles/exp2/CAB/CAB_34_10000.txt",
            "dataFiles/exp2/CAB/CAB_38_10000.txt",
            "dataFiles/exp2/CAB/CAB_42_10000.txt",
            "dataFiles/exp2/CAB/CAB_46_10000.txt",
            "dataFiles/exp2/CAB/CAB_50_10000.txt",
            "dataFiles/exp2/CAB/CAB_54_10000.txt",
    },
    };

    static String[][] EXP2_R_INSERT_ISRT_DATA = new String[][]{{
            "dataFiles/exp2/CAB/CAB_34_10001-11000.csv",
            "dataFiles/exp2/CAB/CAB_38_10001-11000.csv",
            "dataFiles/exp2/CAB/CAB_42_10001-11000.csv",
            "dataFiles/exp2/CAB/CAB_46_10001-11000.csv",
            "dataFiles/exp2/CAB/CAB_50_10001-11000.csv",
            "dataFiles/exp2/CAB/CAB_54_10001-11000.csv",
    },
    };

    static String[][] EXP2_R_REMOVE_RMVD_DATA = new String[][]{{
            "dataFiles/exp2/CAB/CAB_54_10001-11000_del.csv",
            "dataFiles/exp2/CAB/CAB_54_10001-11000_del.csv",
            "dataFiles/exp2/CAB/CAB_54_10001-11000_del.csv",
            "dataFiles/exp2/CAB/CAB_54_10001-11000_del.csv",
            "dataFiles/exp2/CAB/CAB_54_10001-11000_del.csv",
            "dataFiles/exp2/CAB/CAB_54_10001-11000_del.csv",
    }
    };

    // varying r
    static String[][] EXP2_r_INSERT_BASE_DATA = new String[][]{{
            "dataFiles/exp2/CAB/CAB_54_10000.csv",
            "dataFiles/exp2/CAB/CAB_54_11000.csv",
            "dataFiles/exp2/CAB/CAB_54_12000.csv",
            "dataFiles/exp2/CAB/CAB_54_13000.csv",
            "dataFiles/exp2/CAB/CAB_54_14000.csv",
    },
    };

    static String[][] EXP2_r_INSERT_BASE_DIFF = new String[][]{{
            "dataFiles/exp2/CAB/CAB_54_10000.txt",
            "dataFiles/exp2/CAB/CAB_54_11000.txt",
            "dataFiles/exp2/CAB/CAB_54_12000.txt",
            "dataFiles/exp2/CAB/CAB_54_13000.txt",
            "dataFiles/exp2/CAB/CAB_54_14000.txt",
    },
    };

    static String[][] EXP2_r_INSERT_ISRT_DATA = new String[][]{{
            "dataFiles/exp2/CAB/CAB_54_10001-11000.csv",
            "dataFiles/exp2/CAB/CAB_54_11001-12100.csv",
            "dataFiles/exp2/CAB/CAB_54_12001-13200.csv",
            "dataFiles/exp2/CAB/CAB_54_13001-14300.csv",
            "dataFiles/exp2/CAB/CAB_54_14001-15400.csv",
    },
    };

    static String[][] EXP2_r_REMOVE_RMVD_DATA = new String[][]{{
            "dataFiles/exp2/CAB/CAB_54_10001-11000_del.csv",
            "dataFiles/exp2/CAB/CAB_54_11001-12100_del.csv",
            "dataFiles/exp2/CAB/CAB_54_12001-13200_del.csv",
            "dataFiles/exp2/CAB/CAB_54_13001-14300_del.csv",
            "dataFiles/exp2/CAB/CAB_54_14001-15400_del.csv",
    },
    };

    // varying delta r
    static String[][] EXP2_delta_r_INSERT_BASE_DATA = new String[][]{{
            "dataFiles/exp2/CAB/CAB_54_10000.csv",
            "dataFiles/exp2/CAB/CAB_54_10000.csv",
            "dataFiles/exp2/CAB/CAB_54_10000.csv",
            "dataFiles/exp2/CAB/CAB_54_10000.csv",
            "dataFiles/exp2/CAB/CAB_54_10000.csv",
    }
    };

    static String[][] EXP2_delta_r_INSERT_BASE_DIFF = new String[][]{{
            "dataFiles/exp2/CAB/CAB_54_10000.txt",
            "dataFiles/exp2/CAB/CAB_54_10000.txt",
            "dataFiles/exp2/CAB/CAB_54_10000.txt",
            "dataFiles/exp2/CAB/CAB_54_10000.txt",
            "dataFiles/exp2/CAB/CAB_54_10000.txt",
    }
    };

    static String[][] EXP2_delta_r_INSERT_ISRT_DATA = new String[][]{{
            "dataFiles/exp2/CAB/CAB_54_10001-11000.csv",
            "dataFiles/exp2/CAB/CAB_54_10001-12000.csv",
            "dataFiles/exp2/CAB/CAB_54_10001-13000.csv",
            "dataFiles/exp2/CAB/CAB_54_10001-14000.csv",
            "dataFiles/exp2/CAB/CAB_54_10001-15000.csv",
    },
    };

    static String[][] EXP2_delta_r_REMOVE_RMVD_DATA = new String[][]{{
            "dataFiles/exp2/CAB/CAB_54_10001-11000_del.csv",
            "dataFiles/exp2/CAB/CAB_54_10001-12000_del.csv",
            "dataFiles/exp2/CAB/CAB_54_10001-13000_del.csv",
            "dataFiles/exp2/CAB/CAB_54_10001-14000_del.csv",
            "dataFiles/exp2/CAB/CAB_54_10001-15000_del.csv",

    },
    };

    // varying ratio lambda
    static String[][] EXP2_LAMBDA_INSERT_BASE_DATA = new String[][]{{
            "dataFiles/exp2/CAB/CAB_54_10000.csv",
            "dataFiles/exp2/CAB/CAB_54_10000.csv",
            "dataFiles/exp2/CAB/CAB_54_10000.csv",
            "dataFiles/exp2/CAB/CAB_54_10000.csv",
    }
    };

    static String[][] EXP2_LAMBDA_INSERT_BASE_DIFF = new String[][]{{
            "dataFiles/exp2/CAB/CAB_54_10000.txt",
            "dataFiles/exp2/CAB/CAB_54_10000.txt",
            "dataFiles/exp2/CAB/CAB_54_10000.txt",
            "dataFiles/exp2/CAB/CAB_54_10000.txt",
    }
    };

    static String[][] EXP2_LAMBDA_INSERT_ISRT_DATA = new String[][]{{
            "",
            "dataFiles/exp2/CAB/CAB_54_10001-10500.csv",
            "dataFiles/exp2/CAB/CAB_54_10001-11000.csv",
            "dataFiles/exp2/CAB/CAB_54_10001-11500.csv",
            "dataFiles/exp2/CAB/CAB_54_10001-12000.csv",
    },
    };

    static String[][] EXP2_LAMBDA_REMOVE_RMVD_DATA = new String[][]{{
            "dataFiles/exp2/CAB/CAB_54_8001-10000_del.csv",
            "dataFiles/exp2/CAB/CAB_54_9001-10500_del.csv",
            "dataFiles/exp2/CAB/CAB_54_10001-11000_del.csv",
            "dataFiles/exp2/CAB/CAB_54_11001-11500_del.csv",
            "",
    },
    };


    /* exp 3: time decomposition */

    // reuse data files of exp 2
    static String[][] EXP3_INSERT_BASE_DATA = new String[][]{{
            "dataFiles/exp2/CAB/CAB_54_10000.csv",
            "dataFiles/exp2/CAB/CAB_54_11000.csv",
            "dataFiles/exp2/CAB/CAB_54_12000.csv",
            "dataFiles/exp2/CAB/CAB_54_13000.csv",
            "dataFiles/exp2/CAB/CAB_54_14000.csv",
    },
    };

    static String[][] EXP3_INSERT_BASE_DIFF = new String[][]{{
            "dataFiles/exp2/CAB/CAB_54_10000.txt",
            "dataFiles/exp2/CAB/CAB_54_11000.txt",
            "dataFiles/exp2/CAB/CAB_54_12000.txt",
            "dataFiles/exp2/CAB/CAB_54_13000.txt",
            "dataFiles/exp2/CAB/CAB_54_14000.txt",
    },
    };

    static String[][] EXP3_INSERT_ISRT_DATA = new String[][]{{
            "dataFiles/exp2/CAB/CAB_54_10001-11000.csv",
            "dataFiles/exp2/CAB/CAB_54_11001-12100.csv",
            "dataFiles/exp2/CAB/CAB_54_12001-13200.csv",
            "dataFiles/exp2/CAB/CAB_54_13001-14300.csv",
            "dataFiles/exp2/CAB/CAB_54_14001-15400.csv",
    },
    };

    static String[][] EXP3_REMOVE_RMVD_DATA = new String[][]{{
            "dataFiles/exp2/CAB/CAB_54_10001-11000_del.csv",
            "dataFiles/exp2/CAB/CAB_54_11001-12100_del.csv",
            "dataFiles/exp2/CAB/CAB_54_12001-13200_del.csv",
            "dataFiles/exp2/CAB/CAB_54_13001-14300_del.csv",
            "dataFiles/exp2/CAB/CAB_54_14001-15400_del.csv",
    },
    };


    /* exp 4: time decomposition */

    // varying delta r_i
    static String[][] EXP4_r_INSERT_BASE_DATA = new String[][]{{
            "dataFiles/exp4/flights/flights_200000.csv",
            "dataFiles/exp4/flights/flights_200000.csv",
            "dataFiles/exp4/flights/flights_200000.csv",
    },
    };

    static String[][] EXP4_r_INSERT_BASE_DIFF = new String[][]{{
            "dataFiles/exp4/flights/flights_200000.txt",
            "dataFiles/exp4/flights/flights_200000.txt",
            "dataFiles/exp4/flights/flights_200000.txt",
    },
    };

    static String[][][] EXP4_r_INSERT_ISRT_DATA = new String[][][]{{{
            "dataFiles/exp4/flights/flights_200001-208000.csv",
    }, {
            "dataFiles/exp4/flights/flights_200001-204000.csv",
            "dataFiles/exp4/flights/flights_204001-208000.csv",
    }, {
            "dataFiles/exp4/flights/flights_200001-202000.csv",
            "dataFiles/exp4/flights/flights_202001-204000.csv",
            "dataFiles/exp4/flights/flights_204001-206000.csv",
            "dataFiles/exp4/flights/flights_206001-208000.csv",
    }},
    };

    static String[][][] EXP4_r_REMOVE_RMVD_DATA = new String[][][]{{{
            "dataFiles/exp4/flights/flights_204001-208000_del.csv",
    }, {
            "dataFiles/exp4/flights/flights_202001-204000_del.csv",
            "dataFiles/exp4/flights/flights_204001-206000_del.csv",
    }, {
            "dataFiles/exp4/flights/flights_201001-202000_del.csv",
            "dataFiles/exp4/flights/flights_202001-203000_del.csv",
            "dataFiles/exp4/flights/flights_203001-204000_del.csv",
            "dataFiles/exp4/flights/flights_204001-205000_del.csv",
    }},
    };


    /* exp6: DynHS V.S. MMCS */

    static String[] DATASETS = {
            "letter",
    };

    // |R|
    static int[] N_ATTRIBUTES = new int[]{
            17,
    };

    // INSERT
    static String[] EXP6_INSERT_BASE_EDGES = new String[]{
            "dataFiles/exp6/letter/letter_50.txt",
    };

    static String[][] EXP6_INSERT_ISRT_EDGES = new String[][]{{
            "dataFiles/exp6/letter/letter_insert_50-55.txt",
            "dataFiles/exp6/letter/letter_insert_50-60.txt",
            "dataFiles/exp6/letter/letter_insert_50-65.txt",
            "dataFiles/exp6/letter/letter_insert_50-70.txt",
            "dataFiles/exp6/letter/letter_insert_50-75.txt",
            "dataFiles/exp6/letter/letter_insert_50-80.txt",
            "dataFiles/exp6/letter/letter_insert_50-85.txt",
            "dataFiles/exp6/letter/letter_insert_50-90.txt",
            "dataFiles/exp6/letter/letter_insert_50-95.txt",
            "dataFiles/exp6/letter/letter_insert_50-100.txt",
    },
    };

    static String[][] EXP6_INSERT_BATCH_EDGES = new String[][]{{
            "dataFiles/exp6/letter/letter_55.txt",
            "dataFiles/exp6/letter/letter_60.txt",
            "dataFiles/exp6/letter/letter_65.txt",
            "dataFiles/exp6/letter/letter_70.txt",
            "dataFiles/exp6/letter/letter_75.txt",
            "dataFiles/exp6/letter/letter_80.txt",
            "dataFiles/exp6/letter/letter_85.txt",
            "dataFiles/exp6/letter/letter_90.txt",
            "dataFiles/exp6/letter/letter_95.txt",
            "dataFiles/exp6/letter/letter_100.txt",
    },
    };

    // DELETE
    static String[] EXP6_REMOVE_BASE_EDGES = new String[]{
            "dataFiles/exp6/letter/letter_100.txt",
    };

    static String[][] EXP6_REMOVE_RMVD_EDGES = new String[][]{{
            "dataFiles/exp6/letter/letter_removed_95.txt",
            "dataFiles/exp6/letter/letter_removed_90.txt",
            "dataFiles/exp6/letter/letter_removed_85.txt",
            "dataFiles/exp6/letter/letter_removed_80.txt",
            "dataFiles/exp6/letter/letter_removed_75.txt",
            "dataFiles/exp6/letter/letter_removed_70.txt",
            "dataFiles/exp6/letter/letter_removed_65.txt",
    },
    };

    static String[][] EXP6_REMOVE_LEFT_EDGES = new String[][]{{
            "dataFiles/exp6/letter/letter_95.txt",
            "dataFiles/exp6/letter/letter_90.txt",
            "dataFiles/exp6/letter/letter_85.txt",
            "dataFiles/exp6/letter/letter_80.txt",
            "dataFiles/exp6/letter/letter_75.txt",
            "dataFiles/exp6/letter/letter_70.txt",
            "dataFiles/exp6/letter/letter_65.txt",
    },
    };


    /* general execution */

    static String[] BASE_DATA = new String[]{
            "dataFiles/general/letter/letter_15000.csv",
    };

    static String[][] RMVD_DATA = new String[][]{{
            "dataFiles/general/letter/letter_14501-15000_del.csv",
            "dataFiles/general/letter/letter_15001-15500_del.csv",
            "dataFiles/general/letter/letter_15501-16000_del.csv",
    },
    };

    static String[][] ISRT_DATA = new String[][]{{
            "dataFiles/general/letter/letter_15001-16000.csv",
            "dataFiles/general/letter/letter_16001-17000.csv",
            "dataFiles/general/letter/letter_17001-18000.csv",
    },
    };

    static String[] BASE_DIFF_OUTPUT = new String[]{
            "dataFiles/general/letter/output/letter_15000_diff.txt",
    };

    static String[] BASE_FD_OUTPUT = new String[]{
            "dataFiles/general/letter/output/letter_15000_fd.txt",
    };

    static String[][] UPDATED_DIFF_OUTPUT = new String[][]{{
            "dataFiles/general/letter/output/letter_15500_diff.txt",
            "dataFiles/general/letter/output/letter_16000_diff.txt",
            "dataFiles/general/letter/output/letter_16500_diff.txt",
            "dataFiles/general/letter/output/letter_17000_diff.txt",
            "dataFiles/general/letter/output/letter_17500_diff.txt",
    },
    };

    static String[][] UPDATED_FD_OUTPUT = new String[][]{{
            "dataFiles/general/letter/output/letter_15500_fd.txt",
            "dataFiles/general/letter/output/letter_16000_fd.txt",
            "dataFiles/general/letter/output/letter_16500_fd.txt",
            "dataFiles/general/letter/output/letter_17000_fd.txt",
            "dataFiles/general/letter/output/letter_17500_fd.txt",
    }
    };



    /* test INSERT */

    static String[] INSERT_INPUT_BASE_DATA = new String[]{
            //exp1
            "dataFiles/exp1/letter/letter_80.csv",
            "dataFiles/exp1/flights/flights_batch.csv",
            "dataFiles/exp1/fd-re/fd-reduced-30_80.csv",
            //varing R
            "dataFiles/varingR/CAB_54_10000.csv",
            //varing r
            "dataFiles/varing r/CAB_54_14000.csv",
            //varing delta r
            "dataFiles/varingR/CAB_54_10000.csv",
            //varing ratio
            "dataFiles/varingR/CAB_54_10000.csv",
            //varing round
            //6
            "dataFiles/exp1/flights/flights_batch.csv",
    };

    static String[] INSERT_INPUT_BASE_DIFF = new String[]{
            "dataFiles/exp1/letter/letter_80.txt",
            "dataFiles/exp1/flights/flights_batch.txt",
            "dataFiles/exp1/fd-re/fd-reduced-30_80.txt",
            //varing R
            "dataFiles/varingR/CAB_54_10000.txt",
            //varing r
            "dataFiles/varing r/CAB_54_14000.txt",
            //varing delta r
            "dataFiles/varing R/CAB_54_10000.txt",
            //varing ratio
            "dataFiles/varing R/CAB_54_10000.txt",
            //varing round
            //6
            "dataFiles/exp1/flights/flights_batch.txt",

    };

    static String[][] INSERT_INPUT_NEW_DATA = new String[][]{
            {
                    "dataFiles/exp1/letter/letter_batch70.csv",
                    "dataFiles/exp1/letter/letter_batch80.csv",
                    "dataFiles/exp1/letter/letter_batch90.csv",
            },
            {
                    "dataFiles/exp1/flights/flights_batch70.csv",
                    "dataFiles/exp1/flights/flights_batch80.csv",
                    "dataFiles/exp1/flights/flights_batch90.csv"
            },
            {
                    "dataFiles/exp1/fd-re/fd-reduced-30_batch70.csv",
                    "dataFiles/exp1/fd-re/fd-reduced-30_batch80.csv",
                    "dataFiles/exp1/fd-re/fd-reduced-30_batch90.csv",
            },
            {
                    "dataFiles/varingR/CAB_54_1000.csv",
            },
            {
                    "dataFiles/varing r/CAB_54_14001-15000.csv",

            },
            {
                    "dataFiles/varing delta r/CAB_54_10001-11000.csv",
                    "dataFiles/varing delta r/CAB_54_11001-12000.csv",
                    "dataFiles/varing delta r/CAB_54_12001-13000.csv",
                    "dataFiles/varing delta r/CAB_54_13001-14000.csv",
                    "dataFiles/varing delta r/CAB_54_14001-15000.csv",
            },
            {
                    "dataFiles/varing ratio/2000+0.csv",
            },
            {
                    "dataFiles/varing round/6/flights_0.csv",
                    "dataFiles/varing round/6/flights_1.csv",
                    "dataFiles/varing round/6/flights_2.csv",
                    "dataFiles/varing round/6/flights_3.csv",
                    "dataFiles/varing round/6/flights_4.csv",
                    "dataFiles/varing round/6/flights_5.csv",
            },

    };


    /* test REMOVE */

    static String[] REMOVE_INPUT_BASE_DATA = new String[]{
            //exp1
            "dataFiles/exp1/letter/letter_100.csv",
            "dataFiles/exp1/flights/flights_250000.csv",
            "dataFiles/exp1/fd-re/fd-reduced-30_100.csv",
            //varing R
            "dataFiles/varingR/CAB_54_11000.csv",
            //varing r
            "dataFiles/varing r/CAB_54_15000.csv",
            //varing delta r
            "dataFiles/varing r/CAB_54_15000.csv",
            //varing ratio
            "dataFiles/varing r/CAB_54_12000.csv",
            //varing round
            //6
            "dataFiles/varing round/flights_batch+7992.csv",
    };

    static String[] REMOVE_INPUT_BASE_DIFF = new String[]{
            "dataFiles/exp1/letter/letter_100.txt",
            "dataFiles/exp1/flights/flights_250000.txt",
            "dataFiles/exp1/fd-re/fd-reduced-30_100.txt",
            //exp2
            "dataFiles/varingR/CAB_54_11000.txt",
            //exp3
            "dataFiles/varing r/CAB_54_15000.txt",
            //varing delta r
            "dataFiles/varing r/CAB_54_15000.txt",
            //varing ratio
            "dataFiles/varing r/CAB_54_12000.txt",
            //varing round
            //6
            "dataFiles/varing round/flights_batch+7992.txt",
    };

    static String[][] REMOVE_INPUT_DELETED_DATA = new String[][]{
            {
                    "dataFiles/exp1/letter/letterdel_1.csv",
                    "dataFiles/exp1/letter/letterdel_2.csv",
            },
            {
                    "dataFiles/exp1/flights/flights_250000del_1.csv",
                    "dataFiles/exp1/flights/flights_250000del_2.csv",
                    "dataFiles/exp1/flights/flights_250000del_3.csv",
            },
            {
                    "dataFiles/exp1/fd-re/fd-reduced-30del_1.csv",
                    "dataFiles/exp1/fd-re/fd-reduced-30del_2.csv",
                    "dataFiles/exp1/fd-re/fd-reduced-30del_3.csv",
            },
            {
                    "dataFiles/varingR/10001-11000_del.csv",
            },
            {
                    "dataFiles/varing r/CAB_54_14001-15000_del.csv",
            },
            {
                    "dataFiles/varing delta r/CAB_54_14001-15000_del.csv",
                    "dataFiles/varing delta r/CAB_54_13001-14000_del.csv",
                    "dataFiles/varing delta r/CAB_54_12001-13000_del.csv",
                    "dataFiles/varing delta r/CAB_54_11001-12000_del.csv",
                    "dataFiles/varing delta r/CAB_54_10001-11000_del.csv",

            },
            {
                    "dataFiles/varing ratio/0+2000_del.csv",
            },
            {
                    "dataFiles/varing round/6/182325-182993.csv",
                    "dataFiles/varing round/6/181657-182324.csv",
                    "dataFiles/varing round/6/180989-181656.csv",
                    "dataFiles/varing round/6/180321-180988.csv",
                    "dataFiles/varing round/6/179653-180320.csv",
                    "dataFiles/varing round/6/178985-179652.csv",
            }
    };


    /* test Diff */

    static String[][] DIFF_INPUT_DATA = new String[][]{
            {
                    "dataFiles/letter/diff/letter_15000.csv",
                    "dataFiles/letter/diff/letter_16000.csv",
                    "dataFiles/letter/diff/letter_17000.csv",
                    "dataFiles/letter/diff/letter_18000.csv",
                    "dataFiles/letter/diff/letter_19000.csv",
                    "dataFiles/letter/diff/letter_20000.csv",
            },
            {
                    "dataFiles/reduced/diff/fd-reduced_200000.csv",
                    "dataFiles/reduced/diff/fd-reduced_210000.csv",
                    "dataFiles/reduced/diff/fd-reduced_220000.csv",
                    "dataFiles/reduced/diff/fd-reduced_230000.csv",
                    "dataFiles/reduced/diff/fd-reduced_240000.csv",
                    "dataFiles/reduced/diff/fd-reduced_250000.csv",
            },
            {
                    "dataFiles/echo/diff/echocardiogram_70.csv",
                    "dataFiles/echo/diff/echocardiogram_80.csv",
                    "dataFiles/echo/diff/echocardiogram_90.csv",
            },
            {
                    //"dataFiles/pitches/diff/2019_pitches_241666.csv",
                    "dataFiles/pitches/diff/2019_pitches_250000.csv"
            },
            {
                    "dataFiles/hepatitis/diff/hepatitis_71.csv",
                    "dataFiles/hepatitis/diff/hepatitis_154.csv",
                    "dataFiles/hepatitis/diff/hepatitis_70.csv",
                    "dataFiles/hepatitis/diff/hepatitis_72.csv",
            },
            {
                    "dataFiles/cab/diff/CAB_14000.csv",
                    "dataFiles/cab/diff/CAB_13000.csv",
                    "dataFiles/cab/diff/CAB_12000.csv",
                    "dataFiles/cab/diff/CAB_11000.csv",
            }
    };

    static String[][] DIFF_OUTPUT_DIFF = new String[][]{
            {
                    "dataFiles/letter/diff/letter_DS_15000.csv",
                    "dataFiles/letter/diff/letter_DS_16000.csv",
                    "dataFiles/letter/diff/letter_DS_17000.csv",
                    "dataFiles/letter/diff/letter_DS_18000.csv",
                    "dataFiles/letter/diff/letter_DS_19000.csv",
                    "dataFiles/letter/diff/letter_DS_20000.csv",
            },
            {
                    "dataFiles/reduced/diff/fd-reduced_DS_200000.csv",
                    "dataFiles/reduced/diff/fd-reduced_DS_210000.csv",
                    "dataFiles/reduced/diff/fd-reduced_DS_220000.csv",
                    "dataFiles/reduced/diff/fd-reduced_DS_230000.csv",
                    "dataFiles/reduced/diff/fd-reduced_DS_240000.csv",
                    "dataFiles/reduced/diff/fd-reduced_DS_250000.csv",
            },
            {
                    "dataFiles/echo/diff/echocardiogram_DS_70.csv",
                    "dataFiles/echo/diff/echocardiogram_DS_80.csv",
                    "dataFiles/echo/diff/echocardiogram_DS_90.csv",
            },
            {
                    //"dataFiles/pitches/diff/2019_pitches_DS_241666.csv",
                    "dataFiles/pitches/diff/2019_pitches_DS_250000.csv"
            },
            {
                    "dataFiles/hepatitis/diff/hepatitis_DS_71.csv",
                    "dataFiles/hepatitis/diff/hepatitis_DS_154.csv",
                    "dataFiles/hepatitis/diff/hepatitis_70.csv",
                    "dataFiles/hepatitis/diff/hepatitis_72.csv",
            },
            {
                    "dataFiles/cab/diff/CAB_DS_14000.csv",
                    "dataFiles/cab/diff/CAB_DS_13000.csv",
                    "dataFiles/cab/diff/CAB_DS_12000.csv",
                    "dataFiles/cab/diff/CAB_DS_11000.csv",
            }

    };

}
