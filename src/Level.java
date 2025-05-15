import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Level {
    private int level, rows, columns, steps;
    private String board = "";
    List<Level> levels = new ArrayList<>();

    public Level() {
    }

    public Level(boolean start) {
        if (start) {
            this.readLevels();
        }
    }

    public int getLevel() {
        return level;
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

    public String getBoard() {
        return board;
    }

    public List<Level> getLevels() {
        return levels;
    }

    public void readLevels() {
        try {
            FileReader fr = new FileReader("../Levels.txt");
            Scanner scanner = new Scanner(fr);
            int counter_token = 1;
            int currentRows = 0;
            int currentColumns = 0;
            int currentSteps = 0;
            StringBuilder currentBoard = new StringBuilder();

            while (scanner.hasNext()) {
                String token = scanner.next();

                if (Objects.equals(token, "Level")) {
                    // Read the level number.
                    token = scanner.next();

                    Level newLevel = new Level();
                    newLevel.level = Integer.parseInt(token);
                    newLevel.rows = currentRows;
                    newLevel.columns = currentColumns;
                    newLevel.steps = currentSteps;
                    newLevel.board = String.valueOf(currentBoard);

                    this.levels.add(newLevel);

                    counter_token = 1;
                    currentBoard = new StringBuilder();
                } else if (counter_token == 1) {
                    currentRows = Integer.parseInt(token);
                    counter_token++;
                } else if (counter_token == 2) {
                    currentColumns = Integer.parseInt(token);
                    counter_token++;
                } else if (counter_token == 3) {
                    currentSteps = Integer.parseInt(token);
                    counter_token++;
                } else if (counter_token >= 4) {
                    if (counter_token != 4) {
                        currentBoard.append(" ");
                    }

                    currentBoard.append(token);
                    counter_token++;
                }
            }

            scanner.close();
        } catch (Exception e) {
            System.out.println("Something wrong happened: " + e.getMessage());
        }
    }

    public void setLevelInfo(int chosenLevel, User user) {
        for (Level current : getLevels()) {
            if (current.getLevel() == chosenLevel) {
                user.setRows(current.getRows());
                user.setColumns(current.getColumns());
                user.setSteps(current.getSteps());
                user.setBoard(current.getBoard());

                break;
            }
        }
    }

    void printLevels() {
        for (Level current : levels) {
            System.out.println("Level: " + current.level);
            System.out.println("Rows: " + current.rows);
            System.out.println("Columns: " + current.columns);
            System.out.println("Steps: " + current.steps);
            System.out.println("Board:");
            System.out.println(current.board);
        }
    }
}
