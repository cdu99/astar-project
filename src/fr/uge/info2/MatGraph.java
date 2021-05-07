package fr.uge.info2;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.Consumer;

public class MatGraph implements Graph {
    private final int[][] mat;
    private final int n;
    private int nbOfEdges;

    public MatGraph(int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        this.mat = new int[n][n];
        this.nbOfEdges = 0;
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
            throw new IllegalArgumentException();
        }

        nbOfEdges++;
        mat[i][j] = value;
    }

    @Override
    public boolean isEdge(int i, int j) {
        if (i < 0 || i > n || j < 0 || j > n) {
            throw new IllegalArgumentException();
        }
        return mat[i][j] != 0;
    }

    @Override
    public int getWeight(int i, int j) {
        if (i < 0 || i > n || j < 0 || j > n) {
            throw new IllegalArgumentException();
        }
        return mat[i][j];
    }

    @Override
    public Iterator<Edge> edgeIterator(int i) {
        if (i >= n) {
            throw new IllegalArgumentException();
        }
        return new Iterator<>() {
            private Optional<Edge> current = getNextEdge(0, mat[i], i);

            @Override
            public boolean hasNext() {
                return current.isPresent();
            }

            @Override
            public Edge next() {
                if (!hasNext()) {
                    throw new UnsupportedOperationException();
                }
                var toReturn = current;
                current = getNextEdge(current.get().getEnd() + 1, mat[i], i);
                return toReturn.orElseThrow();
            }
        };
    }

    private Optional<Edge> getNextEdge(int current, int[] row, int start) {
        if (current < row.length) {
            for (int i = current; i < row.length; i++) {
                if (row[i] != 0) {
                    return Optional.of(new Edge(start, i, row[i]));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public void forEachEdge(int i, Consumer<Edge> consumer) {
        for (var j = 0; j < n; j++) {
            if (mat[i][j] != 0) {
                var edge = new Edge(i, j, mat[i][j]);
                consumer.accept(edge);
            }
        }
    }

    // dot -Tpng digraph.dot -o output.png
    @Override
    public String toGraphviz() {
        var builder = new StringBuilder("digraph G {\n\t");
        for (int i = 0; i < n; i++) {
            builder.append(i).append(";\n\t");
            forEachEdge(i, (edge) -> {
                builder
                        .append(edge.getStart())
                        .append(" -> ")
                        .append(edge.getEnd())
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
