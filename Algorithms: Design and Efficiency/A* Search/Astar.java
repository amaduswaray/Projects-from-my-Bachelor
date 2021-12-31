import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.lang.StringBuilder;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

//astar class
class Astar{
  PriorityQueue<State> states = new PriorityQueue<State>(); // create a priority queue
  ArrayList<State> seenStates = new ArrayList<State>();
  //create a list of visited states 
  //variables
  File file;
  int str;
  int seen = 0; //states seen
  int match = 0;//duplicates will have a lower priority regardless
  State currentState;
  String finalPath;
  String output = "";
  HashMap<Integer, int[]> goalState = new HashMap<Integer, int[]>();
  String outname;

//
  public Astar(String filename, String out) throws FileNotFoundException {
    outname = out;
    file = new File(filename);

    Scanner sc = new Scanner(file);

    //str = sc.nextInt();

    str = Integer.parseInt(sc.nextLine());

    //creating goal state
    int insertNumber = 1;
    for(int i=0; i<str; i++){

      for(int j=0; j<str; j++){

        int[] indexes = {i,j};

        if(i == str-1 && j==str-1){
          goalState.put(0, indexes);
        }

        else{
          goalState.put(insertNumber, indexes);
          insertNumber++;
        }


      }

    }

    //creating matrix
    int[][] tmp = new int[str][str];
    int y = 0;
    int x = 0;

    int teller = 0;
    while(sc.hasNextLine()) {

      //reading file and putting values to matrix
      String data = sc.nextLine();
      String[] element = data.split(" ");
      //System.out.println(element[0] + element[1] + element[2]);
      for(int i=0; i<element.length; i++){
        output += element[i] + " ";
        tmp[teller][i] = Integer.parseInt(element[i]);

        if(element[i].equals("0")){
          x = i;
          y = teller;
        }
      }
      output += "\n";
      teller++;
    }

    currentState = new State(goalState, y, x, tmp, str, null);//start node
    states.add(currentState);


  }

  //creates a new state that moves the 0
  State newState(State node, int y, int x){//y and x are the new positions of 0
    int[][] tmp = new int[str][];
    for(int i =0; i<str; i++){
      tmp[i] = node.table[i].clone();
    }

    int zeroy = node.y;
    int zerox = node.x;

    int atPosition = tmp[y][x];
    tmp[zeroy][zerox] = atPosition;
    tmp[y][x] = 0;


    /*if(seenTables.get(tmp) != null){
      System.out.println("Using same state");
      return seenTables.get(tmp);
    }*/

    State newState = new State(goalState,y,x,tmp, str, node);
  //if a state is already created, a return the same object
    for(int i=0; i<seenStates.size(); i++){
      if (seenStates.get(i).equals(newState)){
        //System.out.println("We got a match");
        seenStates.get(i).dup++;
        newState = seenStates.get(i);
        match++;
        break;
      } 
    }



    //if state exist, return that state

    return newState;
  }

//checks which directions a state can go, and creates the new states
//but only if they havent been created before
  void nextStates(State node){//create all next states, and adds them to the heap.
    //System.out.println("Time to create new state");
    if(node.y == 0 && node.x == 0){//can only go right and down
      State right = newState(node, node.y, node.x+1);
      State down = newState(node, node.y+1, node.x);
      
      if(right.dup == 0){
        states.add(right);
        seen++;
      } 

      if(down.dup == 0){
        states.add(down);
        seen++;
      }
    }

    else if(node.y == 0 && node.x == str-1){//can only go left and down
      State left = newState(node, node.y, node.x-1);
      State down = newState(node, node.y+1, node.x);

      if(left.dup == 0){
        states.add(left);
        seen++;
      } 

      if(down.dup == 0){
        states.add(down);
        seen++;
      }
    }

    else if(node.y == 0 && node.x < str-1){//can go left, down and right
      State right = newState(node, node.y, node.x+1);
      State left = newState(node, node.y, node.x-1);
      State down = newState(node, node.y+1, node.x);
      if(right.dup == 0){
        states.add(right);
        seen++;
      }

      if(left.dup == 0){
        states.add(left);
        seen++;
      } 

      if(down.dup == 0){
        states.add(down);
        seen++;
      }
    }

    else if(node.y == str-1 && node.x == 0){//can only go up and right
      State right = newState(node, node.y, node.x+1);
      State up = newState(node, node.y-1, node.x);
      
      if(right.dup == 0){
        states.add(right);
        seen++;
      }

      if(up.dup == 0){
        states.add(up);
        seen++;
      } 

    }

    else if(node.y == str-1 && node.x == str-1){//can only go left and up
      State left = newState(node, node.y, node.x-1);
      State up = newState(node, node.y-1, node.x);
      
      if(left.dup == 0){
        states.add(left);
        seen++;
      }

      if(up.dup == 0){
        states.add(up);
        seen++;
      } 

    }
    else if(node.y == str-1 && node.x < str-1){//can go left, right and up
      State right = newState(node, node.y, node.x+1);
      State left = newState(node, node.y, node.x-1);
      State up = newState(node, node.y-1, node.x);
      
      if(right.dup == 0){
        states.add(right);
        seen++;
      }

      if(left.dup == 0){
        states.add(left);
        seen++;
      }

      if(up.dup == 0){
        states.add(up);
        seen++;
      } 

    }

    else if(node.y < str-1 && node.x == 0){//can go up, down and right
      State right = newState(node, node.y, node.x+1);
      State up = newState(node, node.y-1, node.x);
      State down = newState(node, node.y+1, node.x);
     
     if(right.dup == 0){
        states.add(right);
        seen++;
      }

      if(up.dup == 0){
        states.add(up);
        seen++;
      } 

      if(down.dup == 0){
        states.add(down);
        seen++;
      } 
    }

    else if(node.y < str-1 && node.x == str-1){//can go left, up, down
      State left = newState(node, node.y, node.x-1);
      State up = newState(node, node.y-1, node.x);
      State down = newState(node, node.y+1, node.x);
      
      if(left.dup == 0){
        states.add(left);
        seen++;
      }

      if(up.dup == 0){
        states.add(up);
        seen++;
      } 

      if(down.dup == 0){
        states.add(down);
        seen++;
      } 

    }

    else{//can go every direction
      State right = newState(node, node.y, node.x+1);
      State left = newState(node, node.y, node.x-1);
      State up = newState(node, node.y-1, node.x);
      State down = newState(node, node.y+1, node.x);
  
      if(right.dup == 0){
        states.add(right);
        seen++;
      }

      if(left.dup == 0){
        states.add(left);
        seen++;
      }

      if(up.dup == 0){
        states.add(up);
        seen++;
      } 

      if(down.dup == 0){
        states.add(down);
        seen++;
      } 

    }

    
  }

//the path is found backwards
//i reverse the string to have the correct order
  String reverse(String s){
    StringBuilder res = new StringBuilder();

    res.append(s);
    res.reverse();

    return res.toString();
  }

  void findPath(State node){//method that finds path backwards.
    //while parent node != null, go backwards
    //find way to implement LRUD

    State tmp = node;
    String path = "";
    while(tmp.parent != null){
      State back = tmp.parent;
      
      if(back.y < tmp.y){path += "D";}//0 went down
      else if(back.y > tmp.y){path += "U";}//0 went up
      else if(back.x < tmp.x){path += "R";}//0 went right
      else if(back.x > tmp.x){path += "L";}//0 went left

      tmp = back;
    }



    finalPath = reverse(path);
  }

//aStar algorithm
  public int a_star(){
    int steps = 0;
    while(states.size() > 0){//needs final state if sentence
      //System.out.println("We in the while");
      State node = states.poll();
      //System.out.println("New iteration");
      //System.out.println("X position: "+ node.x + ", Y positon: " + node.y);
      //for(int i =0; i<str; i++){
          //System.out.println(node.table[i][0] + " " + node.table[i][1] + " " + node.table[i][2] /*+ " " + node.table[i][3]*/ );
        //}
      //seen++;

      //function ends if a node is a goal state
      if(node.goalState){
        findPath(node);
        
        steps = node.g;
        //seen -= match;
        //System.out.println("States seen: " + seen);
        output += "States seen: " + seen + "\n" + "Solution: " + node.g + ", " + finalPath;
        //System.out.println("Solution: " + node.g + ", " + finalPath);
        System.out.println(output);
        return steps;//perhaps an int
      }

      seenStates.add(node);
      nextStates(node);//creates the next states for current node

    }
    return steps;

  }

  public void writeFile(){

    try{
      FileWriter out = new FileWriter(outname);
      out.write(output);
      out.close();
    } catch(IOException e){
      System.out.println("error");
    }
    
    
    }

  

}
