class Sted{

  protected String beskrivelse;
  protected Skattkiste kiste;
  protected Sted neste;

  public Sted(String b){
    beskrivelse = b;

  }

  public void plasserSkattkiste(Skattkiste skatt){
    kiste = skatt;
  }

  public Skattkiste hentSkattkiste(){
    return kiste;
  }
  public Sted gaaVidere(){
    return neste;

  }

  public String toString(){
    return beskrivelse;
  }
}
