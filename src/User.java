import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class User {
    Scanner scanner = new Scanner(System.in);
    private int chosen_level;
    private int rows;
    private int columns;
    private int steps;
    private String board;
    private int mood;

    public User() {

//        while (true) {
//            System.out.println("Number of Rows:");
//            int tmp;
//
//            try {
//                tmp = scanner.nextInt();
//
//                if (tmp <= 0) {
//                    System.out.println("Number of rows should be a positive integer");
//                } else {
//                    this.rows = tmp;
//
//                    break;
//                }
//            } catch (Exception e) {
//                System.out.println("Wrong Input Type, You Should Enter an Integer!");
//                scanner.nextLine();
//            }
//        }

//        this.rows = level.getRows();

//        while (true) {
//            System.out.println("Number of Columns:");
//            int tmp;
//
//            try {
//                tmp = scanner.nextInt();
//
//                if (tmp <= 0) {
//                    System.out.println("Number of columns should be a positive integer");
//                } else {
//                    this.columns = tmp;
//
//                    break;
//                }
//            } catch (Exception e) {
//                System.out.println("Wrong Input Type, You Should Enter an Integer!");
//                scanner.nextLine();
//            }
//        }

//        this.columns = level.getColumns();

//        while (true) {
//            System.out.println("Please Enter Number of Steps You Can Move:");
//
//            int tmp;
//
//            try {
//                tmp = scanner.nextInt();
//
//                if (tmp < 0) {
//                    System.out.println("Number of Steps Should Be a Positive Integer, Try Again!");
//                } else {
//                    this.steps = tmp;
//
//                    break;
//                }
//            } catch (Exception e) {
//                System.out.println("Wrong Input Type, You Should Enter an Integer!");
//                scanner.nextLine();
//            }
//        }

//        this.steps = level.getSteps();
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getSteps() {
        return steps;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public int getChosen_level() {
        return chosen_level;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    public void setSteps(int value) {
        this.steps = value;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public int getMood() {
        return mood;
    }

    public void setChosen_level(int chosen_level) {
        this.chosen_level = chosen_level;
    }

    public void initialState(char[][] board, Movement move) {
        System.out.println("Please Enter Initial Board:");

        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= columns; j++) {
                System.out.println("Row Number (" + i + "), Column Number (" + j + ").");
                System.out.println("Input Valid Cell's type (B, R, A, S, G, O)");

                String type;

                while (true) {
                    try {
                        type = scanner.next();

                        if (type.charAt(0) == 'B' || type.charAt(0) == 'R' || type.charAt(0) == 'A' || type.charAt(0) == 'S' || type.charAt(0) == 'G' || type.charAt(0) == 'O') {
                            board[i][j] = type.charAt(0);

                            if (type.charAt(0) == 'G' || (type.length() >= 2 && type.charAt(1) == 'G')) {
                                CustomPair pair = new CustomPair(i, j);
                                move.goals.add(pair);
                            }

                            break;
                        } else {
                            System.out.println("OK");
                            System.out.println("Wrong input, Try Again!");
                        }
                    } catch (Exception e) {
                        System.out.println("Wrong Type Input, Try Again!");
                        scanner.nextLine();
                    }
                }
            }
        }
    }

    public void selectLevel(Level level) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(4, 3));

        JLabel welcomeLabel = new JLabel("Welcome to Logic Magnet Game!");
        welcomeLabel.setBounds(0, -100, 1000, 1000);
        welcomeLabel.setVerticalAlignment(SwingConstants.CENTER);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        Font welcomeLabelFont = welcomeLabel.getFont();
        welcomeLabel.setFont(new Font(welcomeLabelFont.getFontName(), Font.PLAIN, 24));

        JLabel selectLevelLabel = new JLabel("Select level");
        selectLevelLabel.setBounds(0, -100, 1000, 1000);
        selectLevelLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        selectLevelLabel.setHorizontalAlignment(SwingConstants.CENTER);

        Font selectLevelLabelFont = selectLevelLabel.getFont();
        selectLevelLabel.setFont(new Font(selectLevelLabelFont.getFontName(), Font.PLAIN, 24));

        DefaultListModel<Integer> listModel = new DefaultListModel<>();
        for (int i = 1; i <= 50; i++) {
            listModel.addElement(i);
        }

        JList<Integer> numberList = new JList<>(listModel);
        numberList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

//        numberList.addListSelectionListener(e -> {
//            if (!e.getValueIsAdjusting()) {
//                System.out.println("Selected number: " + numberList.getSelectedValue());
//            }
//        });

        JScrollPane scrollPane = new JScrollPane(numberList);
        scrollPane.setPreferredSize(new Dimension(1, 10));

        JPanel buttonPanel = getjPanel(level, numberList, frame);

        frame.add(new JLabel());
        frame.add(welcomeLabel);
        frame.add(new JLabel());
        frame.add(new JLabel());
        frame.add(selectLevelLabel);
        frame.add(new JLabel());
        frame.add(new JLabel());
        frame.add(scrollPane, BorderLayout.NORTH);
        frame.add(new JLabel());
        frame.add(new JLabel());
        frame.add(buttonPanel);
        frame.add(new JLabel());

        frame.pack();
        frame.setSize(screenSize.width, screenSize.height);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    private JPanel getjPanel(Level level, JList<Integer> numberList, JFrame frame) {
        JButton sendButton = new JButton("Next");
        sendButton.addActionListener(e -> {

            Integer selectedNumber = numberList.getSelectedValue();

            if (selectedNumber != null) {
//                setChosen_level(selectedNumber);
                level.setLevelInfo(selectedNumber, this);

                State state = new State(getRows(), getColumns());
                Movement move = new Movement(getRows(), getColumns());

                state.initialState(getBoard(), move);
                frame.dispose();
                inputMood(state, move);
            } else {
                System.out.println("No number selected.");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(sendButton);
        return buttonPanel;
    }

    public void inputMood(State state, Movement move) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        JFrame frame = new JFrame("Choose Mood");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(screenSize.width, screenSize.height);
        frame.setLayout(new GridLayout(3, 3));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);

        JLabel descriptionLabel = new JLabel("""
            Please Choose an Option to Solve This Board by:""");
        descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        descriptionLabel.setVerticalAlignment(SwingConstants.BOTTOM);

        Font labelFont = descriptionLabel.getFont();
        descriptionLabel.setFont(new Font(labelFont.getName(), Font.PLAIN, 18));

        String[] options = {
                "BFS - Breadth First Search",
                "UCS - Uniform Cost Search",
                "UCS According to the Magnet's type",
                "Hill Climbing",
                "A* - A Star",
                "Manually"
        };

        JComboBox<String> comboBox = new JComboBox<>(options);
        JPanel comboBoxPanel = new JPanel();
        comboBoxPanel.setPreferredSize(new Dimension(200, 5));

        JButton selectButton = new JButton("Select Mood");
        selectButton.addActionListener(e -> {
            int selectedIndex = comboBox.getSelectedIndex();

            if (selectedIndex >= 0 && selectedIndex < 6) {
                frame.dispose();
                setMood(selectedIndex + 1);

                Algorithms algorithm = new Algorithms();
                if (getMood() == 1) {
                    algorithm.BFS(state.getBoard(), state, move);
                } else if (getMood() == 2) {
                    algorithm.UCS(state.getBoard(), state, move);
                } else if (getMood() == 3) {
                    algorithm.UCSAccordingToMagnetType(state.getBoard(), state, move);
                } else if (getMood() == 4) {
                    algorithm.hillClimbing(state.getBoard(), state, move);
                } else if (getMood() == 5) {
                    algorithm.AStar(state.getBoard(), state, move);
                } else if (getMood() == 6) {
//                    state.generateALlSates(state.getBoard(), move);
//                    state.printBoard();

                    playManually(state, move);

//                    JPanel boardPanel = new JPanel(new GridLayout(1, 1));
//
//                    frame.setLayout(new GridLayout(1, 1));
//                    state.displayBoard(boardPanel, state.getBoard(), state.getRows(), state.getColumns());
//                    frame.add(boardPanel);
//
//                    while (true) {
//                        if (!move.checkGoalState(state.getBoard()) && getSteps() >= 1) {
//                            int[] step = userStep(state.getBoard());
//
//                            move.changeMagnet(step[0], step[1], step[2], step[3], state.getBoard());
//                            state.printBoard();
//                            setSteps(getSteps() - 1);
//                        } else if (move.checkGoalState(state.getBoard())) {
//                            System.out.println("You ARE A WINNER! YEAH!");
//
//                            break;
//                        } else if (getSteps() == 0) {
//                            System.out.println("Sorry, You Have Used All Available Moves.\nGood Luck!");
//
//                            break;
//                        }
//                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a valid option.");
            }
        });

        comboBoxPanel.add(comboBox);
        comboBoxPanel.add(selectButton);

        frame.add(new Label());
        frame.add(descriptionLabel);
        frame.add(new Label());
        frame.add(new Label());
        frame.add(comboBoxPanel);
        frame.add(new Label());
        frame.add(new Label());
        frame.add(new Label());
        frame.add(new Label());
    }


    public void playManually(State state, Movement move) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        JFrame frame = new JFrame("Logic Magnets");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(screenSize.width, screenSize.height);
        frame.setLayout(new GridLayout(3, 3));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel boardPanel = new JPanel(new GridLayout(1, 1));

        state.displayBoard(boardPanel, state.getBoard(), state.getRows(), state.getColumns());

        frame.add(new Label());
        frame.add(new Label());
        frame.add(new Label());
        frame.add(new Label());
        frame.add(boardPanel);
        frame.add(new Label());
        frame.add(new Label());
        frame.add(new Label());
        frame.add(new Label());

        frame.setVisible(true);

        while (true) {
//            if (!move.checkGoalState(state.getBoard()) && getSteps() >= 1) {
//                int[] step = userStep(state.getBoard());
////
////                move.changeMagnet(step[0], step[1], step[2], step[3], state.getBoard());
////                state.printBoard();
////                setSteps(getSteps() - 1);
//            } else if (move.checkGoalState(state.getBoard())) {
//                System.out.println("You ARE A WINNER! YEAH!");
//
//                break;
//            } else if (getSteps() == 0) {
//                System.out.println("Sorry, You Have Used All Available Moves.\nGood Luck!");
//
//                break;
//            }
        }
    }
    public int[] userStep(char[][] board) {
        int[] step = new int[4];

        System.out.println("Number of Available Steps: " + steps);

        while (true) {
            try {
                System.out.println("You Should Choose Magnet to Move, its Row:");

                int currentRow = scanner.nextInt();

                if (1 > currentRow || currentRow > rows) {
                    System.out.println("Wrong Input, Please Try Again!");

                    continue;
                } else {
                    step[0] = currentRow;
                }

                System.out.println("Its Column:");

                int currentColumn = scanner.nextInt();

                if (1 > currentColumn || currentColumn > columns) {
                    System.out.println("Wrong Input, Please Try Again!");
                } else if (board[currentRow][currentColumn] != 'R' && board[currentRow][currentColumn] != 'A') {
                    System.out.println("Sorry, This is not a magnet. Please try again!");
                } else {
                    step[1] = currentColumn;

                    break;
                }
            } catch (Exception e) {
                System.out.println("Wrong Input Type, You Should Enter an Integer!");

                scanner.nextLine();
            }
        }

        while (true) {
            try {
                System.out.println("Now You Should Choose the next Position, its Row:");

                int toRow = scanner.nextInt();

                if (1 > toRow || toRow > rows) {
                    System.out.println("Wrong Input, Please Try Again!");

                    continue;
                } else {
                    step[2] = toRow;
                }

                System.out.println("Its Column:");

                int toColumn = scanner.nextInt();

                if (1 > toColumn || toColumn > columns) {
                    System.out.println("Wrong Input, Please Try Again!");
                } else if (board[step[2]][toColumn] != 'O' && board[step[2]][toColumn] != 'G') {
                    System.out.println("Sorry, This is not an empty space. Please try again!");
                } else {
                    step[3] = toColumn;

                    return step;
                }
            } catch (Exception e) {
                System.out.println("Wrong Input Type, You Should Enter an Integer!");

                scanner.nextLine();
            }
        }
    }
}
