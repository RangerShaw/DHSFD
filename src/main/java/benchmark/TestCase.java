package benchmark;

import algorithm.differenceSet.DiffConnector;
import algorithm.hittingSet.DynHS.DynHS;
import algorithm.hittingSet.MMCS.Mmcs;
import algorithm.hittingSet.DynHS.DynHSConnector;
import util.DataIO;
import util.Utils;

import java.util.*;
import java.util.stream.Collectors;

import static benchmark.DataFp.*;
import static benchmark.DataFp.REMOVE_INPUT_BASE_DIFF;

public class TestCase {

    public static void exp6(int dataset) {
        int nAttributes = N_ATTRIBUTES[dataset];

        {
            System.out.println("===================DynHS==================");
            System.out.println("No.\tHS\t\tTime(ms)\tData File");

            Map<BitSet, Long> diffSetMap = DataIO.readDiffSetsMap(BHMMCS_REMOVE_INPUT_BASE_EDGE[dataset]);
            List<Long> diffSet = diffSetMap.keySet().stream().map(bs -> Utils.bitsetToLong(nAttributes, bs)).collect(Collectors.toList());
            Utils.sortLongSets(nAttributes, diffSet);

            {
                List<Long> left = DataIO.readDiffSetsMap(BHMMCS_REMOVE_INPUT_LEFT_EDGE[dataset][0]).keySet().stream().map(bs -> Utils.bitsetToLong(nAttributes, bs)).collect(Collectors.toList());
                List<Long> rmvd = DataIO.readDiffSetsMap(BHMMCS_REMOVE_INPUT_RMVD_EDGE[dataset][0]).keySet().stream().map(bs -> Utils.bitsetToLong(nAttributes, bs)).collect(Collectors.toList());
                Utils.sortLongSets(nAttributes, left);
                Utils.sortLongSets(nAttributes, rmvd);

                DynHS dynHS = new DynHS(nAttributes);
                dynHS.initiate(diffSet);

                dynHS.removeSubsets(left, rmvd);
            }

            for (int i = 0; i < BHMMCS_REMOVE_INPUT_LEFT_EDGE[dataset].length; i++) {
                List<Long> left = DataIO.readDiffSetsMap(BHMMCS_REMOVE_INPUT_LEFT_EDGE[dataset][i]).keySet().stream().map(bs -> Utils.bitsetToLong(nAttributes, bs)).collect(Collectors.toList());
                List<Long> rmvd = DataIO.readDiffSetsMap(BHMMCS_REMOVE_INPUT_RMVD_EDGE[dataset][i]).keySet().stream().map(bs -> Utils.bitsetToLong(nAttributes, bs)).collect(Collectors.toList());
                Utils.sortLongSets(nAttributes, left);
                Utils.sortLongSets(nAttributes, rmvd);

                DynHS dynHS = new DynHS(nAttributes);
                dynHS.initiate(diffSet);

                long startTime = System.nanoTime();
                dynHS.removeSubsets(left, rmvd);
                double totalTime = (double) (System.nanoTime() - startTime) / 1000000;

                System.out.printf("%d\t%d\t%10.3f\t\t%s\n", i, dynHS.getMinCoverSets().size(), totalTime, BHMMCS_REMOVE_INPUT_RMVD_EDGE[dataset][i]);
            }
        }

        {
            System.out.println("\n===================MMCS==================");
            System.out.println("No.\tHS\t\tTime(ms)\tData File");

            for (int i = 0; i < MMCS_INPUT_EDGE[dataset].length; i++) {
                Map<BitSet, Long> diffSetMap = DataIO.readDiffSetsMap(MMCS_INPUT_EDGE[dataset][i]);
                List<Long> diffSet = diffSetMap.keySet().stream().map(bs -> Utils.bitsetToLong(nAttributes, bs)).collect(Collectors.toList());
//                List<Long> diffSet2 = new ArrayList<>();
//                for (long s : diffSet)
//                    if ((s & 1) != 0) diffSet2.add(s & ~1L);

                long startTime = System.nanoTime();
                Mmcs mmcs = new Mmcs(nAttributes);
                mmcs.initiate(diffSet);
                double totalTime = (double) (System.nanoTime() - startTime) / 1000000;

                System.out.printf("%d\t%d\t%10.3f\t\t%s\n", i, mmcs.getMinCoverSets().size(), totalTime, MMCS_INPUT_EDGE[dataset][i]);
            }
        }

    }

    public static void testMMCS(int dataset) {
        int nAttributes = N_ATTRIBUTES[dataset];
        System.out.println("No.\tHS\tTime(ms)");

        for (int i = 0; i < MMCS_INPUT_EDGE[dataset].length; i++) {
            Map<BitSet, Long> diffSetMap = DataIO.readDiffSetsMap(MMCS_INPUT_EDGE[dataset][i]);
            List<Long> diffSet = diffSetMap.keySet().stream().map(bs -> Utils.bitsetToLong(nAttributes, bs)).collect(Collectors.toList());

            long startTime = System.nanoTime();
            Mmcs mmcs = new Mmcs(nAttributes);
            mmcs.initiate(diffSet);
            double totalTime = (double) (System.nanoTime() - startTime) / 1000000;

            System.out.println(i + "\t" + mmcs.getMinCoverSets().size() + "\t" + totalTime);
        }
    }

    public static void testDiff(int dataset) {
        for (int d = 0, size = DIFF_INPUT_DATA[dataset].length; d < size; d++) {
            DiffConnector diffConnector = new DiffConnector();
            // load base data
            System.out.println("[INITIALIZING]...");
            List<List<String>> csvData = DataIO.readCsvFile(DIFF_INPUT_DATA[dataset][d]);

            // initiate pli and differenceSet
            Map<BitSet, Long> diffMap = diffConnector.generatePliAndDiffMap(csvData);
            System.out.println("Size of diff: " + diffMap.size());
            DataIO.printDiffMap(diffMap, DIFF_OUTPUT_DIFF[dataset][d]);
        }
    }

    public static void testInsert(int dataset) {
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
            DataIO.printFDs(fdConnector, INSERT_OUTPUT_CURR_FD[dataset][i]);
        }

        // 4 print result and time
        printResult(true, insertDiffSets, totalFds, diffTimes, fdTimes);
    }

    public static void testRemove(int dataset) {
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
            DataIO.printFDs(fdConnector, REMOVE_OUTPUT_DELETED_FD[dataset][i]);
        }

        // 4 print result and time
        printResult(false, leftDiffSets, totalFds, diffTimes, fdTimes);
    }

    public static void testBHMMCS(int dataset) {
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
            dynHS.removeSubsets(left, rmvd);
            double totalTime = (double) (System.nanoTime() - startTime) / 1000000;

            System.out.println(i + "\t" + dynHS.getMinCoverSets().size() + "\t" + totalTime);
        }
    }


    static DiffConnector initiateDiff(String BASE_DATA_INPUT, String BASE_DIFF_INPUT) {
        // load base data
        System.out.println("[INITIALIZING]...");
        List<List<String>> csvData = DataIO.readCsvFile(BASE_DATA_INPUT);

        // initiate pli and differenceSet
        DiffConnector diffConnector = new DiffConnector();
        List<Long> initDiffSets = diffConnector.generatePliAndDiff(csvData, BASE_DIFF_INPUT);
        System.out.println("  # of initial Diff: " + initDiffSets.size());

        return diffConnector;
    }

    static DynHSConnector initiateFd(int nElements, List<Long> initDiffSets) {
        // initiate FD
        DynHSConnector fdConnector = new DynHSConnector();
        //FdConnector fdConnector = nElements <= 32 ? new BhmmcsFdConnector() : new BhmmcsFdConnector64();
        fdConnector.initiate(nElements, initDiffSets);
        System.out.println("  # of initial FD: " + fdConnector.getMinFDs().stream().map(List::size).reduce(0, Integer::sum));

        return fdConnector;
    }

    static void printResult(boolean isInsert, List<List<Long>> diffSets, List<List<List<BitSet>>> fds, List<Double> diffTimes, List<Double> fdTimes) {
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
