//skriver kommentarer der koden skiller seg ut fra oppgave 2, 3, 4 og 5

class VeivalgSted extends Sted{

  protected VeivalgSted[] utveier;
  protected Sted hoyre;
  protected Sted venstre;
  protected Sted rettFrem;

  public VeivalgSted(String b){
    super(b);
  }

  public VeivalgSted gaaVidereTil(int indeks){
    return utveier[indeks];
  }

  public void faaUtveier(VeivalgSted[] ut){
    utveier = ut;
  }


}
