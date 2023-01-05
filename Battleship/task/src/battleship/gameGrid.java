package battleship;

import java.util.*;

public class gameGrid {

    private final char[][] grid = new char[10][10];
    private final ArrayList<String> occupiedCells= new ArrayList<>();

    private final char O = 'O';

    // todo: create separate stacks for each of the ships and add or remove the ships when setting or attacking

    private final Set<String> aircraft_carrier = new HashSet<>();
    private final Set<String> battle_ship = new HashSet<>();
    private final Set<String> sub_marine = new HashSet<>();
    private final Set<String> crui_ser = new HashSet<>();
    private final Set<String> destro_yer = new HashSet<>();


    public gameGrid() {
        initializeGrid();
    }

    private void initializeGrid() {
        for (char[] row : grid) {
            Arrays.fill(row, '~');
        }
    }

    public void printGrid() {
        int i = 0;

        System.out.println("  1 2 3 4 5 6 7 8 9 10");

        for (char[] row : grid) {
            char rowName = (char) ('A' + i);
            System.out.print(rowName);
            for (char column : row) {
                System.out.print(" " + column);
            }
            System.out.println();
            i++;
        }

        System.out.println();
    }

    public void setMyGrid() {
        placeShip(Ship.AIRCRAFT_CARRIER);
        placeShip(Ship.BATTLESHIP);
        placeShip(Ship.SUBMARINE);
        placeShip(Ship.CRUISER);
        placeShip(Ship.DESTROYER);
    }

    private void placeShip(Ship ship) {

        Scanner in = new Scanner(System.in);
        String cell1, cell2;
        String shipName = ship.getIdentifier();
        int shipSize = ship.getSize();

        while (true) {
            System.out.printf("Enter the coordinates of the %s (%d cells):%n", shipName, shipSize);
            cell1 = in.next();
            cell2 = in.next();

            if (!checkShipPlacedProperly(ship, cell1, cell2)) {
                continue;
            }
            break;
        }

        updateGrid(cell1, cell2, ship);
        printGrid();
    }
    
    private void updateGrid(String cell1, String cell2, Ship ship) {
        int[] pointA = coordinateXY(cell1);
        int[] pointB = coordinateXY(cell2);
        Set<String> ship_coordinates = getShipSet(ship);

        if (pointA[0] == pointB[0]) {
            int start = Math.min(pointA[1], pointB[1]);
            int end = Math.max(pointA[1], pointB[1]) + 1;

            for (int i = start; i < end; i++) {
                grid[pointA[0]][i] = O;
                ship_coordinates.add("%s%s".formatted(pointA[0], i));
            }
        } else {
            int start = Math.min(pointA[0], pointB[0]);
            int end = Math.max(pointA[0], pointB[0]) + 1;

            for (int i = start; i < end; i++) {
                grid[i][pointA[1]] = O;
                ship_coordinates.add("%s%s".formatted(i, pointA[1]));
            }
        }
    }

    private boolean checkShipPlacedProperly(Ship ship, String cell1, String cell2) {
        // return true or false depending on whether the given ship is placed properly and not diagonally and proper length
        int shipSize = ship.getSize();
        String shipName = ship.getIdentifier();

        if (!isValidCoordinates(cell1) || !isValidCoordinates(cell2)) {
            System.out.println("Error! You entered the wrong coordinates! Try again:\n");
            return false;
        }

        int[] pointA = coordinateXY(cell1);
        int[] pointB = coordinateXY(cell2);

        if (pointA[0] != pointB[0] && pointA[1] != pointB[1]) {
            System.out.println("Error! Wrong ship location! Try again:\n");
            return false;
        } else if (shipLength(pointA, pointB) != shipSize) {
            System.out.printf("Error! Wrong length of the %s! Try again:%n", shipName);
            return false;
        } else if (areCellsOccupied(pointA, pointB)) {
            System.out.println("Error! Wrong ship location! Try again:\n");
            return false;
        } else if (nextToAnotherShip(pointA, pointB)) {
            System.out.println("Error! You placed it too close to another one. Try again:\n");
            return false;
        } else return true;
    }

    private boolean areCellsOccupied(int[] pointA, int[] pointB) {

        if (pointA[0] == pointB[0]) {
            int start = Math.min(pointA[1], pointB[1]);
            int end = Math.max(pointA[1], pointB[1]) + 1;

            for (int i = start; i < end; i++) {
                if (grid[pointA[0]][i] == O) return true;
            }
        } else {
            int start = Math.min(pointA[0], pointB[0]);
            int end = Math.max(pointA[0], pointB[0]) + 1;

            for (int i = start; i < end; i++) {
                if (grid[i][pointA[1]] == O) return true;
            }
        }

        return false;
    }

    private boolean areCellsOccupied2(int[] pointA, int[] pointB) {
    // TODO: wrong implementation and logic
        if (pointA[0] == pointB[0]) {
            int start = Math.min(pointA[1], pointB[1]);
            int end = Math.max(pointA[1], pointB[1]) + 1;

            for (int i = start; i < end; i++) {
                if (occupiedCells.contains(stringifyCellID(pointA[0], i))) return true;
            }
        } else {
            int start = Math.min(pointA[0], pointB[0]);
            int end = Math.max(pointA[0], pointB[0]) + 1;

            for (int i = start; i < end; i++) {
                if (occupiedCells.contains(stringifyCellID(i, pointA[1]))) return true;
            }
        }

        return false;
    }

    private boolean nextToAnotherShip(int[] pointA, int[] pointB) {
        final int firstRow = 0;
        final int lastRow = 9;
        final int firstCol = 0;
        final int lastCol = 9;

        int rowA = pointA[0];
        int colA = pointA[1];
        int rowB = pointB[0];
        int colB = pointB[1];

        if (rowA == rowB) {
            int start = Math.min(pointA[1], pointB[1]);
            int end = Math.max(pointA[1], pointB[1]);

            if (rowA == firstRow) {
                for (int i = start; i < end + 1; i++) {
                    if (grid[rowA + 1][i] == O) return true;
                }
            }

            if (rowA == lastRow) {
                for (int i = start; i < end + 1; i++) {
                    if (grid[rowA - 1][i] == O) return true;
                }
            }

            for (int i = start; i <= end; i++) {
                if (rowA - 1 < firstRow || rowA + 1 > lastRow) continue;
                if (grid[rowA - 1][i] == O || grid[rowA + 1][i] == O) return true;
            }

            if (start != firstCol && grid[rowA][start - 1] == O) return true;

            if (end != lastCol && grid[rowA][end + 1] == O) return true;

        } else {
            int start = Math.min(pointA[0], pointB[0]);
            int end = Math.max(pointA[0], pointB[0]);

            if (colA == firstCol) {
                for (int i = start; i < end + 1; i++) {
                    if (grid[i][colA + 1] == O) return true;
                }
            }

            if (colA == lastCol) {
                for (int i = start; i < end + 1; i++) {
                    if (grid[i][colA - 1] == O) return true;
                }
            }

            for (int i = start; i <= end; i++) {
                if (colA - 1 < firstCol || colA + 1 > lastCol) continue;
                if (grid[i][colA - 1] == O || grid[i][colA + 1] == O) return true;
            }

            if (start != firstRow && grid[start - 1][colA] == O) return true;

            if (end != lastRow && grid[end + 1][colA] == O) return true;

        }

        return false;
    }

    private int shipLength(int[] pointA, int[] pointB) {
        // return the length of the ship input by the user

        if (pointA[0] == pointB[0]) {
            return Math.abs(pointA[1] - pointB[1]) + 1;
        } else return Math.abs(pointA[0] - pointB[0]) + 1;
    }

    public int[] coordinateXY(String cellName) {
        // return row and column numbers.

        String row = String.valueOf(cellName.charAt(0)).toUpperCase();
        rowName r = rowName.valueOf(row);
        int rowNumber = r.getI();
        int columnNumber = Integer.parseInt(cellName.substring(1)) - 1;

        return new int[]{rowNumber, columnNumber};
    }

    public boolean isValidCoordinates(String cellName) {

        String pattern = "[a-jA-J]([1-9]|10)\\b";
        return cellName.matches(pattern);
    }

    public boolean hitOrMiss(int row, int col) {

        if (this.grid[row][col] == 'X') {
            return true;
        }

        String x = "" + row + col;
        if (this.grid[row][col] == 'O') {
            this.grid[row][col] = 'X';
            removeShipCoordinate(x);
            return true;
        } else {
            this.grid[row][col] = 'M';
            return false;
        }
    }

    private void removeShipCoordinate(String x) {

        aircraft_carrier.remove(x);

        battle_ship.remove(x);

        sub_marine.remove(x);

        crui_ser.remove(x);

        destro_yer.remove(x);

    }

    public char getCharacterAtGridLocation(int row, int col) {
        return grid[row][col];
    }

    public void setCharacterAtGridLocation(int row, int col, char c) {
        grid[row][col] = c;
    }

    private Set<String> getShipSet(Ship ship) {
        switch (ship) {
            case AIRCRAFT_CARRIER -> {
                return aircraft_carrier;
            }
            case BATTLESHIP -> {
                return battle_ship;
            }
            case SUBMARINE -> {
                return sub_marine;
            }
            case CRUISER -> {
                return crui_ser;
            }
            default -> {
                return destro_yer;
            }
        }
    }

    public boolean didAllShipsSink() {
        return aircraft_carrier.isEmpty() && battle_ship.isEmpty() && sub_marine.isEmpty() && crui_ser.isEmpty() && destro_yer.isEmpty();
    }

    public boolean didACSink() {
        return aircraft_carrier.isEmpty();
    }

    public boolean didBSSink() {
        return battle_ship.isEmpty();
    }

    public boolean didSubSink() {
        return sub_marine.isEmpty();
    }

    public boolean didCruSink() {
        return crui_ser.isEmpty();
    }

    public boolean didDestSink() {
        return destro_yer.isEmpty();
    }

    private String stringifyCellID(int a, int b) {
        return a + String.valueOf(b);
    }
}