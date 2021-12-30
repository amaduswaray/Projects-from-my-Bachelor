import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.ArrayList;


class Oblig2 {



    public static void main(String[] args) throws IOException {
        String filename = args[0];
        Scanner in = new Scanner(new File(filename));

        int n = in.nextInt();
        Task[] tasks = new Task[n];

        for (int i = 0; i < n; i++) {
            tasks[i] = new Task(i + 1);
        }

        for (int i = 0; i < n; i++) {
            int id = in.nextInt();
            Task task = tasks[id - 1];
            task.name = in.next();
            task.time = in.nextInt();
            task.staff = in.nextInt();

            while (true) {
                int dep = in.nextInt();
                if (dep == 0) {
                    break;
                }
                tasks[dep - 1].addEdge(task);
                tasks[id - 1].cntPredecessors++;
            }
        }

        sorter(tasks);
    }

    public static void DFS(Task t) {
      t.visit();
      for (Task t3: t.outEdges){
        System.out.print(t3 + ", ");
      }
      if(t.outEdges.size() > 0){
        System.out.println(" depends on " + t);
      }

      System.out.println("");
      System.out.println("");

      for (Task tt: t.outEdges) {
        if(!tt.visited) {
          DFS(tt);
        }
      }
    }

    public static void sorter(Task[] graf){
      PriorityQueue<Task> stack = new PriorityQueue<Task>();
      ArrayList<Task> liste = new ArrayList<Task>();



      for(int i = 0; i<graf.length; i++){

        if(graf[i].cntPredecessors ==0){
          stack.offer(graf[i]);
        }


      }


      int e = 1;
      int time = 0;

      while(!(stack.size() == 0)){




        int plussTid = 0;


        int indeks = stack.size();
        for(int i=0; i<indeks; i++){

          Task v = stack.poll();
          v.finish = v.earliestStart + v.time;
          liste.add(v);
          e++;


          if(v.time > plussTid){
            plussTid = v.time;
          }



          for(Task kant: v.outEdges){

            kant.earliestStart = v.time + v.earliestStart;

            kant.cntPredecessors--;

            if(kant.cntPredecessors == 0){

              stack.offer(kant);
            }
          }
        }


        time += plussTid;



      }

      int tid = 0;
      int manpower = 0;
      while(tid <= time){
        int action = 0;



        for(int i = 0; i<liste.size(); i++){

          liste.get(i).setLatest();


          if(liste.get(i).earliestStart == tid){
            manpower += liste.get(i).staff;
            action++;
          }

          if(liste.get(i).finish == tid){
            manpower -= liste.get(i).staff;
            action++;
          }

          liste.get(i).setSlack();

        }

        if(action > 0){

          System.out.println("Time: " + tid);



        for(int i = 0; i<liste.size(); i++){

          if(liste.get(i).earliestStart == tid){
            System.out.println("Starting: " + liste.get(i));
            System.out.println("");
          }

          if(liste.get(i).finish == tid){
            System.out.println("Finish: " + liste.get(i));
            System.out.println("");
          }

        }
        if(manpower > 0){

          System.out.println("Current Staff: " + manpower);
          System.out.println("");
          System.out.println("");
          System.out.println("");
      }

      }



        tid+=1;
      }


      for(int i = 0; i<liste.size(); i++){
        System.out.println("");
        System.out.println("Task ID: " + liste.get(i).id);
        System.out.println("Task name: " + liste.get(i).name);
        System.out.println("Time needed to finish: " + liste.get(i).time);
        System.out.println("Manpower needed to compolete the task: " + liste.get(i).staff);
        System.out.println("Earliest Starting time: " + liste.get(i).earliestStart);
        System.out.println("Latest start: " + liste.get(i).latestStart);
        System.out.println("Slack: " + liste.get(i).slack);

        System.out.println("List of the identities:");
        if(liste.get(i).outEdges.size() > 0){
          for(int j = 0; j<liste.get(i).outEdges.size(); j++){
            System.out.println(liste.get(i).outEdges.get(j));
          }
        }
        else{
          System.out.println("None");
        }
        System.out.println("");


      }

      if(e>graf.length){
        System.out.println("Vi har ikke en sykel");
      }
      else{
        System.out.println("Vi har en sykel");
        DFS(liste.get(0));
      }

    }

}
