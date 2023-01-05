package battleship;

import java.util.Scanner;

public class hitLogic {

    private final gameGrid setGrid;

    private final gameGrid fogOfWarGrid = new gameGrid();

    private int aircraft_flag = 1;
    private int battleship_flag = 1;
    private int submarine_flag = 1;
    private int cruiser_flag = 1;
    private int destroyer_flag = 1;

    public hitLogic(gameGrid setGrid) {
        this.setGrid = setGrid;
//        takeShot();
    }

    public void takeShot() {
        Scanner in = new Scanner(System.in);

        System.out.println("The game starts!\n");
        fogOfWarGrid.printGrid();

        System.out.println("Take a shot!\n");

        while (true) {

            String cellName = in.nextLine();

            if (!setGrid.isValidCoordinates(cellName)) {
                System.out.println("Error! You entered the wrong coordinates! Try again:\n");
                continue;
            }

            int[] rowCol = setGrid.coordinateXY(cellName);
            int rowIndex = rowCol[0];
            int colIndex = rowCol[1];

            boolean hitMiss = setGrid.hitOrMiss(rowIndex, colIndex);
            fogOfWarGrid.setCharacterAtGridLocation(rowIndex, colIndex, setGrid.getCharacterAtGridLocation(rowIndex, colIndex));
            fogOfWarGrid.printGrid();

            if (setGrid.didAllShipsSink()) {
                System.out.println("You sank the last ship. You won. Congratulations!");
                break;
            } else if (setGrid.didACSink() && aircraft_flag == 1) {
                aircraft_flag = 0;
                System.out.println("You sank a ship! Specify a new target:\n");
            } else if (setGrid.didBSSink() && battleship_flag == 1) {
                battleship_flag = 0;
                System.out.println("You sank a ship! Specify a new target:\n");
            } else if (setGrid.didSubSink() && submarine_flag == 1) {
                submarine_flag = 0;
                System.out.println("You sank a ship! Specify a new target:\n");
            } else if (setGrid.didCruSink() && cruiser_flag == 1) {
                cruiser_flag = 0;
                System.out.println("You sank a ship! Specify a new target:\n");
            } else if (setGrid.didDestSink() && destroyer_flag == 1) {
                destroyer_flag = 0;
                System.out.println("You sank a ship! Specify a new target:\n");
            } else if (hitMiss) {
                System.out.println("You hit a ship! Try again:\n");
            } else {
                System.out.println("You missed. Try again:\n");
            }

//            setGrid.printGrid();

        }
    }

    public void printFoggyGrid() {
        fogOfWarGrid.printGrid();
    }

    public void takeShot2() {
        Scanner in = new Scanner(System.in);
        String cellName;

        while (true) {
            cellName = in.nextLine();

            if (!setGrid.isValidCoordinates(cellName)) {
                System.out.println("Error! You entered the wrong coordinates! Try again:\n");
                continue;
            } else break;
        }

        int[] rowCol = setGrid.coordinateXY(cellName);
        int rowIndex = rowCol[0];
        int colIndex = rowCol[1];

        boolean hitMiss = setGrid.hitOrMiss(rowIndex, colIndex);
        fogOfWarGrid.setCharacterAtGridLocation(rowIndex, colIndex, setGrid.getCharacterAtGridLocation(rowIndex, colIndex));


        if (setGrid.didAllShipsSink()) {
            System.out.println("You sank the last ship. You won. Congratulations!");
        } else if (setGrid.didACSink() && aircraft_flag == 1) {
            aircraft_flag = 0;
            System.out.println("You sank a ship! Specify a new target:\n");
        } else if (setGrid.didBSSink() && battleship_flag == 1) {
            battleship_flag = 0;
            System.out.println("You sank a ship! Specify a new target:\n");
        } else if (setGrid.didSubSink() && submarine_flag == 1) {
            submarine_flag = 0;
            System.out.println("You sank a ship! Specify a new target:\n");
        } else if (setGrid.didCruSink() && cruiser_flag == 1) {
            cruiser_flag = 0;
            System.out.println("You sank a ship! Specify a new target:\n");
        } else if (setGrid.didDestSink() && destroyer_flag == 1) {
            destroyer_flag = 0;
            System.out.println("You sank a ship! Specify a new target:\n");
        } else if (hitMiss) {
            System.out.println("You hit a ship!\n");
        } else {
            System.out.println("You missed!\n");
        }
    }

    public boolean hasSunkAll() {
        return setGrid.didAllShipsSink();
    }
}
