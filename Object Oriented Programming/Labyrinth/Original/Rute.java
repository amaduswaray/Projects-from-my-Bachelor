//lager abstrakt klasse rute og lager instans variablene
abstract class Rute{

  protected int rad;
  protected int kolonne;
  protected Labyrint labyrint;

  protected Rute nord;
  protected Rute sor;
  protected Rute ost;
  protected Rute vest;
  protected int rort = 0;

//konstruktoren
  public Rute(int r, int k){

    rad = r;
    kolonne = k;

  }

//lager abstrakt betode
  public abstract char tilTegn();

//lager en boolean hjelpemetode som skjekker om en rute er en forrige.
  private Boolean erForrige(){
    //har en variabel rort som er 0, men som blir 1 naar gaa blir kalt
    if(rort == 1){
      return true;
    }
    else{
      return false;
    }
  }

//lager gaa
  public void gaa(Rute forrige, String veiStreng){
//
//metoden returneres hvis ruten er en forrige,
//hvis alle utveiene er funnet saa returnerer metoden
    if(this.equals(forrige)){
      return;
    }
    else if(this.erForrige()){
      return;
    }
    else if(labyrint.funnet == labyrint.hentAap()){
      return;
    }
    else{
//hvis ikke programmet returneres saa blir rort til 1, og kordinatene blir lagt til strengen
      rort = 1;
      String nyVeiStreng = veiStreng + "  (" + kolonne + ", " + rad + ")" + "  -->";


      if(nord != null){
        nord.gaa(this, nyVeiStreng);
      }

      if(sor != null){
        sor.gaa(this, nyVeiStreng);
      }

      if(ost != null){
        ost.gaa(this, nyVeiStreng);
      }

      if(vest != null){
        vest.gaa(this, nyVeiStreng);
      }

    }
  }



//finnutvei kaller paa gaa, med null og tom streng som parameter.
  public void finnUtvei(){

      gaa(null, "");

//printer ut antall utveier den fant fra den posisjonen
    System.out.println("Det er " + labyrint.funnet + " utvei(er) Fra denne posisjonen");

//setter funnet til 0 igjen slik at programmet kan kjore flere ganger
//siden gaa returneres naar funnet er lik antall aapninger
    labyrint.funnet = 0;



  }





}
