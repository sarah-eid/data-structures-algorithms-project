import java.util.*;

public class SuperMaximumCostQueries {
    static class Edge {
        int u, v, w;
        Edge(int u, int v, int w) {
            this.u = u - 1;  // Convert to 0-based indexing
            this.v = v - 1;
            this.w = w;
        }
    }

    // Union-Find (Disjoint Set Union) implementation
    static class DSU {
        int[] parent;
        long[] size;
        
        DSU(int n) {
            parent = new int[n];
            size = new long[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }
        
        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);  // Path compression
            }
            return parent[x];
        }
        
        // Union two sets and return number of new paths created
        long union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX == rootY) return 0;
            
            long newPaths = size[rootX] * size[rootY];
            // Union by size
            if (size[rootX] < size[rootY]) {
                parent[rootX] = rootY;
                size[rootY] += size[rootX];
            } else {
                parent[rootY] = rootX;
                size[rootX] += size[rootY];
            }
            return newPaths;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // Read input: N nodes, Q queries
        int N = sc.nextInt();
        int Q = sc.nextInt();
        
        // Read edges
        Edge[] edges = new Edge[N - 1];
        for (int i = 0; i < N - 1; i++) {
            edges[i] = new Edge(sc.nextInt(), sc.nextInt(), sc.nextInt());
        }
        
        // Sort edges by weight in ascending order
        Arrays.sort(edges, (a, b) -> Integer.compare(a.w, b.w));
        
        // Preprocess: cumulative counts for each weight
        List<Integer> weights = new ArrayList<>();
        List<Long> cumulativePaths = new ArrayList<>();
        
        DSU dsu = new DSU(N);
        long totalPaths = 0;
        
        // Initialize with weight 0
        weights.add(0);
        cumulativePaths.add(0L);
        
        // Process edges in sorted order
        for (Edge edge : edges) {
            totalPaths += dsu.union(edge.u, edge.v);
            
            // If new weight, add to lists
            if (weights.get(weights.size() - 1) != edge.w) {
                weights.add(edge.w);
                cumulativePaths.add(totalPaths);
            } else {
                // Same weight, update last cumulative count
                cumulativePaths.set(cumulativePaths.size() - 1, totalPaths);
            }
        }
        
        // Process queries
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < Q; i++) {
            int L = sc.nextInt();
            int R = sc.nextInt();
            
            // Binary search for positions
            int posL = Collections.binarySearch(weights, L - 1);
            int posR = Collections.binarySearch(weights, R);
            
            // Adjust for not-found positions
            if (posL < 0) posL = -posL - 2;
            if (posR < 0) posR = -posR - 2;
            
            // Calculate paths in range [L, R]
            long pathsInRange = cumulativePaths.get(posR) - cumulativePaths.get(posL);
            result.append(pathsInRange).append("\n");
        }
        
        System.out.print(result);
        sc.close();
    }
}
