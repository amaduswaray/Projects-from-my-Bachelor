// lager hvite som implimenterer resepter
class Hvite extends Resepter{

  public Hvite(Legemiddel r, Lege l, Pasient p, int re){

    super(r,l,p,re);

  }

//implimentere de abstrakte metodene fra super klassen
  @Override
  public String farge(){
    return "Hvit";
  }

  @Override
  public double prisAaBetale(){
    return legemiddel.hentPris();
  }

//overrider to string metoden
  @Override
  public String toString(){
    return super.toString() + ". Farge: " + farge() +  ". Pris aa betale: " + prisAaBetale();
  }


}
