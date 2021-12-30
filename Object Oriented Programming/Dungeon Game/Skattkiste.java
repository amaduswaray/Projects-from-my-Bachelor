//skriver kommentarer der koden skiller seg ut fra oppgave 2, 3, 4 og 5

import java.util.Random;
import java.util.Scanner;
import java.io.File;

class Skattkiste{

  protected Gjenstand[] gjenstander;
  protected int antall;
  protected int ledig;
  protected int objekter;
  protected Random random = new Random();

  public Skattkiste(int a){
    antall = a;
    gjenstander = new Gjenstand[a];

    //fylle opp gjenstander
    File minFil = null;
    Scanner lesFil = null;
    try {
      minFil = new File("gjenstander.txt");
      lesFil = new Scanner(minFil);
    }catch (Exception e) {
    }

    String[] liste = new String[40];
    int amount = 0;

    String linje = lesFil.nextLine();

    while(lesFil.hasNextLine()){
      liste[amount] = linje;
      amount ++;
      lesFil.nextLine();
    }

    int fyll = random.nextInt(a);
    objekter = fyll;
    ledig = a - fyll;

    for(int i = 0; i < fyll; i++){
      String[] objekt = liste[random.nextInt(39)].split(" ");

      String navn = objekt[0];
      int verdi = Integer.parseInt(objekt[1]);

      Gjenstand ny = new Gjenstand(navn, verdi);

      gjenstander[i] = ny;

    }
  }


  public Gjenstand taUt(){

    Gjenstand retur = null;
    if(objekter > 1){
      Gjenstand gjeldende = gjenstander[random.nextInt(objekter - 1)];
      System.out.println("Du har valgt aa ta ut " + gjeldende);
      objekter -= 1;
      retur = gjeldende;
    }

    else if(objekter == 1){

      Gjenstand gjeldende = gjenstander[0];
      System.out.println("Du har valgt aa ta ut " + gjeldende);
      objekter -= 1;
      retur = gjeldende;

    }

    else{
      System.out.println("Denne kista er tom");
    }

    return retur;

  }

  public int leggNed(Gjenstand gjenstand){

    int retur = 0;

    if(objekter == antall){
      System.out.println("Denne kista er full");
    }

    else{

      int indeks = 0;

      for(int i = (gjenstander.length-1); i > -1; i--){
        if(gjenstander[i] == null){
          indeks = i;
        }
      }

      gjenstander[indeks] = gjenstand;

      System.out.println("Du har lagt inn " + gjenstand);
      objekter += 1;

      retur = gjenstand.hentVerdi();
  }

  return retur;

  }

  public int hentLedig(){
    return ledig;
  }

  public int hentAntall(){
    return antall;
  }

  public int hentObjekter(){
    return objekter;
  }




}
