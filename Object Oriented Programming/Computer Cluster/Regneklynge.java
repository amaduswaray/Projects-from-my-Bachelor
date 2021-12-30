import java.util.ArrayList;

class Regneklynge{
  private int noderPerRack;
  private ArrayList<Racks> rackliste = new ArrayList<Racks>();

  public Regneklynge(int maxNoder){
    noderPerRack = maxNoder;
  }
  public void settInnNode(Node noden){
    int teller = (rackliste.size()) -1;
    if (rackliste.size() == 0){
      Racks nyRack = new Racks();
      nyRack.settInn(noden);
      rackliste.add(nyRack);
    }
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

  public int antProsessorer(){
    int antall = 0;
    for(Racks rack: rackliste){
      antall += rack.antProsessorer();
    }
    return antall;
  }

  public int noderMedNokMinne(int paakrevdMinne){
    int antall = 0;
    for(Racks rack: rackliste){
      antall += rack.noderMedNokMinne(paakrevdMinne);

    }
    return antall;
  }

  public int antRacks(){
    return rackliste.size();
  }

}
