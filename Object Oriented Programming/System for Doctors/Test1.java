class Test1{

  public static void main(String[] args){


  /*  Lege meg = new Lege("Amadu");
    Lege meg2 = new Spesialist("David", 10290);


    Legemiddel v = new Vanlig("Prevansjon", 200.0, 0.20);
    Legemiddel n = new Narkotisk("Xan", 400.0, 0.90, 5);
    Legemiddel vd = new Vanedannede("Molly", 150.0, 0.70, 10);
    Legemiddel nar = new Narkotisk("Perc", 250.0, 1.0, 7);



    Resepter m = new Milliterresepter(n, meg, 1,5);
    Resepter p = new P(v, meg, 2);
    Resepter b = new Blaa(vd, meg2, 3,8);
    Resepter h = new Hvite(nar,meg2, 4,3);


    Pasient david = new Pasient("Ama", "12345678");

    david.leggTil(m);
    david.leggTil(p);
    david.leggTil(b);
    david.leggTil(h);

    for(int i = 0; i < david.hentResepter().length; i++){
    System.out.println(david.hentResepter()[i]);*/


    Lenkeliste<String> daniel = new Lenkeliste<String>();

    daniel.leggTil("Hei");
    daniel.leggTil("Hade");
    daniel.leggTil("Nei");
    daniel.leggTil("Taper");
    daniel.leggTil("L");

    for(String d: daniel){
      System.out.println(d);
    }

  }

  /*  System.out.println(meg);
    System.out.println(meg2);

    System.out.println(" ");


    System.out.println(v);
    System.out.println(n);
    System.out.println(vd);
    System.out.println(nar);

    System.out.println(" ");


    System.out.println(m);
        System.out.println(" ");
    System.out.println(p);
        System.out.println(" ");
    System.out.println(b);
        System.out.println(" ");
    System.out.println(h);*/




  }
