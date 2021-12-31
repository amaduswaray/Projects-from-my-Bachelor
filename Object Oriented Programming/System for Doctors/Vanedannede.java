class Vanedannede extends Legemiddel{

//definerer instans styrke som faar verdi i konstruktor
  protected int styrke;

  public Vanedannede(String n, double p, double v, int s){
    super(n,p,v);
    styrke = s;
  }

//lager get metode for styrke
  public int hentVanedannedeStyrke(){
    return styrke;
  }

//overrrider tostring metoden til legemiddel
  @Override
  public String toString(){
    return super.toString() + ". Styrke: " + styrke;
  }


}
