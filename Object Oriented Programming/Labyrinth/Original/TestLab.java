import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

class TestLab{

public static void main(String[] args) throws FileNotFoundException{

  File fil = new File("3.in");

  Labyrint l = null;

  try{
    l = Labyrint.lesFraFil(fil);
  }catch(FileNotFoundException e){}

    System.out.println(l);

    //l.hentLab()[3][5].finnUtvei();
    Lenkeliste<String> utveier = l.finnUtveiFra(3,5);
    for(String tekst: utveier){
      System.out.println(tekst);
    }



    //System.out.println(l.hentAap());


  }
}
