import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.HashMap;

class AstarMain{

    public static void main(String[] args) throws FileNotFoundException{

        Astar test = new Astar("test3-in.txt", "test3-out.txt");

        int output = test.a_star();

        test.writeFile();


    }
}