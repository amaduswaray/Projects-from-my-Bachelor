//Lager sortert lenkeliste som arver fra lenkeliste og som har en T som implementerer comparable
class SortertLenkeliste<T extends Comparable<T> > extends Lenkeliste<T> {

//oberskriver legg til slik at de bli sortert naar det legges til
  public void leggTil(T x){

//definerer storrelse og indeks
    int stoerrelse = storrelse;
    int index = 0;

//lager  for lokke som gaar gjennom lenke lista
    for(int i = 0; i < stoerrelse; i++){
      //hvis x er storre en det gjeldende elementet saa skal indeksen oke
      if(x.compareTo(hent(i)) > 0){
        index = i+1;
      }

    }
    //x blir da lagt til lista paa den riktige posisjonen
    super.leggTil(index, x);

  }

//gjor det umulig aa bruke metoden
  public void leggTil(int pos, T x) {
    throw new UnsupportedOperationException();
  }

//gjor det umulig aa bruke metoden
  public void sett(int pos, T x) {
    throw new UnsupportedOperationException();
  }

//oberskriver fjern slik at det fjerner det storste/bakerste elementet
  public T fjern(){
    return fjern(storrelse -1);

  }




}
