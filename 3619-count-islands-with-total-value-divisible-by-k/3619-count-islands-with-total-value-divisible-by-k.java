
class Solution {
    int m, n;

    public int countIslands(int[][] grid, int k) {
        m = grid.length;
        n = grid[0].length;

        int count = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (grid[i][j] > 0) {
                    long sum = dfs(grid, i, j);

                    if (sum % k == 0) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    public long dfs(int[][] grid, int i, int j) {

        long sum = grid[i][j];

        // Mark this cell as visited
        grid[i][j] = 0;

        // Up
        if (i > 0 && grid[i - 1][j] > 0) {
            sum += dfs(grid, i - 1, j);
        }

        // Down
        if (i < m - 1 && grid[i + 1][j] > 0) {
            sum += dfs(grid, i + 1, j);
        }

        // Left
        if (j > 0 && grid[i][j - 1] > 0) {
            sum += dfs(grid, i, j - 1);
        }

        // Right
        if (j < n - 1 && grid[i][j + 1] > 0) {
            sum += dfs(grid, i, j + 1);
        }

        return sum;
    }
}
