import java.util.ArrayList;


class Task implements Comparable<Task> {

  int id;
  int time;
  int staff;

  String name;

  int earliestStart;
  int latestStart;
  int slack;

  int finish;

  ArrayList<Task> outEdges;
  int cntPredecessors;

  Boolean visited = false;


  public Task(int i){
    id = i;
    outEdges = new ArrayList<Task>();
  }

  public void visit(){
    visited = true;
  }

  public void setLatest(){
    int late = 0;

    for(Task task: outEdges){

      if(task.earliestStart > late){
        late = task.earliestStart;
      }
    }

    if(late == 0){
      latestStart = earliestStart;
    }
    else{
    latestStart = late - time;
    }
  }

  public void setSlack(){
    slack = latestStart - earliestStart;
  }


  public void addEdge(Task e){
    outEdges.add(e);
  }


  public int compareTo(Task t){
    if(time == t.time){
      return 0;
    }

    else if(time > t.time){
      return 1;
    }

    else{
      return -1;
    }

  }

  public String toString(){
    return "Task " + Integer.toString(id);
  }









}
