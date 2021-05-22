package fr.uge.info2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AstarTest {
    public Graph initGraph() {
        var adjGraph = new AdjGraph(7);
        adjGraph.addEdge(0, 3, (int) Math.ceil(87 * 1.6));
        adjGraph.addEdge(0, 5, (int) Math.ceil(55 * 1.6));
        adjGraph.addEdge(1, 0, (int) Math.ceil(41 * 1.6));
        adjGraph.addEdge(1, 2, (int) Math.ceil(88 * 1.6));
        adjGraph.addEdge(1, 6, (int) Math.ceil(65 * 1.6));
        adjGraph.addEdge(1, 4, (int) Math.ceil(66 * 1.6));
        adjGraph.addEdge(2, 4, (int) Math.ceil(38 * 1.6));
        adjGraph.addEdge(2, 3, (int) Math.ceil(27 * 1.6));
        adjGraph.addEdge(2, 1, (int) Math.ceil(84 * 1.6));
        adjGraph.addEdge(3, 1, (int) Math.ceil(52 * 1.6));
        adjGraph.addEdge(3, 0, (int) Math.ceil(51 * 1.6));
        adjGraph.addEdge(4, 0, (int) Math.ceil(74 * 1.6));
        adjGraph.addEdge(5, 0, (int) Math.ceil(78 * 1.6));
        adjGraph.addEdge(5, 4, (int) Math.ceil(76 * 1.6));
        adjGraph.addEdge(5, 1, (int) Math.ceil(26 * 1.6));
        adjGraph.addEdge(6, 4, (int) Math.ceil(68 * 1.6));
        adjGraph.addEdge(6, 5, (int) Math.ceil(25 * 1.6));
        adjGraph.addEdge(6, 1, (int) Math.ceil(56 * 1.6));
        adjGraph.addEdge(6, 3, (int) Math.ceil(101 * 1.6));
        adjGraph.addEdge(6, 0, (int) Math.ceil(83 * 1.6));
        return adjGraph;
    }

    public Node[] initNodes() {
        var nodes = new Node[7];
        nodes[0] = new Node(0, (int) Math.ceil(86 * 1.6), (int) Math.ceil(40 * 1.6));
        nodes[1] = new Node(1, (int) Math.ceil(54 * 1.6), (int) Math.ceil(36 * 1.6));
        nodes[2] = new Node(2, (int) Math.ceil(41 * 1.6), (int) Math.ceil(82 * 1.6));
        nodes[3] = new Node(3, (int) Math.ceil(19 * 1.6), (int) Math.ceil(81 * 1.6));
        nodes[4] = new Node(4, (int) Math.ceil(76 * 1.6), (int) Math.ceil(94 * 1.6));
        nodes[5] = new Node(5, (int) Math.ceil(56 * 1.6), (int) Math.ceil(15 * 1.6));
        nodes[6] = new Node(6, (int) Math.ceil(30 * 1.6), (int) Math.ceil(16 * 1.6));
        return nodes;
    }

    @Test
    public void should_get_the_same_shortest_path_as_dijkstra() {
        var result1 = Astar.astar(initGraph(), 1, 7, initNodes());
        var result2 = Dijkstra.dijkstra(initGraph(), 1);

        assertEquals(result1.getWeightOfShortestPathTo(7), result2.getWeightOfShortestPathTo(7));
    }
}