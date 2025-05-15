import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class State {
    Border goalBorder = new LineBorder(Color.GREEN, 5);
    private final int rows, columns;
    private final char[][] board;

    private final Map<Character, Color> characterColorMap = new HashMap<>();

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public char[][] getBoard() {
        return board;
    }

    public State(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;

        board = new char[this.rows + 5][this.columns + 5];

        for (int i = 1; i <= this.rows; i++) {
            for (int j = 1; j <= this.columns; j++) {
                board[i][j] = 'O';
            }
        }

        this.characterColorMap.put('A', Color.BLUE);
        this.characterColorMap.put('R', Color.RED);
        this.characterColorMap.put('S', Color.GRAY);
        this.characterColorMap.put('G', Color.GREEN);
        this.characterColorMap.put('B', Color.BLACK);
        this.characterColorMap.put('O', Color.WHITE);
    }

    public void initialState(String tmp_board, Movement move) {
        int string_index = 0;
        int tmp_board_length = tmp_board.length();

        for (int i = 1; (i <= rows && string_index < tmp_board_length); i++) {
            for (int j = 1; (j <= columns && string_index < tmp_board_length); j++) {
                if (tmp_board.charAt(string_index) == ' ') {
                    string_index++;
                }
                if (string_index == tmp_board_length) {
                    break;
                }

                StringBuilder type = new StringBuilder();
                type.append(tmp_board.charAt(string_index));
                string_index++;
                if (string_index < tmp_board_length && tmp_board.charAt(string_index) != ' ') {
                    type.append(tmp_board.charAt(string_index));
                    string_index++;
                }

                try {
                    if (type.charAt(0) == 'B' || type.charAt(0) == 'R' || type.charAt(0) == 'A' || type.charAt(0) == 'S' || type.charAt(0) == 'G' || type.charAt(0) == 'O') {
                        this.board[i][j] = type.charAt(0);

                        if (type.charAt(0) == 'G' || (type.length() >= 2 && type.charAt(1) == 'G')) {
                            CustomPair pair = new CustomPair(i, j);
                            move.goals.add(pair);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Something Wrong Happened While Initializing The State.");
                }

            }
        }

        printBoard();
    }

    public List<char[][]> generateALlSates(char[][] board, Movement move) {
        List<char[][]> allStates = new ArrayList<>();

        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= columns; j++) {
                if (board[i][j] == 'A' || board[i][j] == 'R') {
                    for (int nextI = 1; nextI <= rows; nextI++) {
                        for (int nextJ = 1; nextJ <= columns; nextJ++) {
                            if (board[nextI][nextJ] == 'O' || board[nextI][nextJ] == 'G') {
                                char[][] newBoard = new char[rows + 5][columns + 5];

                                for (int k = 1; k <= rows; k++) {
                                    System.arraycopy(board[k], 1, newBoard[k], 1, columns);
                                }

                                move.changeMagnet(i, j, nextI, nextJ, newBoard);

                                allStates.add(newBoard);
                            }
                        }
                    }
                }
            }
        }

        return allStates;
    }

    /* This Algorithm Generate All States and make Node's Cost += 1 If R Magnet Was Moved
    or Cost += 2 If A Magnet Was Moved */
    public List<Node> generateALlSatesAccordingToMagnetType(Node parent, Movement move) {
        List<Node> allStates = new ArrayList<>();

        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= columns; j++) {
                if (parent.getState()[i][j] == 'A' || parent.getState()[i][j] == 'R') {
                    for (int nextI = 1; nextI <= rows; nextI++) {
                        for (int nextJ = 1; nextJ <= columns; nextJ++) {
                            if (parent.getState()[nextI][nextJ] == 'O' || parent.getState()[nextI][nextJ] == 'G') {
                                Node child = new Node(rows, columns);

                                if (parent.getState()[i][j] == 'R') {
                                    child.setCost(parent.getCost() + 1);
                                } else {
                                    child.setCost(parent.getCost() + 2);
                                }

                                for (int k = 1; k <= rows; k++) {
                                    for (int l = 1; l <= columns; l++) {
                                        child.getParent()[k][l] = child.getState()[k][l] = parent.getState()[k][l];
                                    }
                                }

                                move.changeMagnet(i, j, nextI, nextJ, child.getState());

                                allStates.add(child);
                            }
                        }
                    }
                }
            }
        }

        return allStates;
    }

    public void printBoard() {
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= columns; j++) {
                System.out.print(board[i][j] + "      ");
            }

            System.out.println();
        }
    }

    public void displayBoard(JPanel panel, char[][] pathArray, int rows, int columns, Movement movement) {
        panel.removeAll();
        panel.setLayout(new GridLayout(rows, columns, 2, 2)); // Added spacing between cells

        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= columns; j++) {
                JLabel label = new JLabel();
                label.setOpaque(true);
                label.setBackground(this.characterColorMap.get(pathArray[i][j]));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setVerticalAlignment(SwingConstants.CENTER);
                label.setPreferredSize(new Dimension(60, 60));
                label.setFont(new Font("SansSerif", Font.BOLD, 16));
                label.setForeground(Color.BLACK);

                // Highlight goal cells
                for (CustomPair goal : movement.goals) {
                    if (goal.first() == i && goal.second() == j) {
                        label.setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));
                        break;
                    }
                }

                panel.add(label);
            }
        }

        panel.revalidate();
        panel.repaint();
    }

}
