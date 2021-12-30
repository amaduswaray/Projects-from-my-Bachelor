import java.util.ArrayList;
import java.util.concurrent.locks.*;

//import java.io.InterruptedException;
//lager abstrakt klasse rute og lager instans variablene
abstract class Rute {

  protected int rad;
  protected int kolonne;
  protected Labyrint labyrint;

  protected Rute nord;
  protected Rute sor;
  protected Rute ost;
  protected Rute vest;
  protected int rort = 0;

  protected Lock laas = new ReentrantLock();


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
  public void gaa(Rute forrige, String vei){
//Skjekker om naboene er hvit foer den starter
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
//setter rort til 1 for aa merke at den denne ruten har blitt gatt gjennom
    rort = 1;

    //oppdaterer ruten til utveien
    String veiStreng = vei + "(" + kolonne + "," + rad + ")" + "-->";

    Rute[] naboer = {nord, ost, sor, vest};

//lager liste med de hvite naboene
    ArrayList<Rute> naboHvit = new ArrayList<Rute>();


    for(Rute rute: naboer){
      if(!(rute instanceof SortRute)){
        naboHvit.add(rute);
      }
    }

    int antallHvite = naboHvit.size();

    Thread[] traader = new Thread[antallHvite];

//hvis det er flere enn 1 hvit nabo saa er det flere veier aa gaa
//da lager programmet nye traader og starter den
//Programmet lager nye traader for den gamle traaden gaar videre.
// jeg antar at pga rekursjon metoden gaa, saa er det mulig at den gamle traaden kan finne flere utveier
//Da vil ikke hver traad ha hver sin utgang
//Det blir ikke parallellitet
    if(antallHvite >1){

      for(int i = 0; i < traader.length; i++){

        Traad t = new Traad(veiStreng, naboHvit.get(i), this);
        Thread denne = new Thread(t);
        traader[i] = denne;
        denne.start();

      }
//traadene maa join for aa vente paa at den traaden for er ferdig
      for(int i = 0; i < traader.length; i++){
        try{
          traader[i].join();
        }catch(InterruptedException e){}
      }


    }
    else{
//hvis det  bare er 1 hvit nabo saa fortsetter ruten. med den opperinnelige traaden
      Rute ruten = naboHvit.get(antallHvite-1);

      ruten.gaa(this, veiStreng/* + "-->"*/);
    }

  }



  }



//finnutvei kaller paa gaa, med null og tom streng som parameter.
  public void finnUtvei(){
    laas.lock();
    try{

      gaa(null ,  "");

//printer ut antall utveier den fant fra den posisjonen
    System.out.println("Det er " + labyrint.funnet + " utvei(er) Fra denne posisjonen");
    labyrint.herfra = labyrint.funnet;

//setter funnet til 0 igjen slik at programmet kan kjore flere ganger
//siden gaa returneres naar funnet er lik antall aapninger
    labyrint.funnet = 0;
    }
    finally{
      laas.unlock();
    }



  }

//lager traad classe som kan bruke Runnable
  class Traad implements Runnable{
//denne klassen tar inn den gjeldende ruten den er paa
//Tar inn den forrige
//tar ogsaa inn den forrige ruten
    String vei;
    Rute gjeldende;
    Rute forrige;

    public Traad(String v, Rute g, Rute f){

      vei = v;
      gjeldende = g;
      forrige = f;

    }
//den kaller paa gaa paa den gjeldende ruten
// er det en aapning saa legger man til veistreng til lenkelista
    public void run(){

      if(gjeldende instanceof Aapning){

        labyrint.funnet += 1;
        String nyVeiStreng = vei + "(" + gjeldende.kolonne + ", " + gjeldende.rad + ")" + "\n";
        labyrint.endreLenkeliste(nyVeiStreng);

    }
    else{
      gjeldende.gaa(forrige, vei);
    }



    }
  }





}
