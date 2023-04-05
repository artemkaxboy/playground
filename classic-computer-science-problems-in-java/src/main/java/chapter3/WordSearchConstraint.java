package chapter3;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WordSearchConstraint extends Constraint<String, List<WordGrid.GridLocation>> {

    public WordSearchConstraint(List<String> variables) {
        super(variables);
    }

    @Override
    public boolean satisfied(Map<String, List<WordGrid.GridLocation>> assignment) {
        List<WordGrid.GridLocation> allLocations = assignment.values().stream()
                .flatMap(Collection::stream).toList();
        Set<WordGrid.GridLocation> allLocationsSet = new HashSet<>(allLocations);
        return allLocations.size() == allLocationsSet.size();
    }
}
