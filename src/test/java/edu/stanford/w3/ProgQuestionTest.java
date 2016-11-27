//package edu.stanford.w3;
//
//import edu.stanford.w3.edu.stanford.graph.UndirectedGraph;
//import org.junit.Test;
//
//import java.util.concurrent.ExecutionException;
//
//import static edu.stanford.common.Benchmark.runBenchmark;
//import static edu.stanford.common.CommonUtils.resourse2stream;
//import static edu.stanford.w3.edu.stanford.graph.RandomContractions.contract;
//import static org.apache.commons.lang3.StringUtils.SPACE;
//import static org.apache.commons.lang3.StringUtils.normalizeSpace;
//
//public class ProgQuestionTest {
//
//    private final int RIGHT_ANSWER = 17;
//
//    private static String[][] matrix = parseMatrix("kargerMinCut.txt");
//
//    public static String[][] parseMatrix(String fileName) {
//        return resourse2stream(fileName)
//                .map(line -> normalizeSpace(line).split(SPACE))
//                .toArray(String[][]::new);
//    }
//
//    @Test
//    public void testContraction() throws ExecutionException, InterruptedException {
//        UndirectedGraph edu.stanford.graph = UndirectedGraph.fromAdjMatrix(matrix);
//
//        System.out.println(runBenchmark(() -> contract(edu.stanford.graph)));
//
////        todo: takes too much time!!
////        int n = edu.stanford.graph.getVertices().size();
////        int count = n * n * toIntExact(round(log(n)));
////
////        int minCut = contract(edu.stanford.graph).crossingEdges();
////
////        for (int i = 0; i < count; i++) {
////            int cut = contract(edu.stanford.graph).crossingEdges();
////            minCut = min(cut, minCut);
////            System.out.println(minCut);
////        }
////        assertEquals(minCut, RIGHT_ANSWER);
//    }
//
//}
