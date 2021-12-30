//lager sort rute
class SortRute extends Rute{

//gjor alle svarte ruter sin rort til 1, slik at de returnerer naar de kaller gaa
  public SortRute(int r, int k){
    super(r,k);
    rort = 1;
  }

  @Override
  public char tilTegn(){
    return '#';
  }

  public String toString(){
    return "#";
  }



  public void gaa(){
    return;
  }





}
