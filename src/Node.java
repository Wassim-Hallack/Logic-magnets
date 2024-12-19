public class Node {
    private final int rows, columns;
    private int cost;
    private char[][] parent;
    private final char[][] state;

    public Node(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.cost = 0;

        parent = new char[this.rows + 5][this.columns + 5];
        state = new char[this.rows + 5][this.columns + 5];
    }

    public char[][] getParent() {
        return parent;
    }

    public char[][] getState() {
        return state;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void createNode(char[][] parent, char[][] child) {
        if (parent != null) {
            for (int i = 1; i <= rows; i++) {
                if (columns >= 0) System.arraycopy(parent[i], 1, this.parent[i], 1, columns);
            }
        } else {
            this.parent = null;
        }

        for (int i = 1; i <= rows; i++) {
            if (columns >= 0) System.arraycopy(child[i], 1, this.state[i], 1, columns);
        }
    }
}
