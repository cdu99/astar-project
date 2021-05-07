package fr.uge.info2;

public class Node {
    private final int id;
    private final int x;
    private final int y;

    public Node(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        boolean result;
        if ((o == null) || (getClass() != o.getClass())) {
            result = false;
        } else {
            Node node = (Node) o;
            result = id == node.getId() && x == node.getX() && y == node.getY();
        }
        return result;
    }

    @Override
    public String toString() {
        return id + " (" + x + ", " + y + ")";
    }
}
