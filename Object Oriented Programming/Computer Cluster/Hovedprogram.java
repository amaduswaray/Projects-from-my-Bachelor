import java.util.Scanner;
import java.io.File;

//lager klassen Hovedprogram
class Hovedprogram{

//lager Hovedprogram
  public static void main(String[] args){

//lager dataklynge objekt som leser fra regneklynge.txt
//oppgaven sier dataklynge.txt, men oppgaven hadde ikke den filen.
Dataklynge ama = new Dataklynge("regneklynge.txt");

//printer ut informasjonen om dataklynga
System.out.println("Prosessorer:" + ama.antProsessorer());
System.out.println("32GB: " + ama.noderMedNokMinne(32));
System.out.println("64GB: " + ama.noderMedNokMinne(64));
System.out.println("128GB: " + ama.noderMedNokMinne(128));
System.out.println("Antall: " + ama.antRacks());

  }
}
