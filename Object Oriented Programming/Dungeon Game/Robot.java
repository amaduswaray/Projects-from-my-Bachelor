//skriver kommentarer der koden skiller seg ut fra oppgave 2, 3, 4 og 5

import java.util.Random;

class Robot implements Brukergrensesnitt{

  public void giStatus(String status){
    System.out.println(status);
    //skal gi brukeren informasjon om det som skjer i spillet

  }

  public int beOmKommando(String spoersmaal, String[] alternativer){

    Random random = new Random();

    int robTall = random.nextInt(alternativer.length - 1);

    for(int i = 0; i < alternativer.length; i++){
      System.out.println( i + ": " + alternativer[i]);
    }

    System.out.println("Robot velger " + robTall + " " + alternativer[robTall]);


    return robTall;
  }
}
