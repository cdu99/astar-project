package fr.uge.info2;

import java.util.BitSet;

public class Dijkstra {
    public static ShortestPathFromOneVertex dijkstra(Graph g, int source) {
        source = source - 1;
        var steps = 0;
        var numberOfVertices = g.numberOfVertices();
        var todo = new BitSet(numberOfVertices);
        var d = new int[numberOfVertices];
        var pi = new int[numberOfVertices];
        for (var i = 0; i < numberOfVertices; i++) {
            d[i] = Integer.MAX_VALUE;
            pi[i] = -1;
            todo.set(i);
        }
        d[source] = 0;

        while (!todo.isEmpty()) {
            steps++;
            var s = todo.nextSetBit(0);
            // Get the element in todo that minimize d
            for (var k = s + 1; k < g.numberOfVertices(); k++) {
                if (!todo.get(k)) {
                    continue;
                }
                if (d[k] < d[s]) {
                    s = k;
                }
            }
            todo.set(s, false);

            if (d[s] == Integer.MAX_VALUE) {
                continue;
            }

            var iterator = g.edgeIterator(s);
            while (iterator.hasNext()) {
                var edge = iterator.next();
                var edgeWeight = edge.getValue();
                var edgeEnd = edge.getEnd();
                if (d[s] + edgeWeight < d[edgeEnd]) {
                    d[edgeEnd] = d[s] + edgeWeight;
                    pi[edgeEnd] = s;
                }
            }
        }
        return new ShortestPathFromOneVertex(source, d, pi, steps);
    }
}
