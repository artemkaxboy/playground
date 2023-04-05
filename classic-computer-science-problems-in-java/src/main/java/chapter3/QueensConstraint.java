package chapter3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueensConstraint extends Constraint<Integer, Integer> {

    private final List<Integer> columns;

    public QueensConstraint(List<Integer> columns) {
        super(columns);
        this.columns = columns;
    }

    public static void main(String[] args) {
        List<Integer> variables = List.of(1, 2, 3, 4, 5, 6, 7, 8);

        Map<Integer, List<Integer>> domains = new HashMap<>();
        for (Integer variable : variables) {
            domains.put(variable, List.of(1, 2, 3, 4, 5, 6, 7, 8));
        }

        CSP<Integer, Integer> csp = new CSP<>(variables, domains);
        csp.addConstraint(new QueensConstraint(List.of(1, 2, 3, 4, 5, 6, 7, 8)));

        Map<Integer, Integer> solution = csp.backtrackingSearch();
        System.out.println(solution);
    }

    @Override
    public boolean satisfied(Map<Integer, Integer> assignment) {

        for (Map.Entry<Integer, Integer> item : assignment.entrySet()) {
            int i1r = item.getKey();
            int i1c = item.getValue();

            for (int i2r = i1r + 1; i2r <= columns.size(); i2r++) {
                if (assignment.containsKey(i2r)) {
                    int i2c = assignment.get(i2r);
                    if (i2c == i1c) {
                        return false;
                    }

                    if (Math.abs(i2r - i1r) == Math.abs(i2c - i1c)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
