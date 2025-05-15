import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Algorithms {
    public void displaySolutionInfo(String algorithmName, long duration, int numOfVisitedState, int pathDepth, List<char[][]> path, User user, State state, Level level, Movement movement) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        JFrame frame = new JFrame("Solution Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(screenSize.width, screenSize.height);
        frame.setLayout(new BorderLayout());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // --- Top Panel with Back Button and Title ---
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        topPanel.setBackground(new Color(245, 245, 245));

        JButton backButton = new JButton("← Back");
        backButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        backButton.setFocusPainted(false);
        backButton.setBackground(new Color(230, 230, 230));
        backButton.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        backButton.addActionListener(e -> {
            frame.dispose();
            user.inputMood(state, movement, level);
        });

        JLabel titleLabel = new JLabel("Solution Using " + algorithmName);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(new Color(50, 50, 50));

        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.add(titleLabel, BorderLayout.CENTER);

        // --- Path Panel ---
        JPanel pathPanel = new JPanel(new GridLayout(1, 1));
        final int[] currentIndex = {1};
        state.displayBoard(pathPanel, path.get(currentIndex[0]), state.getRows(), state.getColumns(), movement);

        // --- Info Panel (Right Side) ---
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 40, 40));

        JLabel infoLabel = new JLabel("<html><b>Execution Time:</b> " + duration + " ms<br/><br/>" +
                "<b>Visited States:</b> " + numOfVisitedState + "<br/><br/>" +
                "<b>Solution Depth:</b> " + pathDepth + "</html>");
        infoLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        infoLabel.setForeground(new Color(60, 60, 60));

        infoPanel.add(infoLabel);

        // --- Navigation Buttons ---
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        navPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JButton prevButton = new JButton("← Previous");
        JButton nextButton = new JButton("Next →");

        Font navFont = new Font("SansSerif", Font.BOLD, 16);
        prevButton.setFont(navFont);
        nextButton.setFont(navFont);

        prevButton.setFocusPainted(false);
        nextButton.setFocusPainted(false);

        prevButton.setBackground(new Color(220, 220, 220));
        nextButton.setBackground(new Color(66, 133, 244));
        nextButton.setForeground(Color.WHITE);

        prevButton.addActionListener(e -> {
            if (currentIndex[0] > 1) {
                currentIndex[0]--;
                state.displayBoard(pathPanel, path.get(currentIndex[0]), state.getRows(), state.getColumns(), movement);
            }
        });

        nextButton.addActionListener(e -> {
            if (currentIndex[0] < path.size() - 1) {
                currentIndex[0]++;
                state.displayBoard(pathPanel, path.get(currentIndex[0]), state.getRows(), state.getColumns(), movement);
            }
        });

        navPanel.add(prevButton);
        navPanel.add(nextButton);

        // --- Center Panel with Path & Navigation ---
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 20));
        centerPanel.add(pathPanel, BorderLayout.CENTER);
        centerPanel.add(navPanel, BorderLayout.SOUTH);

        // --- Add Panels to Frame ---
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(infoPanel, BorderLayout.EAST);

        frame.setVisible(true);
    }


    public void displayMessage(String message, User user, State state, Movement move, Level level) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        JFrame frame = new JFrame("Logic Magnets - Message");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(screenSize.width, screenSize.height);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.WHITE);

        // --- Top Bar with Back Button ---
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JButton backButton = new JButton("← Back");
        backButton.setFont(new Font("SansSerif", Font.PLAIN, 18));
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        backButton.setBackground(new Color(240, 240, 240));
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        backButton.addActionListener(e -> {
            frame.dispose();

            user.inputMood(state, move, level); // Go back to the previous screen
        });

        topPanel.add(backButton, BorderLayout.WEST);

        // --- Message Panel ---
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        messagePanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
        messagePanel.setBackground(new Color(250, 250, 250));

        JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>" + message + "</div></html>");
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setVerticalAlignment(SwingConstants.CENTER);
        messageLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        messageLabel.setForeground(new Color(50, 50, 50));

        messagePanel.add(Box.createVerticalGlue());
        messagePanel.add(messageLabel);
        messagePanel.add(Box.createVerticalGlue());

        // --- Add components to frame ---
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(messagePanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }


    public void BFS(char[][] initialState, User user, State state, Level level, Movement move) {
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

                displaySolutionInfo("BFS", duration, visited.size(), (path.size() - 2), path, user, state, level, move);
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

    public void UCS(char[][] initialState, User user, State state, Level level, Movement move) {
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

                displaySolutionInfo("UCS", duration, visited.size(), (path.size() - 2), path, user, state, level, move);
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
    public void UCSAccordingToMagnetType(char[][] initialState, User user, State state, Level level, Movement move) {
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

                displaySolutionInfo("UCS", duration, visited.size(), (path.size() - 2), path, user, state, level, move);
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

    public void hillClimbing(char[][] initialState, User user, State state, Level level, Movement move) {
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
            displayMessage("Solution was not found using Hill Climbing Algorithm.", user, state, move, level);
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

        displaySolutionInfo("Hill Climbing", duration, (path.size() - 2), (path.size() - 2), path, user, state, level, move);
    }

    public void AStar(char[][] initialState, User user, State state, Level level, Movement move) {
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

                displaySolutionInfo("A*", duration, (path.size() - 2), (path.size() - 2), path, user, state, level, move);
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
