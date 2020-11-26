import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author Joseph Adamson
 * @version October 2020
 */
public class TilePuzzleNode {
    /**
     * The width a height of the puzzle board.
     */
    private int dimensions;

    /**
     *  Current state of the puzzle board, represented as a 2d array.
     */
    private int[][] state;

    /**
     * Indexes of the 'blank' in the puzzle board, represented here by zero
     * an (x, y) pair.
     */
    private int[] blankIndexes;
    

    /**
     * Maximum number of legal moves for a given turn.
     */
    public static final int NUMBER_OF_MOVES = 4;
    
    public static final int[][] MOVES = {
            {-1, 0},  // slide the blank tile up.
            {1, 0},   // slide the blank tile down.
            {0, 1},   // slide the blank right. 
            {0, -1},  // slide the blank left.
    };

    /**
     * Constructor for the puzzle node; reads a puzzle file
     * and determines if the puzzle is solvable or not. If solvable
     * the state is saved to the object.
     * 
     * @param filename: a puzzle (.txt) file.
     */
    public TilePuzzleNode(String filename) {
        
        try {
            Scanner s = new Scanner(new File(filename));
            
            // First line of the puzzle file states the board dimensions.
            int n = s.nextInt();
            int[][] candidateBoard = new int[n][n];
            
            for (int i = 0; i < candidateBoard.length; i++) {
                for (int j = 0; j < candidateBoard[i].length; j++) {
                    candidateBoard[i][j] = s.nextInt();
                }
            }
            s.close();
            
            // Check if the board is solvable.
            if (isSolvable(candidateBoard, n)) {
                this.state = candidateBoard;
                this.blankIndexes = findBlank(candidateBoard);
                this.dimensions = n;
                // manhattanDistance();
            } else {
                throw new IllegalArgumentException("Input error: puzzle not solvable");
            }
            
        } catch (IOException e) {
           System.err.println("File not found..."); 
        }
    }

    /**
     * Constructor for making child nodes ONLY.
     * Heuristic score must be recomputed after the
     * blank has been shifted.
     * 
     * @param state: board state
     */
    public TilePuzzleNode (int[][] state) {
        this.state = state;
        this.dimensions = state.length;
        this.blankIndexes = findBlank(this.state);
    }

    /**
     * @return return board state.
     */
    public int[][] getState() {
        return this.state;
    }

    /**
     * @return indexes for the blank tile
     */
    public int[] getBlankIndexes() {
        return this.blankIndexes;
    }

    /**
     * @return puzzle board dimensions.
     */
    public int getDimensions() {
        return this.dimensions;
    }

    /**
     * <pre>
     *     Determining if the puzzle is solvable:
     *     
     *     > If the grid width is odd, then the number of inversions in for 
     *     a solvable problem must be even.
     * 
     *     > If the grid width is even, and the blank is on an even indexed row 
     *     counting from the bottom (second from bottom, fourth from bottom etc.) 
     *     then the number of inversions for a solvable problem must be odd.
     * 
     *     > If the grid width is even and the blank is on an odd indexed row counting 
     *     from the bottom (last, third from last etc.) then the number of inversions 
     *     for a solvable problem must be even.
     * </pre>
     * 
     * @param candidateBoard: a puzzle board.
     * @param n: the dimensions of a board.
     * @return true if the puzzle board is solvable, false otherwise.
     */
    public static boolean isSolvable(int[][] candidateBoard, int n) {

        // Compute the position of the zero tile
        int[] blankPosition = findBlank(candidateBoard);
        
        // Count the number of inversions in the candidate board.
        int[] flattened = InversionCount.flatten(candidateBoard);
        int inversions = InversionCount.inversionCount(flattened);

        // Case if the grid dimensions are odd.
        if ((n & 1) != 0) {
            return ((inversions & 1) == 0);
        } else {  // Case if the grid dimensions are even.

            // counting rows from the bottom up (e.g. last row == 1)
            int bottomUpRow = (candidateBoard.length) - blankPosition[0];

            // Even row from the bottom, must have odd number of inversions.
            if ((bottomUpRow & 1) == 0) {
                return ((inversions & 1) != 0);
            } else { // Odd row from the bottom, must have even number of inversions.
                return ((inversions & 1) == 0);
            }
        }
    }

    /**
     * Locate the indices of the blank tile.
     * 
     * @param board: the puzzle board.
     * @return an int array (x, y) of the blank tile.
     */
    public static int[] findBlank(int[][] board) {

        int[] blankCoords = null;

        // A solvable board should have exactly ONE blank tile.
        int zeroCount = 0;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 0) {
                    blankCoords = new int[2];
                    blankCoords[0] = i;
                    blankCoords[1] = j;
                    zeroCount++;
                }
            }
        }

        if (blankCoords == null) {
            throw new IllegalArgumentException("Puzzle board does " +
                    "not contain blank tile");
        } else if (zeroCount != 1) {
            throw new IllegalArgumentException("Board doesn't contain the correct " +
                    "amount of blank tiles (1)");
        } else {
            return blankCoords;
        }
    }

    /**
     * Move the blank tile one space (up, down, left, right).
     * Clones current state and produces new node where the blank
     * has been moved.
     *
     * @param move (up, down, left, right)
     * @return the
     */
    public TilePuzzleNode slideBlank(int[] move) {

        int nextX = this.getBlankIndexes()[0] + move[0];
        int nextY = this.getBlankIndexes()[1] + move[1];

        // Check move
        if (!(inBoard(nextX, nextY))) {
            return null;
        } else {

            // clone the current node to produce a new state
            // and recompute Manhattan distance.
            int[][] cloned = cloneState(this.state);
            TilePuzzleNode newState = new TilePuzzleNode(cloned);
            newState.swapBlank(newState.blankIndexes[0], 
                    newState.blankIndexes[1], nextX, nextY);
            //newState.manhattanDistance();
            
            return newState;
        }
    }

    /**
     * Used to swap two tiles.
     * 
     * @param x1 the current x index of the blank.
     * @param y1 the current y index of the blank.
     * @param x2 the new x index of the blank.
     * @param y2 the new y index of the blank.
     */
    public void swapBlank(int x1, int y1, int x2, int y2) {
        int temp = state[x1][y1];
        state[x1][y1] = state[x2][y2];
        state[x2][y2] = temp;
        
        // Finally set the new blank indexes.
        this.blankIndexes[0] = x2;
        this.blankIndexes[1] = y2;
    }

    /**
     * Clones the state of the parent node to generate
     * a child.
     * 
     * @param board: current state.
     * @return clone of the current board state.
     */
    public static int[][] cloneState(int[][] board) {
        int[][] cloned = new int[board.length][board.length];
        
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, cloned[i], 0, board[i].length);
        }
        return cloned;
    }

    /**
     * Checks the legality if a move. 
     * 
     * @param x2 the new x index of the blank.
     * @param y2 the new y index of the blank.
     * @return true if the move can be made, false otherwise.
     */
    public boolean inBoard(int x2, int y2) {
        return (x2 >= 0 && y2 >= 0 && x2 < dimensions && y2 < dimensions);
    }

    /**
     * Expands current puzzle node, generating all possible child 
     * nodes according all legitimate moves. A node can generate a
     * maximum of four children. 
     * 
     * @return an arraylist of possible moves (child nodes)
     */
    public ArrayList<TilePuzzleNode> generateChildren() {
        ArrayList<TilePuzzleNode> children = new ArrayList<>(4);
        
        for (int[] move : MOVES) {
            TilePuzzleNode node = slideBlank(move);
            
            if (node != null) {
                
                // set the parent node.
                children.add(node);
            }
        }
        return children;
    }

    /**
     * Compares states of two puzzle nodes.
     * 
     * @param obj: another puzzle node for comparison.
     * @return true if the states of both puzzle nodes
     * are the same (all tiles, including the blank are in 
     * the same place).
     */
    @Override
    public boolean equals(Object obj) {
       if (obj == null) {
           return false;
       } else if (obj.getClass() != this.getClass()) {
           return false;
       } else {
           TilePuzzleNode other = (TilePuzzleNode) obj;
           return Arrays.deepEquals(this.state, other.state);
       }
    }

    /**
     * Auto generated hashCode function allowing puzzle nodes to
     * be stored in a hash map.
     * 
     * @return a unique hash code generated using the attributes
     * of a given puzzle node.
     */
    @Override
    public int hashCode() {
        int result = Arrays.hashCode(state);
        result = 31 * result + Arrays.hashCode(blankIndexes);
        return result;
    }

    /**
     * @return board state in human readable format.
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (int[] ints : state) {
            for (int j = 0; j < ints.length; j++) {
                if (j == state.length - 1) {
                    result.append(ints[j]).append("\t \n");
                } else {
                    result.append(ints[j]).append("\t");
                }
            }
        }
        return result.toString();
    }
}
