import javax.crypto.spec.DESedeKeySpec;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class Algorithms {
    public void displaySolutionInfo(String algorithmName, long duration, int numOfVisitedState, int pathDepth, List<char[][]> path, State state) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        JFrame frame = new JFrame("Solution");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(screenSize.width, screenSize.height);
        frame.setLayout(new GridLayout(3, 3));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        JLabel descriptionLabel = new JLabel("The solution using " + algorithmName + " algorithm can be like this:");
        descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        descriptionLabel.setVerticalAlignment(SwingConstants.CENTER);

        Font labelFont = descriptionLabel.getFont();
        descriptionLabel.setFont(new Font(labelFont.getName(), Font.PLAIN, 22));

        JLabel infoLabel = new JLabel("<html>Execution Time: " + duration + " milliseconds<br/>Number of visited states: " + numOfVisitedState + "<br/>Solution Depth: " + pathDepth);
        infoLabel.setHorizontalAlignment(SwingConstants.LEFT);
        infoLabel.setVerticalAlignment(SwingConstants.CENTER);
        infoLabel.setFont(new Font(labelFont.getName(), Font.PLAIN, 20));

        JPanel pathPanel = new JPanel(new GridLayout(1, 1));

        final int[] currentIndex = {1};

        state.displayBoard(pathPanel, path.get(currentIndex[0]), state.getRows(), state.getColumns());

        JPanel twoButton = new JPanel();
        JButton prevButton = new JButton("< Previous");
        JButton nextButton = new JButton("Next >");
        twoButton.add(prevButton);
        twoButton.add(nextButton);

        prevButton.addActionListener(e -> {
            if (currentIndex[0] > 1) {
                currentIndex[0]--;
                state.displayBoard(pathPanel, path.get(currentIndex[0]), state.getRows(), state.getColumns());
            }
        });

        nextButton.addActionListener(e -> {
            if (currentIndex[0] <= path.size() - 2) {
                currentIndex[0]++;
                state.displayBoard(pathPanel, path.get(currentIndex[0]), state.getRows(), state.getColumns());
            }
        });

        frame.add(new Label());
        frame.add(descriptionLabel);
        frame.add(new Label());
        frame.add(new Label());
        frame.add(pathPanel);
        frame.add(infoLabel);
        frame.add(new Label());
        frame.add(twoButton);
        frame.add(new Label());

        frame.setVisible(true);
    }

    public void displayMessage(String message) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        JFrame frame = new JFrame("Logic Magnets");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(screenSize.width, screenSize.height);
        frame.setLayout(new GridLayout(1, 1));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        JLabel descriptionLabel = new JLabel(message);
        descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        descriptionLabel.setVerticalAlignment(SwingConstants.CENTER);

        Font labelFont = descriptionLabel.getFont();
        descriptionLabel.setFont(new Font(labelFont.getName(), Font.PLAIN, 22));

        frame.add(descriptionLabel);
        frame.setVisible(true);
    }

    public void BFS(char[][] initialState, State state, Movement move) {
        long startTime = System.nanoTime();

        Queue<char[][]> queue = new LinkedList<>();
        List<char[][]> visited = new ArrayList<>();
        List<Node> Nodes = new ArrayList<>();

        Node root = new Node(state.getRows(), state.getColumns());
        root.createNode(null, initialState);

        Nodes.add(root);
        queue.add(initialState);

        while (!queue.isEmpty()) {
            char[][] top = queue.poll();

            visited.add(top);

            if (move.checkGoalState(top)) {
                System.out.println("\nFound The Final State IN BFS.");

                List<char[][]> path = new ArrayList<>();
                char[][] child = new char[state.getRows() + 5][state.getColumns() + 5];

                path.add(top);

                for (int i = 1; i <= state.getRows(); i++) {
                    if (state.getColumns() >= 0) System.arraycopy(top[i], 1, child[i], 1, state.getColumns());
                }

                while (child != null) {
                    for (Node current : Nodes) {
                        if (Arrays.deepEquals(child, current.getState())) {
                            path.add(current.getParent());

                            if (current.getParent() != null) {
                                for (int i = 1; i <= state.getRows(); i++) {
                                    for (int j = 1; j <= state.getColumns(); j++) {
                                        child[i][j] = current.getParent()[i][j];
                                    }
                                }
                            } else {
                                child = null;
                            }

                            break;
                        }
                    }
                }

                Collections.reverse(path);

                long endTime = System.nanoTime();
                long duration = (endTime - startTime) / 1000000;

                System.out.println("Execution Time: " + duration + " milliseconds");
                System.out.println("Number of visited states: " + visited.size());
                System.out.println("Solution Depth: " + (path.size() - 2) + "\n\nThis is the path");

                for (char[][] current : path) {
                    if (current != null) {
                        for (int i = 1; i <= state.getRows(); i++) {
                            for (int j = 1; j <= state.getColumns(); j++) {
                                System.out.print(current[i][j] + "    ");
                            }

                            System.out.println();
                        }

                        System.out.println("=========================");
                    }
                }

                displaySolutionInfo("BFS", duration, visited.size(), (path.size() - 2), path, state);
                break;
            }

            List<char[][]> allStates = state.generateALlSates(top, move);

            for (char[][] currentFromList : allStates) {
                boolean addToQueue = true;

                for (char[][] currentFromVisited : visited) {
                    if (Arrays.deepEquals(currentFromList, currentFromVisited)) {
                        addToQueue = false;

                        break;
                    }
                }

                if (addToQueue) {
                    Node node = new Node(state.getRows(), state.getColumns());
                    node.createNode(top, currentFromList);

                    Nodes.add(node);
                    queue.add(currentFromList);
                }
            }
        }
    }

    static class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node node1, Node node2) {
            return node1.getCost() - node2.getCost();
        }
    }

    public void UCS(char[][] initialState, State state, Movement move) {
        long startTime = System.nanoTime();
        NodeComparator comparator = new NodeComparator();
        PriorityQueue<Node> queue = new PriorityQueue<>(10, comparator);

        List<char[][]> visited = new ArrayList<>();
        List<Node> Nodes = new ArrayList<>();

        Node root = new Node(state.getRows(), state.getColumns());
        root.setCost(0);
        root.createNode(null, initialState);

        Nodes.add(root);
        queue.add(root);

        while (!queue.isEmpty()) {
            Node top = queue.poll();

            visited.add(top.getState());

            if (move.checkGoalState(top.getState())) {
                System.out.println("\nFound The Final State IN UCS.");

                List<char[][]> path = new ArrayList<>();
                char[][] child = new char[state.getRows() + 5][state.getColumns() + 5];

                path.add(top.getState());

                for (int i = 1; i <= state.getRows(); i++) {
                    if (state.getColumns() >= 0)
                        System.arraycopy(top.getState()[i], 1, child[i], 1, state.getColumns());
                }

                while (child != null) {
                    for (Node current : Nodes) {
                        if (Arrays.deepEquals(child, current.getState())) {
                            path.add(current.getParent());

                            if (current.getParent() != null) {
                                for (int i = 1; i <= state.getRows(); i++) {
                                    for (int j = 1; j <= state.getColumns(); j++) {
                                        child[i][j] = current.getParent()[i][j];
                                    }
                                }
                            } else {
                                child = null;
                            }

                            break;
                        }
                    }
                }

                Collections.reverse(path);

                long endTime = System.nanoTime();
                long duration = (endTime - startTime) / 1000000;

                System.out.println("Execution Time: " + duration + " milliseconds");
                System.out.println("Number of visited states: " + visited.size());
                System.out.println("Solution Depth: " + (path.size() - 2) + "\n\nThis is the path");

                for (char[][] current : path) {
                    if (current != null) {
                        for (int i = 1; i <= state.getRows(); i++) {
                            for (int j = 1; j <= state.getColumns(); j++) {
                                System.out.print(current[i][j] + "    ");
                            }

                            System.out.println();
                        }

                        System.out.println("=========================");
                    }
                }

                displaySolutionInfo("UCS", duration, visited.size(), (path.size() - 2), path, state);
                break;
            }

            List<char[][]> allStates = state.generateALlSates(top.getState(), move);

            for (char[][] currentFromList : allStates) {
                boolean addToQueue = true;

                for (char[][] currentFromVisited : visited) {
                    if (Arrays.deepEquals(currentFromList, currentFromVisited)) {
                        addToQueue = false;

                        break;
                    }
                }

                if (addToQueue) {
                    Node node = new Node(state.getRows(), state.getColumns());
                    node.setCost(top.getCost() + 1);
                    node.createNode(top.getState(), currentFromList);

                    Nodes.add(node);
                    queue.add(node);
                }
            }
        }
    }

    /* This Algorithm Make Node's Cost += 1 If R Magnet Was Moved
    or Cost += 2 If A Magnet Was Moved */
    public void UCSAccordingToMagnetType(char[][] initialState, State state, Movement move) {
        long startTime = System.nanoTime();
        NodeComparator comparator = new NodeComparator();
        PriorityQueue<Node> queue = new PriorityQueue<>(10, comparator);

        List<char[][]> visited = new ArrayList<>();
        List<Node> Nodes = new ArrayList<>();

        Node root = new Node(state.getRows(), state.getColumns());
        root.setCost(0);
        root.createNode(null, initialState);

        Nodes.add(root);
        queue.add(root);

        while (!queue.isEmpty()) {
            Node top = queue.poll();

            visited.add(top.getState());

            if (move.checkGoalState(top.getState())) {
                System.out.println("\nFound The Final State IN UCS According to the Magnet's type.");

                List<char[][]> path = new ArrayList<>();
                char[][] child = new char[state.getRows() + 5][state.getColumns() + 5];

                path.add(top.getState());

                for (int i = 1; i <= state.getRows(); i++) {
                    if (state.getColumns() >= 0)
                        System.arraycopy(top.getState()[i], 1, child[i], 1, state.getColumns());
                }

                while (child != null) {
                    for (Node current : Nodes) {
                        if (Arrays.deepEquals(child, current.getState())) {
                            path.add(current.getParent());

                            if (current.getParent() != null) {
                                for (int i = 1; i <= state.getRows(); i++) {
                                    for (int j = 1; j <= state.getColumns(); j++) {
                                        child[i][j] = current.getParent()[i][j];
                                    }
                                }
                            } else {
                                child = null;
                            }

                            break;
                        }
                    }
                }

                Collections.reverse(path);

                long endTime = System.nanoTime();
                long duration = (endTime - startTime) / 1000000;

                System.out.println("Execution Time: " + duration + " milliseconds");
                System.out.println("Number of visited states: " + visited.size());
                System.out.println("Solution Depth: " + (path.size() - 2) + "\n\nThis is the path");

                for (char[][] current : path) {
                    if (current != null) {
                        for (int i = 1; i <= state.getRows(); i++) {
                            for (int j = 1; j <= state.getColumns(); j++) {
                                System.out.print(current[i][j] + "    ");
                            }

                            System.out.println();
                        }

                        System.out.println("=========================");
                    }
                }

                displaySolutionInfo("UCS", duration, visited.size(), (path.size() - 2), path, state);
                break;
            }

            List<Node> allStates = state.generateALlSatesAccordingToMagnetType(top, move);

            for (Node currentFromList : allStates) {
                boolean addToQueue = true;

                for (char[][] currentFromVisited : visited) {
                    if (Arrays.deepEquals(currentFromList.getState(), currentFromVisited)) {
                        addToQueue = false;

                        break;
                    }
                }

                if (addToQueue) {
                    Nodes.add(currentFromList);
                    queue.add(currentFromList);
                }
            }
        }
    }

    public int calculateManhattanDistance(char[][] board, State state, Movement move) {
        int totalCost = 0;

        for (int i = 1; i <= state.getRows(); i++) {
            for (int j = 1; j <= state.getColumns(); j++) {
                if (board[i][j] == 'A' || board[i][j] == 'R' || board[i][j] == 'S') {
                    int minDistance = Integer.MAX_VALUE;

                    for (CustomPair currentGoal : move.goals) {
                        minDistance = Math.min(minDistance, Math.abs(i - currentGoal.first()) + Math.abs(j - currentGoal.second()));
                    }

                    totalCost += minDistance;
                }
            }
        }

        return totalCost;
    }

    public void hillClimbing(char[][] initialState, State state, Movement move) {
        long startTime = System.nanoTime();
        boolean answer = true;

        char[][] currentState = new char[state.getRows() + 5][state.getColumns() + 5];
        for (int i = 1; i <= state.getRows(); i++) {
            if (state.getColumns() >= 0) System.arraycopy(initialState[i], 1, currentState[i], 1, state.getColumns());
        }

        int currentDistance = calculateManhattanDistance(initialState, state, move);
        List<char[][]> path = new ArrayList<>();

        path.add(null);
        path.add(initialState);

        while (currentDistance != 0) {
            List<char[][]> allStates = state.generateALlSates(currentState, move);
            char[][] nextState = new char[state.getRows() + 5][state.getColumns() + 5];
            int nextDistance = Integer.MAX_VALUE;

            for (char[][] next : allStates) {
                int tmp = calculateManhattanDistance(next, state, move);

                if (tmp < nextDistance) {
                    nextDistance = tmp;

                    for (int i = 1; i <= state.getRows(); i++) {
                        if (state.getColumns() >= 0) System.arraycopy(next[i], 1, nextState[i], 1, state.getColumns());
                    }
                }
            }

            path.add(nextState);

            if (currentDistance <= nextDistance) {
                answer = false;

                break;
            } else {
                currentDistance = nextDistance;
            }
        }

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;

        if (!answer) {
            System.out.println("Solution Was Not Found!");
            displayMessage("Solution was not found using Hill Climbing Algorithm.");
            return;
        }

        System.out.println("Execution Time: " + duration + " milliseconds");
        System.out.println("Solution Depth: " + (path.size() - 1) + "\n\nThis is the path");
        System.out.println("\nFound The Final State IN Hill Climbing.");

        for (char[][] current : path) {
            if (current != null) {
                for (int i = 1; i <= state.getRows(); i++) {
                    for (int j = 1; j <= state.getColumns(); j++) {
                        System.out.print(current[i][j] + "    ");
                    }

                    System.out.println();
                }

                System.out.println("=========================");
            }
        }

        displaySolutionInfo("Hill Climbing", duration, (path.size() - 2), (path.size() - 2), path, state);
    }

    public void AStar(char[][] initialState, State state, Movement move) {
        long startTime = System.nanoTime();
        NodeComparator comparator = new NodeComparator();
        PriorityQueue<Node> queue = new PriorityQueue<>(10, comparator);

        List<char[][]> visited = new ArrayList<>();
        List<Node> Nodes = new ArrayList<>();

        Node root = new Node(state.getRows(), state.getColumns());
        root.setCost(0);
        root.createNode(null, initialState);

        Nodes.add(root);
        queue.add(root);

        while (!queue.isEmpty()) {
            Node top = queue.poll();

            visited.add(top.getState());

            if (move.checkGoalState(top.getState())) {
                System.out.println("\nFound The Final State IN A* According to the Magnet's type.");

                List<char[][]> path = new ArrayList<>();
                char[][] child = new char[state.getRows() + 5][state.getColumns() + 5];

                path.add(top.getState());

                for (int i = 1; i <= state.getRows(); i++) {
                    if (state.getColumns() >= 0)
                        System.arraycopy(top.getState()[i], 1, child[i], 1, state.getColumns());
                }

                while (child != null) {
                    for (Node current : Nodes) {
                        if (Arrays.deepEquals(child, current.getState())) {
                            path.add(current.getParent());

                            if (current.getParent() != null) {
                                for (int i = 1; i <= state.getRows(); i++) {
                                    for (int j = 1; j <= state.getColumns(); j++) {
                                        child[i][j] = current.getParent()[i][j];
                                    }
                                }
                            } else {
                                child = null;
                            }

                            break;
                        }
                    }
                }

                Collections.reverse(path);

                long endTime = System.nanoTime();
                long duration = (endTime - startTime) / 1000000;

                System.out.println("Execution Time: " + duration + " milliseconds");
                System.out.println("Number of visited states: " + visited.size());
                System.out.println("Solution Depth: " + (path.size() - 2) + "\n\nThis is the path");

                for (char[][] current : path) {
                    if (current != null) {
                        for (int i = 1; i <= state.getRows(); i++) {
                            for (int j = 1; j <= state.getColumns(); j++) {
                                System.out.print(current[i][j] + "    ");
                            }

                            System.out.println();
                        }

                        System.out.println("=========================");
                    }
                }

                displaySolutionInfo("A*", duration, (path.size() - 2), (path.size() - 2), path, state);
                break;
            }

            List<Node> allStates = state.generateALlSatesAccordingToMagnetType(top, move);

            for (Node current : allStates) {
                int add = calculateManhattanDistance(current.getState(), state, move);
                current.setCost(current.getCost() + add);
            }

            for (Node currentFromList : allStates) {
                boolean addToQueue = true;

                for (char[][] currentFromVisited : visited) {
                    if (Arrays.deepEquals(currentFromList.getState(), currentFromVisited)) {
                        addToQueue = false;

                        break;
                    }
                }

                if (addToQueue) {
                    Nodes.add(currentFromList);
                    queue.add(currentFromList);
                }
            }
        }
    }
}
