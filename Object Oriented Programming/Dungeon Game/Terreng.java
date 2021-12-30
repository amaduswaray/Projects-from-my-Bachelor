//skriver kommentarer der koden skiller seg ut fra oppgave 2, 3, 4 og 5

import java.util.Scanner;
import java.io.File;

class Terreng{

  protected Sted start;
  protected int storrelse;


  public Terreng(String filnavn){

    storrelse = 0;

    File minFil = null;
    Scanner lesFil = null;
    try {
      minFil = new File(filnavn);
      lesFil = new Scanner(minFil);
    }catch (Exception e) {

    }

    String linje = lesFil.nextLine();
    while (lesFil.hasNextLine()){

      leggTil(linje);

      linje = lesFil.nextLine();

    }




  }


  public void leggTil(String x) {

    Sted ny = new VeivalgSted(x);
    Skattkiste ting = new Skattkiste(10);
    ny.plasserSkattkiste(ting);

    if(storrelse == 0){
      start = ny;
    }
    else{

      Sted midlertidlig = start;

      while(midlertidlig.neste != null){

        midlertidlig = midlertidlig.neste;

      }

      midlertidlig.neste = ny;

    }

    storrelse++;

  }

  public Sted hentStart(){
    return start;
  }

  public Sted hent(int pos){

  Sted gjeldende = start;
  int teller = 0;


  if(pos == 0){
    gjeldende = start;
  }
  else{

  while(teller < pos){
    gjeldende = gjeldende.neste;
    teller++;
  }

}

  return gjeldende;

}

}
