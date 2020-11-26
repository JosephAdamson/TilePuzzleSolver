import java.util.HashMap;

public class ManhattanDistance implements Heuristic {

    /**
     * <pre>
     * The Manhattan distance doesn't overestimate the cost to getting
     * to the goal state because every tile will have to be moved at least 
     * the number of spots in between itself and its correct position 
     * (ref: https://en.wikipedia.org/wiki/Admissible_heuristic#:~:text=The%20Manhattan%20distance%20is%20an,itself%20and%20its%20correct%20position.)
     * Using this heuristic will ensure the A* search is optimal.
     *
     * Computes the Manhattan distance of the current
     * state of a puzzle node and its goal state; assumed to
     * be the tile numbers in ascending order with the blank 
     * coming last
     *
     * e.g 
     *      7 2 4        1 2 3
     *      5 0 6   ->   4 5 6 
     *      8 3 1        7 8 0
     *
     * </pre>
     */
    @Override
    public int estimate(TilePuzzleNode node) {
        int count = 0;

        for (int i = 0; i < node.getState().length; i++) {
            for (int j = 0; j < node.getState()[i].length; j++) {
                int val = node.getState()[i][j];

                if (val != 0) {

                    // The index position of the value in the goal state.
                    int goalRow = (val - 1) / node.getState().length;
                    int goalCol = (val - 1) % node.getState().length;

                    count += (Math.abs(goalRow - i) + Math.abs(goalCol - j));
                }
            }
        }
        return count;
    }
}
