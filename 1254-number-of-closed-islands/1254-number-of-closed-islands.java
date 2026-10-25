class Solution {

    public int closedIsland(int[][] grid) {
        int count = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {

                if (grid[i][j] == 0) {
                    if (dfs(grid, i, j)) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    public boolean dfs(int[][] grid, int row, int col) {

        // Outside the grid means the island is open
        if (row < 0 || col < 0 ||
            row >= grid.length || col >= grid[0].length) {
            return false;
        }

        // Water or an already visited cell
        if (grid[row][col] == 1) {
            return true;
        }

        // Mark land as visited
        grid[row][col] = 1;

        // Explore all four directions
        boolean up = dfs(grid, row - 1, col);
        boolean down = dfs(grid, row + 1, col);
        boolean left = dfs(grid, row, col - 1);
        boolean right = dfs(grid, row, col + 1);

        return up && down && left && right;
    }
}