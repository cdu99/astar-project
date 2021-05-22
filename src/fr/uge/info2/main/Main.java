package fr.uge.info2.main;

import fr.uge.info2.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length < 1 || args.length > 3) {
            usage();
            return;
        }

        var filename = args[0];
        var numberOfNodes = 0;
        File coFile = new File(filename + ".co");
        File grFile = new File(filename + ".gr");

        Node[] nodes = null;
        Graph graph = null;
        try (var coScan = new Scanner(coFile); var grScan = new Scanner(grFile)) {
            // We are parsing the .co file to get the nodes
            while (coScan.hasNextLine()) {
                var line = coScan.nextLine();
                var values = line.split("\\s+");
                if (values[0].equals("p") && values[1].equals("aux") && values[2].equals("sp")
                        && values[3].equals("co")) {
                    numberOfNodes = Integer.parseInt(values[4]);
                    nodes = new Node[Integer.parseInt(values[4])];
                }
                if (values[0].equals("v")) {
                    var id = Integer.parseInt(values[1]);
                    // We -1 to match .co format to array format (one starting at 1 and the other starting at 0)
                    nodes[id - 1] = new Node(id - 1, (int) Math.ceil((Integer.parseInt(values[2]) * 1.6)),
                            (int) Math.ceil((Integer.parseInt(values[3]) * 1.6)));
                }
            }
            // We are parsing the .gr file to get the graph with the edges
            while (grScan.hasNextLine()) {
                var line = grScan.nextLine();
                var values = line.split("\\s+");
                if (values[0].equals("p") && values[1].equals("sp")) {
                    graph = new AdjGraph(Integer.parseInt(values[2]));
                }
                if (values[0].equals("a")) {
                    graph.addEdge(Integer.parseInt(values[1]) - 1, Integer.parseInt(values[2]) - 1,
                            (int) Math.ceil((Integer.parseInt(values[3]) * 1.6)));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Invalid filename");
            return;
        }
        // To get the .dot file to print the graph
        try (FileWriter writer = new FileWriter("digraph.dot")) {
            writer.write(graph.toGraphviz());
        }

        ShortestPathFromOneVertex shortestPath;
        // If user gave in argument source and destination
        if (args.length == 3) {
            var source = Integer.parseInt(args[1]);
            var destination = Integer.parseInt(args[2]);
            shortestPath = Astar.astar(graph, source, destination, nodes);
            printResult("Astar", source, destination, shortestPath);

        }
        // If there is only 1 argument, the argument is the source and the destination is the number of nodes in the graph
        else if (args.length == 2) {
            shortestPath = Astar.astar(graph, Integer.parseInt(args[1]), numberOfNodes, nodes);
            printResult("Astar", Integer.parseInt(args[1]), numberOfNodes, shortestPath);
        }
        // If there is no source or destination given, we get the shortest path from 1 to the number of nodes in the graph
        else {
            shortestPath = AstarImproved.astar(graph, 1, numberOfNodes, nodes);
            printResult("Astar", 1, numberOfNodes, shortestPath);
        }
    }

    private static void printResult(String algorithm, int source, int destination, ShortestPathFromOneVertex path) {
        System.out.println(algorithm + ": " + path.getSteps() + " steps.");
        System.out.print("Path of length " + path.getWeightOfShortestPathTo(destination) + " from " + source + " to " + destination + " = ");
        path.printShortestPathTo(destination);
    }

    private static void usage() {
        System.out.println("USAGE: Astar <filename> OR Astar <filename> <source> <destination>");
    }
}
