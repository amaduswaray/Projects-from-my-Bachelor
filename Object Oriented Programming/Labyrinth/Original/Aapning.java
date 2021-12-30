class Aapning extends HvitRute{


  public Aapning(int r, int k){
    super(r,k);
  }

  //paa aapninger blir hele strengen lagt til enlenkeliste og programmet stoppes
  //eller starter igjen og finner en ny utvei.

  public void gaa(Rute forrige, String veiStreng){
    labyrint.funnet+=1;
    String nyVeiStreng = veiStreng + "  (" + kolonne + ", " + rad + ")" + "\n";
    labyrint.utveiListe.leggTil(nyVeiStreng);
    return;


  }


}
