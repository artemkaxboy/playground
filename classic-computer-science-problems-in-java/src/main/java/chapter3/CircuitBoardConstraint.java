package chapter3;

import java.util.*;

public class CircuitBoardConstraint extends Constraint<CircuitBoard.Element, List<CircuitBoard.GridLocation>> {

    public CircuitBoardConstraint(List<CircuitBoard.Element> variables) {
        super(variables);
    }

    @Override
    public boolean satisfied(Map<CircuitBoard.Element, List<CircuitBoard.GridLocation>> assignment) {
        List<CircuitBoard.GridLocation> allLocations = assignment.values().stream()
                .flatMap(Collection::stream).toList();
        Set<CircuitBoard.GridLocation> allLocationsSet = new HashSet<>(allLocations);
        return allLocations.size() == allLocationsSet.size();
    }
}
