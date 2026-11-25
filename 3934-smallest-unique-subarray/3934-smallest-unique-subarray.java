
import java.util.*;

class Solution {
    public int smallestUniqueSubarray(int[] nums) {

        int n = nums.length;

        Integer[] sa = new Integer[n];

        for (int i = 0; i < n; i++) {
            sa[i] = i;
        }

        int[] rank = nums.clone();

        // Build suffix array
        for (int k = 1; k < n; k *= 2) {

            final int[] r = rank;
            final int step = k;

            Arrays.sort(sa, (a, b) -> {

                if (r[a] != r[b]) {
                    return Integer.compare(r[a], r[b]);
                }

                int ra = (a + step < n) ? r[a + step] : -1;
                int rb = (b + step < n) ? r[b + step] : -1;

                return Integer.compare(ra, rb);
            });

            int[] newRank = new int[n];

            newRank[sa[0]] = 0;

            for (int i = 1; i < n; i++) {

                int a = sa[i - 1];
                int b = sa[i];

                int a1 = rank[a];
                int b1 = rank[b];

                int a2 = (a + k < n) ? rank[a + k] : -1;
                int b2 = (b + k < n) ? rank[b + k] : -1;

                newRank[b] = newRank[a];

                if (a1 != b1 || a2 != b2) {
                    newRank[b]++;
                }
            }

            rank = newRank;

            if (rank[sa[n - 1]] == n - 1) {
                break;
            }
        }

        // Inverse suffix array
        int[] pos = new int[n];

        for (int i = 0; i < n; i++) {
            pos[sa[i]] = i;
        }

        // Build LCP array using Kasai's algorithm
        int[] lcp = new int[n];

        int h = 0;

        for (int i = 0; i < n; i++) {

            int r = pos[i];

            if (r == 0) {
                h = 0;
                continue;
            }

            int j = sa[r - 1];

            while (i + h < n && j + h < n &&
                   nums[i + h] == nums[j + h]) {
                h++;
            }

            lcp[r] = h;

            if (h > 0) {
                h--;
            }
        }

        // Find minimum unique subarray length
        int ans = n;

        for (int i = 0; i < n; i++) {

            int r = pos[i];

            int maxLcp = lcp[r];

            if (r + 1 < n) {
                maxLcp = Math.max(maxLcp, lcp[r + 1]);
            }

            int uniqueLength = maxLcp + 1;

            if (uniqueLength <= n - i) {
                ans = Math.min(ans, uniqueLength);
            }
        }

        return ans;
    }
}
