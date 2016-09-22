package edu.stanford.w3.graph;

import java.util.*;

import static edu.stanford.common.CommonUtils.*;
import static edu.stanford.common.MatrixPrinter.Mode.VAR_ROW;
import static edu.stanford.common.MatrixPrinter.printMatrix;
import static edu.stanford.w3.graph.UndirectedGraph.UndirectedEdge.edge;
import static edu.stanford.w3.graph.UndirectedGraph.Vertex.vertx;
import static java.util.Optional.empty;
import static java.util.stream.Collectors.toList;

// todo: make immutable/threadsafe ?
public class UndirectedGraph {

    private final List<Vertex> vertices;

    private final List<UndirectedEdge> edges;

    public UndirectedGraph() {
        this(list(), list());
    }

    public UndirectedGraph(List<Vertex> vertices, List<UndirectedEdge> edges) {
        this.vertices = list(vertices);
        this.edges = list(edges);
    }

    public static UndirectedGraph fromAdjMatrix(String[][] adjacencyMatrix) {
        UndirectedGraph graph = new UndirectedGraph();

        for (String[] row : adjacencyMatrix) {
            String leftLabel = row[0];

            for (int i = 1; i < row.length; i++) {
                String rightLabel = row[i];
                graph.findEdge(leftLabel, rightLabel)
                        .orElseGet(() -> graph.addEdge(leftLabel, rightLabel));
            }
        }

        return graph;
    }

    public Vertex getOrAddVertex(String label) {
        return getVertex(label).orElseGet(() -> addVertex(label));
    }

    private Vertex addVertex(String label) {
        Vertex vertex = vertx(label);
        vertices.add(vertex);
        return vertex;
    }

    public Optional<Vertex> getVertex(String label) {
        return vertices.stream()
                .filter($ -> $.label.equals(label))
                .findFirst();
    }

    public Optional<UndirectedEdge> findEdge(Vertex labelOne, Vertex labelTwo) {
        return edges.stream()
                .filter($ -> $.contains(labelOne, labelTwo))
                .findFirst();
    }

    public Optional<UndirectedEdge> findEdge(String labelOne, String labelTwo) {
        Optional<Vertex> vOne = getVertex(labelOne);
        Optional<Vertex> vTwo = getVertex(labelTwo);

        return vOne.isPresent() && vTwo.isPresent() ?
                findEdge(vOne.get(), vTwo.get()) :
                empty();
    }

    public UndirectedEdge addEdge(String labelOne, String labelTwo) {
        Vertex vOne = getOrAddVertex(labelOne);
        Vertex vTwo = getOrAddVertex(labelTwo);

        UndirectedEdge edge = edge(vOne, vTwo);
        edges.add(edge);

        vOne.adjacentEdges.add(edge);
        vTwo.adjacentEdges.add(edge);

        return edge;
    }

    public UndirectedEdge addEdge(Vertex from, Vertex to) {
        return addEdge(from.label, to.label);
    }

    public List<Vertex> getVertices() {
        return list(vertices);
    }

    public List<UndirectedEdge> getEdges() {
        return list(edges);
    }

    public UndirectedGraph copy() {
        return new UndirectedGraph(vertices, edges);
    }

    public String[][] buildAdjacencyMatrix() {
        return vertices.stream()
                .map($ -> concat($, $.adjacentVertices()))
                .map($ -> $.stream().map(Vertex::label).toArray(String[]::new))
                .toArray(String[][]::new);
    }

    // todo: printing of empty graph
    @Override
    public String toString() {
        return printMatrix(buildAdjacencyMatrix(), VAR_ROW);
    }

    public static class Vertex {

        private final String label;

        private final List<UndirectedEdge> adjacentEdges = new ArrayList<>();

        public Vertex(String label) {
            this.label = label;
        }

        public static Vertex vertx(String label) {
            return new Vertex(label);
        }

        public List<UndirectedEdge> edges() {
            return list(adjacentEdges);
        }

        public String label() {
            return label;
        }

        public List<Vertex> adjacentVertices() {
            return adjacentEdges.stream()
                    .map($ -> $.oppositeTo(this))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(toList());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Vertex)) return false;
            Vertex vertex = (Vertex) o;
            return Objects.equals(label, vertex.label);
        }

        @Override
        public int hashCode() {
            return Objects.hash(label);
        }

        @Override
        public String toString() {
            return "Vertex{" +
                    "label='" + label + '\'' +
                    '}';
        }
    }

    public static class UndirectedEdge {

        private final Set<Vertex> vertices;

        public UndirectedEdge(Vertex from, Vertex to) {
            vertices = set(from, to);
        }

        public boolean isCycle() {
            return isSingleton(vertices);
        }

        public boolean contains(Vertex vertex) {
            return vertices.contains(vertex);
        }

        public boolean contains(Vertex vertexOne, Vertex vertexTwo) {
            return vertices.contains(vertexOne) && vertices.contains(vertexTwo);
        }

        public Set<Vertex> vertices() {
            return set(vertices);
        }

        public Optional<Vertex> oppositeTo(Vertex vertex) {
            if (!contains(vertex)) return empty();
            return vertices.stream()
                    .filter($ -> !vertex.equals($))
                    .findAny();
        }

        public static UndirectedEdge edge(Vertex from, Vertex to) {
            return new UndirectedEdge(from, to);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof UndirectedEdge)) return false;
            UndirectedEdge that = (UndirectedEdge) o;
            return Objects.equals(vertices, that.vertices);
        }

        @Override
        public int hashCode() {
            return Objects.hash(vertices);
        }
    }
}
