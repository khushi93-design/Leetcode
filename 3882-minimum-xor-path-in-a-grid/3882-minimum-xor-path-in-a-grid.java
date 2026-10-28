
class Solution {
    boolean[][][] visited;
    int ans;

    public int minCost(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        visited = new boolean[m][n][1024];
        ans = Integer.MAX_VALUE;

        dfs(grid, 0, 0, 0, m, n);

        return ans;
    }

    public void dfs(int[][] grid, int i, int j, int xor, int m, int n) {
        xor = xor ^ grid[i][j];

        if (visited[i][j][xor]) {
            return;
        }

        visited[i][j][xor] = true;

        if (i == m - 1 && j == n - 1) {
            ans = Math.min(ans, xor);
            return;
        }

        if (i + 1 < m) {
            dfs(grid, i + 1, j, xor, m, n);
        }

        if (j + 1 < n) {
            dfs(grid, i, j + 1, xor, m, n);
        }
    }
}
