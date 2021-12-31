//lager klasssen resepter
abstract class Resepter{

//definerer instansvariablene og gir de verdier i konstruktor
  protected static int idTeller = 0;
  protected int id;
  protected Legemiddel legemiddel;
  protected Lege lege;
  protected Pasient pasient;
  protected int reit;


  public Resepter(Legemiddel r, Lege l, Pasient p, int re){

    id = idTeller;
    legemiddel = r;
    lege = l;
    pasient = p;
    reit = re;
    idTeller++;


  }
  //lager  metode bruk, som minker reit med 1, i tilleg til aa sljekke om det er nok reiter til aa bruke.

  public boolean bruk(){

    boolean check;

    if(reit <= 0){
      check = false;
    }
    else{
      check = true;
      reit--;
    }


    return check;
  }
//lager get metodene for klassen
  public int hentId(){
    return id;
  }


  public Legemiddel hentLegemiddel(){
    return legemiddel;
  }

  public Lege hentLege(){
    return lege;
  }

  public Pasient hentPasient(){
    return pasient;
  }

  public int hentReit(){
    return reit;
  }

//lager anbstrakte klasser som tvinger at subklassene maa implimentere de
  public abstract String farge();

  public abstract double prisAaBetale();

//lager toString metoden
  @Override
  public String toString(){
    return "ID: " + id + ". Legemiddel: " + legemiddel.hentNavn() + ". Lege: " + lege.hentNavn() + ". Pasient: " + pasient + ". Reit: " + reit;
  }



}
