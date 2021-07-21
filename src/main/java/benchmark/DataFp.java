package benchmark;

/**
 * Data File Path
 */
public class DataFp {

    /*  experiment index
        0   N/A
        1
        2
        3
        4
        5
        6   DynHS V.S. MMCS
     */

    /*  dataset index
        0   letter
        1   fdr
        2   echo
        3   pitches
        4   hepatitis
        5   cab
        6   flights
        7   haemoglobin
        8   horse
        9   accident
        10  census
        11  balance
        12  iris
        13  claim
        14  bridges
        15  ncv
        16  plista
    */

    static String[] DATASETS = {
            "letter",
            "fdr",
            "echo",
            "pitches",
            "hepatitis",
            "cab",
            "flights",
            "haemoglobin",
            "horse",
            "accident",
            "census",
            "balance",
            "iris",
            "claim",
            "bridges",
            "ncv",
            "plista",
    };

    static int[] N_ATTRIBUTES = new int[]{
            17,
            30,
            13,
            40,
            20,
            54,
            31,
            34,
            28,
            47,
            42,
            5,
            5,
            11,
            13,
            19,
            63,
    };


    /* exp6: DynHS V.S. MMCS */

    static int[] exp6Datasets = {0, 3, 5, 6, 7, 9, 10};

    public static void genExp6FilePath() {
        int N = DATASETS.length;

        INSERT_BASE_EDGES = new String[N];
        INSERT_BATCH_EDGES = new String[N][10];
        INSERT_ISRT_EDGES = new String[N][10];
        REMOVE_BASE_EDGES = new String[N];

        for (int i = 0; i < N; i++) {
            String prefix = "dataFiles/exp6/" + DATASETS[i] + "/" + DATASETS[i];

            INSERT_BASE_EDGES[i] = prefix + "_50.txt";
            for (int j = 1; j <= 10; j++) {
                INSERT_ISRT_EDGES[i][j - 1] = prefix + "_insert_50-" + (50 + j * 5) + ".txt";
                INSERT_BATCH_EDGES[i][j - 1] = prefix + "_" + (50 + j * 5) + ".txt";
            }

            REMOVE_BASE_EDGES[i] = prefix + "_100.txt";
        }
    }

    /* INSERT */
    static String[] INSERT_BASE_EDGES = new String[]{
            "dataFiles/exp6/letter/letter_50.txt",
            "dataFiles/exp6/fdr/fdr_50.txt",
            "dataFiles/exp6/echo/echo_50.txt",
            "dataFiles/exp6/pitches/pitches_50.txt",
            "dataFiles/exp6/fdr/fdr_50.txt",
            "dataFiles/exp6/cab/cab_50.txt",
            "dataFiles/exp6/flights/flights_50.txt",
            "dataFiles/exp6/Haemoglobin/Haemoglobin_50.txt",
            "dataFiles/exp6/horse/horse_50.txt",
            "dataFiles/exp6/accident/accident_50.txt",
            "dataFiles/exp6/census/census_50.txt",
            "dataFiles/exp6/balance/_50.txt",
            "dataFiles/exp6/iris/iris_50.txt",
            "dataFiles/exp6/claim/claim_50.txt",
            "dataFiles/exp6/bridges/bridges_50.txt",
            "dataFiles/exp6/ncv/ncv_50.txt",
            "dataFiles/exp6/plista/plista_50.txt",
    };

    static String[][] INSERT_ISRT_EDGES = new String[][]{{
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

    static String[][] INSERT_BATCH_EDGES = new String[][]{{
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

    /* DELETE */
    static String[] REMOVE_BASE_EDGES = new String[]{
            "dataFiles/exp6/letter/letter_100.txt",
    };

    static String[][] REMOVE_RMVD_EDGES = new String[][]{{
            "dataFiles/exp6/letter/letter_removed_95.txt",
            "dataFiles/exp6/letter/letter_removed_90.txt",
            "dataFiles/exp6/letter/letter_removed_85.txt",
            "dataFiles/exp6/letter/letter_removed_80.txt",
            "dataFiles/exp6/letter/letter_removed_75.txt",
            "dataFiles/exp6/letter/letter_removed_70.txt",
            "dataFiles/exp6/letter/letter_removed_65.txt",
    }, {
    }, {
    }, {
            "dataFiles/exp6/pitches/pitches_removed_95.txt",
            "dataFiles/exp6/pitches/pitches_removed_90.txt",
            "dataFiles/exp6/pitches/pitches_removed_85.txt",
            "dataFiles/exp6/pitches/pitches_removed_80.txt",
            "dataFiles/exp6/pitches/pitches_removed_75.txt",
    }, {
    }, {
            "dataFiles/exp6/cab/cab_removed_95.txt",
            "dataFiles/exp6/cab/cab_removed_90.txt",
            "dataFiles/exp6/cab/cab_removed_85.txt",
            "dataFiles/exp6/cab/cab_removed_80.txt",
            "dataFiles/exp6/cab/cab_removed_77.txt",
            "dataFiles/exp6/cab/cab_removed_70.txt",
            "dataFiles/exp6/cab/cab_removed_65.txt",
            "dataFiles/exp6/cab/cab_removed_60.txt",
    }, {
            "dataFiles/exp6/flights/flights_removed_95.txt",
            "dataFiles/exp6/flights/flights_removed_90.txt",
            "dataFiles/exp6/flights/flights_removed_85.txt",
            "dataFiles/exp6/flights/flights_removed_80.txt",
    }, {
            "dataFiles/exp6/haemoglobin/haemoglobin_removed_95.txt",
            "dataFiles/exp6/haemoglobin/haemoglobin_removed_90.txt",
            "dataFiles/exp6/haemoglobin/haemoglobin_removed_85.txt",
            "dataFiles/exp6/haemoglobin/haemoglobin_removed_80.txt",
            "dataFiles/exp6/haemoglobin/haemoglobin_removed_75.txt",
            "dataFiles/exp6/haemoglobin/haemoglobin_removed_70.txt",
            "dataFiles/exp6/haemoglobin/haemoglobin_removed_65.txt",
            "dataFiles/exp6/haemoglobin/haemoglobin_removed_60.txt",
            "dataFiles/exp6/haemoglobin/haemoglobin_removed_55.txt",
            "dataFiles/exp6/haemoglobin/haemoglobin_removed_50.txt",
            "dataFiles/exp6/haemoglobin/haemoglobin_removed_45.txt",
            "dataFiles/exp6/haemoglobin/haemoglobin_removed_40.txt",
            "dataFiles/exp6/haemoglobin/haemoglobin_removed_35.txt",
    }, {
    }, {
            "dataFiles/exp6/accident/accident_removed_95.txt",
            "dataFiles/exp6/accident/accident_removed_90.txt",
            "dataFiles/exp6/accident/accident_removed_85.txt",
            "dataFiles/exp6/accident/accident_removed_80.txt",
            "dataFiles/exp6/accident/accident_removed_75.txt",
    }, {
            "dataFiles/exp6/census/census_removed_95.txt",
            "dataFiles/exp6/census/census_removed_90.txt",
            "dataFiles/exp6/census/census_removed_85.txt",
            "dataFiles/exp6/census/census_removed_80.txt",
            "dataFiles/exp6/census/census_removed_75.txt",
    },
    };

    static String[][] REMOVE_LEFT_EDGES = new String[][]{{
            "dataFiles/exp6/letter/letter_95.txt",
            "dataFiles/exp6/letter/letter_90.txt",
            "dataFiles/exp6/letter/letter_85.txt",
            "dataFiles/exp6/letter/letter_80.txt",
            "dataFiles/exp6/letter/letter_75.txt",
            "dataFiles/exp6/letter/letter_70.txt",
            "dataFiles/exp6/letter/letter_65.txt",
    }, {
    }, {
    }, {
            "dataFiles/exp6/pitches/pitches_95.txt",
            "dataFiles/exp6/pitches/pitches_90.txt",
            "dataFiles/exp6/pitches/pitches_85.txt",
            "dataFiles/exp6/pitches/pitches_80.txt",
            "dataFiles/exp6/pitches/pitches_75.txt",
    }, {
    }, {
            "dataFiles/exp6/cab/cab_95.txt",
            "dataFiles/exp6/cab/cab_90.txt",
            "dataFiles/exp6/cab/cab_85.txt",
            "dataFiles/exp6/cab/cab_80.txt",
            "dataFiles/exp6/cab/cab_77.txt",
            "dataFiles/exp6/cab/cab_70.txt",
            "dataFiles/exp6/cab/cab_65.txt",
            "dataFiles/exp6/cab/cab_60.txt",
    }, {
            "dataFiles/exp6/flights/flights_95.txt",
            "dataFiles/exp6/flights/flights_90.txt",
            "dataFiles/exp6/flights/flights_85.txt",
            "dataFiles/exp6/flights/flights_80.txt",
    }, {
            "dataFiles/exp6/haemoglobin/haemoglobin_95.txt",
            "dataFiles/exp6/haemoglobin/haemoglobin_90.txt",
            "dataFiles/exp6/haemoglobin/haemoglobin_85.txt",
            "dataFiles/exp6/haemoglobin/haemoglobin_80.txt",
            "dataFiles/exp6/haemoglobin/haemoglobin_75.txt",
            "dataFiles/exp6/haemoglobin/haemoglobin_70.txt",
            "dataFiles/exp6/haemoglobin/haemoglobin_65.txt",
            "dataFiles/exp6/haemoglobin/haemoglobin_60.txt",
            "dataFiles/exp6/haemoglobin/haemoglobin_55.txt",
            "dataFiles/exp6/haemoglobin/haemoglobin_50.txt",
            "dataFiles/exp6/haemoglobin/haemoglobin_45.txt",
            "dataFiles/exp6/haemoglobin/haemoglobin_40.txt",
            "dataFiles/exp6/haemoglobin/haemoglobin_35.txt",
    }, {
    }, {
            "dataFiles/exp6/accident/accident_95.txt",
            "dataFiles/exp6/accident/accident_90.txt",
            "dataFiles/exp6/accident/accident_85.txt",
            "dataFiles/exp6/accident/accident_80.txt",
            "dataFiles/exp6/accident/accident_75.txt",
    }, {
            "dataFiles/exp6/census/census_95.txt",
            "dataFiles/exp6/census/census_90.txt",
            "dataFiles/exp6/census/census_85.txt",
            "dataFiles/exp6/census/census_80.txt",
            "dataFiles/exp6/census/census_75.txt",
    },
    };


    /* test INSERT */

    static String[] INSERT_INPUT_BASE_DATA = new String[]{
            "dataFiles/letter/insert/letter_15000.csv",
            "dataFiles/reduced/insert/fd-reduced_200000.csv",
            "dataFiles/echo/insert/echocardiogram_70.csv",
            "",
            "dataFiles/hepatitis/insert/hepatitis_71.csv",
            "dataFiles/cab/insert/CAB_14000.csv",
            "",
            "dataFiles/heam/insert/Haemoglobin.csv",
            "dataFiles/exp6/horse/horse.csv"
    };

    static String[] INSERT_INPUT_BASE_DIFF = new String[]{
            "dataFiles/letter/insert/letter_DS_15000.txt",
            "dataFiles/reduced/insert/fd-reduced_DS_200000.txt",
            "dataFiles/echo/insert/echocardiogram_DS_70.txt",
            "",
            "dataFiles/hepatitis/insert/hepatitis_DS_71.txt",
            "dataFiles/cab/insert/CAB_DS_14000.txt",
            "",
            "dataFiles/heam/insert/Haemoglobin_DS_100.txt",
            "dataFiles/exp6/horse/_100_.txt"
    };

    static String[] INSERT_OUTPUT_BASE_FD = new String[]{
            "dataFiles/letter/insert/letter_FD_15000.txt",
            "dataFiles/reduced/insert/fd-reduced_FD_200000.csv",
            "dataFiles/echo/insert/echocardiogram_FD_70.csv",
    };

    static String[][] INSERT_INPUT_NEW_DATA = new String[][]{{
            "dataFiles/letter/insert/letter_15000-16000.csv",
            "dataFiles/letter/insert/letter_16000-17000.csv",
            "dataFiles/letter/insert/letter_17000-18000.csv",
            "dataFiles/letter/insert/letter_18000-19000.csv",
            "dataFiles/letter/insert/letter_19000-20000.csv"
    }, {
            "dataFiles/reduced/insert/fd-reduced_200000-210000.csv",
            "dataFiles/reduced/insert/fd-reduced_210000-220000.csv",
            "dataFiles/reduced/insert/fd-reduced_220000-230000.csv",
            "dataFiles/reduced/insert/fd-reduced_230000-240000.csv",
            "dataFiles/reduced/insert/fd-reduced_240000-250000.csv",
    }, {
            "dataFiles/echo/insert/echocardiogram_70-80.csv",
            "dataFiles/echo/insert/echocardiogram_80-90.csv",
    }, {

    }, {

    }, {
//            "dataFiles/cab/insert/CAB_54_10001-11000.csv",
//            "dataFiles/cab/insert/CAB_54_11001-12000.csv",
//            "dataFiles/cab/insert/CAB_54_12001-13000.csv",
//            "dataFiles/cab/insert/CAB_54_13001-14000.csv",
//            "dataFiles/cab/insert/CAB_54_14001-15000.csv",
    }, {

    }, {
            //"dataFiles/heam/insert/Haemoglobin_batch70.csv",
            //"dataFiles/heam/insert/Haemoglobin_batch80.csv",
            //"dataFiles/heam/insert/Haemoglobin_batch90.csv",
    }
    };

    static String[][] INSERT_OUTPUT_CURR_DIFF = new String[][]{{
            "dataFiles/letter/insert/letter_DS_16000.txt",
            "dataFiles/letter/insert/letter_DS_17000.txt",
            "dataFiles/letter/insert/letter_DS_18000.txt",
            "dataFiles/letter/insert/letter_DS_19000.txt",
            "dataFiles/letter/insert/letter_DS_20000.txt"
    }, {
            "dataFiles/reduced/insert/fd-reduced_DS_210000.csv",
            "dataFiles/reduced/insert/fd-reduced_DS_220000.csv",
            "dataFiles/reduced/insert/fd-reduced_DS_230000.csv",
            "dataFiles/reduced/insert/fd-reduced_DS_240000.csv",
            "dataFiles/reduced/insert/fd-reduced_DS_250000.csv",
    }, {

    }, {

    }, {

    }, {
            "dataFiles/cab/insert/CAB_DS-11000.csv",
            "dataFiles/cab/insert/CAB_DS-12000.csv",
            "dataFiles/cab/insert/CAB_DS-13000.csv",
            "dataFiles/cab/insert/CAB_DS-14000.csv",
            "dataFiles/cab/insert/CAB_DS-15000.csv",
    }, {

    }, {
            "dataFiles/heam/insert/Haemoglobin_DS_80.txt",
            "dataFiles/heam/insert/Haemoglobin_DS_90.txt",
            "dataFiles/heam/insert/Haemoglobin_DS_100.txt",
    }
    };

    static String[][] INSERT_OUTPUT_CURR_FD = new String[][]{{
            "dataFiles/letter/insert/letter_FD_16000.txt",
            "dataFiles/letter/insert/letter_FD_17000.txt",
            "dataFiles/letter/insert/letter_FD_18000.txt",
            "dataFiles/letter/insert/letter_FD_19000.txt",
            "dataFiles/letter/insert/letter_FD_20000.txt"
    }, {
            "dataFiles/reduced/insert/fd-reduced_FD_210000.csv",
            "dataFiles/reduced/insert/fd-reduced_FD_220000.csv",
            "dataFiles/reduced/insert/fd-reduced_FD_230000.csv",
            "dataFiles/reduced/insert/fd-reduced_FD_240000.csv",
            "dataFiles/reduced/insert/fd-reduced_FD_250000.csv",
    }, {
            "dataFiles/echo/insert/echocardiogram_FD_80.csv",
            "dataFiles/echo/insert/echocardiogram_FD_90.csv",
    }, {

    }, {

    }, {
//                    "dataFiles/cab/insert/CAB_FD-11000.csv",
//                    "dataFiles/cab/insert/CAB_FD-12000.csv",
//                    "dataFiles/cab/insert/CAB_FD-13000.csv",
//                    "dataFiles/cab/insert/CAB_FD-14000.csv",
//                    "dataFiles/cab/insert/CAB_FD-15000.csv",
    }
    };



    /* test REMOVE */

    static String[] REMOVE_INPUT_BASE_DATA = new String[]{
            "dataFiles/letter/remove/letter_20000.csv",
            "dataFiles/reduced/remove/fd-reduced_250000.csv",
            "",
            "dataFiles/pitches/remove/2019_pitches_250000.csv",
            "dataFiles/hepatitis/remove/hepatitis_154.csv",
            "dataFiles/cab/remove/CAB_15000.csv",
    };

    static String[] REMOVE_INPUT_BASE_DIFF = new String[]{
            "dataFiles/letter/remove/letter_DS_20000.txt",
            "dataFiles/reduced/remove/fd-reduced_DS_250000.csv",
            "",
            "dataFiles/pitches/remove/2019_pitches_DS_250000.txt",
            "dataFiles/hepatitis/remove/hepatitis_DS_154.txt",
            "dataFiles/cab/remove/CAB_DS_15000.csv",
    };

    static String[] REMOVE_OUTPUT_BASE_FD = new String[]{
            "dataFiles/letter/remove/letter_FD_20000.txt",
            "dataFiles/reduced/remove/fd-reduced_FD_250000.csv",
    };

    static String[][] REMOVE_INPUT_DELETED_DATA = new String[][]{{
            "dataFiles/letter/remove/letter_19000-19999.csv",
            "dataFiles/letter/remove/letter_18000-18999.csv",
            "dataFiles/letter/remove/letter_17000-17999.csv",
            "dataFiles/letter/remove/letter_16000-16999.csv",
            "dataFiles/letter/remove/letter_15000-15999.csv",
    }, {
            "dataFiles/reduced/remove/fd-reduced_249999-240000.csv",
            "dataFiles/reduced/remove/fd-reduced_239999-230000.csv",
            "dataFiles/reduced/remove/fd-reduced_229999-220000.csv",
            "dataFiles/reduced/remove/fd-reduced_219999-210000.csv",
            "dataFiles/reduced/remove/fd-reduced_209999-200000.csv",
    }, {

    }, {
            "dataFiles/pitches/remove/2019_pitchesdel_241667-249999.csv",
    }, {
            "dataFiles/hepatitis/remove/hepatities_72-154.csv",
    }, {
            "dataFiles/cab/remove/CAB_del_14000-14999.csv",
            "dataFiles/cab/remove/CAB_del_13000-13999.csv",
            "dataFiles/cab/remove/CAB_del_12000-12999.csv",
            "dataFiles/cab/remove/CAB_del_11000-11999.csv",
            "dataFiles/cab/remove/CAB_del_10000-10999.csv",
    }
    };

    static String[][] REMOVE_OUTPUT_CURR_DIFF = new String[][]{{
            "dataFiles/letter/remove/letter_DS_16000.txt",
            "dataFiles/letter/remove/letter_DS_17000.txt",
            "dataFiles/letter/remove/letter_DS_18000.txt",
            "dataFiles/letter/remove/letter_DS_19000.txt",
            "dataFiles/letter/remove/letter_DS_20000.txt"
    }, {
            "dataFiles/reduced/remove/fd-reduced_DS_210000.csv",
            "dataFiles/reduced/remove/fd-reduced_DS_220000.csv",
            "dataFiles/reduced/remove/fd-reduced_DS_230000.csv",
            "dataFiles/reduced/remove/fd-reduced_DS_240000.csv",
            "dataFiles/reduced/remove/fd-reduced_DS_250000.csv",
    }, {

    }, {
            "dataFiles/pitches/remove/2019_pitches_DS_241666.csv"
    }, {

    }, {
            "dataFiles/cab/remove/CAB_DS_14000.csv",
            "dataFiles/cab/remove/CAB_DS_13000.csv",
            "dataFiles/cab/remove/CAB_DS_12000.csv",
            "dataFiles/cab/remove/CAB_DS_11000.csv",
            "dataFiles/cab/remove/CAB_DS_10000.csv",
    }
    };


    static String[][] REMOVE_OUTPUT_DELETED_FD = new String[][]{{
            "dataFiles/letter/remove/letter_FD_19000.txt",
            "dataFiles/letter/remove/letter_FD_18000.txt",
            "dataFiles/letter/remove/letter_FD_17000.txt",
            "dataFiles/letter/remove/letter_FD_16000.txt",
            "dataFiles/letter/remove/letter_FD_15000.txt",
    }, {
            "dataFiles/reduced/remove/fd-reduced_FD_240000.csv",
            "dataFiles/reduced/remove/fd-reduced_FD_230000.csv",
            "dataFiles/reduced/remove/fd-reduced_FD_220000.csv",
            "dataFiles/reduced/remove/fd-reduced_FD_210000.csv",
            "dataFiles/reduced/remove/fd-reduced_FD_200000.csv",
    }, {

    }, {

    }, {

    }, {
            "dataFiles/cab/remove/CAB_FD_14000.csv",
            "dataFiles/cab/remove/CAB_FD_13000.csv",
            "dataFiles/cab/remove/CAB_FD_12000.csv",
            "dataFiles/cab/remove/CAB_FD_11000.csv",
            "dataFiles/cab/remove/CAB_FD_10000.csv",
    },
    };

    /* test Diff */

    static String[][] DIFF_INPUT_DATA = new String[][]{{
            "dataFiles/letter/diff/letter_15000.csv",
            "dataFiles/letter/diff/letter_16000.csv",
            "dataFiles/letter/diff/letter_17000.csv",
            "dataFiles/letter/diff/letter_18000.csv",
            "dataFiles/letter/diff/letter_19000.csv",
            "dataFiles/letter/diff/letter_20000.csv",
    }, {
            "dataFiles/reduced/diff/fd-reduced_200000.csv",
            "dataFiles/reduced/diff/fd-reduced_210000.csv",
            "dataFiles/reduced/diff/fd-reduced_220000.csv",
            "dataFiles/reduced/diff/fd-reduced_230000.csv",
            "dataFiles/reduced/diff/fd-reduced_240000.csv",
            "dataFiles/reduced/diff/fd-reduced_250000.csv",
    }, {
            "dataFiles/echo/diff/echocardiogram_70.csv",
            "dataFiles/echo/diff/echocardiogram_80.csv",
            "dataFiles/echo/diff/echocardiogram_90.csv",
    }, {
            //"dataFiles/pitches/diff/2019_pitches_241666.csv",
            "dataFiles/pitches/diff/2019_pitches_250000.csv"
    }, {
            "dataFiles/hepatitis/diff/hepatitis_71.csv",
            "dataFiles/hepatitis/diff/hepatitis_154.csv",
            "dataFiles/hepatitis/diff/hepatitis_70.csv",
            "dataFiles/hepatitis/diff/hepatitis_72.csv",
    }, {
            "dataFiles/cab/diff/CAB_14000.csv",
            "dataFiles/cab/diff/CAB_13000.csv",
            "dataFiles/cab/diff/CAB_12000.csv",
            "dataFiles/cab/diff/CAB_11000.csv",
    }, {

    }, {
            "dataFiles/heam/diff/Haemoglobin.csv",
            //"dataFiles/heam/diff/Haemoglobin_90.csv"
    }
    };

    static String[][] DIFF_OUTPUT_DIFF = new String[][]{{
            "dataFiles/letter/diff/letter_DS_15000.txt",
            "dataFiles/letter/diff/letter_DS_16000.txt",
            "dataFiles/letter/diff/letter_DS_17000.txt",
            "dataFiles/letter/diff/letter_DS_18000.txt",
            "dataFiles/letter/diff/letter_DS_19000.txt",
            "dataFiles/letter/diff/letter_DS_20000.txt",
    }, {
            "dataFiles/reduced/diff/fd-reduced_DS_200000.txt",
            "dataFiles/reduced/diff/fd-reduced_DS_210000.txt",
            "dataFiles/reduced/diff/fd-reduced_DS_220000.txt",
            "dataFiles/reduced/diff/fd-reduced_DS_230000.txt",
            "dataFiles/reduced/diff/fd-reduced_DS_240000.txt",
            "dataFiles/reduced/diff/fd-reduced_DS_250000.txt",
    }, {
            "dataFiles/echo/diff/echocardiogram_DS_70.txt",
            "dataFiles/echo/diff/echocardiogram_DS_80.txt",
            "dataFiles/echo/diff/echocardiogram_DS_90.txt",
    }, {
            //"dataFiles/pitches/diff/2019_pitches_DS_241666.txt",
            "dataFiles/pitches/diff/2019_pitches_DS_250000.txt"
    }, {
            "dataFiles/hepatitis/diff/hepatitis_DS_71.txt",
            "dataFiles/hepatitis/diff/hepatitis_DS_154.txt",
            "dataFiles/hepatitis/diff/hepatitis_70.txt",
            "dataFiles/hepatitis/diff/hepatitis_72.txt",
    }, {
            "dataFiles/cab/diff/CAB_DS_14000.txt",
            "dataFiles/cab/diff/CAB_DS_13000.txt",
            "dataFiles/cab/diff/CAB_DS_12000.txt",
            "dataFiles/cab/diff/CAB_DS_11000.txt",
    }, {

    }, {
            "dataFiles/heam/diff/Haemoglobin_DS_100_BF.txt",
            //"dataFiles/heam/diff/Haemoglobin_DS_90.txt"
    }

    };


}
