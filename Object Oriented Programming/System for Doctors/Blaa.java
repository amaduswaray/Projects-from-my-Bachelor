//lager blaa klassen sim arver fra resepter
class Blaa extends Resepter{

//lager privat int pris som er nye prisen man faar ved blaa resept
  private double prisen;

  public Blaa(Legemiddel r, Lege l, Pasient p, int re){

    super(r,l,p,re);

//setter ny pris til legemiddelet for denne resepten
    double nypris = r.hentPris() * 0.25;

  //  hentLegemiddel().settNyPris(nypris);

    prisen = nypris;

  }

//implimenterer de abstrakte metodene fra superklassen
  @Override
  public String farge(){
    return "Blaa";
  }

  @Override
  public double prisAaBetale(){

    return prisen;

  }

//overrider tostring metoden
  @Override
  public String toString(){
    return super.toString() + ". Farge: " + farge() +  ". Pris aa betale: " + prisAaBetale();
  }


}
