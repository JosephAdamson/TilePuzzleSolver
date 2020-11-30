import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManhattanWithLinearConflictTest {
    
    private ManhattanWithLinearConflict mdlc;
    private TilePuzzleNode nodeOne;
    
    @BeforeEach
    public void init() {
        mdlc = new ManhattanWithLinearConflict();
        
        int[][] dat1 = {
                {4, 2, 5},
                {1, 0, 6},
                {3, 8, 7}
        };
        nodeOne = new TilePuzzleNode(dat1);
    }
    
    @Test
    public void HeuristicTestOne() {
        int expected = 14;
        int actual = mdlc.estimate(nodeOne);
        assertEquals(expected, actual);
    }
    
}