package chapter3;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class SudokuConstraint extends Constraint<SudokuCreator.GridLocation, Character> {

    private final int charsCount;
    private final int squareSize;

    public SudokuConstraint(List<SudokuCreator.GridLocation> variables) {
        super(variables);
        charsCount = (int) Math.sqrt(variables.size());
        squareSize = (int) Math.sqrt(charsCount);
    }

    @Override
    public boolean satisfied(Map<SudokuCreator.GridLocation, Character> assignment) {

        for (int i = 0; i < charsCount; i++) {
            if (!isRowOk(i, assignment) || !isColumnOk(i, assignment) || !isSquareOk(i, assignment)) {
                return false;
            }
        }
        return true;
    }

    private boolean isRowOk(int rowIndex, Map<SudokuCreator.GridLocation, Character> assignment) {
        HashSet<Character> values = new HashSet<>();
        for (int columnIndex = 0; columnIndex < charsCount; columnIndex++) {
            Character assignedValue = assignment.get(new SudokuCreator.GridLocation(rowIndex, columnIndex));
            if (assignedValue != null) {
                boolean newValue = values.add(assignedValue);
                if (!newValue) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isColumnOk(int columnIndex, Map<SudokuCreator.GridLocation, Character> assignment) {
        HashSet<Character> values = new HashSet<>();
        for (int rowIndex = 0; rowIndex < charsCount; rowIndex++) {
            Character assignedValue = assignment.get(new SudokuCreator.GridLocation(rowIndex, columnIndex));
            if (assignedValue != null) {
                boolean newValue = values.add(assignedValue);
                if (!newValue) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isSquareOk(int squareIndex, Map<SudokuCreator.GridLocation, Character> assignment) {
        HashSet<Character> values = new HashSet<>();
        int rowOffset = (squareIndex / squareSize) * squareSize;
        int columnOffset = (squareIndex % squareSize) * squareSize;
        for (int item = 0; item < charsCount; item++) {
            Character assignedValue = assignment.get(new SudokuCreator.GridLocation(rowOffset + (item / squareSize),
                    columnOffset + (item % squareSize)));
            if (assignedValue != null) {
                boolean newValue = values.add(assignedValue);
                if (!newValue) {
                    return false;
                }
            }
        }
        return true;
    }
}
