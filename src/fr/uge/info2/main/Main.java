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
                    nodes[id - 1] = new Node(id - 1, Integer.parseInt(values[2]), Integer.parseInt(values[3]));
                }
            }

            while (grScan.hasNextLine()) {
                var line = grScan.nextLine();
                var values = line.split("\\s+");
                if (values[0].equals("p") && values[1].equals("sp")) {
                    graph = new MatGraph(Integer.parseInt(values[2]));
                }
                if (values[0].equals("a")) {
                    graph.addEdge(Integer.parseInt(values[1]) - 1, Integer.parseInt(values[2]) - 1, Integer.parseInt(values[3]));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Invalid filename");
            return;
        }
        // To print the graph .dot
        try (FileWriter writer = new FileWriter("digraph.dot")) {
            writer.write(graph.toGraphviz());
        }

        ShortestPathFromOneVertex shortestPath;
        if (args.length == 3) {
            shortestPath = Astar.astar(graph, Integer.parseInt(args[1]), Integer.parseInt(args[2]), nodes);
            System.out.println(shortestPath);
        } else if (args.length == 2) {
            shortestPath = Astar.astar(graph, Integer.parseInt(args[1]), numberOfNodes, nodes);
            System.out.println(shortestPath);
        } else {
            shortestPath = Astar.astar(graph, 1, numberOfNodes, nodes);
            System.out.println(shortestPath);
        }

        shortestPath.printShortestPathTo(6);
    }

    private static void usage() {
        System.out.println("USAGE: Astar <filename> <source> <destination>");
    }
}
