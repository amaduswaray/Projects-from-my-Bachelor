import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Queue;
import java.util.ArrayDeque;
import java.lang.Object;
import java.lang.Math;
import java.util.Set;
import java.util.HashSet;
import java.io.FileWriter;
import java.io.IOException;



class Netflow{
    File file;
    Graph graphen;
    int size;
    int endFlow;
    int steps;
    String outname;

    public Netflow(String filename, String outname) throws FileNotFoundException {
        this.outname = outname;
        file = new File(filename);
        Scanner sc = new Scanner(file);
        size = Integer.parseInt(sc.nextLine());

        graphen = new Graph(size, 0, size-1);


        int currentNode = 0;
        while(sc.hasNextLine()){
            String data = sc.nextLine();
            String[] elements = data.split(" ");

            for(int i = 0; i<elements.length; i++){
                //if(Integer.parseInt(elements[i]) == 0) continue;
                graphen.addEdge(currentNode, i, Integer.parseInt(elements[i]));
            }

            currentNode++;
        }

    }

    public void runNetflow(){
        graphen.run();
        endFlow = graphen.maxFlow;
        steps = graphen.iterations;
    }

    public String getEndState(){
        //return the end matrix
        String retur = "";

        /*for(int i = 0; i<size; i++){
            for(int j=0; j<size; j++){
                /*if(graph.getEdgeFlow(i,j) == null){
                    retur += "0 ";
                    continue;
                }

                retur += String.valueOf(graph.getEdgeFlow(i,j)) + " ";
            }
            retur += "\n";
        }*/

        for(List<Edge> edges: graphen.graph){
            for(Edge e: edges){
                if(e == null) continue;
                //retur += String.valueOf(graph.getEdgeFlow(e.start, e.end)) + " ";
                retur += graphen.getEdgeFlow(e.start, e.end) + " ";
            }
            retur += "\n";
        }

        return retur;
    }


    public void printGraph(){

        for(int i=0; i < size; i++){
            System.out.println("Edges from Node nr: " + i);

            for(Edge e: graphen.graph[i]){
                System.out.println(e);
            }

            System.out.println("");
        }
    }

    public void finalPrint(){

        String retur = "";
        retur += "Max Flow: " + String.valueOf(endFlow) + "\n" + "Steps: " + String.valueOf(steps) + "\n";

        for(int i: graphen.cut){
            retur += String.valueOf(i) + " ";
        }

        for(int i=0; i < size; i++){

            for(Edge e: graphen.graph[i]){
                if(e.residual()) continue;

                retur += String.valueOf(e.flow) + " ";
            }

            retur += "\n";
        }

        System.out.println(retur);
    }

    public void printCut(){
        for(int i: graphen.cut){
            System.out.println("Node: " + i);
        }
    }

    
    //method that writes to file
    public void writeFile(){
        String retur = "";
        retur += "Max Flow: " + String.valueOf(endFlow) + "\n" + "Steps: " + String.valueOf(steps) + "\n";
        retur += "Cut: ";

        for(int i: graphen.cut){
            retur += String.valueOf(i) + " ";
        }
        retur += "\n";

        for(int i=0; i < size; i++){

            for(Edge e: graphen.graph[i]){
                if(e.residual()) continue;

                retur += String.valueOf(e.flow) + " ";
            }

            retur += "\n";
        }

        try{
            FileWriter out = new FileWriter(outname);
            out.write(retur);
            out.close();
        } catch(IOException e){
            System.out.println("error");
        }

       
    }








    private class Edge{
        int start;
        int end;
        final int maxCap;
        int remCap;
        int flow;
        Edge residual;
        boolean empty;


        public Edge(int s, int e, int m){
            start = s;
            end = e;

            maxCap = m;
            remCap = m;

            if(m == -1){
                empty = true;
            }
            //System.out.println("From: " + start + ", to: " + end + ", With capacity: " + maxCap);

        }

        public boolean residual(){
            if(maxCap == 0) return true;
            return false;
        }

        public int remainingCap(){
            return (maxCap - flow);
        }

        public boolean isEmpty(){
            return empty;
        }


        public void addPath(int f){
            flow += f;
            residual.flow -= f;
            remCap = maxCap - flow;
        }

        public String toString(){
            String ret = "Edge from: " + start + ", to: " + end + ", With flow = " + flow + ", and capacity: " + maxCap;
            if(residual()){
                ret = "Residual edge";
            }
            return ret;
        }

    }

    private class Graph{
        int nodes;//number of nodes
        int source;
        int sink;
        int iterations = 0;
        boolean[] visited; //list of nodes where the "nodes" are the index, and the valuse visited[i] is a boolean saying if it has been visited
        //create an unvisit all, which loops through and puts everything as false
        boolean solved;
        int maxFlow;
        List<Edge>[] graph;
        String output = "";
        Set<Integer> cut = new HashSet<Integer>();//cut being the final augmented path

        public Graph(int n, int s, int t){
            nodes = n;
            source = s;
            sink = t;

            this.graph = new List[n];
            for(int i=0; i<n; i++){
                graph[i] = new ArrayList<Edge>();
            }

            visited = new boolean[n];
            unvisitAll();
        }

        public boolean isVisited(int node){
            return visited[node];
        }

        public void visit(int node){
            visited[node] = true;
        }

        public void unvisitAll(){
            for(int i = 0; i<nodes; i++){
                visited[i] = false;
            }
        }

        public void addEdge(int from, int to, int cap){
            if(cap == 0){
                Edge emt1 = new Edge(from, to, -1);
                graph[from].add(emt1);
                return;
            }
            else{
                Edge main = new Edge(from, to, cap);
                Edge residual = new Edge(to, from, 0);
                main.residual = residual;
                residual.residual = main;
                graph[from].add(main);//add edge to the node
                graph[to].add(residual);//add residual edge to the node
            }
            
        }

        public String getEdgeFlow(int from, int to){//returns current flow of the edge

            Edge tmp = graph[from].get(to);//add edges in a way that makes the index of the arraylist the same as the node
            if(tmp == null || tmp.isEmpty()){
                return "0";
            }
            if(tmp.residual()) return "";
            
            return String.valueOf(tmp.flow);
        }

        public int getSteps(){
            return iterations;
        }

        public int bfs(){
            Queue<Integer> que = new ArrayDeque<>(nodes);
            visit(source);
            que.offer(source);

            Edge[] prev = new Edge[nodes];

            while(!que.isEmpty()){
                iterations++; //increase the amount of steps
                int tmp = que.poll();
                if(tmp == sink) break;

                for(Edge edge: graph[tmp]){
                    if(edge == null) continue;
                    int cap = edge.remainingCap();

                    if(cap > 0 && !isVisited(edge.end)){
                        visit(edge.end);
                        prev[edge.end] = edge;
                        que.offer(edge.end);
                    }
                }

            }

            if(prev[sink] == null) return 0;

            int bottleneck = Integer.MAX_VALUE - 1;
            for(Edge edge = prev[sink]; edge != null; edge = prev[edge.start]){
                bottleneck = Math.min(bottleneck, edge.remainingCap());//might have to create remcap as a function
            }
            int checker = 0;
            for(Edge edge = prev[sink]; edge != null; edge = prev[edge.start]){
                edge.addPath(bottleneck);
                if(edge.flow < edge.maxCap && checker < maxFlow){
                    cut.add(edge.end);
                    cut.add(edge.start);
                    checker += edge.flow;
                }
                
            }
            return bottleneck;

        }


        public void run(){
            int bottleneck;
            do{
                unvisitAll();
                bottleneck = bfs();
                maxFlow += bottleneck;
            } while(bottleneck != 0);
            
        }






    }




}