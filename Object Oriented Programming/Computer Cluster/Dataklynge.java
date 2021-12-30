//importerer Arraylist, scanner og file.
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

//definerer klassen Dataklynge
class Dataklynge{
  //lager instansvariablene
  private int noderPerRack;
  private ArrayList<Racks> rackliste = new ArrayList<Racks>();
  //setter file som null for aa endre til en ekte fil senere, naar man har filnavn
  private File fil = null;
  //setter scanner som null siden det ikke har et objekt aa scanne enda
  private Scanner skan = null;
  private int amount;
  private int memory;
  private int cpu;

//Lager konstuktor, som lager alle nodene. Har filnavn som parameter
  public Dataklynge(String filnavn){
    //definerer fil som en ny fil med vilnavnet som er valgt
    fil = new File(filnavn);

//prover aa catche exception
  try{
    //setter scanner objektet til et objekt som leser filen
    skan = new Scanner(fil);
  }
  catch(Exception e){}

    //setter maks node per rack som det forste tallet i filemn
    noderPerRack = skan.nextInt();
    //gaar til neste linje siden det ikke er linjeshift etter nextInt
    skan.nextLine();

//lager while lokke som varer saa lenge filen har et nextline/node objekt
    while(skan.hasNextLine()){

//definerer nextline som et objekt.
      String line = skan.nextLine();

      //splitter den linjen for aa ha en ayyar med innholdet i linja
      String innhold[] = line.split(" ");

      //gjor om tallene i arrayen til det det representerer i noden.
      //indeks 0 er antall noder, osv..
      int antall = Integer.parseInt(innhold[0]);
      int minne = Integer.parseInt(innhold[1]);
      int prosessorer = Integer.parseInt(innhold[2]);

      //lager en for lokke som gaar like mange ganger som det er antall noder
      for(int i=0; i<antall; i++){
        //lager et node objekt og setter det inn i dataklynga
        //settInnNode metoden kommer senere i koden
        settInnNode(new Node(minne,prosessorer));
      }



    }

  }

  //lager metode som gaar gjennom dataklynga og setter inn node i en ledig rack
  public void settInnNode(Node noden){
    //lager en indeks, teller, som holder styr paa siste objektet i arraylisten
    int teller = (rackliste.size()) -1;
    //hvis arraylisten ikke har racks, saa skal den lage en ny rack som legger til en node, og deretter legger til racken til arraylisten
    if (rackliste.size() == 0){
      Racks nyRack = new Racks();
      nyRack.settInn(noden);
      rackliste.add(nyRack);
    }
    //hvis racklisten ikke er tom, saa sjekker den om den siste racken har plass til noder, saa legger den til en node
    //hvis sisge racken er full, saa lager den ny rack.
    else{
      Racks tellendeRack = rackliste.get(teller);
      if(tellendeRack.getAntNoder() == noderPerRack){
        Racks nyereRack = new Racks();
        nyereRack.settInn(noden);
        rackliste.add(nyereRack);

      }
      else{
        tellendeRack.settInn(noden);
      }

    }

  }

//Lager metode som gaar gjennom racklisten og retunerer antall prosessorertotalt
  public int antProsessorer(){
    int antall = 0;
    for(Racks rack: rackliste){
      antall += rack.antProsessorer();
    }
    return antall;
  }

//Metode som gaar gjennom dataklynga og retunerer antall moder med nok minne.
  public int noderMedNokMinne(int paakrevdMinne){
    int antall = 0;
    for(Racks rack: rackliste){
      antall += rack.noderMedNokMinne(paakrevdMinne);

    }
    return antall;
  }

//metode som retunerer antall racks
  public int antRacks(){
    return rackliste.size();
  }

}
