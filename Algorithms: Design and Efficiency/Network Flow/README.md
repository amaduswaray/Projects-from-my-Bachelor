# Network Flow
The exercise is to implement the FordFulkerson-algorithm, using the shortest aug- mentation path in each step (Edmonds and Karp’s version). Given a graph with capacities your program shall output the value of an optimal flow, the flow over each edge, and a cut (the one given by the algorithm) proving that the flow is optimal.

The graph is a directed graph, i.e. the capacity from vertex u to vertex v can be different from the capacity from v back to u. All capacities are integer and non- negative. (We use the term vertex in this exercise; it is sometimes also called a node.)


## Example
<img width="785" alt="image" src="https://user-images.githubusercontent.com/86655546/147826775-9ae27f97-9ee5-486d-b7d7-7ceee807049d.png">

The input is the size of the matrix, followed up the matrix which represents the flow between each node(index).
The output shows the optimal flow, cut, steps and and a new matrix with the remaining flow between each node(index).
