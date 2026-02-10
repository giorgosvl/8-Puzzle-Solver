Eight Puzzle Solver (UCS & A*)

This project implements a solver for the 8-Puzzle problem using two classical search algorithms:

Uniform Cost Search (UCS)

A* search with Chebyshev distance heuristic

The puzzle is represented as a 3×3 grid, where the empty tile (0) can move in 8 possible directions (horizontal, vertical, and diagonal).
Each state keeps track of its path cost, heuristic value, number of expansions, and parent state in order to reconstruct the solution path.

Features
