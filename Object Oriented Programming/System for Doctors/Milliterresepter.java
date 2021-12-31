//lager milliterresepter metoden som arver fra hvite
class Milliterresepter extends Hvite{

  public Milliterresepter(Legemiddel r, Lege l, Pasient p, int re){

    super(r,l,p,re);

//gir legemiddelet til resepten en nuy pris

    //r.settNyPris(0.0);

  }

//overrider prisAaBetale metoden
  @Override
  public double prisAaBetale(){
    return 0.0;
  }

}
