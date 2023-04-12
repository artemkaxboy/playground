package chapter3;

import java.util.*;

public class SudokuCreator {

    private static final int DEFAULT_SIZE = 3;

    private final int size;
    private final byte[][] grid;

    public SudokuCreator(int size) {
        this.size = size;
        grid = new byte[size][size];
    }

    public static void main(String[] args) {
        int size = DEFAULT_SIZE;
        int symbolsCount = size * size;

        SudokuCreator creator = new SudokuCreator(size);
        List<Character> availableChars = new ArrayList<>(symbolsCount);
        for (int i = 0; i < symbolsCount; i++) {
            availableChars.add(getChar(i));
        }

        List<GridLocation> grid = new ArrayList<>(symbolsCount);
        Map<GridLocation, List<Character>> domains = new HashMap<>();
        for (int row = 0; row < symbolsCount; row++) {
            for (int column = 0; column < symbolsCount; column++) {
                GridLocation location = new GridLocation(row, column);
                grid.add(location);
                ArrayList<Character> chars = new ArrayList<>(availableChars);
                Collections.shuffle(chars);
                domains.put(location, chars);
            }
        }

        CSP<GridLocation, Character> csp = new CSP<>(grid, domains);
        csp.addConstraint(new SudokuConstraint(grid));
        Map<GridLocation, Character> solution = csp.backtrackingSearch();
        if (solution == null) {
            System.out.println("No solution found!");
        } else {
            for (int row = 0; row < symbolsCount; row++) {
                if (row % size == 0) {
                    System.out.println("-------------");
                }
                for (int column = 0; column < symbolsCount; column++) {
                    if (column % size == 0) {
                        System.out.print("|");
                    }
                    System.out.print(solution.get(new GridLocation(row, column)));
                }
                System.out.println("|");
            }
            System.out.println("-------------");
        }
    }

    private static char getChar(int index) {
        if (index < 10) {
            return (char) ('1' + index);
        } else if (index <= 36) {
            return (char) ('A' + index - 10);
        } else {
            throw new IllegalStateException("Index of char cannot be greater that 36");
        }
    }

    static class GridLocation {

        private final int row;
        private final int column;

        GridLocation(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            GridLocation that = (GridLocation) o;

            return row == that.row && column == that.column;
        }

        @Override
        public int hashCode() {
            return (row << 16) + column;
        }
    }
}
