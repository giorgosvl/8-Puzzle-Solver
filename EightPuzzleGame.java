import java.util.*;

class EightPuzzleGame{

	static class State {
    int[][] board;
    int cost;
    int heuristic; // Heuristic value
    int expansions; // Number of expansions
    State parent; // Parent state

    	State(int[][] board, int cost, int heuristic, int expansions, State parent) {
        	this.board = board;
        	this.cost = cost;
        	this.heuristic = heuristic;
        	this.expansions = expansions;
        	this.parent = parent;
    	}

	}
    static int[][] goal = {
        {6, 5, 4},
        {7, 0, 3},
        {8, 1, 2}
    };
    
    static int[][] directions = {
        {0, 1},   // Right
        {1, 0},   // Down
        {0, -1},  // Left
        {-1, 0},  // Up
        {-1, -1}, // Up-Left
        {-1, 1},  // Up-Right
        {1, -1},  // Down-Left
        {1, 1}    // Down-Right
    }; 

    static int calculateEuclideanDistance(int[][] state, int[][] goalState) {
        int distance = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int value = state[i][j];
                if (value != 0) {
                    for (int k = 0; k < 3; k++) {
                        for (int l = 0; l < 3; l++) {
                            if (goalState[k][l] == value) {
                                distance += Math.sqrt(Math.pow(k - i, 2) + Math.pow(l - j, 2));
                            }
                        }
                    }
                }
            }
        }
        return distance;
    }
    static int calculateChebyshevDistance(int[][] state, int[][] goalState) {
        int distance = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int value = state[i][j];
                if (value != 0) {
                    for (int k = 0; k < 3; k++) {
                        for (int l = 0; l < 3; l++) {
                            if (goalState[k][l] == value) {
                                distance += Math.max(Math.abs(k - i), Math.abs(l - j));
                                //distance = Math.max(distance, Math.max(Math.abs(k - i), Math.abs(l - j)));
                            }
                        }
                    }
                }
            }
        }
        return distance;
    }

    static boolean isValid(int x, int y) {
        return x >= 0 && x < 3 && y >= 0 && y < 3;
    }

    static void printBoard(int[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    static void printPath(State state) {
        if (state == null) {
            return;
        }
        printPath(state.parent);
        printBoard(state.board);
        System.out.println();
    }

    static boolean isGoal(int[][] board) {
        return Arrays.deepEquals(board, goal);
    }

    static List<int[][]> generateSuccessorStates(int[][] state) {
        List<int[][]> successors = new ArrayList<>();
        // locate the empty tail
        int emptyX = -1, emptyY = -1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state[i][j] == 0) {
                    emptyX = i;
                    emptyY = j;
                    break;
                }
            }
            if (emptyX != -1) {
                break;
            }
        }
        // iterate through all possible movements
        for (int[] dir : directions) {
            int newX = emptyX + dir[0];
            int newY = emptyY + dir[1];

            if (isValid(newX, newY)) {
                int[][] newState = new int[3][3];
                for (int i = 0; i < 3; i++) {
                    // copy the specific line to the new array
                    System.arraycopy(state[i], 0, newState[i], 0, 3);
                }
                newState[emptyX][emptyY] = newState[newX][newY];
                newState[newX][newY] = 0;
                successors.add(newState);
            }
        }
        return successors;
    }


    static void uniformCostSearch(int[][] board) {
        /*the line initializes a priority queue where elements (states) are ordered based on their cost. 
          This ensures that states with lower costs are explored first during the search process.*/
        PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.cost));
        pq.add(new State(board, 0, 0, 0, null));
        Set<String> visited = new HashSet<>();
        
        while (!pq.isEmpty()) {
            State current = pq.poll();
            if (isGoal(current.board)) {
                System.out.println("Path from Initial State to Goal State:");
                printPath(current);
                System.out.println("Goal Found!");
                System.out.println("Cost of the Path: " + current.cost);
                System.out.println("Number of Expansions: " + current.expansions);
                return;
            }
            //System.out.println("WHILE Array!");
            //printBoard(current.board);
            
            visited.add(Arrays.deepToString(current.board));

            List<int[][]> successors = generateSuccessorStates(current.board);
            for (int[][] successor : successors) {
                String key = Arrays.deepToString(successor);
                if (!visited.contains(key)) {
                    pq.add(new State(successor, current.cost + 1, 0 , current.expansions + 1, current));
                    visited.add(key);
                }
            }
        }
        System.out.println("Goal not reachable!");
    }

    static void AStar(int[][] initialState) {
        /*the line initializes a priority queue where elements (states) are ordered based on their cost and their heuristic.*/
        PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.cost + a.heuristic));
        int heuristic = calculateChebyshevDistance(initialState, goal);
        pq.add(new State(initialState, 0, heuristic, 0, null));

        Set<String> visited = new HashSet<>();
        visited.add(Arrays.deepToString(initialState));

        while (!pq.isEmpty()) {
            State current = pq.poll();

            if (isGoal(current.board)) {
                System.out.println("Path from Initial State to Goal State:");
                printPath(current);
                System.out.println("Goal Found!");
                System.out.println("Cost of the Path: " + current.cost);
                System.out.println("Number of Expansions: " + current.expansions);
                return;
            }

            List<int[][]> successors = generateSuccessorStates(current.board);
            for (int[][] successor : successors) {
                String key = Arrays.deepToString(successor);
                if (!visited.contains(key)) {
                    int newHeuristic = calculateChebyshevDistance(successor, goal);
                    pq.add(new State(successor, current.cost + 1, newHeuristic, current.expansions + 1, current));
                    visited.add(key);
                }
            }
        }
        System.out.println("Goal not reachable!");
    }


    public static void main(String[] args) {
    	long start,elapsed,end;
        int[][] initial = {
            {8, 7, 6},
            {5, 4, 3},
            {2, 1, 0}
        };
        System.out.println("Initial Board:");
        printBoard(initial);
        System.out.println("\nExecuting uniformCostSearch algorithm\n");
        //   uniformCostSearch
        start = System.currentTimeMillis();
        uniformCostSearch(initial);
		end = System.currentTimeMillis();

		elapsed = end - start;
		System.out.println("Time taken to solve: " + elapsed + " milliseconds");

        System.out.println("\n!-------------------------------------!\n");

        System.out.println("Executing A* algorithm\n");
        //  AStar
        start = System.currentTimeMillis();
        AStar(initial);
        end = System.currentTimeMillis();

		elapsed = end - start;
		System.out.println("Time taken to solve: " + elapsed + " milliseconds");

    }
}