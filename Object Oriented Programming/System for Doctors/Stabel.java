//lager stabel klasse som arver fra lenkeliste
import java.util.Iterator;
class Stabel<T> extends Lenkeliste<T> {

//skal lage LIFO testStabelMetoder

//metode som legger objekt bakerst i listen
  public void leggPaa(T x){
    //bruker leggTil med posisjon bakerst, og objektet x
    leggTil(storrelse, x);
  }

//metode som fjerner bakerste elementet
  public T taAv(){
    //bruker fjern metoden som fjerner bakerste elementet
    return fjern(storrelse-1);
  }


}
