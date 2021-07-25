package util;

import algorithm.differenceSet.DiffConnector;
import algorithm.hittingSet.DynHS.DynHSConnector;
import com.csvreader.CsvReader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class DataIO {


    public static List<List<String>> readCsvFile(String readCsvFilePath) {
        List<List<String>> content = new ArrayList<>();

        try {
            CsvReader csvReader = new CsvReader(readCsvFilePath, ',', StandardCharsets.UTF_8);
            csvReader.readHeaders();    // skip the header
            while (csvReader.readRecord())
                content.add(new ArrayList<>(Arrays.asList(csvReader.getValues())));
            csvReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return content;
    }

    public static List<Integer> readRemoveFile(String fp) {
        List<Integer> res = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fp))) {
            String s;
            while ((s = br.readLine()) != null)
                res.add(Integer.parseInt(s));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static List<Long> readDiffMapToList(int N, String dsFilePath) {
        Map<BitSet, Long> diffMap = readDiffMap(dsFilePath);
        return diffMap.keySet().stream().map(bs -> Utils.bitsetToLong(N, bs)).collect(Collectors.toList());
    }

    public static Map<BitSet, Long> readDiffMap(String dsFilePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(dsFilePath))) {
            String s;
            while ((s = br.readLine()) != null)
                lines.add(s);
        } catch (Exception e) {
            e.printStackTrace();
        }

        HashMap<BitSet, Long> differnceSetAll = new HashMap<>();
        for (String s : lines) {
            int index = s.indexOf('}');
            BitSet bitSet = new BitSet();
            for (String str : s.substring(1, index).split(", ")) {
                if (str != null && str.length() > 0) bitSet.set(Integer.parseInt(str));
            }
            differnceSetAll.put(bitSet, Long.parseLong(s.substring(index + 2)));
        }

        return differnceSetAll;
    }

//    public static void printFDs(BhmmcsFdConnector fdConnector, String writeFilePath, boolean append) {
//        List<List<BitSet>> fd = fdConnector.getMinFDs();
//        for (int i = 0; i < fd.size(); i++) {
//            try (PrintWriter pw = new PrintWriter(new FileWriter(writeFilePath, append))) {
//                pw.println("FDs for attribute " + i + ":");
//                fd.get(i).forEach(pw::println);
//                pw.println();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public static void printFDs(DynHSConnector fdConnector, String writeFilePath) {
        List<List<BitSet>> fds = fdConnector.getMinFDs();
        for (List<BitSet> fd : fds)
            fd.sort(Utils.BitsetComparator());

        try (PrintWriter pw = new PrintWriter(new FileWriter(writeFilePath, false))) {
            for (int i = 0; i < fds.size(); i++) {
                pw.println("FDs for attribute " + i + ":");
                fds.get(i).forEach(pw::println);
                pw.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printDiffMap(Map<BitSet, Long> map, String writeFilePath) {
        List<Map.Entry<BitSet, Long>> entries = new ArrayList<>(map.entrySet());
        entries.sort(Utils.BitsetMapComparator());

        try (PrintWriter pw = new PrintWriter(new FileWriter(writeFilePath,false))) {
            for (var entry : entries) {
                pw.println(entry);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printLongDiffMap(DiffConnector diffConnector, String writeFilePath) {
        Map<BitSet, Long> map = new HashMap<>();
        Map<Long,Long> diffFreq = diffConnector.getDiffFreq();
        for (Map.Entry<Long, Long> df : diffFreq.entrySet())
            map.put(Utils.longToBitSet(diffConnector.nElements, df.getKey()), df.getValue());

        List<Map.Entry<BitSet,Long>> entries = new ArrayList<>(map.entrySet());
        entries.sort(Utils.BitsetMapComparator());

        try (PrintWriter pw = new PrintWriter(new FileWriter(writeFilePath,false))) {
            for (var entry : entries) {
                pw.println(entry);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
