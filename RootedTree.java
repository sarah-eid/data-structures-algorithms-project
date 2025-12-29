import java.util.*;

public class RootedTree {
    static final int MOD = 1_000_000_007;
    static final int LOG = 17;  // For 2^17 > 100000
    
    static List<Integer>[] tree;
    static int[][] parent;  // Binary lifting table
    static int[] depth;
    static int[] eulerIn, eulerOut;
    static int timer = 0;
    
    // Three Fenwick trees for handling V + d*K formula
    static long[] fenwick1, fenwick2, fenwick3;
    
    // Euler Tour DFS
    static void dfs(int node, int par) {
        eulerIn[node] = timer++;
        
        parent[0][node] = par;
        for (int i = 1; i < LOG; i++) {
            if (parent[i-1][node] != -1) {
                parent[i][node] = parent[i-1][parent[i-1][node]];
            }
        }
        
        for (int child : tree[node]) {
            if (child != par) {
                depth[child] = depth[node] + 1;
                dfs(child, node);
            }
        }
        
        eulerOut[node] = timer - 1;
    }
    
    // Lowest Common Ancestor using binary lifting
    static int lca(int u, int v) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }
        
        // Lift u to same depth as v
        int diff = depth[u] - depth[v];
        for (int i = 0; i < LOG; i++) {
            if (((diff >> i) & 1) == 1) {
                u = parent[i][u];
            }
        }
        
        if (u == v) return u;
        
        // Lift both until parents are different
        for (int i = LOG - 1; i >= 0; i--) {
            if (parent[i][u] != -1 && parent[i][u] != parent[i][v]) {
                u = parent[i][u];
                v = parent[i][v];
            }
        }
        
        return parent[0][u];
    }
    
    // Fenwick tree operations
    static void fenwickUpdate(long[] fenwick, int idx, long value) {
        value %= MOD;
        if (value < 0) value += MOD;
        
        for (; idx < fenwick.length; idx += idx & -idx) {
            fenwick[idx] = (fenwick[idx] + value) % MOD;
        }
    }
    
    static long fenwickQuery(long[] fenwick, int idx) {
        long sum = 0;
        for (; idx > 0; idx -= idx & -idx) {
            sum = (sum + fenwick[idx]) % MOD;
        }
        return sum;
    }
    
    // Range update in Euler Tour range
    static void rangeUpdate(int node, long V, long K) {
        int l = eulerIn[node] + 1;  // Fenwick is 1-based
        int r = eulerOut[node] + 1;
        
        // Update based on formula: value += V + d*K
        // Decomposed into three Fenwick trees for efficiency
        fenwickUpdate(fenwick1, l, V);
        fenwickUpdate(fenwick1, r + 1, -V);
        
        // For K*d term: we need to handle depth
        long term2 = K;
        fenwickUpdate(fenwick2, l, term2);
        fenwickUpdate(fenwick2, r + 1, -term2);
        
        long term3 = (K * depth[node]) % MOD;
        fenwickUpdate(fenwick3, l, term3);
        fenwickUpdate(fenwick3, r + 1, -term3);
    }
    
    // Get value at a node
    static long getNodeValue(int node) {
        int idx = eulerIn[node] + 1;
        long val1 = fenwickQuery(fenwick1, idx);
        long val2 = fenwickQuery(fenwick2, idx);
        long val3 = fenwickQuery(fenwick3, idx);
        
        // Reconstruct: V + K*(depth[node] - depth[T])
        // Actually returns: val1 + depth[node]*val2 - val3
        long result = (val1 + (depth[node] * val2) % MOD - val3) % MOD;
        if (result < 0) result += MOD;
        
        return result;
    }
    
    // Query path sum from u to v
    static long queryPath(int u, int v) {
        int ancestor = lca(u, v);
        long sumU = getNodeValue(u);
        long sumV = getNodeValue(v);
        long sumAncestor = getNodeValue(ancestor);
        
        long result = (sumU + sumV - sumAncestor) % MOD;
        if (result < 0) result += MOD;
        
        if (parent[0][ancestor] != -1) {
            long sumParent = getNodeValue(parent[0][ancestor]);
            result = (result - sumParent) % MOD;
            if (result < 0) result += MOD;
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int N = sc.nextInt();  // Number of nodes
        int M = sc.nextInt();  // Number of operations
        int R = sc.nextInt() - 1;  // Root (0-based)
        
        // Initialize data structures
        tree = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            tree[i] = new ArrayList<>();
        }
        
        // Read tree edges
        for (int i = 0; i < N - 1; i++) {
            int u = sc.nextInt() - 1;
            int v = sc.nextInt() - 1;
            tree[u].add(v);
            tree[v].add(u);
        }
        
        // Initialize arrays
        depth = new int[N];
        eulerIn = new int[N];
        eulerOut = new int[N];
        parent = new int[LOG][N];
        
        for (int i = 0; i < LOG; i++) {
            Arrays.fill(parent[i], -1);
        }
        
        // Perform Euler Tour
        dfs(R, -1);
        
        // Initialize Fenwick trees (1-indexed)
        fenwick1 = new long[N + 2];
        fenwick2 = new long[N + 2];
        fenwick3 = new long[N + 2];
        
        StringBuilder output = new StringBuilder();
        
        // Process operations
        for (int i = 0; i < M; i++) {
            char op = sc.next().charAt(0);
            
            if (op == 'U') {
                // Update operation
                int T = sc.nextInt() - 1;
                long V = sc.nextLong();
                long K = sc.nextLong();
                rangeUpdate(T, V, K);
                
            } else if (op == 'Q') {
                // Query operation
                int A = sc.nextInt() - 1;
                int B = sc.nextInt() - 1;
                long result = queryPath(A, B);
                output.append(result).append("\n");
            }
        }
        
        System.out.print(output);
        sc.close();
    }
}