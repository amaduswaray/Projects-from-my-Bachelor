class Eksempel{

  public static void main(String[] args){

    Legesystem ny = new Legesystem();


    try {
      ny.lesInnfil("fil2.txt");
    }catch(UlovligUtskrift e){

    }


    ny.skriv();

  }


}
