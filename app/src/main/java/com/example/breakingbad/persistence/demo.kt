        // A simple Java program to implement Game of Life
        object GameOfLife {
            @JvmStatic
            fun main(args: Array<String>) {
                val M = 10
                val N = 10
                // Intiliazing the grid.
                val grid = arrayOf(
                    intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                    intArrayOf(0, 0, 0, 1, 1, 0, 0, 0, 0, 0),
                    intArrayOf(0, 0, 0, 0, 1, 0, 0, 0, 0, 0),
                    intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                    intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
                    intArrayOf(0, 0, 0, 1, 1, 0, 0, 0, 0, 0),
                    intArrayOf(0, 0, 1, 1, 0, 0, 0, 0, 0, 0),
                    intArrayOf(0, 0, 0, 0, 0, 1, 0, 0, 0, 0),
                    intArrayOf(0, 0, 0, 0, 1, 0, 0, 0, 0, 0),
                    intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
                )
                // Displaying the grid
                println("Original Generation")
                for (i in 0 until M) {
                    for (j in 0 until N) {
                        if (grid[i][j] == 0) print(".") else print("*")
                    }
                    println()
                }
                println()
                nextGeneration(grid, M, N)
            }

            // Function to print next generation
            fun nextGeneration(grid: Array<IntArray>, M: Int, N: Int) {
                val future = Array(M) { IntArray(N) }
                // Loop through every cell
                for (l in 1 until M - 1) {
                    for (m in 1 until N - 1) { // finding no Of Neighbours that are alive
                        var aliveNeighbours = 0
                        for (i in -1..1) for (j in -1..1) aliveNeighbours += grid[l + i][m + j]
                        // The cell needs to be subtracted from
        // its neighbours as it was counted before
                        aliveNeighbours -= grid[l][m]
                        // Implementing the Rules of Life
        // Cell is lonely and dies
                        if (grid[l][m] == 1 && aliveNeighbours < 2) future[l][m] =
                            0 else if (grid[l][m] == 1 && aliveNeighbours > 3) future[l][m] =
                            0 else if (grid[l][m] == 0 && aliveNeighbours == 3) future[l][m] =
                            1 else future[l][m] =
                            grid[l][m]
                    }
                }
                println("Next Generation")
                for (i in 0 until M) {
                    for (j in 0 until N) {
                        if (future[i][j] == 0) print(".") else print("*")
                    }
                    println()
                }
            }
        }