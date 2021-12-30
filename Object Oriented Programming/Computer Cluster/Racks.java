//importerer ArrayList util
import java.util.ArrayList;

//definerer klassen rack
class Racks{

//lager instansvariabel som er en arraylist som skal inneholde node objekter
private ArrayList<Node> nodeliste = new ArrayList<Node>();

//lager metode som adder valgt node klasse til arraylisten
public void settInn(Node noden){
  nodeliste.add(noden);
}

//lager metode som retunerer antall noder det er i nodelisten
public int getAntNoder(){
  return nodeliste.size();
}

//lager metode som gaar gjennom racken og ser vor mange prosessorer det er totalt i racken
public int antProsessorer(){
  int antall = 0;
  for(Node noden: nodeliste){
    antall += noden.antProsessorer();
  }
  return antall;
}

//lager metode som gaar gjennom racken og retunerer antall noder som har nok minne
public int noderMedNokMinne(int paakrevdMinne){
  int godkjent = 0;
  for(Node noden: nodeliste){
    if (noden.nokMinne(paakrevdMinne)){
      godkjent +=1;
    }
  }
  return godkjent;
}

}
