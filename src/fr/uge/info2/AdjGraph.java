package fr.uge.info2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.function.Consumer;

public final class AdjGraph implements Graph {
    private final ArrayList<LinkedList<Edge>> adj;
    private final int n;
    private int nbOfEdges;

    public AdjGraph(int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        this.adj = new ArrayList<>(n);
        for (var i = 0; i < n; i++) {
            adj.add(new LinkedList<>());
        }
        this.nbOfEdges = 0;
        this.n = n;
    }

    @Override
    public int numberOfEdges() {
        return nbOfEdges;
    }

    @Override
    public int numberOfVertices() {
        return n;
    }

    @Override
    public void addEdge(int i, int j, int value) {
        if (i < 0 || i > n || j < 0 || j > n) {
            throw new IndexOutOfBoundsException();
        }
        var linkedList = adj.get(i);
        if (isEdge(i, j)) {
            throw new IllegalStateException("Edge is already present");
        }
        linkedList.add(new Edge(i, j, value));
    }

    @Override
    public boolean isEdge(int i, int j) {
        if (i < 0 || i > n || j < 0 || j > n) {
            throw new IndexOutOfBoundsException();
        }
        var linkedList = adj.get(i);
        return linkedList.stream().anyMatch(e -> e.getEnd() == j);
    }

    @Override
    public int getWeight(int i, int j) {
        if (i < 0 || i > n || j < 0 || j > n) {
            throw new IndexOutOfBoundsException();
        }
        var linkedList = adj.get(i);
        return linkedList.stream()
                .filter(e -> e.getEnd() == j)
                .findFirst()
                .orElseThrow()
                .getValue();
    }

    @Override
    public Iterator<Edge> edgeIterator(int i) {
        if (i < 0 || i > n) {
            throw new IndexOutOfBoundsException();
        }
        return adj.get(i).iterator();
    }

    @Override
    public void forEachEdge(int i, Consumer<Edge> consumer) {
        Objects.checkIndex(i, adj.size());
        adj.get(i).forEach(consumer);
    }

    @Override
    public String toGraphviz() {
        var builder = new StringBuilder("digraph G {\n\t");
        for (int i = 0; i < n; i++) {
            builder.append(i + 1).append(";\n\t");
            forEachEdge(i, (edge) -> {
                builder
                        .append(edge.getStart() + 1)
                        .append(" -> ")
                        .append(edge.getEnd() + 1)
                        .append("[ label=\"")
                        .append(edge.getValue())
                        .append("\" ] ;\n\t");
            });
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append("}");
        return builder.toString();
    }
}