package battleship;

import java.io.IOException;
import java.util.Scanner;

public class gameLogic {

    gameGrid player1Grid;
    gameGrid player2Grid;

    hitLogic hl1;
    hitLogic hl2;

    public gameLogic() {
        initialize();
    }

    private void initialize() {
        player1Grid = new gameGrid();
        player2Grid = new gameGrid();
        hl1 = new hitLogic(player2Grid);
        hl2 = new hitLogic(player1Grid);
    }

    private void setPlayer1Ships() {
        System.out.println("Player 1, place your ships on the game field\n");
        player1Grid.printGrid();
        player1Grid.setMyGrid();

        waitUserEnter();
    }

    private void setPlayer2Ships() {
        System.out.println("Player 2, place your ships to the game field\n");
        player2Grid.printGrid();
        player2Grid.setMyGrid();

        waitUserEnter();
    }

    private void printGameField(int player, hitLogic h, gameGrid gameGrid) {
        h.printFoggyGrid();
        System.out.println("---------------------");
        gameGrid.printGrid();
        System.out.println();
        System.out.printf("Player %d, it's your turn:%n", player);
    }

    private void waitUserEnter() {
        Scanner in = new Scanner(System.in);
        System.out.println("Press Enter and pass the move to another player");
        System.out.println("...");
        try {
            System.in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void gameLoop() {
        setPlayer1Ships();
        setPlayer2Ships();

        while (true) {
            printGameField(1, hl1, player1Grid);
            hl1.takeShot2();

            if (hl1.hasSunkAll()) {
                break;
            }

            waitUserEnter();

            printGameField(2, hl2, player2Grid);
            hl2.takeShot2();

            if (hl2.hasSunkAll()) {
                break;
            }

            waitUserEnter();
        }


    }

    public static void main(String[] args) {
        gameLogic g = new gameLogic();
        g.gameLoop();
    }
}
