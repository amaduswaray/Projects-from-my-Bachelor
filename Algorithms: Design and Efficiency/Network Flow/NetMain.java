import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Queue;
import java.util.ArrayDeque;
import java.lang.Object;
import java.lang.Math;
import java.util.Set;
import java.util.HashSet;




class NetMain{

    public static void main(String[] args) throws FileNotFoundException {

        Netflow test = new Netflow("test15-in.txt", "test15-out.txt");

        test.runNetflow();
        System.out.println(test.endFlow);
        System.out.println("Steps: " + test.steps);
        test.printCut();
        //System.out.println(test.getEndState());
        //test.printGraph();
        //test.finalPrint();
        test.writeFile();
        
    }

}