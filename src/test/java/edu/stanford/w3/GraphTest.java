package edu.stanford.w3;

import edu.stanford.w3.graph.UndirectedGraph;
import org.junit.Test;

import static edu.stanford.common.MatrixPrinter.Mode.VAR_ROW;
import static edu.stanford.common.MatrixPrinter.printMatrix;
import static edu.stanford.w3.graph.RandomContractions.contract;
import static edu.stanford.w3.graph.UndirectedGraph.fromAdjMatrix;

public class GraphTest {

    @Test
    public void testMatrixPrinting() {
        String[][] matrix = new String[5][];

        matrix[0] = new String[]{"1", "123", "4", "616"};
        matrix[1] = new String[]{"333", "1"};
        matrix[2] = new String[]{"1334", "123", "616"};
        matrix[3] = new String[]{"5", "123", "554", "616"};
        matrix[4] = new String[]{"123", "4", "6"};

        System.out.println(printMatrix(matrix, VAR_ROW));
    }

    @Test
    public void testGraphPrinting() {
        UndirectedGraph graph = createTestGraph();
        System.out.println(graph.toString());
    }

    @Test
    public void testContractions() {
        UndirectedGraph graph = createTestGraph();

        System.out.println(graph);

        System.out.println(contract(graph));
    }

    @Test
    public void testAdjMatrix() throws Exception {
        UndirectedGraph testGraph = createTestGraph();
        System.out.println(testGraph);

        String[][] adjacencyMatrix = testGraph.buildAdjacencyMatrix();
        UndirectedGraph graph = fromAdjMatrix(adjacencyMatrix);
        System.out.println(graph);
    }

    private UndirectedGraph createTestGraph() {
        UndirectedGraph graph = new UndirectedGraph();
        graph.addEdge("1", "2");
        graph.addEdge("2", "3");
        graph.addEdge("3", "4");
        graph.addEdge("4", "1");
        graph.addEdge("3", "1");
        graph.addEdge("5", "1");
        return graph;
    }

}
