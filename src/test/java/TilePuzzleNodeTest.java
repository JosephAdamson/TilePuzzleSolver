import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.*;

class TilePuzzleNodeTest {

    private int[][] threeByThree;
    private int[][] fourByFour;
    private int[][] unsolvable;
    private int[][] noBlank;
    private int[][] moreThanOneBlank;

    @BeforeEach
    public void init() {
        threeByThree = new int[][]{
                {1, 8, 2},
                {0, 4, 3},
                {7, 6, 5}
        };

        fourByFour = new int[][]{
                {5, 1, 3, 4},
                {2, 0, 7, 8},
                {9, 6, 10, 12},
                {13, 14, 11, 15}
        };

        unsolvable = new int[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 15},
                {13, 14, 12, 0}
        };

        noBlank = new int[][]{
                {1, 3, 6, 5},
                {11, 34, 8, 9},
                {4, 67, 68, 99},
                {55, 15, 16, 41}
        };

        moreThanOneBlank = new int[][]{
                {1, 3, 6, 5},
                {11, 0, 8, 9},
                {4, 67, 68, 99},
                {55, 15, 16, 0}
        };

    }

    //----constructor tests----

    /**
     * Constructing a legitimate board correctly.
     */
    @Test
    public void constructorTest1() {
        int[][] expectedBoard = {
                {12, 1, 10, 2},
                {7, 11, 4, 14},
                {5, 0, 9, 15},
                {8, 13, 6, 3}
        };

        int[] expectedBlankPosition = {2, 1};

        TilePuzzleNode result = new TilePuzzleNode(System.getProperty("user.dir")
                + "/src/test/testResources/puzzle1.txt");

        int[][] actualBoard = result.getState();
        int[] actualBlankPosition = result.getBlankIndexes();


        for (int i = 0; i < actualBoard.length; i++) {
            assertTrue(Arrays.equals(expectedBoard[i], actualBoard[i]));
        }

        for (int i = 0; i < expectedBlankPosition.length; i++) {
            assertTrue(Arrays.equals(expectedBlankPosition, actualBlankPosition));
        }
    }

    //----isSolvable----

    /**
     *  3x3 tile board; solvable.
     */
    @Test
    public void isSolvableTest1() {
        assertTrue(TilePuzzleNode.isSolvable(threeByThree, 3));
    }

    /**
     * Classic 4x4; solvable.
     */
    @Test
    public void isSolvableTest2() {
        assertTrue(TilePuzzleNode.isSolvable(fourByFour, 4));
    }

    /**
     * 4x4; unsolvable.
     */
    @Test
    public void isSolvableTest() {
        assertFalse(TilePuzzleNode.isSolvable(unsolvable, 4));
    }

    //----findBlank----

    /**
     *  3x3 0 tile @ [1, 0]
     */
    @Test
    public void findBlankTest1() {
        int[] expected = {1, 0};
        int[] actual = TilePuzzleNode.findBlank(threeByThree);

        assertTrue(Arrays.equals(expected, actual));
    }

    /**
     * 4x4 0 tile @ [1, 1]
     */
    @Test
    public void findBlankTest2() {
        int[] expected = {1, 1};
        int[] actual = TilePuzzleNode.findBlank(fourByFour);

        assertTrue(Arrays.equals(expected, actual));
    }

    /**
     * No blank
     */
    @Test
    public void findBlankTest3() {
        assertThrows(IllegalArgumentException.class, () -> {
            TilePuzzleNode.findBlank(noBlank);
        });
    }

    /**
     * More than one blank.
     */
    @Test
    public void findBlankTest4() {
        assertThrows(IllegalArgumentException.class, () -> {
            TilePuzzleNode.findBlank(noBlank);
        });
    }
    
    //----testing moves----

    /**
     * Slide up
     * 
     * original: 
     *           12 1 10 2
     *           7 11 4 14
     *           5 0 9 15
     *           8 13 6 3
     */
    @Test
    public void slideBlankTest1() {
        int[][] expected = {
                {12, 1, 10, 2},
                {7, 0, 4, 14},
                {5, 11, 9, 15},
                {8, 13, 6, 3}
        };
        
        TilePuzzleNode node = new TilePuzzleNode(System.getProperty("user.dir")
                + "/src/test/testResources/puzzle1.txt");
        
        TilePuzzleNode newNode = node.slideBlank(TilePuzzleNode.MOVES[0]);
        
        int[][] actual = newNode.getState();

        for (int i = 0; i < actual.length; i++) {
            System.out.println(Arrays.toString(actual[i]));
        }
        
        for (int i = 0; i < actual.length; i++) {
            assertTrue(Arrays.equals(expected[i], actual[i]));
        }
    }

    /**
     * Slide down
     * 
     * original: 
     *           12 1 10 2
     *           7 11 4 14
     *           5 0 9 15
     *           8 13 6 3
     */
    @Test
    public void slideBlankTest2() {
        int[][] expected = {
                {12, 1, 10, 2},
                {7, 11, 4, 14},
                {5, 13, 9, 15},
                {8, 0, 6, 3}
        };

        TilePuzzleNode node = new TilePuzzleNode(System.getProperty("user.dir")
                + "/src/test/testResources/puzzle1.txt");

        TilePuzzleNode newNode = node.slideBlank(TilePuzzleNode.MOVES[1]);

        int[][] actual = newNode.getState();

        for (int i = 0; i < actual.length; i++) {
            System.out.println(Arrays.toString(actual[i]));
        }

        for (int i = 0; i < actual.length; i++) {
            assertTrue(Arrays.equals(expected[i], actual[i]));
        }
    }

    /**
     * Slide right
     *
     * original: 
     *           12 1 10 2
     *           7 11 4 14
     *           5 0 9 15
     *           8 13 6 3
     */
    @Test
    public void slideBlankTest3() {
        int[][] expected = {
                {12, 1, 10, 2},
                {7, 11, 4, 14},
                {5, 9, 0, 15},
                {8, 13, 6, 3}
        };

        TilePuzzleNode node = new TilePuzzleNode(System.getProperty("user.dir")
                + "/src/test/testResources/puzzle1.txt");

        TilePuzzleNode newNode = node.slideBlank(TilePuzzleNode.MOVES[2]);

        int[][] actual = newNode.getState();

        for (int i = 0; i < actual.length; i++) {
            System.out.println(Arrays.toString(actual[i]));
        }

        for (int i = 0; i < actual.length; i++) {
            assertTrue(Arrays.equals(expected[i], actual[i]));
        }
    }

    /**
     * Slide left
     *
     * original: 
     *           12 1 10 2
     *           7 11 4 14
     *           5 0 9 15
     *           8 13 6 3
     */
    @Test
    public void slideBlankTest4() {
        int[][] expected = {
                {12, 1, 10, 2},
                {7, 11, 4, 14},
                {0, 5, 9, 15},
                {8, 13, 6, 3}
        };

        TilePuzzleNode node = new TilePuzzleNode(System.getProperty("user.dir")
                + "/src/test/testResources/puzzle1.txt");

        TilePuzzleNode newNode = node.slideBlank(TilePuzzleNode.MOVES[3]);

        int[][] actual = newNode.getState();

        for (int i = 0; i < actual.length; i++) {
            assertTrue(Arrays.equals(expected[i], actual[i]));
        }
    }

    /**
     * Out of bounds will return null
     *
     * original: 
     *           1 8 2
     *           0 4 3
     *           7 6 5
     */
    @Test
    public void slideBlankTest5() {
        TilePuzzleNode node = new TilePuzzleNode(System.getProperty("user.dir")
                + "/src/test/testResources/puzzle2.txt");
        
        TilePuzzleNode actual = node.slideBlank(TilePuzzleNode.MOVES[3]);
        
        assertEquals(null, actual);
    }
    
    //----generateChild----

    /**
     * original: 
     *           12 1 10 2
     *           7 11 4 14
     *           5 0 9 15
     *           8 13 6 3
     */
    @Test
    public void generateChildTest1() {
        TilePuzzleNode node = new TilePuzzleNode(System.getProperty("user.dir")
                + "/src/test/testResources/puzzle1.txt");
        
        ManhattanDistance md = new ManhattanDistance();

        int[][] slideUp = {
                {12, 1, 10, 2},
                {7, 0, 4, 14},
                {5, 11, 9, 15},
                {8, 13, 6, 3}
        };
        TilePuzzleNode sLu = new TilePuzzleNode(slideUp);
        
        int[][] slideDown = {
                {12, 1, 10, 2},
                {7, 11, 4, 14},
                {5, 13, 9, 15},
                {8, 0, 6, 3}
        };
        TilePuzzleNode sLd = new TilePuzzleNode(slideDown);
        
        int[][] slideRight = {
                {12, 1, 10, 2},
                {7, 11, 4, 14},
                {5, 9, 0, 15},
                {8, 13, 6, 3}
        };
        TilePuzzleNode sLr = new TilePuzzleNode(slideRight);
        
        int[][]slideLeft = {
                {12, 1, 10, 2},
                {7, 11, 4, 14},
                {0, 5, 9, 15},
                {8, 13, 6, 3}
        };
        TilePuzzleNode sLl = new TilePuzzleNode(slideLeft);

        TilePuzzleNode[] arr = {sLu, sLd, sLr, sLl};
        ArrayList<TilePuzzleNode> expected = new ArrayList<>(Arrays.asList(arr));
        
        ArrayList<TilePuzzleNode> actual = node.generateChildren();

        for (int i = 0; i < actual.size(); i++) {
            /*System.out.println("===================");
            System.out.println(expected.get(i));
            System.out.println(expected.get(i).getHeuristicScore());
            System.out.println(actual.get(i));
            System.out.println(actual.get(i).getHeuristicScore());
            System.out.println("===================");*/
            assertEquals(md.estimate(expected.get(i)), md.estimate(actual.get(i)));
        }
    }

    /**
     * original:
     *           1 8 2
     *           0 4 3
     *           7 6 5
     */
    @Test
    public void generateChildTest2() {
        TilePuzzleNode node = new TilePuzzleNode(System.getProperty("user.dir")
                + "/src/test/testResources/puzzle2.txt");
        ManhattanDistance md = new ManhattanDistance();

        int[][] slideUp = {
                {0, 8, 2}, 
                {1, 4, 3}, 
                {7, 6, 5}
        };
        TilePuzzleNode sLu = new TilePuzzleNode(slideUp);

        int[][] slideDown = {
                {1, 8, 2},
                {7, 4, 3},
                {0, 6, 5}
        };
        TilePuzzleNode sLd = new TilePuzzleNode(slideDown);

        int[][] slideRight = {
                {1, 8, 2},
                {4, 0, 3},
                {7, 6, 5}
        };
        TilePuzzleNode sLr = new TilePuzzleNode(slideRight);

        TilePuzzleNode[] arr = {sLu, sLd, sLr};
        ArrayList<TilePuzzleNode> expected = new ArrayList<>(Arrays.asList(arr));
        
        ArrayList<TilePuzzleNode> actual = node.generateChildren();

        for (int i = 0; i < actual.size(); i++) {
            assertEquals(md.estimate(expected.get(i)), md.estimate(actual.get(i)));
        }
    }

    /**
     * ironing out a possible problem 
     */
    @Test
    public void generateChildTest3() {
        int[][] t = {
                {1, 0, 3},
                {4, 2, 5},
                {7, 8, 6}
        };
        TilePuzzleNode test = new TilePuzzleNode(t);
        ArrayList<TilePuzzleNode> children = test.generateChildren();
        
        for (TilePuzzleNode child : children) {
            System.out.println(child);
        }
    }

    /**
     * Simple test using the same state.
     */
    @Test
    public void EqualsTest() {
        int[][] stateOne = {
                {1, 8, 2}, 
                {0, 4, 3}, 
                {7, 6, 5}
        };
        
        int[][] stateTwo = {
                {1, 8, 2},
                {0, 4, 3},
                {7, 6, 5}
        };
        
        TilePuzzleNode one = new TilePuzzleNode(stateOne);
        TilePuzzleNode two = new TilePuzzleNode(stateTwo);
        assertTrue(one.equals(two));
    }
}