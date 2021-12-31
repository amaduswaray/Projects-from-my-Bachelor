//lager Spesialist som arver fra lege og som implimenterer Godkjenningsfritak
class Spesialist extends Lege implements Godkjenningsfritak{

//definerer kontrollID, og den faar verdi i konstruktor
  protected int kontrollID;

  public Spesialist(String n, int kid){
    super(n);
    kontrollID = kid;
  }

//implimenterer interface metoden
  public int hentKontrollID(){
    return kontrollID;
  }

//overrider toString metoden
  @Override
  public String toString(){
    return super.toString() + ". KontrollID: " + kontrollID;
  }

}
