//definerer klassen Node
class Node{

//lager instansvafiablene minne og antPros
private int minne;
private int antPros;

//Lager konstuktor
public Node(int min, int an){
    minne = min;
    antPros = an;
  }

//lager metode som returnerer antall prosessorer
  public int antProsessorer(){
    return antPros;
  }


//lager metode som sier om en Node har nok minne
  public boolean nokMinne(int paakrevdMinne){
    if (minne>=paakrevdMinne){
      return true;
    }
    else{
      return false;
    }
  }
}
