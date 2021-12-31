import java.lang.Math;
import java.io.File;
import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.HashMap;

class State implements Comparable<State>{
  boolean goalState = false;
  HashMap<String, State> neighbours = new HashMap<String, State>();
  int size;
  int y;
  int x;
  int[][] table;
  int h;
  int g;
  int f;
  int dup = 0;
  State parent;//the previous node
  //use the initialization of parent to check of node has been in queue

//It should have g, which is the lenght of the startnode to this node
// it should also gave h, which is the leangth of this node, to the goal node.
//bruk manhattan hueristic for å finne h
//den finner antal steg for å komme til riktig steg.
// it should also have f, which is simply g + h

//implement compareTo which compares f between nodes
  public State(HashMap<Integer, int[]> go, int posy, int posx, int[][] t, int s, State p){
    table = t;
    size = s;
    y = posy;
    x = posx;
    h = manhattan(go);
    parent = p;
    if(parent == null){g = 0;}
    else{g = parent.g + 1;}
    f = h+g;
    
  
  }

//manhattan hueristic
  int manhattan(HashMap<Integer, int[]> goal){
    //fin h ved bruk av en implementasjon av manhattan hueristic

    //Bruk index av final state for å finne antall steg til mål

    int sum = 0;

    for(int i =0; i<size; i++){

      for(int j=0; j<size; j++){
        if(table[i][j] == 0){continue;}

        int[] current = goal.get(table[i][j]);
        int valY = Math.abs(current[0] - i);
        int valX = Math.abs(current[1] - j);
        int total = valY + valX;

        sum += total;
      }

    }
    if(sum == 0){
      //System.out.println("We created a goalstate");
      goalState = true;
      }
    return sum;

  }
  @Override
  public int compareTo(State s){
    if(f == s.f) return 0;
    else if(f > s.f) return 1;
    else {return -1;}
  }


  public boolean equals(State o){
    return java.util.Arrays.deepEquals(this.table, o.table);
  }



}
