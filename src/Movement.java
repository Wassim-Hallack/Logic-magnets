import java.util.ArrayList;
import java.util.List;

public class Movement {
    private final int rows, columns;
    final List<CustomPair> goals = new ArrayList<>();

    public Movement(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }

    public void changeMagnet(int currentRow, int currentColumn, int toRow, int toColumn, char[][] board) {
        if (board[toRow][toColumn] == 'O' || board[toRow][toColumn] == 'G') {
            board[toRow][toColumn] = board[currentRow][currentColumn];
            board[currentRow][currentColumn] = 'O';

            if (board[toRow][toColumn] == 'R') {
                reversEffect(toRow, toColumn, board);
            } else {
                attractiveEffect(toRow, toColumn, board);
            }

            setGoals(board);
        } else {
            System.out.println("Sorry, Please Enter another Move!");
        }
    }

    public void reversEffect(int row, int column, char[][] board) {
        // To Down
        for (int i = row + 1; i <= rows; i++) {
            if (board[i][column] == 'S' || board[i][column] == 'R' || board[i][column] == 'A') {
                int startIndex = i;

                while ((board[i][column] == 'S' || board[i][column] == 'R' || board[i][column] == 'A') && i <= rows) {
                    i++;
                }

                if (board[i][column] == 'O' || board[i][column] == 'G') {
                    for (; i > startIndex; i--) {
                        board[i][column] = board[i - 1][column];
                        board[i - 1][column] = 'O';
                    }
                }

                break;
            }
        }

        // To Up
        for (int i = row - 1; i >= 1; i--) {
            if (board[i][column] == 'S' || board[i][column] == 'R' || board[i][column] == 'A') {
                int startIndex = i;

                while ((board[i][column] == 'S' || board[i][column] == 'R' || board[i][column] == 'A') && i >= 1) {
                    i--;
                }

                if (board[i][column] == 'O' || board[i][column] == 'G') {
                    for (; i < startIndex; i++) {
                        board[i][column] = board[i + 1][column];
                        board[i + 1][column] = 'O';
                    }
                }

                break;
            }
        }

        // To Right
        for (int j = column + 1; j <= columns; j++) {
            if (board[row][j] == 'S' || board[row][j] == 'R' || board[row][j] == 'A') {
                int startIndex = j;

                while ((board[row][j] == 'S' || board[row][j] == 'R' || board[row][j] == 'A') && j <= columns) {
                    j++;
                }

                if (board[row][j] == 'O' || board[row][j] == 'G') {
                    for (; j > startIndex; j--) {
                        board[row][j] = board[row][j - 1];
                        board[row][j - 1] = 'O';
                    }
                }

                break;
            }
        }

        // To Left
        for (int j = column - 1; j >= 1; j--) {
            if (board[row][j] == 'S' || board[row][j] == 'R' || board[row][j] == 'A') {
                int startIndex = j;

                while ((board[row][j] == 'S' || board[row][j] == 'R' || board[row][j] == 'A') && j >= 1) {
                    j--;
                }

                if (board[row][j] == 'O' || board[row][j] == 'G') {
                    for (; j < startIndex; j++) {
                        board[row][j] = board[row][j + 1];
                        board[row][j + 1] = 'O';
                    }
                }

                break;
            }
        }
    }

    public void attractiveEffect(int row, int column, char[][] board) {
        // To Down
        for (int i = row + 1; i <= rows; i++) {
            if ((board[i][column] == 'S' || board[i][column] == 'R' || board[i][column] == 'A') && (board[i - 1][column] == 'O' || board[i - 1][column] == 'G')) {
                board[i - 1][column] = board[i][column];
                board[i][column] = 'O';
            }
        }

        // To Up
        for (int i = row - 1; i >= 1; i--) {
            if ((board[i][column] == 'S' || board[i][column] == 'R' || board[i][column] == 'A') && (board[i + 1][column] == 'O' || board[i + 1][column] == 'G')) {
                board[i + 1][column] = board[i][column];
                board[i][column] = 'O';
            }
        }

        // To Right
        for (int j = column + 1; j <= columns; j++) {
            if ((board[row][j] == 'S' || board[row][j] == 'R' || board[row][j] == 'A') && (board[row][j - 1] == 'O' || board[row][j - 1] == 'G')) {
                board[row][j - 1] = board[row][j];
                board[row][j] = 'O';
            }
        }

        // To Left
        for (int j = column - 1; j >= 1; j--) {
            if ((board[row][j] == 'S' || board[row][j] == 'R' || board[row][j] == 'A') && (board[row][j + 1] == 'O' || board[row][j + 1] == 'G')) {
                board[row][j + 1] = board[row][j];
                board[row][j] = 'O';
            }
        }
    }

    public void setGoals(char[][] board) {
        for (CustomPair current : goals) {
            if (board[current.first()][current.second()] == 'O') {
                board[current.first()][current.second()] = 'G';
            }
        }
    }

    public boolean checkGoalState(char[][] board) {
        for (CustomPair current : goals) {
            if (board[current.first()][current.second()] == 'G') {
                return false;
            }
        }

        return true;
    }
}
