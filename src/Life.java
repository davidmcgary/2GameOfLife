import java.util.Arrays;

/**
 * @author David McGary, dmcgary@purdue.edu
 */
public class Life {
    final int ROWS = 20;
    final int COLS = 50;
    final int GENERATIONS = 5000;
    final long SLEEP = 100;
    final double CHANCE = 0.3;
    final boolean ALIVE = true;
    final boolean DEAD = false;

    boolean[][] board = new boolean[ROWS][COLS];

    //populates a board with random live cells
    void setBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (Math.random() <= CHANCE) {
                    board[i][j] = true;
                }
            }
        }
    }

    public boolean isCorner(int i, int j) {
        if (i == 0 && j == 0) {
            return true;
        }
        if (i == 0 && j == COLS - 1) {
            return true;
        }
        if (i == ROWS - 1 && j == 0) {
            return true;
        }
        if (i == ROWS - 1 && j == COLS - 1) {
            return true;
        }
        return false;
    }

    public boolean isEdge(int i, int j) {
        return i == 0 || j == 0 || i == ROWS - 1 || j == COLS - 1;
    }

    public boolean isPossible(int i, int j) {
        return !(i < 0 || j < 0 || i >= ROWS || j >= COLS);
    }

    public boolean isALIVE(int i, int j) {
        int cells = 0;
        if (isPossible(i-1, j-1) && board[i-1][j-1]) {
            cells++;
        }
        if (isPossible(i-1, j) && board[i-1][j]) {
            cells++;
        }
        if (isPossible(i-1, j+1) && board[i-1][j+1]) {
            cells++;
        }
        if (isPossible(i, j-1) && board[i][j-1]) {
            cells++;
        }
        if (isPossible(i, j+1) && board[i][j+1]) {
            cells++;
        }
        if (isPossible(i+1, j-1) && board[i+1][j-1]) {
            cells++;
        }
        if (isPossible(i+1, j) && board[i+1][j]) {
            cells++;
        }
        if (isPossible(i+1, j+1) && board[i+1][j+1]) {
            cells++;
        }

        if (isCorner(i, j)) {
            if (!board[i][j]) {
                if (cells == 2) {
                    return ALIVE;
                } else {
                    return DEAD;
                }
            }
            if (cells == 1) {
                return ALIVE;
            }
            return DEAD;
        }

        if (isEdge(i, j)) {
            if (!board[i][j]) {
                if (cells == 2 || cells == 3) {
                    return ALIVE;
                } else {
                    return DEAD;
                }
            }
            if (cells == 1 || cells == 2) {
                return ALIVE;
            }
            return DEAD;
        }

        if (board[i][j] && (cells == 2 || cells == 3)) {
            return ALIVE;
        }
        if (!board[i][j] && cells == 3) {
            return ALIVE;
        }
        return DEAD;
    }

    char[][] makeCharBoard(boolean[][] board) {
        char[][] chars = new char[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (board[i][j]) {
                    chars[i][j] = 'X';
                } else {
                    chars[i][j] = ' ';
                }
            }
        }
        return chars;
    }

    public void printBoard() {
        char[][] charBoard = makeCharBoard(board);
        System.out.printf("\n ");
        System.out.println();

        //print first horizontal line
        for (int i = 0; i < COLS; i++)
            System.out.printf("----");
        System.out.println("-");

        // Print the char version of the board
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++)
                System.out.printf("| %c ", charBoard[i][j]);
            System.out.println("|");

            // print the line between each row
            for (int j = 0; j < COLS; j++)
                System.out.printf("----");
            System.out.println("-");
        }
    }

    public void play() {
        setBoard();
        for (int g = 0; g < GENERATIONS; g++) {
            boolean[][] tempBoard = new boolean[ROWS][COLS];
            System.out.println("Generation " + g);
            printBoard();
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLS; j++) {
                    tempBoard[i][j] = isALIVE(i, j);
                }
            }
            board = Arrays.copyOf(tempBoard, tempBoard.length);
            try {Thread.sleep(SLEEP);}
            catch (InterruptedException e) {}
        }
    }

    public static void main(String[] args) {
        Life l = new Life();
        l.play();
    }
}