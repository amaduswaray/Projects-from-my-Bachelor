class Aapning extends HvitRute{


  public Aapning(int r, int k){
    super(r,k);
  }


  /*public void run(){
    // kaller join paa gamle trad
    //lager ny trad og
    //return
  }*/

  //paa aapninger blir hele strengen lagt til enlenkeliste og programmet stoppes
  //eller starter igjen og finner en ny utvei.

  public void gaa(int row, int kol, String vei){

    labyrint.funnet+=1;
    String nyVeiStreng = vei + "  (" + kolonne + ", " + rad + ")" + "\n";
    labyrint.utveiListe.leggTil(nyVeiStreng);

    return;


  }


}
