package chapter3;

import java.util.*;

public class CircuitBoard {

    private final int rows;
    private final int columns;
    private final char[][] grid;

    public CircuitBoard(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        grid = new char[rows][columns];

        for (int row = 0; row < rows; row++) {
            Arrays.fill(grid[row], '*');
        }
    }

    public static void main(String[] args) {
        CircuitBoard grid = new CircuitBoard(9, 9);
        List<Element> elements = List.of(new Element(1, 1, 1), new Element(2, 2, 2), new Element(3, 3, 3), new Element(4, 4, 4), new Element(5, 5, 5));
        Map<Element, List<List<GridLocation>>> domains = new HashMap<>();
        for (Element element : elements) {
            List<List<GridLocation>> domain = grid.generateDomain(element);
            Collections.shuffle(domain);
            domains.put(element, domain);
        }
        CSP<Element, List<GridLocation>> csp = new CSP<>(elements, domains);
        csp.addConstraint(new CircuitBoardConstraint(elements));
        Map<Element, List<GridLocation>> solution = csp.backtrackingSearch();
        if (solution == null) {
            System.out.println("No solution found!");
        } else {
            for (Map.Entry<Element, List<GridLocation>> item : solution.entrySet()) {
                Element element = item.getKey();
                List<GridLocation> locations = item.getValue();
                grid.mark(element, locations);
            }
            System.out.println(grid);

            System.out.println("Elements: " + elements);
        }
    }

    public void mark(Element element, List<GridLocation> locations) {
        for (GridLocation location : locations) {

            grid[location.row][location.column] = Character.forDigit((int) (element.id % 10), 10);
        }
    }

    public List<List<GridLocation>> generateDomain(Element element) {
        List<List<GridLocation>> domain = new ArrayList<>();
        int width = element.width;
        int height = element.height;

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                if (column + width <= columns && row + height < rows) {
                    fill(domain, row, column, element);
                }
            }
        }

        return domain;
    }

    private void fill(List<List<GridLocation>> domain, int row, int column, Element element) {
        List<GridLocation> locations = new ArrayList<>();
        for (int c = column; c < (column + element.width); c++) {
            for (int r = row; r < (row + element.height); r++) {
                locations.add(new GridLocation(r, c));
            }
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

    public record GridLocation(int row, int column) {

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            GridLocation that = (GridLocation) o;

            if (row != that.row) return false;
            return column == that.column;
        }
    }

    static class Element {

        long id;
        int width;
        int height;

        public Element(long id, int width, int height) {
            this.id = id;
            this.width = width;
            this.height = height;
        }

        @Override
        public String toString() {
            return id + ": " + width + "x" + height;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Element element = (Element) o;

            return id == element.id;
        }

        @Override
        public int hashCode() {
            return (int) (id ^ (id >>> 32));
        }
    }
}
