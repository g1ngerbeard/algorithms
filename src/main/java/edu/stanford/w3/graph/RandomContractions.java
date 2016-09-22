package edu.stanford.w3.graph;

import edu.stanford.w3.graph.UndirectedGraph.UndirectedEdge;
import edu.stanford.w3.graph.UndirectedGraph.Vertex;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static edu.stanford.common.Assertions.assertSize;
import static edu.stanford.common.Assertions.assertSizeGt;
import static edu.stanford.common.CommonUtils.immutableSet;
import static edu.stanford.common.CommonUtils.set;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.apache.commons.lang3.StringUtils.LF;

public class RandomContractions {

    public static ContractionResult contract(UndirectedGraph graph) {
        assertSizeGt(1, graph.getVertices());

        return ContractionResult.fromGraph(doContract(graph));
    }

    private static ContractedGraph doContract(UndirectedGraph graph) {
        return doContract(new ContractedGraph(graph));
    }

    private static ContractedGraph doContract(ContractedGraph graph) {
        if (graph.vertices.size() <= 2 || graph.edges.isEmpty()) return graph;

        // select next edge to be contracted
        UndirectedEdge edge = graph.edges.get(RandomUtils.nextInt(0, graph.edges.size()));

        // find vertices in contracted graph
        Set<ContractedVertex> verticesToMerge = edge.vertices()
                .stream()
                .map(graph::findVertex)
                .collect(toSet());

        // remove contracted edge and merge vertices
        graph.vertices.removeAll(verticesToMerge);
        graph.edges.remove(edge);
        graph.vertices.add(merge(verticesToMerge));

        // remove cycles
        List<UndirectedEdge> edgesWithoutCycles = graph.edges.stream()
                .filter($ -> graph.vertices.stream().noneMatch(vrtx -> vrtx.contains($)))
                .collect(toList());

        return doContract(new ContractedGraph(graph.vertices, edgesWithoutCycles));
    }

    private static ContractedVertex merge(Set<ContractedVertex> vertexSet) {
        assertSize(2, vertexSet);
        return vertexSet.stream().reduce(ContractedVertex::merge).get();
    }

    private static class ContractedGraph {

        final List<ContractedVertex> vertices;

        final List<UndirectedEdge> edges;

        ContractedGraph(List<ContractedVertex> vertices, List<UndirectedEdge> edges) {
            this.edges = edges;
            this.vertices = vertices;
        }

        ContractedGraph(UndirectedGraph graph) {
            this.edges = graph.getEdges();
            this.vertices = graph
                    .getVertices()
                    .stream()
                    .map(ContractedVertex::new)
                    .collect(toList());
        }

        ContractedVertex findVertex(Vertex vertex) {
            return vertices.stream()
                    .filter($ -> $.contains(vertex))
                    .findFirst()
                    .get();
        }
    }

    private static class ContractedVertex {
        Set<Vertex> vertices;

        public ContractedVertex(Vertex vertex) {
            this.vertices = set(vertex);
        }

        public boolean contains(Vertex vertex) {
            return vertices.contains(vertex);
        }

        public boolean contains(UndirectedEdge edge) {
            return vertices.containsAll(edge.vertices());
        }

        public ContractedVertex merge(ContractedVertex vertex) {
            vertices.addAll(vertex.vertices);
            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ContractedVertex)) return false;
            ContractedVertex that = (ContractedVertex) o;
            return Objects.equals(vertices, that.vertices);
        }

        @Override
        public int hashCode() {
            return Objects.hash(vertices);
        }
    }

    public static class ContractionResult {

        private final int crossingEdges;

        private final Set<Vertex> leftGroup;

        private final Set<Vertex> rightGroup;

        public ContractionResult(int crossingEdges,
                                 Set<Vertex> leftGroup,
                                 Set<Vertex> rightGroup) {
            this.crossingEdges = crossingEdges;
            this.leftGroup = immutableSet(leftGroup);
            this.rightGroup = immutableSet(rightGroup);
        }

        public Set<Vertex> rightGroup() {
            return rightGroup;
        }

        public int crossingEdges() {
            return crossingEdges;
        }

        public Set<Vertex> leftGroup() {
            return leftGroup;
        }

        private static ContractionResult fromGraph(ContractedGraph graph) {
            List<ContractedVertex> vertices = graph.vertices;

            return new ContractionResult(
                    graph.edges.size(),
                    vertices.get(0).vertices,
                    vertices.get(1).vertices);
        }

        @Override
        public String toString() {
            return "ContractionResult{" + LF +
                    "   crossingEdges=" + crossingEdges + "," + LF +
                    "   leftGroup=" + leftGroup + "," + LF +
                    "   rightGroup=" + rightGroup + LF +
                    '}';
        }
    }
}
