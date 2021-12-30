//importerer java utiler
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.locks.*;

//lager klassen labyrint
class Labyrint {

//definerer instansvariablene
  private Rute[][] lab;
  private int rader;
  private int kolonner;
  public  Lenkeliste<String> utveiListe = new Lenkeliste<String>();

//lager variabel som skal telle antall aapninger som er funnet
  public int funnet = 0;
  public int herfra;

  Lock laas = new ReentrantLock();

//konstruktoren
  private Labyrint(Rute[][] rut, int r, int k){


    lab = rut;
    rader = r;
    kolonner = k;

  }
  //metode som beskytter og legger til utvei i lenkelisten
  public void endreLenkeliste(String utvei){
    laas.lock();
    try{
      utveiListe.leggTil(utvei);
    }
    finally{
      laas.unlock();
    }
  }


  public void leggTilVei(int kol, int rad){

      lab[rad][kol].finnUtvei();

  }

//lager metoden finnUtveiFra
  public Lenkeliste<String> finnUtveiFra(int kol, int rad){



//lager ny lenke liste som og git utveiListe samme referanse
    Lenkeliste<String> nyListe = new Lenkeliste<String>();
    utveiListe = nyListe;


//kaller paa ruter metoden finnUtvei
    //lab[rad][kol].finnUtvei();
    leggTilVei(kol, rad);


//gjor alle rutene sin rort til 0. brukes for aa skjekke hvor gaa kallet kom fra
    for(int i = 0; i < lab.length; i++){

      for(int j=0; j < lab[i].length; j++){
        if(lab[i][j] instanceof SortRute){
          lab[i][j].rort = 1;
        }
        else{
        lab[i][j].rort = 0;
        }

      }
    }
    //returnerer utveilista
    return utveiListe;
  }

//lager flere get metoder som henter instansvariablene
  public Lenkeliste<String> hentUtveiliste(){
    return utveiListe;
  }

  public Rute[][] hentLab(){
    return lab;
  }

  public int hentRader(){
    return rader;
  }

  public int hentKolonner(){
    return kolonner;
  }

  public int hentSort(){
    int ant = 0;

    for(int i = 0; i < lab.length; i++){

      for(int j=0; j < lab[i].length; j++){
        if(lab[i][j] instanceof SortRute){
          ant+=1;
        }
      }

    }

    return ant;

  }

  public int hentAap(){
    int ant = 0;

    for(int i = 0; i < lab.length; i++){

      for(int j=0; j < lab[i].length; j++){
        if(lab[i][j] instanceof Aapning){
          ant+=1;
        }
      }

    }

    return ant;

  }

//to string metoden til labyrinten

  public String toString(){

    String retur = "";

    for(int i = 0; i < lab.length; i++){
      String linje = "";

      for(int j=0; j < lab[i].length; j++){
        linje += lab[i][j].toString();
      }

      retur += (linje + "\n");
    }

    return retur;
  }


//lager les fra fil
  public static Labyrint lesFraFil(File fil) throws FileNotFoundException {
//lager en scanner som leser en ful
    Scanner skan = new Scanner(fil);

    String dimensjoner = skan.nextLine();
    String[] verdier = dimensjoner.split(" ");

//definerer de to forste tallene som rad og kolonne
    int rad = Integer.parseInt(verdier[0]);
    int kol = Integer.parseInt(verdier[1]);

//lager ruter liste men lengden til rad og kolonne
    Rute[][] ruter = new Rute[rad][kol];

//lager en teller som sier hvilken linje filen er paa
    int teller = 0;
//lager while lokke som deler hvert element i linja og lager rute objekter
//basert paa om det er . eller #
//lager aapling hvis det er . og paa kanten
    while(skan.hasNextLine()){

      String linje = skan.nextLine();

      String[] innhold = linje.split("");

      for(int i= 0; i< innhold.length; i++){

        Rute gjeldende = null;

        if (innhold[i].equals("#")){
          gjeldende = new SortRute(teller,i);
          ruter[teller][i] = gjeldende;
        }
        else if(innhold[i].equals(".")){

          if(i == 0){
            gjeldende = new Aapning(teller,i);
            ruter[teller][i] = gjeldende;
          }
          else if(teller == 0){
            gjeldende = new Aapning(teller,i);
            ruter[teller][i] = gjeldende;
          }
          else if(i == (innhold.length-1)){
            gjeldende = new Aapning(teller,i);
            ruter[teller][i] = gjeldende;
          }
          else if(!(skan.hasNextLine())){
            gjeldende = new Aapning(teller,i);
            ruter[teller][i] = gjeldende;
          }
          else{

          gjeldende = new HvitRute(teller,i);
          ruter[teller][i] = gjeldende;
          }

        }

      }

      teller++;

    }

    //gir rutene sine naboer

    for(int i=0; i < ruter.length; i++){

      for(int j=0; j < ruter[i].length; j++){

        if(i == 0 && j== 0){
          ruter[i][j].nord = null;
          ruter[i][j].sor = ruter[i+1][j];
          ruter[i][j].ost = ruter[i][j+1];
          ruter[i][j].vest = null;
        }
        else if(i == (ruter.length -1) && j == (ruter[i].length -1)){
          ruter[i][j].nord = ruter[i-1][j];
          ruter[i][j].sor = null;
          ruter[i][j].ost = null;
          ruter[i][j].vest = ruter[i][j-1];

        }
        else if(i == 0 && j == (ruter[i].length -1)){
          ruter[i][j].nord = null;
          ruter[i][j].sor = ruter[i+1][j];
          ruter[i][j].ost = null;
          ruter[i][j].vest = ruter[i][j-1];
        }
        else if(i == (ruter.length -1) && j == 0){
          ruter[i][j].nord = ruter[i-1][j];
          ruter[i][j].sor = null;
          ruter[i][j].ost = ruter[i][j+1];
          ruter[i][j].vest = null;
        }
        else if(i == 0){
          ruter[i][j].nord = null;
          ruter[i][j].sor = ruter[i+1][j];
          ruter[i][j].ost = ruter[i][j+1];
          ruter[i][j].vest = ruter[i][j-1];
        }
        else if(j == 0){
          ruter[i][j].nord = ruter[i-1][j];
          ruter[i][j].sor = ruter[i+1][j];
          ruter[i][j].ost = ruter[i][j+1];
          ruter[i][j].vest = null;
        }
        else if(i == (ruter.length -1)){
          ruter[i][j].nord = ruter[i-1][j];
          ruter[i][j].sor = null;
          ruter[i][j].ost = ruter[i][j+1];
          ruter[i][j].vest = ruter[i][j-1];
        }
        else if(j == (ruter[i].length -1)){
          ruter[i][j].nord = ruter[i-1][j];
          ruter[i][j].sor = ruter[i+1][j];
          ruter[i][j].ost = null;
          ruter[i][j].vest = ruter[i][j-1];
        }
        else{
          ruter[i][j].nord = ruter[i-1][j];
          ruter[i][j].sor = ruter[i+1][j];
          ruter[i][j].ost = ruter[i][j+1];
          ruter[i][j].vest = ruter[i][j-1];
        }


      }
    }

//lager labyrint og gir rutene i labyrinten verdi til labyrint instansvariablen
    Labyrint retur = new Labyrint(ruter,rad,kol);

    for(int i=0; i < retur.lab.length; i++){

      for(int j=0; j < retur.lab[i].length; j++){

        retur.lab[i][j].labyrint = retur;

      }
    }


//returnerer labyrinten
    return retur;

  }




}
