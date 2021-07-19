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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static benchmark.DataFp.*;

public class TestCase {

    class ResultExp6 {
        int hsSize;
        int emptyCount = 0;
        double fminTime = 0;
        double totalTime;

        public ResultExp6(int hsSize, double totalTime) {
            this.hsSize = hsSize;
            this.totalTime = totalTime;
        }

        public ResultExp6(int hsSize, int emptyCount, double totalTime) {
            this.hsSize = hsSize;
            this.emptyCount = emptyCount;
            this.totalTime = totalTime;
        }

        public ResultExp6(int hsSize, double fminTime, double totalTime) {
            this.hsSize = hsSize;
            this.fminTime = fminTime;
            this.totalTime = totalTime;
        }
    }

    List<Long> genEdgeRhs(int e, List<Long> edges) {
        List<Long> edgeRhs = new ArrayList<>();
        long mask = 1L << e;
        for (long edge : edges)
            if ((edge & mask) != 0) edgeRhs.add(edge & ~mask);
        return edgeRhs;
    }

//    ResultExp6 initInsertDynHS2(int nAttributes, List<Long> baseEdges, String istdEdgeFp) {
//        List<Long> istd = DataIO.readDiffSetsMap(istdEdgeFp).keySet().stream().map(bs -> Utils.bitsetToLong(nAttributes, bs)).collect(Collectors.toList());
//        Utils.sortLongSets(nAttributes, istd);
//
//        int hsSize = 0;
//        double totalTime = 0;
//        for (int e = 0; e < nAttributes; e++) {
//            List<Long> baseEdgeRhs = genEdgeRhs(e, baseEdges);
//            List<Long> istdRhs = genEdgeRhs(e, istd);
//
//            DynHS2 dynHS = new DynHS2(nAttributes);
//            dynHS.initiate(baseEdgeRhs);
//
//            long startTime = System.nanoTime();
//            dynHS.insertEdges(istdRhs);
//            totalTime += (double) (System.nanoTime() - startTime) / 1000000;
//            hsSize += dynHS.getMinCoverSets().size();
//        }
//
//        return new ResultExp6(hsSize, totalTime);
//    }

    ResultExp6 initInsertDynHS(int nAttributes, List<Long> baseEdges, String istdEdgeFp) {
        List<Long> istd = DataIO.readDiffSetsMap(istdEdgeFp).keySet().stream().map(bs -> Utils.bitsetToLong(nAttributes, bs)).collect(Collectors.toList());
        Utils.sortLongSets(nAttributes, istd);

        int hsSize = 0;
        double totalTime = 0;
        for (int e = 0; e < nAttributes; e++) {
            List<Long> baseEdgeRhs = genEdgeRhs(e, baseEdges);
            List<Long> istdRhs = genEdgeRhs(e, istd);

            DynHS dynHS = new DynHS(nAttributes);
            dynHS.initiate(baseEdgeRhs);

            long startTime = System.nanoTime();
            dynHS.insertEdges(istdRhs);
            totalTime += (double) (System.nanoTime() - startTime) / 1000000;
            hsSize += dynHS.getMinCoverSets().size();
        }

        return new ResultExp6(hsSize, totalTime);
    }

//    ResultExp6 initRemoveDynHS2(int nAttributes, List<Long> baseEdges, String leftEdgeFp, String rmvdEdgeFp) {
//        List<Long> left = DataIO.readDiffSetsMap(leftEdgeFp).keySet().stream().map(bs -> Utils.bitsetToLong(nAttributes, bs)).collect(Collectors.toList());
//        List<Long> rmvd = DataIO.readDiffSetsMap(rmvdEdgeFp).keySet().stream().map(bs -> Utils.bitsetToLong(nAttributes, bs)).collect(Collectors.toList());
//        Utils.sortLongSets(nAttributes, left);
//        Utils.sortLongSets(nAttributes, rmvd);
//
//        int hsSize = 0;
//        double totalTime = 0;
//        for (int e = 0; e < nAttributes; e++) {
//            List<Long> baseEdgeRhs = genEdgeRhs(e, baseEdges);
//            List<Long> leftRhs = genEdgeRhs(e, left);
//            List<Long> rmvdRhs = genEdgeRhs(e, rmvd);
//
//            DynHS2 dynHS = new DynHS2(nAttributes);
//            dynHS.initiate(baseEdgeRhs);
//
//            long startTime = System.nanoTime();
//            dynHS.removeEdges(leftRhs, rmvdRhs);
//            totalTime += (double) (System.nanoTime() - startTime) / 1000000;
//            hsSize += dynHS.getMinCoverSets().size();
//        }
//
//        return new ResultExp6(hsSize, totalTime);
//    }

    ResultExp6 initRemoveDynHS(int nAttributes, List<Long> baseEdges, String leftEdgeFp, String rmvdEdgeFp) {
        List<Long> left = DataIO.readDiffSetsMap(leftEdgeFp).keySet().stream().map(bs -> Utils.bitsetToLong(nAttributes, bs)).collect(Collectors.toList());
        List<Long> rmvd = DataIO.readDiffSetsMap(rmvdEdgeFp).keySet().stream().map(bs -> Utils.bitsetToLong(nAttributes, bs)).collect(Collectors.toList());
        Utils.sortLongSets(nAttributes, left);
        Utils.sortLongSets(nAttributes, rmvd);

        int hsSize = 0;
        double totalTime = 0;
        for (int e = 0; e < nAttributes; e++) {
            List<Long> baseEdgeRhs = genEdgeRhs(e, baseEdges);
            List<Long> leftRhs = genEdgeRhs(e, left);
            List<Long> rmvdRhs = genEdgeRhs(e, rmvd);

            DynHS dynHS = new DynHS(nAttributes);
            dynHS.initiate(baseEdgeRhs);

            long startTime = System.nanoTime();
            dynHS.removeEdges(leftRhs, rmvdRhs);
            totalTime += (double) (System.nanoTime() - startTime) / 1000000;
            hsSize += dynHS.getMinCoverSets().size();
        }

        return new ResultExp6(hsSize, totalTime);
    }

    ResultExp6 initMmcs(int nAttributes, String EdgeFp) {
        List<Long> left = DataIO.readDiffSetsMap(EdgeFp).keySet().stream().map(bs -> Utils.bitsetToLong(nAttributes, bs)).collect(Collectors.toList());
        Utils.sortLongSets(nAttributes, left);

        int hsSize = 0;
        double totalTime = 0;
        double fminTime = 0;
        for (int e = 0; e < nAttributes; e++) {
            List<Long> leftRhs = genEdgeRhs(e, left);

            Mmcs mmcs = new Mmcs(nAttributes);

            long startTime = System.nanoTime();
            mmcs.initiate(leftRhs, true, true);
            totalTime += (double) (System.nanoTime() - startTime) / 1000000;
            hsSize += mmcs.getMinCoverSets().size();
        }

        return new ResultExp6(hsSize, fminTime, totalTime);
    }

    public void exp6remove(int dataset) {
        int nAttributes = N_ATTRIBUTES[dataset];


        System.out.println("\n===================DynHS==================");
        System.out.println("No.\tHS\t\tTime(ms)\tData File");

        Map<BitSet, Long> diffSetMap = DataIO.readDiffSetsMap(BHMMCS_REMOVE_INPUT_BASE_EDGE[dataset]);
        List<Long> baseEdges = diffSetMap.keySet().stream().map(bs -> Utils.bitsetToLong(nAttributes, bs)).collect(Collectors.toList());
        Utils.sortLongSets(nAttributes, baseEdges);

        // preheat
        initRemoveDynHS(nAttributes, baseEdges, BHMMCS_REMOVE_INPUT_LEFT_EDGE[dataset][0], BHMMCS_REMOVE_INPUT_RMVD_EDGE[dataset][0]);

        for (int i = 0; i < BHMMCS_REMOVE_INPUT_LEFT_EDGE[dataset].length; i++) {
            ResultExp6 res = initRemoveDynHS(nAttributes, baseEdges, BHMMCS_REMOVE_INPUT_LEFT_EDGE[dataset][i], BHMMCS_REMOVE_INPUT_RMVD_EDGE[dataset][i]);
            System.out.printf("%d\t%d\t%10.3f\t\t%s\n", i, res.hsSize, res.totalTime, BHMMCS_REMOVE_INPUT_LEFT_EDGE[dataset][i]);
        }


        System.out.println("\n===================MMCS==================");
        System.out.println("No.\tHS\tTotal Time(ms)\tData File");

        // preheat
        initMmcs(nAttributes, BHMMCS_REMOVE_INPUT_LEFT_EDGE[dataset][0]);

        for (int i = 0; i < BHMMCS_REMOVE_INPUT_LEFT_EDGE[dataset].length; i++) {
            ResultExp6 res = initMmcs(nAttributes, BHMMCS_REMOVE_INPUT_LEFT_EDGE[dataset][i]);
            System.out.printf("%d\t%d\t%10.3f\t\t%s\n", i, res.hsSize, res.totalTime, BHMMCS_REMOVE_INPUT_LEFT_EDGE[dataset][i]);
        }

    }

    public void exp6insert(int dataset) {
        int nAttributes = N_ATTRIBUTES[dataset];


        System.out.println("\n===================DynHS==================");
        System.out.println("No.\tHS\t\tTime(ms)\tData File");

        Map<BitSet, Long> diffSetMap = DataIO.readDiffSetsMap(BHMMCS_INSERT_INPUT_BASE_EDGE[dataset]);
        List<Long> baseEdges = diffSetMap.keySet().stream().map(bs -> Utils.bitsetToLong(nAttributes, bs)).collect(Collectors.toList());
        Utils.sortLongSets(nAttributes, baseEdges);

        // preheat
        initInsertDynHS(nAttributes, baseEdges, BHMMCS_INSERT_INPUT_ISTD_EDGE[dataset][0]);

        for (int i = 0; i < BHMMCS_INSERT_INPUT_ISTD_EDGE[dataset].length; i++) {
            ResultExp6 res = initInsertDynHS(nAttributes, baseEdges, BHMMCS_INSERT_INPUT_ISTD_EDGE[dataset][i]);
            System.out.printf("%d\t%d\t%10.3f\t\t%s\n", i, res.hsSize, res.totalTime, BHMMCS_INSERT_INPUT_ISTD_EDGE[dataset][i]);
        }


        System.out.println("\n===================MMCS==================");
        System.out.println("No.\tHS\tTotal Time(ms)\tData File");

        // preheat
        initMmcs(nAttributes, MMCS_INSERT_INPUT_EDGE[dataset][0]);

        for (int i = 0; i < MMCS_INSERT_INPUT_EDGE[dataset].length; i++) {
            ResultExp6 res = initMmcs(nAttributes, MMCS_INSERT_INPUT_EDGE[dataset][i]);
            System.out.printf("%d\t%d\t%10.3f\t\t%s\n", i, res.hsSize, res.totalTime, MMCS_INSERT_INPUT_EDGE[dataset][i]);
        }

    }


    public void testDiffBF(int dataset) {
        for (int d = 0, size = DIFF_INPUT_DATA[dataset].length; d < size; d++) {
            // load base data
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


    public void testDiff(int dataset) {
        for (int d = 0, size = DIFF_INPUT_DATA[dataset].length; d < size; d++) {
            DiffConnector diffConnector = new DiffConnector();
            // load base data
            System.out.println("[INITIALIZING]...");
            List<List<String>> csvData = DataIO.readCsvFile(DIFF_INPUT_DATA[dataset][d]);

            HashLongLongMap diffFreq = HashLongLongMaps.newMutableMap();


            // initiate pli and differenceSet
            Map<BitSet, Long> diffMap = diffConnector.generatePliAndDiffMap(csvData);
            for (Map.Entry<Long, Long> df : diffFreq.entrySet())
                diffMap.put(Utils.longToBitSet(34, df.getKey()), df.getValue());

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
            DataIO.printLongDiffMap(diffConnector, INSERT_OUTPUT_CURR_DIFF[dataset][i]);
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

    public void testBHMMCS(int dataset) {
        int nAttributes = N_ATTRIBUTES[dataset];

        Map<BitSet, Long> diffSetMap = DataIO.readDiffSetsMap(BHMMCS_REMOVE_INPUT_BASE_EDGE[dataset]);
        List<Long> diffSet = diffSetMap.keySet().stream().map(bs -> Utils.bitsetToLong(nAttributes, bs)).collect(Collectors.toList());

        System.out.println("No.\tHS\tTime(ms)");

        for (int i = 0; i < BHMMCS_REMOVE_INPUT_LEFT_EDGE[dataset].length; i++) {
            List<Long> left = DataIO.readDiffSetsMap(BHMMCS_REMOVE_INPUT_LEFT_EDGE[dataset][i]).keySet().stream().map(bs -> Utils.bitsetToLong(nAttributes, bs)).collect(Collectors.toList());
            List<Long> rmvd = DataIO.readDiffSetsMap(BHMMCS_REMOVE_INPUT_RMVD_EDGE[dataset][i]).keySet().stream().map(bs -> Utils.bitsetToLong(nAttributes, bs)).collect(Collectors.toList());

            DynHS dynHS = new DynHS(nAttributes);
            dynHS.initiate(diffSet);

            long startTime = System.nanoTime();
            dynHS.removeEdges(left, rmvd);
            double totalTime = (double) (System.nanoTime() - startTime) / 1000000;

            System.out.println(i + "\t" + dynHS.getMinCoverSets().size() + "\t" + totalTime);
        }
    }


    DiffConnector initiateDiff(String BASE_DATA_INPUT, String BASE_DIFF_INPUT) {
        // load base data
        System.out.println("[INITIALIZING]...");
        List<List<String>> csvData = DataIO.readCsvFile(BASE_DATA_INPUT);

        // initiate pli and differenceSet
        DiffConnector diffConnector = new DiffConnector();
        List<Long> initDiffSets = diffConnector.generatePliAndDiff(csvData, BASE_DIFF_INPUT);
        System.out.println("  # of initial Diff: " + initDiffSets.size());

        return diffConnector;
    }

    DynHSConnector initiateFd(int nElements, List<Long> initDiffSets) {
        // initiate FD
        DynHSConnector fdConnector = new DynHSConnector();
        //FdConnector fdConnector = nElements <= 32 ? new BhmmcsFdConnector() : new BhmmcsFdConnector64();
        fdConnector.initiate(nElements, initDiffSets);
        System.out.println("  # of initial FD: " + fdConnector.getMinFDs().stream().map(List::size).reduce(0, Integer::sum));

        return fdConnector;
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
