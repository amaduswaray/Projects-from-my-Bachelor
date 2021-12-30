//skriver kommentarer der koden skiller seg ut fra oppgave 2, 3, 4 og 5

class Spiller{

  protected Skattkiste ryggsekk = new Skattkiste(10);
  protected Sted start;
  protected Terminal objekt;
  protected Sted gjeldende;
  protected int formue = 0;
  protected String navn;


  public Spiller(Sted s, Terminal o, String n){

    start = s;
    objekt = o;
    gjeldende = s;
    navn = n;


  }


  public void nyttTrekk(){

    for(int i=0; i<100; i++){
      System.out.println("");
    }

    System.out.println("Det er " + navn + " sin tur");

    int sekkPlasser = (ryggsekk.hentAntall() - ryggsekk.hentObjekter());

    objekt.giStatus(gjeldende.toString());

    System.out.println("Du har " + sekkPlasser + " plasser igjen i sekken din.");

    if(gjeldende.hentSkattkiste().hentObjekter() == 0){

      if(sekkPlasser == 10){

        String[] alternativer = {"Gaa videre"};

        int svar = objekt.beOmKommando("Skattekisten her er tom. Sekken din er tom", alternativer);


      }

      else if(sekkPlasser == 0){
        String[] alternativer = {"Selg en gjenstand", "gaa videre"};

        int svar = objekt.beOmKommando("Skattekisten er tom", alternativer);

        if(svar == 0){

          int price = gjeldende.hentSkattkiste().leggNed(ryggsekk.taUt());
          formue += price;
          sekkPlasser -= 1;
        }

      }

    }

    else if(gjeldende.hentSkattkiste().hentObjekter() == gjeldende.hentSkattkiste().hentAntall()){

      if(sekkPlasser == 10){

        String[] alternativer = {"Plukk opp en gjenstand", "Gaa videre"};

        int svar = objekt.beOmKommando("Kisten er full.", alternativer);

        if(svar == 0){
          ryggsekk.leggNed(gjeldende.hentSkattkiste().taUt());
          sekkPlasser +=1;
        }

      }

      else if(sekkPlasser == 0){
        String[] alternativer = {"gaa videre"};

        int svar = objekt.beOmKommando("Kisten er full. Sekken din er full", alternativer);


      }

    }

    else{

      if(sekkPlasser == 10){

        String[] alternativer = {"Plukk opp en gjenstand", "Gaa videre"};

        int svar = objekt.beOmKommando("Hva vil du gjore?", alternativer);

        if(svar == 0){
          ryggsekk.leggNed(gjeldende.hentSkattkiste().taUt());
          sekkPlasser +=1;
        }

      }

      else if(sekkPlasser == 0){
        String[] alternativer = {"Selg en gjenstand", "gaa videre"};

        int svar = objekt.beOmKommando("Hva vil du gjore?", alternativer);

        if(svar == 0){

          int price = gjeldende.hentSkattkiste().leggNed(ryggsekk.taUt());
          formue += price;
          sekkPlasser -= 1;
        }
      }

      else{
        String[] alternativer = {"Plukk opp en gjenstand", "Selg en gjenstand", "Selg og plukk opp en gjenstand", "Gaa videre"};

        int svar = objekt.beOmKommando("Hva vil du gjore?", alternativer);

        if(svar == 0){

          ryggsekk.leggNed(gjeldende.hentSkattkiste().taUt());

        }

        else if(svar == 1){

          int price = gjeldende.hentSkattkiste().leggNed(ryggsekk.taUt());
          formue += price;

        }

        else if(svar == 2){

          ryggsekk.leggNed(gjeldende.hentSkattkiste().taUt());

          int price = gjeldende.hentSkattkiste().leggNed(ryggsekk.taUt());
          formue += price;


        }


      }


    }




    gjeldende = gjeldende.gaaVidere();



  }


  public int hentFormue(){
    return formue;
  }

  public String hentNavn(){
    return navn;
  }


}
