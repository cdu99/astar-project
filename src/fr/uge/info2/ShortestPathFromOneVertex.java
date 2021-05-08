package fr.uge.info2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

public class ShortestPathFromOneVertex {
    private final int source;
    private final int[] d;
    private final int[] pi;
    private final int steps;

    ShortestPathFromOneVertex(int vertex, int[] d, int[] pi, int steps) {
        this.source = vertex;
        this.d = d;
        this.pi = pi;
        this.steps = steps;
    }

    @Override
    public String toString() {
        // +1 to source & pi if it has predecessors to match .co/.gr format. -1 means no path/no predecessor
        return (source + 1) + " " +
                Arrays.toString(Arrays.stream(d).map(i -> (i == Integer.MAX_VALUE ? -1 : i)).toArray()) + " " +
                Arrays.toString(Arrays.stream(pi).map(i -> (i == -1 ? i : i + 1)).toArray());
    }

    public void printShortestPathTo(int destination) {
        destination = destination - 1;
        ArrayList<Integer> path = new ArrayList<>();

        if (pi[destination] == -1) {
            System.out.println((destination + 1) + " is unreachable from " + (source + 1));
            return;
        }
        var current = destination;
        while (current != source) {
            path.add(current);
            current = pi[current];
        }
        path.add(current);

        Collections.reverse(path);
        System.out.println(path.stream()
                .map(i -> i + 1).map(Objects::toString).collect(Collectors.joining(" --> ")));
    }

    public int getSteps() {
        return steps;
    }

    public int getWeightOfShortestPathTo(int destination) {
        return d[destination - 1];
    }
}
