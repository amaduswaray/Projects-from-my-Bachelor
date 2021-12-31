//lager p klasse som arver fra hvite
class P extends Hvite{

//lager en privat int pris som faar verdi i konstruktor, og som retuneres i prisAaBetale metodej
  private double prisen;

  public P(Legemiddel r, Lege l, Pasient p){

    super(r,l,p,3);

//setter ny pris til legemiddelet
    double nypris = r.hentPris() - 108.0;
    if(nypris < 0){
      nypris = 0;
    }

    //hentLegemiddel().settNyPris(nypris);

    prisen = nypris;

  }

//overrider priis aa betale metoden
  @Override
  public double prisAaBetale(){

    return prisen;

  }


}
