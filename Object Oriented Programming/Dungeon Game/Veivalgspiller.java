//skriver kommentarer der koden skiller seg ut fra oppgave 2, 3, 4 og 5

class VeivalgSpiller extends Spiller{

  protected VeivalgSted gjeldende2;
  protected VeivalgSted start2;


  public VeivalgSpiller(VeivalgSted s, Terminal o, String n){
    super(null , o, n);

    start2 = s;
    gjeldende2 = s;
  }


  public void nyttTrekk(){

    for(int i=0; i<100; i++){
      System.out.println("");
    }

    System.out.println("Det er " + navn + " sin tur");

    int sekkPlasser = (ryggsekk.hentAntall() - ryggsekk.hentObjekter());

    objekt.giStatus(gjeldende2.toString());

    System.out.println("Du har " + sekkPlasser + " plasser igjen i sekken din.");

    if(gjeldende2.hentSkattkiste().hentObjekter() == 0){

      if(sekkPlasser == 10){

        String[] alternativer = {"Gaa videre"};

        int svar = objekt.beOmKommando("Skattekisten her er tom. Sekken din er tom", alternativer);


      }

      else if(sekkPlasser == 0){
        String[] alternativer = {"Selg en gjenstand", "gaa videre"};

        int svar = objekt.beOmKommando("Skattekisten er tom", alternativer);

        if(svar == 0){

          int price = gjeldende2.hentSkattkiste().leggNed(ryggsekk.taUt());
          formue += price;
          sekkPlasser -= 1;
        }

      }

    }

    else if(gjeldende2.hentSkattkiste().hentObjekter() == gjeldende2.hentSkattkiste().hentAntall()){

      if(sekkPlasser == 10){

        String[] alternativer = {"Plukk opp en gjenstand", "Gaa videre"};

        int svar = objekt.beOmKommando("Kisten er full.", alternativer);

        if(svar == 0){
          ryggsekk.leggNed(gjeldende2.hentSkattkiste().taUt());
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
          ryggsekk.leggNed(gjeldende2.hentSkattkiste().taUt());
          sekkPlasser +=1;
        }

      }

      else if(sekkPlasser == 0){
        String[] alternativer = {"Selg en gjenstand", "gaa videre"};

        int svar = objekt.beOmKommando("Hva vil du gjore?", alternativer);

        if(svar == 0){

          int price = gjeldende2.hentSkattkiste().leggNed(ryggsekk.taUt());
          formue += price;
          sekkPlasser -= 1;
        }
      }

      else{
        String[] alternativer = {"Plukk opp en gjenstand", "Selg en gjenstand", "Selg og plukk opp en gjenstand", "Gaa videre"};

        int svar = objekt.beOmKommando("Hva vil du gjore?", alternativer);

        if(svar == 0){

          ryggsekk.leggNed(gjeldende2.hentSkattkiste().taUt());

        }

        else if(svar == 1){

          int price = gjeldende2.hentSkattkiste().leggNed(ryggsekk.taUt());
          formue += price;

        }

        else if(svar == 2){

          ryggsekk.leggNed(gjeldende2.hentSkattkiste().taUt());

          int price = gjeldende2.hentSkattkiste().leggNed(ryggsekk.taUt());
          formue += price;


        }


      }


    }




    //gjeldende = gjeldende.gaaVidere();

    String[] veier = {"Hoyre", "Venstre", "Rett Frem"};

    int videre = objekt.beOmKommando("Hvor vil du gaa videre?", veier);

    gjeldende2 = gjeldende2.gaaVidereTil(videre);



  }

}
