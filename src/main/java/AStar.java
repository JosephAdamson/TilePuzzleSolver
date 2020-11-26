import java.util.*;

/**
 * Conventional A* search.
 * 
 * @author Joseph Adamson
 * @version November
 */
public class AStar {

    /**
     * Heuristic function to inform the search.
     */
    private final Heuristic heuristic;

    /**
     * @param heuristic search heuristic of choice.
     */
    public AStar(Heuristic heuristic) {
        this.heuristic = heuristic;
    }

    /**
     * A* implementation.
     * 
     * @param start the initial node (puzzle to be solved)
     * @return the path from the start state to the goal state.
     */
    public ArrayList<TilePuzzleNode> search(TilePuzzleNode start) {

        // Good for re-tracing path from goal to start in the search 
        // space {child : parent}, also used as a log of visited nodes.
        HashMap<TilePuzzleNode, TilePuzzleNode> predecessor = new HashMap<>();
        
        // g (the depth of the node in the search space).
        HashMap<TilePuzzleNode, Integer> g = new HashMap<>();
        
        // f(node) = g(node) + h(node).
        HashMap<TilePuzzleNode, Integer> fScore = new HashMap<>();
        
        // Used to enforce order on the basis of f(node).
        Comparator<TilePuzzleNode> comp = new Comparator<TilePuzzleNode>() {
            @Override
            public int compare(TilePuzzleNode a, TilePuzzleNode b) {
                return fScore.get(a) - fScore.get(b);
            }
        };
        PriorityQueue<TilePuzzleNode> toVisit = new PriorityQueue<>(10000, comp);

        g.put(start, 0);
        fScore.put(start, heuristic.estimate(start));
        toVisit.add(start);
        while (!(toVisit.isEmpty())) {
            TilePuzzleNode current = toVisit.poll();
            
            if (fScore.get(current) - g.get(current) == 0) {
                ArrayList<TilePuzzleNode> solution = new ArrayList<>();
                
                TilePuzzleNode backTrace = current;
                while(backTrace != null) {
                    solution.add(backTrace);
                    backTrace = predecessor.get(backTrace);
                }
                return solution;
            }
            
            ArrayList<TilePuzzleNode> children = current.generateChildren();
            for (TilePuzzleNode child : children) {
                if (!(predecessor.containsKey(child))) {
                    predecessor.put(child, current);
                    g.put(child, g.get(current) + 1);
                    fScore.put(child, g.get(child) + heuristic.estimate(child));
                    toVisit.add(child);
                }
            }
        }
        throw new IllegalStateException("Goal node not reachable");
    }
}
