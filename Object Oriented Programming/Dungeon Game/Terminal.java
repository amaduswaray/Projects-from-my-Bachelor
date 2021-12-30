//skriver kommentarer der koden skiller seg ut fra oppgave 1

import java.util.Scanner;

class Terminal implements Brukergrensesnitt{

  protected Scanner skan;

  public Terminal(Scanner s){

    skan = s;
  }

  public void giStatus(String status){
    System.out.println(status);

  }

  public int beOmKommando(String spoersmaal, String[] alternativer){
    int retur = 0;
    System.out.println(spoersmaal);

    for(int i = 0; i < alternativer.length; i++){
      System.out.println(i + ": " + alternativer[i]);
    }

    String svar = skan.nextLine();
    retur = Integer.parseInt(svar);

    return retur;

  }

}
