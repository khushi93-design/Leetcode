
import java.util.*;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {

        int n = items.length;

        // Step 1: Find the number of free items for each type
        int[] free = new int[n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                if (i != j && items[j][0] % items[i][0] == 0) {
                    free[i]++;
                }
            }
        }

        // Total value gained by buying one copy of an item
        int[] value = new int[n];

        for (int i = 0; i < n; i++) {
            value[i] = 1 + free[i];
        }

        // Step 2: Sort item indices by price (descending)
        Integer[] order = new Integer[n];

        for (int i = 0; i < n; i++) {
            order[i] = i;
        }

        Arrays.sort(order, (a, b) ->
            Integer.compare(items[b][1], items[a][1])
        );

        // dp[c] = maximum value using items with higher prices
        int NEG = -1000000000;

        int[] dp = new int[budget + 1];
        Arrays.fill(dp, NEG);

        dp[0] = 0;

        int ans = 0;

        // Step 3: Process items grouped by price
        int start = 0;

        while (start < n) {

            int price = items[order[start]][1];

            int end = start;

            while (end < n &&
                   items[order[end]][1] == price) {
                end++;
            }

            // At least one item of current price must be selected
            int[] curr = new int[budget + 1];
            Arrays.fill(curr, NEG);

            for (int k = start; k < end; k++) {

                int idx = order[k];

                for (int c = budget; c >= price; c--) {

                    if (dp[c - price] != NEG) {
                        curr[c] = Math.max(
                            curr[c],
                            dp[c - price] + value[idx]
                        );
                    }

                    if (curr[c - price] != NEG) {
                        curr[c] = Math.max(
                            curr[c],
                            curr[c - price] + value[idx]
                        );
                    }
                }
            }

            // Step 4: Buy additional copies at current price
            for (int c = 0; c <= budget; c++) {

                if (curr[c] != NEG) {

                    int extra = (budget - c) / price;

                    ans = Math.max(
                        ans,
                        curr[c] + extra
                    );
                }
            }

            // Step 5: Update DP for the next price group
            for (int k = start; k < end; k++) {

                int idx = order[k];

                for (int c = budget; c >= price; c--) {

                    if (dp[c - price] != NEG) {
                        dp[c] = Math.max(
                            dp[c],
                            dp[c - price] + value[idx]
                        );
                    }
                }
            }

            start = end;
        }

        return ans;
    }
}
