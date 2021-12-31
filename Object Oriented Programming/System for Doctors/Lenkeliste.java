import java.util.Iterator;

//lager klassen lenkeliste
class Lenkeliste<T> implements Liste<T>{

//skal ha start og slutt variabler
  protected Node start;
  protected int storrelse;

//konstroktoren setter storrelsen til 0
  public Lenkeliste(){
      storrelse = 0;
    }



//metode som retunerer storrelse
    public int stoerrelse(){
      return storrelse;
    }

//legg til metode
    public void leggTil(int pos, T x){

//begynner med en ny node som faar data x
      Node ny = new Node(x);

//kaster ugyldig indeks exception dersom parameteren faar en pos som ikke er i indeksen til lista
      if(pos >= storrelse+1 | pos<0){
        throw new UgyldigListeIndeks(pos);
      }
      else{
//hvis pos er i indeks saa legger vi til noden i den posisjonen

//begynner med aa si hvis lista er tom, saa blir den nye noden det forste objektet
      if (storrelse == 0){
        start = ny;
      }
      else{

//hvis ikke saa ma vi gaa gjennom alle nodene frem dil den riktige posisjonen

//lager en teller og setter gjeldende node lik start
      Node gjeldende = start;
      int teller = 0;

      //hvis posisjonen er start saa legger vi bare noden til i indeks 0
      if (pos == 0){
        ny.neste = gjeldende;
        gjeldende.forrige = ny;

        start = ny;
      }
      else{
//ellers bruker vi while lokke for aa gaa gjennom lista
      while(teller < pos){
        //saa lenge gjeldende sin neste ikke er null, saa skal gjeldende bli sin neste
        if(gjeldende.neste != null){
        gjeldende = gjeldende.neste;
      }
      else{
        //hvis gjeldende sin neste er null saa blir den nye noden den neste

        gjeldende.neste = ny;
        ny.forrige = gjeldende;
        gjeldende = ny;
      }

//oker teller etter lokka fordi den skal bare vare til den bestempte posisjonen
      teller++;
      }

//settr forrige og neste pekere til de riktige stedene
      if(ny.equals(gjeldende)){
        ny = gjeldende;
      }
      else{
      ny.forrige = gjeldende.forrige;
      ny.forrige.neste = ny;
      ny.neste = gjeldende;
      gjeldende.forrige = ny;
    }

    }

  }
}

//til slutt oker vi storrelsen etter aa ha lagt til et element
      storrelse++;


    }

//legg til uten pos er bare vanlig legg til som legger til elementet bakers
    public void leggTil(T x) {

      Node ny = new Node(x);

      if(storrelse == 0){
        start = ny;
      }
      else{

        Node midl = start;

        while (midl.neste != null){
          midl = midl.neste;
        }

        midl.neste = ny;
        ny.forrige = midl;

      }

      storrelse++;
    }


//metode som endrer data til en bestemt node
    public void sett(int pos, T x){

      Node gjeldende = start;
      int teller = 0;

//kaster ugyldig indeks exception dersom pos er utenfor indeksen til lista
      if(pos >= storrelse | pos<0){
        throw new UgyldigListeIndeks(pos);
      }
      else{

//hvis ikke saa lager jeg gjeldende node som skal endre data
      if(pos == 0){
        gjeldende = start;
      }
      else{

        while(teller < pos){
          gjeldende = gjeldende.neste;
          teller++;
        }
      }

    }

      gjeldende.data = x;


    }

//lager metode som henter bestemt element
    public T hent(int pos){

      Node gjeldende = start;
      int teller = 0;

//kaster indeks exception
      if(pos >= storrelse | pos<0){
        throw new UgyldigListeIndeks(pos);
      }
      else{

      if(pos == 0){
        gjeldende = start;
      }
      else{

      while(teller < pos){
        gjeldende = gjeldende.neste;
        teller++;
      }

    }
  }

//lager objekt retur som faar dataen som skal returneres som verdi
      T retur = gjeldende.data;

      return retur;

    }

//fjerner bestemt element
    public T fjern(int pos){

      Node gjeldende = start;
      int teller = 0;

      T retur = null;
//kaster
      if(pos < 0){
        throw new UgyldigListeIndeks(pos);
      }
      else if(storrelse > pos){

      while(teller < pos){
        gjeldende = gjeldende.neste;
        teller++;
      }

//setter riktig neste og forrige pekere
      if(pos == 0){
        gjeldende = start;
        start = gjeldende.neste;
      }
      else{
        gjeldende.forrige.neste = gjeldende.neste;

        if(gjeldende.neste != null){
        gjeldende.neste.forrige = gjeldende.forrige;
      }


    }

//minker storrelse
      storrelse -= 1;

      retur = gjeldende.data;
      gjeldende = null;

    }
    else{
      throw new UgyldigListeIndeks(pos);
    }
//returnerer data til det fjernet elementet
      return retur;


    }

//fjerner forste elementet i listen
    public T fjern(){

      T retur = null;

      Node gjeldende = start;

      if(storrelse != 0){

      start = gjeldende.neste;
      start.forrige = null;

      retur = gjeldende.data;

      gjeldende = null;
      storrelse -= 1;
    }
    else{
      throw new UgyldigListeIndeks(0);
    }



      return retur;


    }







//node klasse som har neste og forrige peker. og data som blir gitt i konstruktoren
    private class Node {

    protected T data;
    protected Node neste;
    protected Node forrige;

    public Node(T data){
        this.data = data;
    }
  }



    public Iterator<T> iterator(){
      return new LenkelisteIterator();
  }

  private class LenkelisteIterator implements Iterator<T>{

      private Node tmp = start;

      public boolean hasNext(){
          if(tmp != null){
              return true;
          }
          return false;
      }
      public T next(){
          T n = tmp.data;
          tmp = tmp.neste;
          return n;
      }
  }


}
