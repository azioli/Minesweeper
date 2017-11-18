import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Board {
    private int[][] mineCounts;
    private char[][] board;
    private int ROWS, COLS, MINE_TOTAL;
    private int visitedCount;
    Random random = new Random();

    public Board(int ROWS, int COLS, int MINE_TOTAL) {
        mineCounts = new int[ROWS][COLS];
        board = new char[ROWS][COLS];
        this.ROWS = ROWS;
        this.COLS = COLS;
        this.MINE_TOTAL = MINE_TOTAL;
        this.visitedCount = 0;
        randomMines();
        startBoard();
    }

    public boolean win() {
        return visitedCount == ROWS * COLS - MINE_TOTAL;
    }

    public void openNeighbors(int Row, int Col) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(Row * COLS + Col);
        while (!queue.isEmpty()) {
            int temp = queue.poll();
            int row = temp / COLS, col = temp % COLS;
            board[row][col] = mineCounts[row][col] == 0 ? ' ' : Character.forDigit(mineCounts[row][col], 10);
            visitedCount++;
            if (mineCounts[row][col] == 0) {
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        int x = row + i, y = col + j;
                        if (isValid(x, y) && !isVisited(x, y) && !isMine(x, y)) {
                            queue.add(x * COLS + y);
                        }
                    }
                }
            }
        }
    }

    public boolean isValid(int row, int col) {
        return row >= 0 && row < ROWS && col >= 0 && col < COLS;
    }

    public boolean isVisited(int row, int col) {
        return board[row][col] != '_';
    }

    public boolean isMine(int row, int col) {
        return mineCounts[row][col] == -1;
    }

    public void showBoard() {
        System.out.println("\n     Rows");
        for (int row = 0; row < ROWS; row++) {
            System.out.print("       " + row + " ");

            for (int col = 0; col < COLS; col++) {
                System.out.print("   " + board[row][col]);
            }

            System.out.println();
        }
        System.out.print("\n            ");
        for (int col = 0; col < COLS - 1; col++) {
            System.out.print(col + "   ");
        }
        System.out.println(COLS - 1);
        System.out.println("                      Columns");

    }

    public void showMines() {
        for (int i = 0; i < ROWS; i++)
            for (int j = 0; j < COLS; j++)
                if (mineCounts[i][j] == -1)
                    board[i][j] = '*';

        showBoard();
    }

    public void startBoard() {
        for (int i = 0; i < ROWS; i++)
            for (int j = 0; j < COLS; j++)
                board[i][j] = '_';
    }

    public void randomMines() {
        int row, col;
        for (int i = 0; i < MINE_TOTAL; i++) {
            do {
                row = random.nextInt(ROWS);
                col = random.nextInt(COLS);
            } while (mineCounts[row][col] == -1);

            mineCounts[row][col] = -1;
        }
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < ROWS; j++) {
                mineCounts[i][j] = countMines(i, j);
            }
        }
        for (int i = 0; i < ROWS; i++)
            System.out.println(Arrays.toString(mineCounts[i]));
    }

    private int countMines(int row, int col) {
        if (mineCounts[row][col] == -1) return -1;
        int count = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                int x = row + i, y = col + j;
                if (isValid(x, y) && mineCounts[x][y] == -1)
                    count++;
            }
        }
        return count;
    }
}
