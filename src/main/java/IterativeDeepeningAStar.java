import java.util.ArrayList;

/**
 * @author Joseph Adamson
 * @version November
 */
public class IterativeDeepeningAStar {

    /**
     * Instantiated as false.
     */
    private boolean goalFound;

    /**
     * Used to store the path to the goal (if found).
     */
    private final ArrayList<TilePuzzleNode> solution;

    /**
     * Basic constructor.
     */
    public IterativeDeepeningAStar(){
        this.solution = new ArrayList<>();
    }

    /**
     * Search space is gradually expanded, slowly increasing the 
     * f-score bound according to the next smallest f-score on the
     * frontier.
     *  
     * @param start: the start state (puzzle)
     * @return the path from the start state to the goal state.            
     */
    public ArrayList<TilePuzzleNode> IterativeDeepeningSearch(TilePuzzleNode start) {
        
        int bound = start.getHeuristicScore();
        
        while(true) {
            
            int boundUpdate = fScoreLimitedSearch(start, 0, bound);
            
            if (goalFound) {
                return solution; 
            }
            
            if (boundUpdate == Integer.MAX_VALUE) {
                throw new IllegalStateException("Target not in search space");
            }
            
            bound = boundUpdate;
        }
    }

    /**
     * Expands current node in a depth first search fashion;
     * search space is limited by f-score.
     * 
     * @param node: current state.
     * @param g: depth of the node in the search tree/number of 
     *         moves to reach the current state.
     * @param bound: defines the search space; algorithm 
     *             explores all nodes with an f-score < bound.
     * @return a f-score bound for the next search; the next smallest in
     * the search space.
     */
    private int fScoreLimitedSearch(TilePuzzleNode node, int g, int bound) {
        
        int f = g + node.getHeuristicScore();
        
        if (f > bound) {
            return f;
        }
        
        if (node.getHeuristicScore() == 0) {
            goalFound = true;
            solution.add(node);
            return 0;
        }
        
        int min = Integer.MAX_VALUE;

        ArrayList<TilePuzzleNode> children = node.generateChildren();
        for (TilePuzzleNode child : children) {
            int boundUpdate = fScoreLimitedSearch(child, g + 1, bound);

            // Make sure to the next smallest f-score of all possible
            // children for the next search step.
            min = Math.min(boundUpdate, min);
            
            if (goalFound) {
                solution.add(node);
                return 0;
            }
        }
        return min;
    }
}
