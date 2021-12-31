import java.io.PrintWriter;
import java.util.Scanner;
class Skeleton {

  public static void main(String[] args) throws UlovligUtskrift{

    Legesystem system = new Legesystem();



    Scanner skan = new Scanner(System.in);
    System.out.println("Hei og velkommen til dette legesystemet.");
    System.out.println("Skriv hvilken som helst tast for aa fortsette.");




    int avslutt = 1;



    while(avslutt != 0){

      if(skan.nextLine().equals("q")){
        System.out.println("Avslutter program");
        avslutt = 0;
      }
      else{

        for(int i=0; i<100; i++){
          System.out.println(" ");
        }

        System.out.println("Dette er kommandoene til dette programmet.");
        System.out.println("1: Opprette og legge til nye elementer i systemet.");
        System.out.println("2: Bruke en gitt resept fra listen til en pasient.");
        System.out.println("3: Skrive ut forskjellige former for statistikk.");
        System.out.println("4: Skrive alle data til fil.");
        System.out.println("5: Skrive ut hele legesystemet.");
        System.out.println("q: Avslutt programmet");

        String guide = skan.nextLine();

        if(guide.equals("1")){
          System.out.println("Du har valgt aa Opprette og legge til nye elementer i systemet.");
          System.out.println("Hvilket element vil du legge til?(Pasient, Lege, Legemiddel, Resept.)");

          String svar = skan.nextLine();

          if(svar.equals("Pasient")){
            System.out.println("Du har valgt aa legge til Pasient.");

            System.out.println("Skriv inn navn og fødselsnummer til din pasient. (navn,fodselsnmr)");
            System.out.println("Dette er et eksempel:");
            System.out.println("Daniel,25120098672");
            System.out.println("Husk ingen mellomrom etter komma.");


            String svar2 = skan.nextLine();
            String[] ord = svar2.split(",");
            Pasient p = new Pasient(ord[0],ord[1]);
            system.hentPasientListe().leggTil(p);
            System.out.println("Pasient lagt til. Trykk en tast for aa fortsette. (q for å avslutte)");


          }

          else if(svar.equals("Legemiddel")){

            System.out.println("Skriv Legemiddel type(Vanlig,narkotisk eller vanedannende)");
            System.out.println("Dette er et eksempel:");
            System.out.println("Narkotisk");

            String type = skan.nextLine();

            if(type.equals("Vanlig")){
              System.out.println("Du har valgt vanlig");
              System.out.println("Skriv inn navn, pris og virkestoff.");
              System.out.println("Dette er et eksempel:");
              System.out.println("Ibux,240,200");
              System.out.println("Husk ingen mellomrom mellom komma");

              String ting = skan.nextLine();
              String[] ord = ting.split(",");
              String navn = ord[0];
              Double pris = Double.parseDouble(ord[1]);
              Double virkestoff = Double.parseDouble(ord[2]);

              Legemiddel vanlig = new Vanlig(navn,pris,virkestoff);
              system.hentLegemiddelListe().leggTil(vanlig);
              System.out.println("Vanlig lagt til. Trykk en tast for aa fortsette. (q for å avslutte)");

            }

            else if(type.equals("Narkotisk")){
              System.out.println("Du har valgt narktoisk");
              System.out.println("Skriv inn navn, pris, virkestoff og styrke.");
              System.out.println("Dette er et eksempel:");
              System.out.println("Percaset,240,200,4");
              System.out.println("Husk ingen mellomrom mellom komma");

              String ting = skan.nextLine();
              String[] ord = ting.split(",");
              String navn = ord[0];
              Double pris = Double.parseDouble(ord[1]);
              Double virkestoff = Double.parseDouble(ord[2]);
              int styrke = Integer.parseInt(ord[3]);

              Legemiddel narkotisk = new Narkotisk(navn,pris,virkestoff,styrke);
              system.hentLegemiddelListe().leggTil(narkotisk);
              System.out.println("Narkotisk lagt til. Trykk en tast for aa fortsette. (q for å avslutte)");

            }

            else if(type.equals("Vanedannende")){
              System.out.println("Du har valgt vanedannende");
              System.out.println("Skriv inn navn, pris, virkestoff og styrke.");
              System.out.println("Dette er et eksempel:");
              System.out.println("Molly,240,200,4");
              System.out.println("Husk ingen mellomrom mellom komma");

              String ting = skan.nextLine();
              String[] ord = ting.split(",");
              String navn = ord[0];
              Double pris = Double.parseDouble(ord[1]);
              Double virkestoff = Double.parseDouble(ord[2]);
              int styrke = Integer.parseInt(ord[3]);

              Legemiddel vanedannende = new Vanedannede(navn,pris,virkestoff,styrke);
              system.hentLegemiddelListe().leggTil(vanedannende);
              System.out.println("Vanedannende lagt til. Trykk en tast for aa fortsette. (q for å avslutte)");
            }

          }

          else if(svar.equals("Lege")){
            System.out.println("Du har valgt aa legge til en Lege.");

              System.out.println("Skriv inn navn og kontrollid");
              System.out.println("Dette er et Eksempel:");
              System.out.println("Dr. Daniel,0231");
              System.out.println("Husk ingen mellomrom etter komma.");

              String svar2 = skan.nextLine();
              String[] ord = svar2.split(",");

              String legeNavn = ord[0];

              int kID = Integer.parseInt(ord[1]);




              if (ord[1].equals("0")) {
                Lege ny = new Lege(legeNavn);
                system.hentLegeliste().leggTil(ny);
                //sortert.leggTil(ny);

                System.out.println("Lege lagt til, Trykk en tast for aa fortsette. (q for å avslutte)");


              }
              else  {


                Spesialist sp = new Spesialist(legeNavn, kID);
                system.hentLegeliste().leggTil(sp);
                //sortert.leggTil(sp);



                System.out.println("Spesialist lagt til, Trykk en tast for aa fortsette. (q for å avslutte)");

              }
          }

          else if(svar.equals("Resept")){
            System.out.println("Skriv inn navn til legen som skriver resept, hvilket legemiddelID, pasientID, eventuelt reit, og type resept (hvite, militer, blaa, p)");
            System.out.println("(lege,legemiddelID,pasientID,reit,type resept)");
            System.out.println("Dette er et eksempel:");
            System.out.println("Dr. Daniel,1,0,3,Blaa,");

            String svar2 = skan.nextLine();
            String[] ord = svar2.split(",");

            String lege = ord[0];
            int legemiddelID = Integer.parseInt(ord[1]);
            int pID = Integer.parseInt(ord[2]);
            int reit = Integer.parseInt(ord[3]);
            String type = ord[4];

            Lege utskrevende = null;
            Legemiddel l1 = null;
            Pasient p = null;

            for (Lege l: system.hentLegeliste()) {
              if (lege.equals(l.hentNavn())) utskrevende = l;

            }
            if (utskrevende==null) System.out.println("Fant ikke legen");

            for (Legemiddel lm : system.hentLegemiddelListe()) {
              if (legemiddelID==lm.hentId()) l1 = lm;
            }

            if (l1==null) System.out.println("Fant ikke legemiddel");

            for (Pasient p1 : system.hentPasientListe()) {
              if (pID == p1.hentID()) p = p1;
            }

            if (p==null) System.out.println("Fant ikke pasient");

            if((l1 instanceof Narkotisk) && !(utskrevende instanceof Spesialist)){
              throw new UlovligUtskrift(utskrevende,l1);
              //System.out.println("UlovligUtskrift");
            }
            else if (utskrevende!=null && l1!=null &&p!=null) {

              try{


              if (type.toLowerCase().equals("hvite")) {


                    utskrevende.skrivHvitResept(l1,p,reit);
                    System.out.println("Hvit resept skrevet. Trykk en tast for aa fortsette. (q for å avslutte)");


              }

              else if(type.toLowerCase().equals("militer")){

                    utskrevende.skrivMilliterResept(l1, p, reit);
                    System.out.println("Militer resept skrevet. Trykk en tast for aa fortsette. (q for å avslutte)");

              }

              else if(type.toLowerCase().equals("p")){


                    utskrevende.skrivPResept(l1,p);
                    System.out.println("P-resept skrevet. Trykk en tast for aa fortsette. (q for å avslutte)");


              }

              else if(type.toLowerCase().equals("blaa")) {


                    utskrevende.skrivBlaaResept(l1,p,reit);
                    System.out.println("Blaa resept skrevet. Trykk en tast for aa fortsette. (q for å avslutte)");


              }

            }catch(UlovligUtskrift e){}

            }


          }

          else if(svar.equals("q")){
            System.out.println("Avslutter program");
            avslutt = 0;
          }

          else{
            System.out.println("Du valgte ikke et gyldig element. Prov igjen.");
            System.out.println("Skriv inn en tast for a gaa tilbake til menyen.");

          }

        }

        else if(guide.equals("2")){

          System.out.println("Du har valgt aa bruke en gitt resept fra listen til en pasient.");
          System.out.println("Hvilken pasient vil du se resepter for?");

          for (Pasient p : system.hentPasientListe()) {
            System.out.println(p.hentPNavn());
          }

          Pasient riktigPas = null;
          String pasient = skan.nextLine();

          for (Pasient sjekkPas : system.hentPasientListe()) {
              if(sjekkPas.hentPNavn().equals(pasient)) {
                riktigPas = sjekkPas;

              }
          }

          System.out.println("Du valgte " + pasient + " Og Vi skal naa finne denne pasienten. ");

          if (riktigPas == null) {
              System.out.println("Fant ikke pasienten. Trykk en tast for aa gaa til meny.");
          }

          else{

          System.out.println("Hvilken resept vil du bruke ? ");
          int teller = 0;
          Resepter valgt = null;
          for (Resepter r: riktigPas.hentResepter()) {
              System.out.println(teller + ": " + r.hentLegemiddel().hentNavn() + "(" + r.hentReit() + " reit)");
              teller++;
          }

          int svar2 = skan.nextInt();
          String videre = skan.nextLine();
          valgt = riktigPas.hentResepter().hent(svar2);

          if (valgt.bruk()) {
            System.out.println("Brukte resept paa " + valgt.hentLegemiddel().hentNavn() + ". Antall gjenverende reit: " + (valgt.hentReit()));
            //System.out.println("Trykk en tast for aa fortsette. (q for aa avslutte)");
          }

          else {
            System.out.println("Kunne ikke bruke resept på " + valgt.hentLegemiddel().hentNavn() + " ingen gjenverende reit");
            //System.out.println("Trykk en tast for aa gaa til meny. (q for aa avlsutte)");
          }

        }

          System.out.println("Trykk en tast for aa fortsette. (q for aa avslutte)");


        }



        else if(guide.equals("3")){

          int antallVane = 0;
          for (Lege doc : system.hentLegeliste()) {
            for (Resepter r: doc.hentUtskrevedeResepter()) {
              if (r.hentLegemiddel() instanceof Vanedannede) {
                antallVane ++;
            }

            }
          }

          System.out.println("Totalt antall utskrevne resepter på vanedannende legemidler er: " + antallVane);

          int antallNark = 0;
          for (Lege doc : system.hentLegeliste()) {
            for (Resepter r: doc.hentUtskrevedeResepter()) {
              if (r.hentLegemiddel() instanceof Narkotisk) {
                antallNark ++;

            }
          }
        }

          System.out.println("Totalt antall utskrevne resepter på narktoisk legemidler er: " + antallNark);



          for (Lege doc : system.hentLegeliste()) {
              int antallLegeNarkotsik = 0;
              for (Resepter r: doc.hentUtskrevedeResepter()) {
                  if (r.hentLegemiddel() instanceof Narkotisk) {
                    antallLegeNarkotsik++;
                  }
                }

              if (antallLegeNarkotsik>0) {
                  System.out.println("Legen " + doc.hentNavn() + " har skrevet ut " + antallLegeNarkotsik + " narkotiske resepter");
              }
          }


          for (Pasient pas : system.hentPasientListe()) {
              int antallPasientNarkotsik = 0;
              for (Resepter r: pas.hentResepter()) {
                  if (r.hentLegemiddel() instanceof Narkotisk) {
                    antallPasientNarkotsik++;
                  }
                }
              if (antallPasientNarkotsik>0) {
                  System.out.println("Pasienten " + pas.hentPNavn() + " har skrevet ut " + antallPasientNarkotsik + " narkotiske resepter");
              }
          }

          System.out.println("Trykk en tast for aa fortsette. (q for aa avslutte)");





        }

        else if(guide.equals("4")){
          System.out.println("Du valgte aa skrive all tada til fil.");
          System.out.println("Skriv inn filnavnet du vil skrive til paa dette formatet:");
          System.out.println("filnavn.txt");

          String filnavn = skan.nextLine();

          /*try {
            system.lesInnfil("fil.txt");
          }catch(UlovligUtskrift e) {
            System.out.println(e);
          }*/
          PrintWriter fil = null;
          try{
              fil = new PrintWriter(filnavn);
          }
          catch(Exception e){
              System.out.println(e);
          }

          fil.println("#Pasienter (navn, fnr)");
          for (Pasient p: system.hentPasientListe()) {
            fil.println(p.hentPNavn() + "," + p.hentNmr());
          }

          fil.println("#Legemiddler (navn, type, pris, virkestoff, [styrke])");
          for (Legemiddel l: system.hentLegemiddelListe()) {
            String type = null;
            int styrke = 0;
            if (l instanceof Vanlig) {
              type = "vanlig";
              fil.println(l.hentNavn() + "," + type + "," + l.hentPris() + "," + l.hentVirkestoff());
            }
            else if (l instanceof Vanedannede) {
              type = "vanedannende";
              Vanedannede vane = (Vanedannede) l;
              fil.println(vane.hentNavn() + "," + type + "," + vane.hentPris() + "," + vane.hentVirkestoff() + "," + vane.hentVanedannedeStyrke());
            }
            else {
              type = "narkotisk";
              Narkotisk nark = (Narkotisk) l;
              fil.println(nark.hentNavn() + "," + type + "," + nark.hentPris() + "," + nark.hentVirkestoff() + "," + nark.hentNarkotiskStyrke());
            }





          }

          fil.println("#Leger (navn, kontrollid /0 hvis vanlig lege)");
          for (Lege l1: system.hentLegeliste()) {
            if (!(l1 instanceof Spesialist)) {
              fil.println(l1.hentNavn()+ "," + "0");
            }
            else {
              Spesialist spes = (Spesialist) l1;
              fil.println(l1.hentNavn() + "," + spes.hentKontrollID());
            }

          }

          fil.println("#Resepter (legemiddelNummer, legeNavn, pasientID, type, [reit])");
          for(Lege doc: system.hentLegeliste()){
              String type = "";
              for(Resepter r: doc.hentUtskrevedeResepter()){
                if (r instanceof Blaa) {
                  type = "blaa";

                }


                else if (r instanceof Milliterresepter) {
                  type = "militaer";

                }

                else if (r instanceof P) {
                    type = "p";
                }

                else  {
                  type = "hvit";
                }

                fil.println(r.hentLegemiddel().hentId() + "," + r.hentLege().hentNavn() + "," + r.hentPasient().hentID() + "," +type +","+ r.hentReit());

              }



          }


          fil.close();

          System.out.println("Data er lagt til fil. Trykk en tast for aa fortsette(q for aa avslutte)");




        }

        else if(guide.equals("5")){

          system.skriv();



          System.out.println("Trykk en tast for aa gaa til meny.");

        }

        else if(guide.equals("q")){
          System.out.println("Avslutter program");
          avslutt = 0;
        }

        else{
          System.out.println("Du skrev noe ugyldig");
          System.out.println("Trykk en tast for aa gaa til hovedmeny");

        }










      }



    }



  }
}
