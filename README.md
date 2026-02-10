\section*{Eight Puzzle Solver (UCS \& A*)}

This project implements a solver for the \textbf{8-Puzzle problem} using two classical search algorithms:
\begin{itemize}
    \item \textbf{Uniform Cost Search (UCS)}
    \item \textbf{A* search} with \textbf{Chebyshev distance heuristic}
\end{itemize}

The puzzle is represented as a $3 \times 3$ grid, where the empty tile ($0$) can move in \textbf{eight possible directions} (horizontal, vertical, and diagonal).
Each state stores its path cost, heuristic value, number of node expansions, and a reference to its parent state, allowing reconstruction of the solution path.

\subsection*{Features}
\begin{itemize}
    \item State-space representation with parent tracking
    \item Successor generation including diagonal moves
    \item Chebyshev and Euclidean heuristic functions
    \item PriorityQueue-based search implementation
    \item Displays solution path, total cost, number of expansions, and execution time
\end{itemize}

\subsection*{Goal State}
\[
\begin{matrix}
6 & 5 & 4 \\
7 & 0 & 3 \\
8 & 1 & 2
\end{matrix}
\]

This project is intended for \textbf{educational purposes}, demonstrating both uninformed and informed search techniques in Artificial Intelligence.
