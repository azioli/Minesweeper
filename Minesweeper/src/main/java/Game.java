import java.util.Scanner;

public class Game {
    private Board board;
    int turn = 0;
    Scanner input = new Scanner(System.in);

    public Game() {
        board = new Board(8, 8,10);
    }

    public void play() {
        while (true) {
            turn++;
            System.out.println("Turn " + turn);
            board.showBoard();
            System.out.print("\nRow: ");
            int row = input.nextInt();
            System.out.print("Column: ");
            int col = input.nextInt();
            if (!board.isValid(row, col))
                System.out.println("Choose a number between 0 and 7");
            else if (board.isVisited(row, col))
                System.out.println("Field already shown");
            else {
                if (!board.isMine(row, col)) {
                    board.openNeighbors(row, col);
                    if (board.win()) {
                        System.out.println("Congratulations, you let the 10 fields with the mines in " + turn + " turns");
                        board.showMines();
                        return;
                    }
                } else {
                    System.out.println("Mine! You lost!");
                    board.showMines();
                    return;
                }
            }
        }
    }
}
