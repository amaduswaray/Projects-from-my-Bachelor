//lager klassen legemiddel. Abstakt for at det ikke skal vere mulig aa lage instans av denne klassen
 abstract class Legemiddel{

//definerer instans variablene
  protected String navn;
  protected static int idTeller = 0;
  protected int id;
  protected double pris;
  protected double virkestoff;

//tar inn parametere i konstruktor og gir instansvariablene en verdi
  public Legemiddel(String n, double p, double v){

    navn = n;
    pris = p;
    virkestoff = v;
    id = idTeller;
    idTeller++;
  }

//lager get metodene for alle instanseverdiene
  public int hentId(){
    return id;
  }

  public String hentNavn(){
    return navn;
  }

  public double hentPris(){
    return pris;
  }

  public double hentVirkestoff(){
    return virkestoff;
  }

//lager metode som endrer pris
  public void settNyPris(double nyPris){
    pris = nyPris;

  }

//lager to string metoden
  @Override
  public String toString(){
    return "Navn: " + navn + ". ID: " + id + ". Pris: " + pris + ". Virkestoff: " + virkestoff;
  }


}
