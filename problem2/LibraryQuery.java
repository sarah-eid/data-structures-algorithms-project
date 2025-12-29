import java.util.*;

public class LibraryQuery {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int testCases = sc.nextInt();
        StringBuilder output = new StringBuilder();
        
        for (int t = 0; t < testCases; t++) {
            int N = sc.nextInt();
            int[] shelves = new int[N];
            
            // Read initial shelf contents
            for (int i = 0; i < N; i++) {
                shelves[i] = sc.nextInt();
            }
            
            int Q = sc.nextInt();
            
            // Process queries
            for (int q = 0; q < Q; q++) {
                int queryType = sc.nextInt();
                
                if (queryType == 1) {
                    // Update query: Change books on a shelf
                    int x = sc.nextInt();
                    int k = sc.nextInt();
                    shelves[x - 1] = k;  // 1-based to 0-based indexing
                    
                } else {
                    // Query type 0: Get k-th rank in range
                    int x = sc.nextInt();
                    int y = sc.nextInt();
                    int k = sc.nextInt();
                    
                    // Frequency array for books (1-1000)
                    int[] freq = new int[1000];
                    
                    // Count frequencies in range [x, y]
                    for (int i = x - 1; i < y; i++) {
                        freq[shelves[i] - 1]++;  // books are 1-1000, array is 0-999
                    }
                    
                    // Find k-th smallest
                    int result = 0;
                    for (int bookType = 0; bookType < 1000; bookType++) {
                        k -= freq[bookType];
                        if (k <= 0) {
                            result = bookType + 1;  // Convert back to 1-1000
                            break;
                        }
                    }
                    output.append(result).append("\n");
                }
            }
        }
        
        System.out.print(output);
        sc.close();
    }
}
