package fr.uge.info2;

import java.util.BitSet;
import java.util.NoSuchElementException;

public class Astar {
    public static ShortestPathFromOneVertex astar(Graph graph, int source, int destination, Node[] nodes) {
        // -1 to source & destination to match .co/.gr format
        destination = destination - 1;
        source = source - 1;
        var numberOfVertices = graph.numberOfVertices();
        var border = new BitSet(numberOfVertices);
        var computed = new BitSet(numberOfVertices);
        var f = new int[numberOfVertices];
        var g = new int[numberOfVertices];
        var h = new int[numberOfVertices];
        var pi = new int[numberOfVertices];
        var steps = 0;

        // Initialization
        for (var i = 0; i < numberOfVertices; i++) {
            f[i] = Integer.MAX_VALUE;
            g[i] = Integer.MAX_VALUE;
            pi[i] = -1;
        }
        var destinationCoordinate = nodes[destination];
        for (var j = 0; j < nodes.length; j++) {
            var node = nodes[j];
            h[j] = (int) Math.sqrt(Math.pow(node.getX() - destinationCoordinate.getX(), 2) +
                    Math.pow(node.getY() - destinationCoordinate.getY(), 2));
        }
        border.set(source);
        computed.set(source);
        g[source] = 0;
        f[source] = g[source] + h[source];

        while (!border.isEmpty()) {
            steps++;
            var x = border.nextSetBit(0);
            // We get the element in border with the smallest value in f[]
            for (var i = x + 1; i < numberOfVertices; i++) {
                if (!border.get(i)) {
                    continue;
                }
                if (f[i] < f[x]) {
                    x = i;
                }
            }
            if (x == destination) {
                return new ShortestPathFromOneVertex(source, g, pi, steps);
            }
            // Remove x from border
            border.set(x, false);
            // For all successors y of x in the graph
            var iterator = graph.edgeIterator(x);
            while (iterator.hasNext()) {
                var edge = iterator.next();
                var weight = edge.getValue();
                var y = edge.getEnd();

                if (computed.get(y)) {
                    if (g[y] > g[x] + weight) { // Better path
                        g[y] = g[x] + weight;
                        f[y] = g[y] + h[y];
                        if (!border.get(y)) { // We need to recompute paths from y
                            border.set(y);
                        }
                    }
                } else {
                    g[y] = g[x] + weight;
                    pi[y] = x;
                    f[y] = g[y] + h[y];
                    border.set(y);
                    computed.set(y);
                }
            }
        }
        throw new NoSuchElementException("No shortest path, " + destination + " is unreachable from " + source);
    }
}
