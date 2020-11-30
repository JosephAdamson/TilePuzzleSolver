import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Inversion count using merge sort.
 * 
 * An inversion is a when a tile (value) precedes a tile that has a lower value. 
 * The solution state would have no such inversions. More formally an inversion is 
 * a pair of tiles (a,b) where a appears before b but a > b.
 * 
 * @author Joseph Adamson
 * @version October 2020
 */
public class InversionCount {

    /**
     * Method uses Java stream to flatten a 2d array into 
     * a 1d array
     * 
     * @param board: a given game board
     * @return the game board as a flattened 1d array 
     */
    public static int[] flatten(int[][] board) {
    
        return Stream.of(board)
                .flatMapToInt(IntStream::of)

                // filters the 'blank' zero tile out so it is not
                // counted in the inversions.
                .filter(num -> num != 0)
                .toArray();
    }

    /**
     * Wrapper method for modified merge sort.
     * 
     * @param arr array to be sorted.
     * @return the number of inversions in the array.
     */
    public static int inversionCount(int[] arr) {
        return sortAndCount(arr, 0, arr.length -1);
    }

    /**
     * Modified merge sort used to find inversion pairs.
     * Method takes an array of size n and recursively partitions
     * it util we get n arrays of size 1. Each array is subsequently
     * merged with its neighbour.
     *
     * @param arr: an unsorted array of size n.
     */
    public static int sortAndCount(int[] arr, int left, int right) {
        int inversionCount = 0;
        
        if (left < right) {
            
            int mid = (left + right) / 2;
            
            // Sort the sub-arrays of arr
            inversionCount += sortAndCount(arr, left, mid);
            inversionCount += sortAndCount(arr, mid + 1, right);

            // merge the sorted sub-arrays.
            inversionCount += mergeAndCount(arr, left, mid, right);
        } 
        return inversionCount;
    }

    /**
     * Recursive method divides an array of size n by 2 by
     * computing a new mid variable for each recursive step
     * for a total time complexity of O(1).
     *
     * @param arr: an unsorted array.
     * @param left: left-most index; 0.
     * @param right: right-most index; arr.length.
     */
    public static int mergeAndCount(int[] arr, int left,int mid, int right) {
        int inversionCount = 0;
        
        // Find sizes of the sub-arrays
        int aSize = (mid - left) + 1;
        int bSize = right - mid;

        // Create temporary sub-arrays and copy data
        int[] a = new int[aSize];
        int[] b = new int[bSize];

        for (int i = 0; i < aSize; i++) {
            a[i] = arr[left + i];
        }
        for (int j = 0; j < bSize; j++) {
            b[j] = arr[mid + 1 + j];
        }

        // fence posts (starting points for both sub-arrays)
        int i = 0;
        int j = 0;

        // starting index of original array 
        int k = left;

        while (i < aSize && j < bSize) {
            if (a[i] <= b[j]) {
                arr[k++] = a[i++];
            } else {
                arr[k++] = b[j++];

                // IMPORTANT RULE:
                // 
                // e.g
                //       i
                //      {5, 6, 7}
                //
                //       j
                //      {2}
                //
                // If element i > element j than this holds true
                // for all elements to the RIGHT of i, therefore each element
                // after i adds an inverted pair to the count. (mid + 1)
                // being the maximum number of inversions (the length of array a).
                inversionCount += (mid + 1) - (left + i);
            }
        }

        // Copy the remainder of the contents (if any) of the sub-array 
        // 'a' back to the original.
        while (i < aSize) {
            arr[k++] = a[i++];
        }

        // Copy the remainder of the contents (if any) of the sub-array
        // 'b' back to the original.
        while (j < bSize) {
            arr[k++] = b[j++];
        }
        
        return inversionCount;
    }
}
