# Description
Implementation of graph search algorithms (A* and IDA*) for popular 
N-tile sliding puzzle problem, dealing here with puzzles where N is 8 and 15.

eg.

```
Initial state                                 Goal state

    0 1 3      1 0 3      1 2 3      1 2 3      1 2 3
    4 2 5  ->  4 2 5  ->  4 0 5  ->  4 5 0  ->  4 5 6
    7 8 6      7 8 6      7 8 6      7 8 0      7 8 0	   
  
  Path length: 4 
```

### 8-Tile Puzzle

A* implementation can solve any 8-tile puzzle in a reasonable time using 
Manhattan distance heuristic, with the more memory efficient IDA* implementation 
often generating the solution quicker.

### 15-tile Puzzle

Space complexity for A* is exponential in the branching factor 
(4 possibilities max for an expanded node) requiring
larger amount of memory the larger N, because of this the more
memory efficient (linear in depth) IDA* is used for 15-tile
puzzles. However, even this does not come up with a solution 
every time (60% of test cases) (see TODO).

## License

MIT License

Copyright (c) [2020] [Joseph Adamson]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.