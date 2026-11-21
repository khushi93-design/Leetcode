
class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;

        List<List<Integer>> tree = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            tree.add(new ArrayList<>());
        }

        for (int i = 1; i < n; i++) {
            tree.get(parent[i]).add(i);
        }

        int[] newParent = parent.clone();
        int[] last = new int[26];

        Arrays.fill(last, -1);

        dfs(0, tree, s, last, newParent);

        List<List<Integer>> newTree = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            newTree.add(new ArrayList<>());
        }

        for (int i = 1; i < n; i++) {
            newTree.get(newParent[i]).add(i);
        }

        int[] ans = new int[n];

        calculateSize(0, newTree, ans);

        return ans;
    }

    void dfs(int node, List<List<Integer>> tree,
             String s, int[] last, int[] newParent) {

        int ch = s.charAt(node) - 'a';

        int old = last[ch];

        if (node != 0 && old != -1) {
            newParent[node] = old;
        }

        last[ch] = node;

        for (int child : tree.get(node)) {
            dfs(child, tree, s, last, newParent);
        }

        last[ch] = old;
    }

    int calculateSize(int node, List<List<Integer>> tree,
                      int[] ans) {

        int size = 1;

        for (int child : tree.get(node)) {
            size += calculateSize(child, tree, ans);
        }

        ans[node] = size;

        return size;
    }
}
