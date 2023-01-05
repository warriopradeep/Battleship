package battleship;

public enum Ship {

    AIRCRAFT_CARRIER("Aircraft Carrier", 5),
    BATTLESHIP("Battleship", 4),
    SUBMARINE("Submarine", 3),
    CRUISER("Cruiser", 3),
    DESTROYER("Destroyer", 2);

    private final String identifier;
    private final int size;
    Ship(String identifier, int size) {
        this.identifier = identifier;
        this.size = size;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public int getSize() {
        return this.size;
    }
}
