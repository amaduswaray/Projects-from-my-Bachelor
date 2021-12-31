# A* Search
The exercise is to write a program for solving the 8-puzzle and 15-puzzle; in general the (N*N−1)-puzzle (on an N×N-board), but the required computing power increases rapidly Your program should be made for an N×N-board, but you can assume that N £ 10. A state for the game with N = 3 can be shown like this:

<img width="93" alt="image" src="https://user-images.githubusercontent.com/86655546/147826179-d629a099-d155-4536-a872-d707b51b308b.png">

### Example:

<img width="796" alt="image" src="https://user-images.githubusercontent.com/86655546/147826197-b68e32e3-d671-4fd3-8d54-97fb3c222dbc.png">

### The change of states can be represented as a graph:

<img width="439" alt="image" src="https://user-images.githubusercontent.com/86655546/147826382-b0bd7085-f303-4dd6-ba42-1f072704e390.png">

#### The A* algoruthm is used to find the shortest path to our final state
With A* we had to implement a heuristic function. One could simply be a function that counts the misplaced tiles. However, I chose to use the manhattan heuristic, which checks each tile and calculates how many steps it has to go to reach the final step. Then it takes the sum of the steps needed from each tile.
