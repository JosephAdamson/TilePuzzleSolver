# Description
Implementation of heuristic-informed graph search algorithms (A* and IDA*) for popular 
N-tile sliding puzzle problem, dealing here with puzzles where N is 8 and 15.

eg.

```
Initial state                                 Goal state

    0 1 3      1 0 3      1 2 3      1 2 3      1 2 3
    4 2 5  ->  4 2 5  ->  4 0 5  ->  4 5 0  ->  4 5 6
    7 8 6      7 8 6      7 8 6      7 8 0      7 8 0	   
  
  Path length: 4 
```

### Checking if a puzzle is solvable

An inversion is when one tile precedes a tile with a lower value, 
more formally we have a pair of tiles (a, b) where a appears
before b but a > b. We can determine if a given N-tile puzzle is solvable by factoring
in the number of inversions it has using the following rules:

1. If the grid width is odd, then the number of inversions for a solvable problem must be even. 

2. If the grid width is even and the blank is on an even indexed row counting from the bottom, 
(second from bottom, fourth from bottom etc.) then the number of inversions for a solvable problem must be odd. 

3. If the grid width is even and the blank is on an odd indexed row counting from the bottom 
(last, third from last etc.) then the number of inversions for a solvable problem must be even


### A *

A* is an informed search algorithm, navigating a given search
space by picking a path to the solution with the lowest cumulative cost 
(informed by problem-specific knowledge). At each step in the search process 
the algorithm picks the next action that will minimize the total cost. 
This cost is determined by the function f(node) = g(node) + h(node); where g(node) 
represents the number of actions taken so far (or the depth of the node in 
the search tree) and h(node) is our heuristic function.  

### Iterative Deepening A*

Space complexity for A* is exponential in the branching factor 
(maintaining the frontier in a priority queue) requiring a
larger amount of memory the larger N. Due to this fact the more
memory efficient (linear in depth) IDA* is needed for 15-tile puzzles.

### Heuristics
Both heuristics used are problem specific.

* Manhattan

* Manhattan + Linear conflict 

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