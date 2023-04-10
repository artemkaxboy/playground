package chapter3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CrossingWordSearchConstraint extends Constraint<String, List<WordGrid.GridLocation>> {

    public CrossingWordSearchConstraint(List<String> variables) {
        super(variables);
    }

    @Override
    public boolean satisfied(Map<String, List<WordGrid.GridLocation>> assignment) {

        Map<Integer, Character> letters = new HashMap<>();
        for (Map.Entry<String, List<WordGrid.GridLocation>> wordWithLocations : assignment.entrySet()) {
            for (int i = 0; i < wordWithLocations.getValue().size(); i++) {
                WordGrid.GridLocation location = wordWithLocations.getValue().get(i);
                int key = (location.row << 16) + location.column;
                char letter = wordWithLocations.getKey().charAt(i);
                Character existing = letters.put(key, letter);
                if (existing != null && !Objects.equals(existing, letter)) return false;
            }
        }

        return true;
    }
}
