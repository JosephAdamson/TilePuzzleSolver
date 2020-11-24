import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Class used for puzzle generation and to gauge the
 * effectiveness of the implemented algorithms.
 */
public class Solver {

    /**
     * Reconstructs path from start state to goal state, printing
     * the path to the terminal.
     *
     * @param solution path to goal state.
     */
    public static void printPath(ArrayList<TilePuzzleNode> solution) {
        Collections.reverse(solution);
        for (int i = 0; i < solution.size(); i++) {
            TilePuzzleNode node = solution.get(i);

            System.out.println(node);

            if (i != solution.size() -1) {
                System.out.println("    |");
                System.out.println("    V\n");
            }
        }
    }

    /**
     * Method randomly generates a solvable 8 tile puzzle.
     * 
     * @return TilePuzzleNode.
     */
    public static TilePuzzleNode generateEightTilePuzzle() {
        
        // Create and populate array.
        int[][] arr = new int[3][3];
        int num = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                arr[i][j] = num++;
            }
        }
        // Array populated array is actually already solvable; we
        // do an initial shuffle to avoid method outputting the
        // same state each time.
        shuffleBoard(arr);
        
        while (!(TilePuzzleNode.isSolvable(arr, 3))) {
            shuffleBoard(arr);
        }
        TilePuzzleNode result = new TilePuzzleNode(arr);
        result.manhattanDistance();
        return result;
    }

    /**
     * Modified fisher-Yates shuffle for 2D array.
     * 
     * @param arr: a puzzle board.
     */
    public static void shuffleBoard(int[][] arr) {
        Random rand = new Random();
        
        int n = arr.length -1;
            for (int i = n; i >= 0; i--) {
                for (int j = n; j >= 0; j--) {
                    
                    int xPos = rand.nextInt(i + 1);
                    int yPos = rand.nextInt(j + 1);
                    
                    // Swap
                    int temp = arr[i][j];
                    arr[i][j] = arr[xPos][yPos];
                    arr[xPos][yPos] = temp;
                }
            }
    }
    
    
    public static void main(String[] args) {
        TilePuzzleNode test = generateEightTilePuzzle();
        System.out.println(test);
        printPath(AStar.AStarSearch(test));
    }
}
