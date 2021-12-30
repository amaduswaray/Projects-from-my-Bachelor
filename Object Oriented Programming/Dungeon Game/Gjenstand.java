//skriver kommentarer der koden skiller seg ut fra oppgave 2, 3, 4 og 5

class Gjenstand{

  protected String navn;
  protected int verdi;


  public Gjenstand(String n, int v){
    navn = n;
    verdi = v;
  }

  public String hentNavn(){
    return navn;
  }

  public int hentVerdi(){
    return verdi;
  }

  public String toString(){
    return navn;
  }
}
