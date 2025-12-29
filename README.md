# Advanced Algorithmic Solutions for Data Structures & Problems

A comprehensive collection of algorithmic solutions to three complex data structure problems involving trees, graphs, and range queries. This project demonstrates advanced problem-solving skills and efficient implementation techniques for handling large-scale constraints.

## Project Overview

This project provides optimized solutions to three challenging algorithmic problems:

1. **Super Maximum Cost Queries** - Counting tree paths by edge weight constraints
2. **Library Query** - Dynamic rank queries with updates on array ranges  
3. **Rooted Tree** - Subtree updates and path sum queries on weighted trees

Each solution handles constraints up to 10⁵ nodes/queries with optimal time complexity.

## Key Features

- **Union-Find with Edge Sorting**: Efficient path counting in weighted trees
- **Frequency-Based Rank Queries**: Optimized for constrained value ranges (1-1000)
- **Euler Tour + Fenwick Trees**: Fast subtree updates and path queries  
- **Binary Lifting for LCA**: `O(log N)` lowest common ancestor queries
- **Mathematical Decomposition**: Efficient handling of `V + d×K` update formulas

## Algorithm Performance

### Super Maximum Cost Queries
- **Approach**: DSU with edge sorting + binary search
- **Time Complexity**: `O((N+Q) log N)`
- **Space Complexity**: `O(N)`

### Library Query
- **Approach**: Frequency counting with constrained values
- **Time Complexity**: `O(Q × 1000) for rank queries`
- **Space Complexity**: `O(N + 1000)`

### Rooted Tree  
- **Approach**: Euler Tour + 3 Fenwick Trees + LCA
- **Time Complexity**: `O((N+Q) log N)`
- **Space Complexity**: `O(N log N)`

## Repository Structure
```
├── problem1/ # Super Maximum Cost Queries
│  └── SuperMaximumCostQueries.java
├── problem2/ # Library Query
│  └── LibraryQuery.java
└── problem3/ # Rooted Tree
   └── RootedTree.java
├── docs/
│ ├── Final_Project_CS2011.pdf # Code submission document
│ └── Data structures Final Project.pdf # Presentation slides
├── README.md 
└── .gitignore 
```

## Technologies Used

- **Language**: Java
- **Data Structures**: Union-Find, Fenwick Trees, Arrays, Adjacency Lists
- **Algorithms**: Binary Search, Euler Tour, LCA with Binary Lifting
- **Optimizations**: Path Compression, Range Query Techniques

## Solution Approaches

### 1. Super Maximum Cost Queries
Process edges in increasing weight order, using DSU to count newly connected paths at each weight. Precompute cumulative counts for O(log N) query response.

### 2. Library Query  
Leverage constrained book counts (1-1000) to maintain frequency arrays. Rank queries scan frequencies for O(1000) complexity regardless of range size.

### 3. Rooted Tree
Transform subtree updates to range updates via Euler Tour. Use three Fenwick Trees to handle V, d, and d² terms from the V + d×K update formula.

## Key Insights

- **DSU + Sorting**: Powerful combination for problems involving "maximum edge in path"
- **Value Constraints**: Can dramatically simplify solutions (Library Query's 1-1000 range)
- **Mathematical Reformulation**: Complex update formulas can be decomposed into simpler terms
- **Euler Tour**: Transforms tree problems into array problems for easier manipulation


## Contributors

- Sarah Eid | [Judy Abuquta](https://github.com/JudyAbuquta) | [Abeer Hussain](https://github.com/abeerahrar) 
- Effat University, College of Engineering, Computer Science Department

## Documentation
Code Submission: Complete solution code in docs `Final_Project_CS2011.pdf`

Presentation: Problem explanations and solution walkthrough in docs `Data structures Final Project.pdf`

## License
Academic Use - This project was developed for CS2011 coursework at Effat University.

**Tags** : `data-structures` `algorithms` `java` `tree-algorithms` `union-find` `fenwick-tree` `competitive-programming` `euler-tour` `lowest-common-ancestor` `range-queries`

