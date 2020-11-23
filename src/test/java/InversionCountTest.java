import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class InversionCountTest {
    
    private int[][] arr1;
    private int[][] singleton;
    private int[][] allTheSame;
    private int[][] differentRows;

    @BeforeEach
    public void init() {
        arr1 = new int[][]{
                {12, 1, 10, 2},
                {7, 11, 4, 14},
                {5, 0, 9, 15},
                {8, 13, 6, 3}
        };
        
        singleton = new int[][]{
                {0}
        };
        
        allTheSame = new int[][]{
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 0, 1, 1, 1},
                {1, 1, 1, 1, 1}
        };
        
        differentRows = new int[][]{
                {3, 4, 7},
                {4, 6, 8, 8},
                {0, 2}
        };
        
        
    }
    
    //-----flatten tests-----
    
    /**
     * Normal case, with 0 tile (which should be omitted).
     */
    @Test
    public void flattenTest1() {
        int[] expected = {12, 1, 10, 2, 7, 11, 4, 14, 5, 9, 15, 8, 13, 6, 3};
        int[] actual = InversionCount.flatten(arr1);
        
        assertTrue(Arrays.equals(expected, actual));
    }

    /**
     * Singleton case.
     */
    @Test
    public void flattenTest2() {
        int[] expected = {};
        int[] actual = InversionCount.flatten(singleton);

        assertTrue(Arrays.equals(expected, actual));
    }

    /**
     * 2d array where all elements are the same.
     */
    @Test
    public void flattenTest3() {
        int[] expected = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        int[] actual = InversionCount.flatten(allTheSame);

        assertTrue(Arrays.equals(expected, actual));
    }

    /**
     * Different row lengths 
     */
    @Test
    public void flattenTest4() {
        int[] expected = {3, 4, 7, 4, 6, 8, 8, 2};
        int[] actual = InversionCount.flatten(differentRows);

        assertTrue(Arrays.equals(expected, actual));
    }
    
    //-----inversion count test-----

    /**
     * Normal case, should have 49 inversions
     */
    @Test
    public void inversionCountTest1() {
        int[] flattened = InversionCount.flatten(arr1);
        
        int expected = 49;
        int actual = InversionCount.inversionCount(flattened);

        assertEquals(expected, actual);
    }

    /**
     * Singleton test
     */
    @Test
    public void inversionCountTest2() {
        int[] flattened = InversionCount.flatten(singleton);

        int expected = 0;
        int actual = InversionCount.inversionCount(flattened);

        assertEquals(expected, actual);
    }

    /**
     * No inversions
     */
    @Test
    public void inversionCountTest3() {
        int[] flattened = {1, 2, 3, 4, 5, 6};

        int expected = 0;
        int actual = InversionCount.inversionCount(flattened);

        assertEquals(expected, actual);
    }

    /**
     * Elements all the same.
     */
    @Test
    public void inversionCountTest4() {
        int[] flattened = InversionCount.flatten(allTheSame);

        int expected = 0;
        int actual = InversionCount.inversionCount(flattened);

        assertEquals(expected, actual);
    }

    /**
     * All the same, with some negatives.
     */
    @Test
    public void inversionCountTest5() {
        int[] flattened = {1, 1, 1, -1, 1};

        int expected = 3;
        int actual = InversionCount.inversionCount(flattened);

        assertEquals(expected, actual);
    }
}