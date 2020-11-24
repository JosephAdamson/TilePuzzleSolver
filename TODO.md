* ~~Be able to generate a puzzle using a .txt file (can be done in the puzzler constructor)~~:

  * ~~Be able to count inversions~~
  * ~~Be able to check if the puzzle is solvable according
  to pre-established formula.~~
  * ~~puzzle should be able to accommodate different configurations
  of (n * n) - 1~~ 

* PuzzleNode :
  * ~~Execute moves (up, down, left, right)~~
  * ~~current state (As dictated by input puzzle).~~
  * ~~coords (x,y) of blank (represented as 0 or _).~~
  * ~~Compute cost of heuristic function using the ~~manhattan distance~~. 
  * ~~Generate children method~~.
  * ~~Generate path method~~.
  
  
* A* search:  
  * ~~NOTE: The cost of using ordinary A* (memory-wise) is too high, 
  look at Iterative deepening A*~~
  * ~~Review bugs in IDA* (a rewrite)~~
  
* Solution
  * ~~3 x 3 puzzle generator to test robustness of 
  A* algorithm with more variable puzzle combinations~~.
  
  * The same by 4 x 4 puzzles for IDA* to get
  better gauge of effectiveness (how often will
  it find a solution). 
  
  * Need to look at better heuristics for improved
  IDA* search results
    * possible linear conflict + manhattan distance.