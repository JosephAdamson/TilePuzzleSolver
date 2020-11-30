/**
 * @author Joseph Adamson
 * @version November 2020
 */
public class ManhattanWithLinearConflict implements Heuristic{

    /**
     * Manhattan distance plus linear conflicts.
     * 
     * @param node: the current search state
     * @return the heuristic (priority) value of the node.
     */
    @Override
    public int estimate(TilePuzzleNode node) {
        int md = 0;
        int lc = 0;

        for (int i = 0; i < node.getDimensions(); i++) {
            for (int j = 0; j < node.getDimensions(); j++) {

                int val = node.getState()[i][j];

                if (val != 0) {
                    int goalRow = (val - 1) / node.getDimensions();
                    int goalCol = (val - 1) % node.getDimensions();

                    // Computing Manhattan Distance
                    md += (Math.abs(goalRow - i) + Math.abs(goalCol - j));

                    // Computing horizontal conflicts.
                    if (goalRow == i) {
                        for (int k = j + 1; k < node.getDimensions(); k++) {
                            int vNeighbour = node.getState()[i][k];
                            int vNeighbourGoalRow = (vNeighbour - 1) / node.getDimensions();

                            if (vNeighbourGoalRow == i && vNeighbour < val) {
                                lc += 2;
                            }
                        }
                    }

                    // Computing vertical conflicts.
                    if (goalCol == j) {
                        for (int m = i + 1; m < node.getDimensions(); m++) {
                            int hNeighbour = node.getState()[m][j];
                            int hNeighbourGoalCol = (hNeighbour - 1) % node.getDimensions();

                            if (hNeighbourGoalCol == j && hNeighbour < val) {
                                lc += 2;
                            }
                        }
                    }
                }
            }
        }
        return md + lc;
    }
}
