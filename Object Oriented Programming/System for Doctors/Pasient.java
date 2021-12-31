class Pasient{

  String navn;
  String fodselsnmr;
  static int IDteller = 0;
  int id;

  Stabel<Resepter> liste = new Stabel<Resepter>();


  public Pasient(String n, String f){
    navn = n;
    fodselsnmr = f;
    id = IDteller;
    IDteller++;
  }

  public String hentPNavn(){
    return navn;
  }

  public int hentID(){
    return id;
  }

  public String hentNmr(){
    return fodselsnmr;
  }


  void leggTil(Resepter r){
    liste.leggPaa(r);
  }

    Stabel<Resepter> hentResepter(){
      return liste;

  }


  public String toString(){
    return "Navn: " + navn + ". FN: " + fodselsnmr;
  }





}
