package fr.uge.info2;

import java.util.*;

public class AstarImproved {
    public static ShortestPathFromOneVertex astar(Graph graph, int source, int destination, Node[] nodes) {
        // -1 to source & destination to match .co/.gr format
        destination = destination - 1;
        source = source - 1;
        var numberOfVertices = graph.numberOfVertices();
        var border = new BitSet(numberOfVertices);
        var computed = new BitSet(numberOfVertices);
        var f = new PriorityQueue<Integer>();
        var fPosition = new HashMap<Integer, Integer>();
        var g = new int[numberOfVertices];
        var h = new int[numberOfVertices];
        var pi = new int[numberOfVertices];
        var steps = 0;

        // Initialization
        for (var i = 0; i < numberOfVertices; i++) {
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
        f.add(g[source] + h[source]);
        // We keep the node position using a HashMap for KEY the node value and VALUE the node position
        fPosition.put(g[source] + h[source], source);


        while (!border.isEmpty()) {
            steps++;
            // We get the element in border with the smallest value in f
            var getSmallest = false;
            int x = 0;
            while (!getSmallest) {
                if (border.get(fPosition.get(f.peek()))) {
                    x = fPosition.get(f.poll());
                    getSmallest = true;
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
                        f.add(g[y] + h[y]);
                        fPosition.put(g[y] + h[y], y);
                        if (!border.get(y)) { // We need to recompute paths from y
                            border.set(y);
                        }
                    }
                } else {
                    g[y] = g[x] + weight;
                    pi[y] = x;
                    f.add(g[y] + h[y]);
                    fPosition.put(g[y] + h[y], y);
                    border.set(y);
                    computed.set(y);
                }
            }
        }
        throw new NoSuchElementException("No shortest path, " + destination + " is unreachable from " + source);
    }
}
