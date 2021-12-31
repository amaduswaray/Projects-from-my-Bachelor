import java.util.Scanner;
import java.io.File;
class Legesystem {

  SortertLenkeliste<Lege> legeliste = new SortertLenkeliste<Lege>();
  Lenkeliste<Legemiddel> legemiddelliste = new Lenkeliste<Legemiddel>();

  Lenkeliste<Pasient> pasientliste = new Lenkeliste<Pasient>();


  public SortertLenkeliste<Lege> hentLegeliste() {
    return legeliste;
  }

  public Lenkeliste<Legemiddel> hentLegemiddelListe() {
    return legemiddelliste;
  }

  public Lenkeliste<Pasient> hentPasientListe() {
    return pasientliste;
  }


  public void skriv() {


    System.out.println("Legemiddler");
    for (Legemiddel l: legemiddelliste) {
      System.out.println(l);
    }

    System.out.println("Leger");
    for (Lege l1: legeliste) {
      System.out.println(l1);
    }

    System.out.println("Pasienter");
    for (Pasient p: pasientliste) {
      System.out.println(p);
    }

    for (Lege doc: legeliste) {
      for (Resepter r: doc.hentUtskrevedeResepter()) {
        System.out.println(r);
      }
    }









  }

  public void leggTilPasient(Pasient p) {
    pasientliste.leggTil(p);
  }



  public void lesInnfil(String filnavn) throws UlovligUtskrift{
    File minFil = null;
    Scanner lesFil = null;
    try {
      minFil = new File(filnavn);
      lesFil = new Scanner(minFil);
    }catch (Exception e) {

    }

    String linje = lesFil.nextLine();
    while (lesFil.hasNext()) {

      if (linje.contains("Pasienter")) {
          linje = lesFil.nextLine();

          while(!(linje.contains("#"))){
            String[] ord = linje.split(",");
            Pasient ny = new Pasient(ord[0], ord[1]);
            pasientliste.leggTil(ny);
            linje = lesFil.nextLine();
          }

        }




      else if(linje.contains("Legemidler")) {
          linje = lesFil.nextLine();

          while(!(linje.contains("#"))){
              String[] ordene = linje.split(",");



              try {
                 if (ordene[1].contains("kombinasjoner") || ordene.length>=6) {
                  linje = lesFil.nextLine();
                }

                else if (ordene[1].contains("narkotisk")){


                    Double pris = Double.parseDouble(ordene[2]);
                    Double v = Double.parseDouble(ordene[3]);
                    int s = Integer.parseInt(ordene[4]);
                    Narkotisk n1 = new Narkotisk(ordene[0],pris,v,s);
                    legemiddelliste.leggTil(n1);
                    linje = lesFil.nextLine();

                }
                else if(ordene[1].equals("vanedannende")){

                    Double pris = Double.parseDouble(ordene[2]);
                    Double virkestoff = Double.parseDouble(ordene[3]);
                    int styrke = Integer.parseInt(ordene[4]);
                    Vanedannede vane = new Vanedannede(ordene[0], pris, virkestoff, styrke);
                    legemiddelliste.leggTil(vane);
                    linje = lesFil.nextLine();
                }
                else if (ordene[1].contains("vanlig")){
                    Double pris = Double.parseDouble(ordene[2]);
                    Double virkestoff = Double.parseDouble(ordene[3]);
                    Vanlig v = new Vanlig(ordene[0], pris, virkestoff);
                    legemiddelliste.leggTil(v);
                    linje = lesFil.nextLine();
                }

                else {
                  linje = lesFil.nextLine();
                }



            }catch(Exception e) {
              System.out.println("feil format");

            }



          }
        }








      else if (linje.contains("Leger")) {

          linje = lesFil.nextLine();
          while(!(linje.contains("#"))){

              String[] ordene = linje.split(",");

              if(ordene[1].equals("0")){
                Lege vanlig = new Lege(ordene[0]);
                legeliste.leggTil(vanlig);
                linje = lesFil.nextLine();
              }
              else {
                Spesialist s = new Spesialist(ordene[0],Integer.parseInt(ordene[1]));
                legeliste.leggTil(s);
                linje = lesFil.nextLine();

              }
          }
      }


      else if (linje.startsWith("# Resepter")) {





        while(lesFil.hasNext()){
          linje = lesFil.nextLine();





          String[] ord = linje.split(",");


          int id = Integer.parseInt(ord[0]);
          int pID = Integer.parseInt(ord[2]);
          String legeNavn = ord[1];
          Legemiddel riktig = null;
          Lege utskrevende = null;
          Pasient p = null;



            for(Legemiddel lm: legemiddelliste){
              if(lm.hentId() == id){
                riktig = lm;
              }
            }


          for(Lege l: legeliste){
            if(l.hentNavn().equals(legeNavn)){
              utskrevende = l;
            }
          }

          for(Pasient pas: pasientliste){
            if(pas.hentID() == pID){
              p = pas;
            }
          }



          if (riktig instanceof Narkotisk && !(utskrevende instanceof Spesialist)) {


              riktig = null;
              utskrevende=null;




          }

          if (riktig !=null && utskrevende!= null &&p!=null) {


            if(ord[3].contains("hvit"))  {

               utskrevende.skrivHvitResept(riktig,p,Integer.parseInt(ord[4]));
           }

           else if (ord[3].contains("blaa")){

               utskrevende.skrivBlaaResept(riktig,p,Integer.parseInt(ord[4]));
           }

           else if (ord[3].contains("militaer")){

               utskrevende.skrivMilliterResept(riktig,p,Integer.parseInt(ord[4]));
           }


           else if (ord[3].contains("p")) {

             utskrevende.skrivPResept(riktig,p);
           }






         }








        }



    }

  }


}

}
