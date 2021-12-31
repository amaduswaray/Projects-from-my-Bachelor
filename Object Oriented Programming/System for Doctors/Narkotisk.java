//lager Narkotisk som arver fra legemiddel
class Narkotisk extends Legemiddel{

//definerer ny iunstansvariabel styrke, setter verdi i konstrukteren.
  protected int styrke;

  public Narkotisk(String n, double p, double v, int s){
    super(n,p,v);
    styrke = s;
  }

//lager get metode for styrke
  public int hentNarkotiskStyrke(){
    return styrke;
  }

//overrider tostring metoden til legemiddel
  @Override
  public String toString(){
    return super.toString() + ". Styrke: " + styrke;
  }


}
