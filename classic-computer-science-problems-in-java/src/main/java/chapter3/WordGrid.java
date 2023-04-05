package chapter3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class WordGrid {

    private final char ALPHABET_LENGTH = 26;
    private final char FIRST_LETTER = 'A';
    private final int rows;
    private final int columns;
    private final char[][] grid;

    public WordGrid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        grid = new char[rows][columns];

        Random random = new Random();
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                char randomLetter = (char) (random.nextInt(ALPHABET_LENGTH) + FIRST_LETTER);
                grid[row][column] = randomLetter;
            }
        }
    }

    public static void main(String[] args) {
        WordGrid grid = new WordGrid(9, 9);
        List<String> words = List.of("MATTHEW", "JOE", "MARY", "SARAH", "SALLY");
        Map<String, List<List<GridLocation>>> domains = new HashMap<>();
        for (String word : words) {
            List<List<GridLocation>> domain = grid.generateDomain(word);
            Collections.shuffle(domain);
            domains.put(word, domain);
        }
        CSP<String, List<GridLocation>> csp = new CSP<>(words, domains);
        csp.addConstraint(new WordSearchConstraint(words));
        Map<String, List<GridLocation>> solution = csp.backtrackingSearch();
        if (solution == null) {
            System.out.println("No solution found!");
        } else {
            Random random = new Random();
            for (Map.Entry<String, List<GridLocation>> item : solution.entrySet()) {
                String word = item.getKey();
                List<GridLocation> locations = item.getValue();
                if (random.nextBoolean()) {
                    Collections.reverse(locations);
                }
                grid.mark(word, locations);
            }
            System.out.println(grid);

            System.out.println("Words: " + words);
        }
    }

    public void mark(String word, List<GridLocation> locations) {
        for (int i = 0; i < word.length(); i++) {
            GridLocation location = locations.get(i);
            grid[location.row][location.column] = word.charAt(i);
        }
    }

    public List<List<GridLocation>> generateDomain(String word) {
        List<List<GridLocation>> domain = new ArrayList<>();
        int length = word.length();

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                if (column + length <= columns) {
                    fillRight(domain, row, column, length);
                    if (row + length <= rows) {
                        fillDiagonalRight(domain, row, column, length);
                    }
                }
                if (row + length <= rows) {
                    fillDown(domain, row, column, length);
                    if (column - length >= 0) {
                        fillDiagonalLeft(domain, row, column, length);
                    }
                }
            }
        }

        return domain;
    }

    private void fillRight(List<List<GridLocation>> domain, int row, int column, int length) {
        List<GridLocation> locations = new ArrayList<>();
        for (int c = column; c < (column + length); c++) {
            locations.add(new GridLocation(row, c));
        }
        domain.add(locations);
    }

    private void fillDiagonalRight(List<List<GridLocation>> domain, int row, int column, int length) {
        List<GridLocation> locations = new ArrayList<>();
        for (int c = column, r = row; c < (column + length); c++, r++) {
            locations.add(new GridLocation(r, c));
        }
        domain.add(locations);
    }

    private void fillDown(List<List<GridLocation>> domain, int row, int column, int length) {
        List<GridLocation> locations = new ArrayList<>();
        for (int r = row; r < (row + length); r++) {
            locations.add(new GridLocation(r, column));
        }
        domain.add(locations);
    }

    private void fillDiagonalLeft(List<List<GridLocation>> domain, int row, int column, int length) {
        List<GridLocation> locations = new ArrayList<>();
        for (int c = column, r = row; r < (row + length); c--, r++) {
            locations.add(new GridLocation(r, c));
        }
        domain.add(locations);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (char[] rowArray : grid) {
            sb.append(rowArray);
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    public static class GridLocation {

        public final int row;
        public final int column;

        public GridLocation(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            GridLocation that = (GridLocation) o;

            if (row != that.row) return false;
            return column == that.column;
        }

        @Override
        public int hashCode() {
            int result = row;
            result = 31 * result + column;
            return result;
        }
    }
}
