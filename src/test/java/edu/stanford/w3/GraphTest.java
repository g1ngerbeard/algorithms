//package edu.stanford.w3;
//
//import edu.stanford.w3.edu.stanford.graph.UndirectedGraph;
//import org.junit.Test;
//
//import static edu.stanford.common.MatrixPrinter.Mode.VAR_ROW;
//import static edu.stanford.common.MatrixPrinter.printMatrix;
//import static edu.stanford.w3.edu.stanford.graph.RandomContractions.contract;
//import static edu.stanford.w3.edu.stanford.graph.UndirectedGraph.fromAdjMatrix;
//
//public class GraphTest {
//
//    @Test
//    public void testMatrixPrinting() {
//        String[][] matrix = new String[5][];
//
//        matrix[0] = new String[]{"1", "123", "4", "616"};
//        matrix[1] = new String[]{"333", "1"};
//        matrix[2] = new String[]{"1334", "123", "616"};
//        matrix[3] = new String[]{"5", "123", "554", "616"};
//        matrix[4] = new String[]{"123", "4", "6"};
//
//        System.out.println(printMatrix(matrix, VAR_ROW));
//    }
//
//    @Test
//    public void testGraphPrinting() {
//        UndirectedGraph edu.stanford.graph = createTestGraph();
//        System.out.println(edu.stanford.graph.toString());
//    }
//
//    @Test
//    public void testContractions() {
//        UndirectedGraph edu.stanford.graph = createTestGraph();
//
//        System.out.println(edu.stanford.graph);
//
//        System.out.println(contract(edu.stanford.graph));
//    }
//
//    @Test
//    public void testAdjMatrix() throws Exception {
//        UndirectedGraph testGraph = createTestGraph();
//        System.out.println(testGraph);
//
//        String[][] adjacencyMatrix = testGraph.buildAdjacencyMatrix();
//        UndirectedGraph edu.stanford.graph = fromAdjMatrix(adjacencyMatrix);
//        System.out.println(edu.stanford.graph);
//    }
//
//    private UndirectedGraph createTestGraph() {
//        UndirectedGraph edu.stanford.graph = new UndirectedGraph();
//        edu.stanford.graph.addEdge("1", "2");
//        edu.stanford.graph.addEdge("2", "3");
//        edu.stanford.graph.addEdge("3", "4");
//        edu.stanford.graph.addEdge("4", "1");
//        edu.stanford.graph.addEdge("3", "1");
//        edu.stanford.graph.addEdge("5", "1");
//        return edu.stanford.graph;
//    }
//
//}
