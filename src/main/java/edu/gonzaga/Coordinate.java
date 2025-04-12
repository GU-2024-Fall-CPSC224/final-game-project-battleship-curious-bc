package edu.gonzaga;

/**
 * Represents a coordinate on the 10x10 game board.
 * Coordinates range from A0 to J9.
 */
public class Coordinate {
    private int row; // 0–9 corresponds to A–J
    private int col; // 0–9

    /**
     * Constructs a Coordinate using row and column integers.
     * @param row The row index (0–9).
     * @param col The column index (0–9).
     */
    public Coordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Constructs a Coordinate from a string like "B5" or "T1".
     * @param input A string with one letter (A–J) and one or two digits (0–9).
     */
    public Coordinate(String input) {
        input = input.toUpperCase();
        this.row = input.charAt(0) - 'A';
        this.col = Integer.parseInt(input.substring(1));
    }

    // Return row value
    public int getRow() {
        return row;
    }

    // Return column value
    public int getCol() {
        return col;
    }

    /**
     * Determines if two Coordinate objects are equal based on row and column.
     * @param obj The object to compare with.
     * @return True if both Coordinates have the same row and column values.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Coordinate)) return false;
        Coordinate other = (Coordinate) obj;
        return row == other.row && col == other.col;
    }

    /**
     * Generates a hash code for use in hash-based collections (e.g., HashSet).
     * @return A hash code derived from row and column values.
     */

    @Override
    public int hashCode() {
        return 31 * row + col;
    }

    /**
     * Converts the coordinate to a human-readable string format.
     * Example: row = 1, col = 5 returns "B5"
     * @return The string representation of the coordinate.
     */
    @Override
    public String toString() {
        return String.format("%c%d", (char) ('A' + row), col);
    }
}
