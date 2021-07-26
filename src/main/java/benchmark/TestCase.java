package benchmark;

import algorithm.differenceSet.DiffConnector;
import algorithm.hittingSet.DynHS.DynHS;
import algorithm.hittingSet.DynHS.DynHSConnector;
import algorithm.hittingSet.MMCS.Mmcs;
import com.koloboke.collect.map.hash.HashLongLongMap;
import com.koloboke.collect.map.hash.HashLongLongMaps;
import me.tongfei.progressbar.ProgressBar;
import util.DataIO;
import util.Utils;

import java.util.*;
import java.util.stream.IntStream;

import static benchmark.DataFp.*;
import static util.Utils.genEdgeRhs;

public class TestCase {

    static class RuntimeResult {
        double diffTime;
        double hsTime;
        double totalTime;

        public RuntimeResult(double diffTime, double hsTime) {
            this.diffTime = diffTime;
            this.hsTime = hsTime;
            this.totalTime = diffTime + hsTime;
        }

        public RuntimeResult(double totalTime) {
            this.diffTime = 0;
            this.hsTime = 0;
            this.totalTime = totalTime;
        }

        public double getTotalTime() {
            return totalTime;
        }

        public static RuntimeResult sum(List<RuntimeResult> results) {
            RuntimeResult res = new RuntimeResult(0);
            for (RuntimeResult result : results) {
                res.diffTime += result.diffTime;
                res.hsTime += result.hsTime;
                res.totalTime += result.totalTime;
            }
            return res;
        }
    }


    DiffConnector initiateDiff(String baseDataFp, String baseDiffFp) {
        // load base data
        System.out.println("  [INITIALIZING]...");
        List<List<String>> csvData = DataIO.readCsvFile(baseDataFp);

        // initiate pli and differenceSet
        DiffConnector diffConnector = new DiffConnector();
        List<Long> initDiffSets = diffConnector.generatePliAndDiff(csvData, baseDiffFp);
        System.out.println("    # of initial Diff: " + initDiffSets.size());

        return diffConnector;
    }

    DynHSConnector initiateFd(int nElements, List<Long> initDiffSets) {
        // initiate FD
        DynHSConnector fdConnector = new DynHSConnector();
        fdConnector.initiate(nElements, initDiffSets);
        System.out.println("    # of initial FD: " + fdConnector.getMinFDs().stream().map(List::size).reduce(0, Integer::sum));

        return fdConnector;
    }

    RuntimeResult insertThenDelete(DiffConnector diffConnector, DynHSConnector fdConnector, String isrtDataFp, String rmvdDataFp) {
        List<List<String>> insertData = isrtDataFp == null || isrtDataFp.isEmpty() ? new ArrayList<>() : DataIO.readCsvFile(isrtDataFp);
        List<Integer> removedData = rmvdDataFp == null || rmvdDataFp.isEmpty() ? new ArrayList<>() : DataIO.readRemoveFile(rmvdDataFp);

        System.out.println("  [INSERT THEN DELETE]...");
        double diffTime = 0.0, hsTime = 0.0;

        if (!insertData.isEmpty()) {        // insert
            long startTime = System.nanoTime();
            List<Long> newDiffs = diffConnector.insertData(insertData);
            long midTime = System.nanoTime();
            fdConnector.insertSubsets(newDiffs);
            long endTime = System.nanoTime();

            diffTime += (double) (midTime - startTime) / 1000000;
            hsTime += (double) (endTime - midTime) / 1000000;
        }

        if (!removedData.isEmpty()) {       // delete
            long startTime = System.nanoTime();
            Set<Long> removedDiffs = diffConnector.removeData(removedData);
            List<Long> leftDiffs = diffConnector.getDiffSet();
            long midTime = System.nanoTime();
            fdConnector.removeSubsets(leftDiffs, removedDiffs);
            long endTime = System.nanoTime();

            diffTime += (double) (midTime - startTime) / 1000000;
            hsTime += (double) (endTime - midTime) / 1000000;
        }

        return new RuntimeResult(diffTime, hsTime);
    }


    RuntimeResult insertThenDelete(String baseDataFp, String baseDiffFp, String isrtDataFp, String rmvdDataFp) {
        DiffConnector diffConnector = initiateDiff(baseDataFp, baseDiffFp);
        DynHSConnector fdConnector = initiateFd(diffConnector.nElements, diffConnector.getDiffSet());
        return insertThenDelete(diffConnector, fdConnector, isrtDataFp, rmvdDataFp);
    }

    /**
     * initiate multiple times. for each initiation, insert and delete once
     */
    List<RuntimeResult> insertThenDelete(String[] baseDataFp, String[] baseDiffFp, String[] isrtDataFp, String[] rmvdDataFp) {
        assert baseDataFp.length == baseDiffFp.length && baseDataFp.length == isrtDataFp.length && baseDataFp.length == rmvdDataFp.length;

        //insertThenDelete(baseDataFp[0], baseDiffFp[0], isrtDataFp[0], rmvdDataFp[0]);   // preheat

        List<RuntimeResult> results = new ArrayList<>();
        //for (int i = 0; i < baseDataFp.length; i++) {
            for (int i = 0; i < 1; i++) {
            RuntimeResult res = insertThenDelete(baseDataFp[i], baseDiffFp[i], isrtDataFp[i], rmvdDataFp[i]);
            results.add(res);
        }
        return results;
    }

    /**
     * initiate once. insert and delete multiple times
     */
    RuntimeResult insertThenDelete(String baseDataFp, String baseDiffFp, String[] isrtDataFp, String[] rmvdDataFp) {
        assert isrtDataFp.length == rmvdDataFp.length;

        insertThenDelete(baseDataFp, baseDiffFp, isrtDataFp[0], rmvdDataFp[0]);         // preheat

        DiffConnector diffConnector = initiateDiff(baseDataFp, baseDiffFp);
        DynHSConnector fdConnector = initiateFd(diffConnector.nElements, diffConnector.getDiffSet());

        List<RuntimeResult> results = new ArrayList<>();
        for (int i = 0; i < isrtDataFp.length; i++) {
            RuntimeResult res = insertThenDelete(diffConnector, fdConnector, isrtDataFp[i], rmvdDataFp[i]);
            results.add(res);
        }
        return RuntimeResult.sum(results);
    }

    void printTotalTimes(List<RuntimeResult> results, String[] dataFiles) {
        System.out.println("  No.\tTime(ms)\t\tKey File");
        for (int i = 0; i < results.size(); i++)
            System.out.printf("  %d\t%12.3f\t%s\n", i, results.get(i).totalTime, dataFiles[i]);
    }

    void printDiffHsTimes(List<RuntimeResult> results, String[] dataFiles) {
        System.out.println("  No.\tDiff Time(ms)\tHS Time(ms)\t\tKey File");
        for (int i = 0; i < results.size(); i++)
            System.out.printf("  %d\t%12.3f\t%12.3f\t\t%s\n", i, results.get(i).diffTime, results.get(i).hsTime, dataFiles[i]);
    }

    public void exp1(int d) {
        System.out.println("\n[EXP 1] DHSFD Runtime");
        List<RuntimeResult> results = insertThenDelete(EXP1_INSERT_BASE_DATA[d], EXP1_INSERT_BASE_DIFF[d], EXP1_INSERT_ISRT_DATA[d], EXP1_REMOVE_RMVD_DATA[d]);
        printTotalTimes(results, EXP1_INSERT_ISRT_DATA[d]);
    }

    public void exp2_R(int d) {
        System.out.println("\n[EXP 2] Varying R");
        List<RuntimeResult> results = insertThenDelete(EXP2_R_INSERT_BASE_DATA[d], EXP2_R_INSERT_BASE_DIFF[d], EXP2_R_INSERT_ISRT_DATA[d], EXP2_R_REMOVE_RMVD_DATA[d]);
        printTotalTimes(results, EXP2_R_INSERT_BASE_DATA[d]);
    }

    public void exp2_delta_r(int d) {
        System.out.println("\n[EXP 2] Varying delta r");
        List<RuntimeResult> results = insertThenDelete(EXP2_delta_r_INSERT_BASE_DATA[d], EXP2_delta_r_INSERT_BASE_DIFF[d], EXP2_delta_r_INSERT_ISRT_DATA[d], EXP2_delta_r_REMOVE_RMVD_DATA[d]);
        printTotalTimes(results, EXP2_delta_r_INSERT_ISRT_DATA[d]);
    }

    public void exp2_r(int d) {
        System.out.println("\n[EXP 2] Varying r");
        List<RuntimeResult> results = insertThenDelete(EXP2_r_INSERT_BASE_DATA[d], EXP2_r_INSERT_BASE_DIFF[d], EXP2_r_INSERT_ISRT_DATA[d], EXP2_r_REMOVE_RMVD_DATA[d]);
        printTotalTimes(results, EXP2_r_INSERT_BASE_DATA[d]);
    }

    public void exp2_lambda(int d) {
        System.out.println("\n[EXP 2] Varying Ratio Lambda");
        List<RuntimeResult> results = insertThenDelete(EXP2_LAMBDA_INSERT_BASE_DATA[d], EXP2_LAMBDA_INSERT_BASE_DIFF[d], EXP2_LAMBDA_INSERT_ISRT_DATA[d], EXP2_LAMBDA_REMOVE_RMVD_DATA[d]);
        printTotalTimes(results, EXP2_LAMBDA_INSERT_ISRT_DATA[d]);
    }

    public void exp3_r(int d) {
        System.out.println("\n[EXP 3] Time Decomposition");
        List<RuntimeResult> results = insertThenDelete(EXP3_INSERT_BASE_DATA[d], EXP3_INSERT_BASE_DIFF[d], EXP3_INSERT_ISRT_DATA[d], EXP3_REMOVE_RMVD_DATA[d]);
        printDiffHsTimes(results, EXP3_INSERT_BASE_DATA[d]);
    }

    public void exp4_delta_ri(int d) {
        System.out.println("\n[EXP 4] Varying delta r_i");
        List<RuntimeResult> results = new ArrayList<>();
        for (int i = 0; i < EXP4_r_INSERT_ISRT_DATA.length; i++) {
            RuntimeResult res = insertThenDelete(EXP4_r_INSERT_BASE_DATA[d][i], EXP4_r_INSERT_BASE_DIFF[d][i], EXP4_r_INSERT_ISRT_DATA[d][i], EXP4_r_REMOVE_RMVD_DATA[d][i]);
            results.add(res);
        }

        System.out.println("  No.\tTime(ms)\tdelta r\t\tData File");
        int[] delta_r_i = new int[]{12, 6, 3, 2, 1};
        for (int i = 0; i < results.size(); i++)
            System.out.printf("  %d\t%12.3f\t%d\t\t%s\n", i, results.get(i).getTotalTime(), delta_r_i[i], EXP4_r_INSERT_ISRT_DATA[d][i][0]);
    }

    public void exp4_round(int d) {
        System.out.println("\n[EXP 4] Multiple round");
        System.out.println("  Results of exp4_round can be obtained from exp4_delta_ri.");
    }

    public void exp6_insert(int d) {
        System.out.println("\n[EXP 6] DynHS VS MMCS: insert");
        int N = N_ATTRIBUTES[d];

        System.out.println("===============================DynHS==============================");
        List<Long> baseEdges = DataIO.readDiffMapToList(N, EXP6_INSERT_BASE_EDGES[d]);
        Utils.sortLongSets(N, baseEdges);

        insertDynHS(N, baseEdges, EXP6_INSERT_ISRT_EDGES[d][0]);                                // preheat

        List<RuntimeResult> results1 = new ArrayList<>();
        for (int i = 0; i < EXP6_INSERT_ISRT_EDGES[d].length; i++) {
            RuntimeResult res = insertDynHS(N, baseEdges, EXP6_INSERT_ISRT_EDGES[d][i]);
            results1.add(res);
        }
        printTotalTimes(results1, EXP6_INSERT_ISRT_EDGES[d]);

        System.out.println("\n===============================MMCS==============================");
        List<RuntimeResult> results2 = runMMCS(N, EXP6_INSERT_BATCH_EDGES[d]);
        printTotalTimes(results2, EXP6_INSERT_BATCH_EDGES[d]);
    }

    public void exp6_delete(int d) {
        System.out.println("\n[EXP 6] DynHS VS MMCS: delete");
        int N = N_ATTRIBUTES[d];

        System.out.println("===============================DynHS==============================");
        List<Long> baseEdges = DataIO.readDiffMapToList(N, EXP6_REMOVE_BASE_EDGES[d]);
        Utils.sortLongSets(N, baseEdges);

        removeDynHS(N, baseEdges, EXP6_REMOVE_LEFT_EDGES[d][0], EXP6_REMOVE_RMVD_EDGES[d][0]);  // preheat

        List<RuntimeResult> results1 = new ArrayList<>();
        for (int i = 0; i < EXP6_REMOVE_LEFT_EDGES[d].length; i++) {
            RuntimeResult res = removeDynHS(N, baseEdges, EXP6_REMOVE_LEFT_EDGES[d][i], EXP6_REMOVE_RMVD_EDGES[d][i]);
            results1.add(res);
        }
        printTotalTimes(results1, EXP6_REMOVE_LEFT_EDGES[d]);

        System.out.println("\n===============================MMCS==============================");
        List<RuntimeResult> results2 = runMMCS(N, EXP6_REMOVE_LEFT_EDGES[d]);
        printTotalTimes(results2, EXP6_REMOVE_LEFT_EDGES[d]);
    }

    RuntimeResult insertDynHS(int N, List<Long> baseEdges, String isrtEdgeFp) {
        List<Long> isrt = DataIO.readDiffMapToList(N, isrtEdgeFp);
        Utils.sortLongSets(N, isrt);

        double totalTime = 0.0;
        for (int e = 0; e < N; e++) {
            List<Long> baseEdgeRhs = genEdgeRhs(e, baseEdges);
            List<Long> isrtRhs = genEdgeRhs(e, isrt);

            DynHS dynHS = new DynHS(N);
            dynHS.initiate(baseEdgeRhs);

            long startTime = System.nanoTime();
            dynHS.insertEdges(isrtRhs);
            totalTime += (double) (System.nanoTime() - startTime) / 1000000;
        }

        return new RuntimeResult(totalTime);
    }

    RuntimeResult removeDynHS(int N, List<Long> baseEdges, String leftEdgeFp, String rmvdEdgeFp) {
        List<Long> left = DataIO.readDiffMapToList(N, leftEdgeFp);
        List<Long> rmvd = DataIO.readDiffMapToList(N, rmvdEdgeFp);
        Utils.sortLongSets(N, left);
        Utils.sortLongSets(N, rmvd);

        double totalTime = 0.0;
        for (int e = 0; e < N; e++) {
            List<Long> baseEdgeRhs = genEdgeRhs(e, baseEdges);
            List<Long> leftRhs = genEdgeRhs(e, left);
            List<Long> rmvdRhs = genEdgeRhs(e, rmvd);

            DynHS dynHS = new DynHS(N);
            dynHS.initiate(baseEdgeRhs);

            long startTime = System.nanoTime();
            dynHS.removeEdges(leftRhs, rmvdRhs);
            totalTime += (double) (System.nanoTime() - startTime) / 1000000;
        }

        return new RuntimeResult(totalTime);
    }

    RuntimeResult runMMCS(int N, String EdgeFp) {
        List<Long> left = DataIO.readDiffMapToList(N, EdgeFp);
        Utils.sortLongSets(N, left);

        double totalTime = 0;
        for (int e = 0; e < N; e++) {
            List<Long> leftRhs = genEdgeRhs(e, left);
            Mmcs mmcs = new Mmcs(N);

            long startTime = System.nanoTime();
            mmcs.initiate(leftRhs, true, true);
            totalTime += (double) (System.nanoTime() - startTime) / 1000000;
        }
        return new RuntimeResult(totalTime);
    }

    List<RuntimeResult> runMMCS(int N, String[] EdgeFp) {
        runMMCS(N, EdgeFp[0]);      // preheat

        List<RuntimeResult> results = new ArrayList<>();
        for (int i = 0; i < EdgeFp.length; i++) {
            RuntimeResult res = runMMCS(N, EdgeFp[i]);
            results.add(res);
        }
        return results;
    }



    public void genDiffBF(int dataset) {
        for (int d = 0, size = DIFF_INPUT_DATA[dataset].length; d < size; d++) {
            System.out.println("[INITIALIZING]...");
            List<List<String>> csvData = DataIO.readCsvFile(DIFF_INPUT_DATA[dataset][d]);

            HashLongLongMap diffFreq = HashLongLongMaps.newMutableMap();

            ProgressBar.wrap(IntStream.range(0, csvData.size()), "Task").forEach(i -> {
                for (int j = i + 1; j < csvData.size(); j++) {
                    long diff = 0L;
                    for (int k = 0; k < csvData.get(0).size(); k++)
                        if (!csvData.get(i).get(k).equals(csvData.get(j).get(k))) diff |= 1L << k;
                    diffFreq.addValue(diff, 1L, 0L);
                }
            });

            Map<BitSet, Long> diffMap = new HashMap<>();
            for (Map.Entry<Long, Long> df : diffFreq.entrySet())
                diffMap.put(Utils.longToBitSet(csvData.get(0).size(), df.getKey()), df.getValue());

            System.out.println("Size of diff: " + diffMap.size());
            DataIO.printDiffMap(diffMap, DIFF_OUTPUT_DIFF[dataset][d]);
        }
    }


    public void genDiff(int dataset) {
        for (int d = 0, size = DIFF_INPUT_DATA[dataset].length; d < size; d++) {
            DiffConnector diffConnector = new DiffConnector();
            // load base data
            System.out.println("[INITIALIZING]...");
            List<List<String>> csvData = DataIO.readCsvFile(DIFF_INPUT_DATA[dataset][d]);

            HashLongLongMap diffFreq = HashLongLongMaps.newMutableMap();

            // initiate pli and differenceSet
            Map<BitSet, Long> diffMap = diffConnector.generatePliAndDiffMap(csvData);
            for (Map.Entry<Long, Long> df : diffFreq.entrySet())
                diffMap.put(Utils.longToBitSet(N_ATTRIBUTES[dataset], df.getKey()), df.getValue());

            System.out.println("Size of diff: " + diffMap.size());
            DataIO.printDiffMap(diffMap, DIFF_OUTPUT_DIFF[dataset][d]);
        }
    }

    public void testInsert(int dataset) {
        // 1 initiate
        DiffConnector diffConnector = initiateDiff(INSERT_INPUT_BASE_DATA[dataset], INSERT_INPUT_BASE_DIFF[dataset]);
        DynHSConnector fdConnector = initiateFd(diffConnector.nElements, diffConnector.getDiffSet());

        // 2 load inserted data all at once
        List<List<List<String>>> insertDatas = new ArrayList<>();
        for (String fp : INSERT_INPUT_NEW_DATA[dataset])
            insertDatas.add(DataIO.readCsvFile(fp));

        // 3 insert data and record running time
        System.out.println("[INSERTING]...");

        List<Double> diffTimes = new ArrayList<>();
        List<Double> fdTimes = new ArrayList<>();

        List<List<Long>> insertDiffSets = new ArrayList<>();
        List<List<List<BitSet>>> totalFds = new ArrayList<>();

        for (int i = 0; i < insertDatas.size(); i++) {
            // 3.1 update pli and differenceSet
            long startTime = System.nanoTime();
            List<Long> newDiffs = diffConnector.insertData(insertDatas.get(i));
            diffTimes.add((double) (System.nanoTime() - startTime) / 1000000);
            insertDiffSets.add(newDiffs);
            //DataIO.printLongDiffMap(diffConnector,INSERT_OUTPUT_CURR_DIFF[dataset][i]);

            // 3.2 update FD
            startTime = System.nanoTime();
            List<List<BitSet>> currFDs = fdConnector.insertSubsets(newDiffs);
            fdTimes.add((double) (System.nanoTime() - startTime) / 1000000);
            totalFds.add(currFDs);
            //DataIO.printFDs(fdConnector, INSERT_OUTPUT_CURR_FD[dataset][i]);
        }

        // 4 print result and time
        printResult(true, insertDiffSets, totalFds, diffTimes, fdTimes);
    }

    public void testRemove(int dataset) {
        // 1 initiate
        DiffConnector diffConnector = initiateDiff(REMOVE_INPUT_BASE_DATA[dataset], REMOVE_INPUT_BASE_DIFF[dataset]);
        DynHSConnector fdConnector = initiateFd(diffConnector.nElements, diffConnector.getDiffSet());
        //DataIO.printFDs(fdConnector, REMOVE_OUTPUT_BASE_FD[dataset]);

        // 2 load removed data all at once
        List<List<Integer>> removedDatas = new ArrayList<>();
        for (String fp : REMOVE_INPUT_DELETED_DATA[dataset])
            removedDatas.add(DataIO.readRemoveFile(fp));

        // 3 remove data and record running time
        System.out.println("[REMOVING]...");

        List<Double> diffTimes = new ArrayList<>();
        List<Double> fdTimes = new ArrayList<>();

        List<List<Long>> leftDiffSets = new ArrayList<>();
        List<List<List<BitSet>>> totalFds = new ArrayList<>();

        for (int i = 0; i < removedDatas.size(); i++) {         // different rounds
            // 3.1 update pli and differenceSet
            long startTime = System.nanoTime();
            Set<Long> removedDiffs = diffConnector.removeData(removedDatas.get(i));
            List<Long> leftDiffSet = diffConnector.getDiffSet();
            diffTimes.add((double) (System.nanoTime() - startTime) / 1000000);
            leftDiffSets.add(leftDiffSet);
            //DataIO.printLongDiffMap(diffConnector,INSERT_OUTPUT_CURR_DIFF[dataset][i]);

            // 3.2 update FD
            startTime = System.nanoTime();
            List<List<BitSet>> currFDs = fdConnector.removeSubsets(leftDiffSet, removedDiffs);
            fdTimes.add((double) (System.nanoTime() - startTime) / 1000000);
            totalFds.add(currFDs);
            //DataIO.printFDs(fdConnector, REMOVE_OUTPUT_DELETED_FD[dataset][i]);
        }

        // 4 print result and time
        printResult(false, leftDiffSets, totalFds, diffTimes, fdTimes);
    }

    void printResult(boolean isInsert, List<List<Long>> diffSets, List<List<List<BitSet>>> fds, List<Double> diffTimes, List<Double> fdTimes) {
        double diffTimeTotal = diffTimes.stream().reduce(0.0, Double::sum);
        double fdTimeTotal = fdTimes.stream().reduce(0.0, Double::sum);

        System.out.println("[RESULT]");
        System.out.println("----------------------------------------------------------------------------------------------");
        System.out.println("|       |              Size               |                     Time/ms                      |");
        System.out.println("|  No.  |---------------------------------+--------------------------------------------------|");
        System.out.printf("|       | %s |       FD       |      Diff      |       FD       |      Total     |\n", isInsert ? "   New Diff   " : "Remaining Diff");
        System.out.println("|-------+----------------+----------------+----------------+----------------+----------------|");
        for (int i = 0; i < diffTimes.size(); i++)
            System.out.printf("|  %2d   |   %10d   |   %10d   |   %10.2f   |   %10.2f   |   %10.2f   |\n", i, diffSets.get(i).size(), fds.get(i).stream().map(List::size).reduce(0, Integer::sum), diffTimes.get(i), fdTimes.get(i), diffTimes.get(i) + fdTimes.get(i));
        System.out.println("|-------+----------------+----------------+----------------+----------------+----------------|");
        System.out.printf("|  Avg  |      %3c       |       %3c      |   %10.2f   |   %10.2f   |   %10.2f   |\n", ' ', ' ', diffTimeTotal / diffTimes.size(), fdTimeTotal / fdTimes.size(), (diffTimeTotal + fdTimeTotal) / fdTimes.size());
        System.out.printf("| Total |      %3c       |       %3c      |   %10.2f   |   %10.2f   |   %10.2f   |\n", ' ', ' ', diffTimeTotal, fdTimeTotal, diffTimeTotal + fdTimeTotal);
        System.out.println("----------------------------------------------------------------------------------------------\n");
    }

}
