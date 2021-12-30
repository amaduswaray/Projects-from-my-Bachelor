//skriver kommentarer der koden skiller seg ut fra oppgave 2, 3, 4 og 5

import java.util.Scanner;
import java.util.Random;
import java.io.File;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.event.*;
import java.io.File;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Spill extends Application{

  Text topp;
  Text bruker;
  Text formu;

  String brukerString = "";
  String formueString = "";

  Button avslutt;

  Stage teater;



  public void start(Stage stage){

    play();

    topp = new Text("Dette ble resultatet av spillet");
    topp.setY(40);
    topp.setFont(new Font(40));



    bruker = new Text(brukerString);
    bruker.setFont(new Font(25));

    formu = new Text(formueString);
    formu.setFont(new Font(25));

    teater = stage;

    VBox visning = new VBox();

    avslutt = new Button("Trykk her for aa avslutte");
    avslutt.setFont(new Font(40));
    avslutt.setOnAction(new Lukk());

    visning.getChildren().addAll(topp, bruker, formu, avslutt);

    Scene scene = new Scene(visning, 700, 700);
    stage.setScene(scene);
    stage.setTitle("Resultatet");
    stage.show();

    Traad t = new Traad();
    Thread traad = new Thread(t);
    traad.start();


  }

  class Lukk implements EventHandler<ActionEvent>{

    public void handle(ActionEvent e){
      Platform.exit();
    }

  }

  public void play(){

    System.out.println("Hei og velkommen til Skattekiste Spillet!");
    System.out.println("Reglene er som Folger:");
    System.out.println("1: Du befinner deg i forskjellige steder.");
    System.out.println("2: Hvert sted inneholder en skattekiste.");
    System.out.println("3: Du har en ryggsekk som du tar med fra sted til sted");
    System.out.println("4: Naar du kommer til et sted saa kan du velge om du bil selge");
    System.out.println("eller plukke opp en gjenstand.");
    System.out.println("5: Dersom du selger en gjenstand saa faar du en formue");
    System.out.println("6: Plukker du opp en gjenstand saa minker plassene i sekken din");
    System.out.println("");
    System.out.println("Maalet er aa ha saa hoy formue som mulig.");
    System.out.println("NB! denne versjonen av spillet genererer alt helt tilfeldig.");
    System.out.println("Saa antall gjenstander i kistene og i din sekk vil alltid vaere tilfeldig");
    System.out.println("");
    System.out.println("");
    System.out.println("La oss Begynne!");
    System.out.println("Hvor mange trekk onsker du aa spille med?");

    Scanner skan = new Scanner(System.in);
    int trekk = skan.nextInt();


      Scanner spillere = new Scanner(System.in);
      System.out.println("Hvor mange spillere skal spille?");

      int antall = spillere.nextInt();


        Scanner valg = new Scanner(System.in);
        System.out.println("Vil du spille med veivalg eller vanlig?");

        String choice = valg.nextLine();

        if(choice.toLowerCase().equals("vanlig")){

          System.out.println("Du valgte vanlig modus.");

          Terreng bane = new Terreng("Steder.txt");

          //lager Thread og spillertraad liste for antall spillere
          Thread[] traader = new Thread[antall];
          SpillerTraad[] spilleTrader = new SpillerTraad[antall];

          for(int i=0; i < antall; i++){

            Scanner skan1 = new Scanner(System.in);

            Terminal terminal = new Terminal(skan1);
            //lager en spiller traad og legger det til spiller traad lista
            SpillerTraad st = new SpillerTraad(bane.hentStart(), terminal, "Spiller " + (i+1));
            spilleTrader[i] = st;

          }



          //i while lokka saa lager nye Threads for hver SpillerTraad
          //deretter blir den Thread objektet lagt til i Thread lista

          while(trekk != 0){

            for(int i=0; i < spilleTrader.length; i++){
              Thread denne = new Thread(spilleTrader[i]);
              traader[i] = denne;
            }

            //lager ny for lokke som starter traaden
            //bruker join slik at hver traad avlsutter for den neste starter

            for(Thread trad: traader){
              trad.start();

              try{
                trad.join();
              }catch(InterruptedException e){}



            }
            trekk -= 1;
          }

          System.out.println("Da er alle trekkene ferdig.");
          System.out.println("RESULTAT:");
          System.out.println("");
          for(SpillerTraad s: spilleTrader){

            System.out.println("Spiller navn: " + s.getNavn());
            System.out.println("Formue: " + s.getFormue());

            brukerString += "Spiller navn: " + s.getNavn() + "\n";
            formueString += "" + s.getNavn() + " sin formue: " + s.getFormue() + "\n";

          }



        }

        else{

          System.out.println("Du valgte spill med veivalg");

          VeivalgTerreng bane = new VeivalgTerreng("Steder.txt");
          bane.alternativVei();

          Thread[] traader = new Thread[antall];
          VeivalgTraad[] spilleTrader = new VeivalgTraad[antall];

          for(int i=0; i < antall; i++){

            Scanner skan1 = new Scanner(System.in);

            Terminal terminal = new Terminal(skan1);

            VeivalgTraad vt = new VeivalgTraad(bane.hentRandomStart(), terminal, "Spiller " + (i+1));
            spilleTrader[i] = vt;


          }

          //int trekk = 5;

          while(trekk != 0){

            for(int i=0; i < spilleTrader.length; i++){
              Thread denne = new Thread(spilleTrader[i]);
              traader[i] = denne;
            }

            for(Thread trad: traader){
              trad.start();

              try{
                trad.join();
              }catch(InterruptedException e){}



            }
            trekk -= 1;
          }


          System.out.println("Da er alle trekkene ferdig.");
          System.out.println("RESULTAT:");
          System.out.println("");
          for(VeivalgTraad s: spilleTrader){

            System.out.println("Spiller navn: " + s.getNavn());
            System.out.println("Formue: " + s.getFormue());

            brukerString += "Spiller navn: " + s.getNavn() + "\n";
            formueString += "" + s.getNavn() + " sin formue: " + s.getFormue() + "\n";

          }





        }


  }

  class Traad implements Runnable{

    public void run() throws IllegalThreadStateException{

      try{
        Thread.sleep(5000);
      } catch(Exception e){}

        Platform.exit();

    }

  }

  //lager spiller traad for vanlig spill

  class SpillerTraad implements Runnable{

//lager spiller objekt
    Spiller pl;

    public SpillerTraad(Sted s, Terminal t, String n){
      pl = new Spiller(s, t, n);

    }

//run metoden skriver hvem sin tur det er og gjor et nytt trekk
    public void run(){

      System.out.println("Det er " + pl.hentNavn() + " sin tur.");

      pl.nyttTrekk();


    }

//geter metoder for formue og navn
    public String getNavn(){
      return pl.hentNavn();
    }

    public int getFormue(){
      return pl.hentFormue();
    }

  }

//lager traad for VeivalgSpiller
  class VeivalgTraad implements Runnable{

//lager veivalg objekt
    VeivalgSpiller vpl;

    public VeivalgTraad(VeivalgSted s, Terminal t, String n){

      vpl = new VeivalgSpiller(s, t, n);

    }

//run metoden sier hvem sin tur det er og gjor et nytt trekk
    public void run(){

      System.out.println("Det er " + vpl.hentNavn() + " sin tur.");
      vpl.nyttTrekk();
    }

//geter metoder for aa hente navn og formue slik at man kan printe det ut senere
    public String getNavn(){
      return vpl.hentNavn();
    }

    public int getFormue(){
      return vpl.hentFormue();
    }

  }

  public static void main(String[] args){




    launch(args);


  }

}
