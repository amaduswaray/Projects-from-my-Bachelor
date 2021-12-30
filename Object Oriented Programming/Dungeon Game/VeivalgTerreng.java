//skriver kommentarer der koden skiller seg ut fra oppgave 2, 3, 4 og 5

import java.util.Scanner;
import java.io.File;
import java.util.Random;


class VeivalgTerreng extends Terreng{

  protected VeivalgSted[] oversikt;

  public VeivalgTerreng(String filnavn){
    super(filnavn);

    oversikt = new VeivalgSted[storrelse];
  }

  public void alternativVei(){

    Random random1 = new Random();

    //VeivalgSted[] oversikt = new VeivalgSted[storrelse];
    for(int i = 0; i < storrelse; i++){
      VeivalgSted alt = (VeivalgSted) hent(i);
      oversikt[i] = alt;
    }

    for(VeivalgSted gjeldende: oversikt){
      VeivalgSted[] alternativUtveier = new VeivalgSted[3];

      for(int i = 0; i < 3; i++){
        alternativUtveier[i] = oversikt[random1.nextInt(storrelse -1)];
      }

      gjeldende.faaUtveier(alternativUtveier);
    }

    //bane = oversikt;

  }

  public VeivalgSted hentRandomStart(){
    Random random2 = new Random();

    VeivalgSted denne = oversikt[random2.nextInt(storrelse -1)];
    return denne;

  }



}
