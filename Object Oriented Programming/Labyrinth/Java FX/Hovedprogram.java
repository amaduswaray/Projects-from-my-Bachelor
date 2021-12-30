//importerer alle verktoyene som trengs
import javafx.application.Application;
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


//lager applikasjon klassen

public class Hovedprogram extends Application {

//lager alle instansvariablene som jeg skal bruke i programmet
  Labyrint labyrint = null;
  int antRad;
  int antKol;

  //utveitall er brukt som indeks for lenkelista
  int utveiTall = 0;
  MinKnapp gjeldendeKnapp;

//textene i programmet
  Text topp;
  Text info;

  File fil;
  FileChooser brukerfil;

  Stage teater;
  Button knapp;
  Button nullStill;
  GridPane pane;

  Button forrigeUtvei;
  Button nesteUtvei;

  MinKnapp[][] ruteKnapper;

  public void start(Stage stage){

    //lager tittel
      topp = new Text("Dette er Labyrinter. Velg en labyrint og se utveiene.");
      topp.setY(40);
      topp.setFont(new Font(40));

//lager tekst som skal vise hvor mange losninger det er
      info = new Text("");
      info.setFont(new Font(25));

//setter stagen fra instansvariablen lik til den i parameteren
      teater = stage;

      //lager en vbox s
      VBox rootPane = new VBox();
      Font font = new Font(30);

//lager alle knappene som brukeren trenger for aa navigere seg rundt
      knapp = new Button("Trykk her for aa velge Fil");
      knapp.setFont(font);
      knapp.setOnAction(new AapneFil());

      nullStill = new Button("Trykk her for aa nullstille Labyrinten");
      nullStill.setFont(font);
      nullStill.setOnAction(new Fjern());
      nullStill.setDisable(true);

      forrigeUtvei = new Button("Forrige Utvei");
      forrigeUtvei.setFont(font);
      forrigeUtvei.setOnAction(new visForrige());
      forrigeUtvei.setDisable(true);


      nesteUtvei = new Button("Neste Utvei");
      nesteUtvei.setFont(font);
      nesteUtvei.setOnAction(new visNeste());
      nesteUtvei.setDisable(true);

//definereer grid pane, som skal brukes for aa lage labyrinten
      pane = new GridPane();


//setter alle objektene i vboksen
      rootPane.getChildren().addAll(topp, info, knapp, nullStill, pane, nesteUtvei, forrigeUtvei);

//setter stagen
      Scene scene = new Scene(rootPane, 700, 700);
      stage.setScene(scene);
      stage.setTitle("Velkommen til Labyrinter");
      stage.show();
  }

//lager metode som lager labyrint
  public void settLabyrint(){

//leser fra fil som bruker har valgt
      try{
        Labyrint nyLab = Labyrint.lesFraFil(fil);
          labyrint = nyLab;
      }
      catch(Exception e){
          System.out.println("Feil");
      }

//bruker lab fra labyrint for aa faa en nostet liste
      antRad = labyrint.hentRader();
      antKol = labyrint.hentKolonner();
      Rute[][] labRuter = labyrint.hentLab();
      ruteKnapper = new MinKnapp[antRad][antKol];

      for(int i = 0; i < antRad; i++){
         for(int j = 0; j < antKol; j++){
//lager svarte ruter av SortRute
            if(labRuter[i][j] instanceof SortRute){
                Button sort = new Button();
                sort.setPrefWidth(40);
                sort.setPrefHeight(40);
                sort.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-background-color: black;");
                pane.add(sort,j,i);
            }
            else{
              //lager hvite ruter av HvitRute
                MinKnapp hvit = new MinKnapp();
                ruteKnapper[i][j] = hvit;
                hvit.settKord(i,j);
                hvit.setPrefWidth(40);
                hvit.setPrefHeight(40);
                hvit.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-background-color: white;");
                MinKnappBehandler behandler = new MinKnappBehandler();
                hvit.setOnAction(behandler);
                pane.add(hvit,j,i);
            }
         }
      }
//lar brukeren trykke nullstill knappen etter at labyrinten er lkaget
      nullStill.setDisable(false);

  }
//lager metode som  nullstiller hele programmet
  public void nullStillLab(){
    pane.getChildren().clear();
    nesteUtvei.setDisable(true);
    forrigeUtvei.setDisable(true);
    utveiTall = 0;
    info.setText("");
  }

//lager aapne fil metode som lar bruker velge fil.
  class AapneFil implements EventHandler<ActionEvent>{

      public void handle(ActionEvent e){
          brukerfil = new FileChooser();
          File nyFil = brukerfil.showOpenDialog(teater);
          fil = nyFil;
          if(fil != null){
              settLabyrint();
          }
      }
  }

//lager en event handler klasse som kaller paa nullstill metoden.
//den nullstiller alt
  class Fjern implements EventHandler<ActionEvent>{

    public void handle(ActionEvent e){
      nullStillLab();
      nullStill.setDisable(true);
    }
  }

//lager en knapp subklasse som har metoder slik at jeg kan hente egenskapene
  class MinKnapp extends Button{

      int rad;
      int kolonne;

      private String farge = "hvit";

      public String hentFarge(){
          return farge;
      }

      public void settFarge(String f){
          farge = f;
      }

      public void settKord(int r, int k){
          rad = r;
          kolonne = k;
      }
      public int hentRad(){
          return rad;
      }
      public int hentKolonne(){
          return kolonne;
      }
  }



//event handler som finner utvei fra den ruten brukeren valgte
  class MinKnappBehandler implements EventHandler<ActionEvent>{

      public void handle(ActionEvent e){

//begynner med aa gjore alle hvite ruter hvit, slik at gamle veier ikke blir staaende

        for(int i=0; i < ruteKnapper.length; i++){
          for(int j=0; j < ruteKnapper[i].length; j++){
            if(ruteKnapper[i][j] instanceof MinKnapp){

              ruteKnapper[i][j].setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-background-color: white;");
              ruteKnapper[i][j].settFarge("hvit");
            }

          }
        }

//Definerer knappen som den som ble trykket paa
          MinKnapp ruteKnapp = (MinKnapp) e.getSource();
          gjeldendeKnapp = ruteKnapp;
          String farge = ruteKnapp.hentFarge();
          int antTrue = 0;
          int teller = 0;
          utveiTall = 0;

//hvis fargen er hvit saa skal en utvei bli funnet
          if(farge.equals("hvit")){

//kaller paa labyrint metoden finn utveifra, med kordinatene til knappen.
              Lenkeliste<String> utveier = labyrint.finnUtveiFra(ruteKnapp.hentKolonne(), ruteKnapp.hentRad());
              boolean[][] ruterMedUtvei = null;

//endrer info tekst slik at den viser hvor mange aapninger den posisjonen kan naa
              info.setText("Det er " + labyrint.herfra + " utvei(er) fra denne posisjonen");

              //gir ruterMedUtvei verdien til string til tabell metoden, der jeg sender inn forste stringen i utveilista
              ruterMedUtvei = losningStringTilTabell(utveier.hent(utveiTall), antRad, antKol);

//lager to forlokker
//Denne er for aa finne antall posisjoner som har en utvei
              for(int i = 0; i < ruterMedUtvei.length; i++){
                  for(int j = 0; j < ruterMedUtvei[i].length; j++){
                      if(ruterMedUtvei[i][j] == true){
                          antTrue++;
                      }
                  }
              }

              String[] trueKordinater = new String[antTrue];
//denne for lokka er for aa finne koordinatene til de rutene som har utvei
//setter ogsaa de kordinatgene i en liste
              for(int i = 0; i < ruterMedUtvei.length; i++){
                  for(int j = 0; j < ruterMedUtvei[i].length; j++){
                      if(ruterMedUtvei[i][j] == true){
                          String kordinat = j + "," + i;
                          trueKordinater[teller] = kordinat;
                          teller++;
                      }
                  }
              }
//gaar gjennom kordinat lista og splitter det og setter de rutene med e kordinatene til en gronn farge.
              for(String kord: trueKordinater){
                  String[] linje = kord.split(",");
                  int raden = Integer.parseInt(linje[1]);
                  int kolonnen = Integer.parseInt(linje[0]);
                  ruteKnapper[raden][kolonnen].setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-background-color: green;");
                  ruteKnapper[raden][kolonnen].settFarge("grønn");
              }
//lyser opp neste utvei knappen dersom det er flere utveier
              if((labyrint.hentAap() - 1) > 0){
                nesteUtvei.setDisable(false);
              }





          }
      }
  }

//viste ikke om man kunne bare kalle paa MinKnappBehandler metoden paa en knapp, siden det er en event hanlder metode
//saa denne metoden gjor det samme som MinKnappBehandler, men indeksen til lenkelista er okt, slit at neste utvei blir vist.
  class visForrige implements EventHandler<ActionEvent>{

    public void handle(ActionEvent e){

      for(int i=0; i < ruteKnapper.length; i++){
        for(int j=0; j < ruteKnapper[i].length; j++){
          if(ruteKnapper[i][j] instanceof MinKnapp){

            ruteKnapper[i][j].setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-background-color: white;");
            ruteKnapper[i][j].settFarge("hvit");
          }

        }
      }

      utveiTall -=1;


      //gjeldendeKnapp = ruteKnapp;
      String farge = gjeldendeKnapp.hentFarge();
      int antTrue = 0;
      int teller = 0;


      if(farge.equals("hvit")){

          Lenkeliste<String> utveier = labyrint.finnUtveiFra(gjeldendeKnapp.hentKolonne(), gjeldendeKnapp.hentRad());
          boolean[][] ruterMedUtvei = null;


          ruterMedUtvei = losningStringTilTabell(utveier.hent(utveiTall), antRad, antKol);


          for(int i = 0; i < ruterMedUtvei.length; i++){
              for(int j = 0; j < ruterMedUtvei[i].length; j++){
                  if(ruterMedUtvei[i][j] == true){
                      antTrue++;
                  }
              }
          }

          String[] trueKordinater = new String[antTrue];

          for(int i = 0; i < ruterMedUtvei.length; i++){
              for(int j = 0; j < ruterMedUtvei[i].length; j++){
                  if(ruterMedUtvei[i][j] == true){
                      String kordinat = j + "," + i;
                      trueKordinater[teller] = kordinat;
                      teller++;
                  }
              }
          }

          for(String kord: trueKordinater){
              String[] linje = kord.split(",");
              int raden = Integer.parseInt(linje[1]);
              int kolonnen = Integer.parseInt(linje[0]);
              ruteKnapper[raden][kolonnen].setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-background-color: green;");
              ruteKnapper[raden][kolonnen].settFarge("grønn");
          }


          if(utveiTall == labyrint.hentAap() - 1){
            nesteUtvei.setDisable(true);
            forrigeUtvei.setDisable(false);
          }
          else if((utveiTall < labyrint.hentAap() -1) && (utveiTall > 0)){
            nesteUtvei.setDisable(false);
            forrigeUtvei.setDisable(false);
          }
          else{
            nesteUtvei.setDisable(false);
            forrigeUtvei.setDisable(true);
          }




        }
      }


  }

//samme som visForrige, bare at indeksen til lenkelista er minket.
  class visNeste implements EventHandler<ActionEvent>{

    public void handle(ActionEvent e){

      for(int i=0; i < ruteKnapper.length; i++){
        for(int j=0; j < ruteKnapper[i].length; j++){
          if(ruteKnapper[i][j] instanceof MinKnapp){

            ruteKnapper[i][j].setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-background-color: white;");
            ruteKnapper[i][j].settFarge("hvit");
          }

        }
      }

      utveiTall +=1;


      //gjeldendeKnapp = ruteKnapp;
      String farge = gjeldendeKnapp.hentFarge();
      int antTrue = 0;
      int teller = 0;


      if(farge.equals("hvit")){

          Lenkeliste<String> utveier = labyrint.finnUtveiFra(gjeldendeKnapp.hentKolonne(), gjeldendeKnapp.hentRad());
          boolean[][] ruterMedUtvei = null;


          ruterMedUtvei = losningStringTilTabell(utveier.hent(utveiTall), antRad, antKol);


          for(int i = 0; i < ruterMedUtvei.length; i++){
              for(int j = 0; j < ruterMedUtvei[i].length; j++){
                  if(ruterMedUtvei[i][j] == true){
                      antTrue++;
                  }
              }
          }

          String[] trueKordinater = new String[antTrue];

          for(int i = 0; i < ruterMedUtvei.length; i++){
              for(int j = 0; j < ruterMedUtvei[i].length; j++){
                  if(ruterMedUtvei[i][j] == true){
                      String kordinat = j + "," + i;
                      trueKordinater[teller] = kordinat;
                      teller++;
                  }
              }
          }

          for(String kord: trueKordinater){
              String[] linje = kord.split(",");
              int raden = Integer.parseInt(linje[1]);
              int kolonnen = Integer.parseInt(linje[0]);
              ruteKnapper[raden][kolonnen].setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-background-color: green;");
              ruteKnapper[raden][kolonnen].settFarge("grønn");
          }

          if(utveiTall == labyrint.hentAap() - 1){
            nesteUtvei.setDisable(true);
            forrigeUtvei.setDisable(false);
          }
          else if((utveiTall < labyrint.hentAap() -1) && (utveiTall > 0)){
            nesteUtvei.setDisable(false);
            forrigeUtvei.setDisable(false);
          }
          else{
            nesteUtvei.setDisable(false);
            forrigeUtvei.setDisable(true);
          }





        }
      }


  }

//string til tabell metoden som er oppgitt i oppgaven.
  static boolean[][] losningStringTilTabell(String losningString, int bredde, int hoyde) {
      boolean[][] losning = new boolean[hoyde][bredde];
      java.util.regex.Pattern p = java.util.regex.Pattern.compile("\\(([0-9]+),([0-9]+)\\)");
      java.util.regex.Matcher m = p.matcher(losningString.replaceAll("\\s",""));
      while (m.find()) {
          int x = Integer.parseInt(m.group(1));
          int y = Integer.parseInt(m.group(2));
          losning[y][x] = true;
      }
      return losning;
  }

//launcher programmet.
  public static void main(String[] args){
      launch(args);
  }


}
