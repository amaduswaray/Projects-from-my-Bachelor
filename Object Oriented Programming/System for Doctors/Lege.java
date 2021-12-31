class Lege implements Comparable<Lege>{

    protected String navn;
    protected Lenkeliste<Resepter> utskrevendeResepter = new Lenkeliste<Resepter>();

    public Lege(String n){  //kontruktøren til Lege (tar inn et navn)
        navn = n;   //setter navnet lik en variabel navn som er deklarert
    }
    public String hentNavn(){ //henter lege navnet
        return navn;
    }
    @Override   //overrider toString
    public String toString(){
        return "Navn: " + navn;
    }

    public Lenkeliste<Resepter> hentUtskrevedeResepter() {
        return utskrevendeResepter;
    }
    public int compareTo(Lege annen){
        String l1 = navn;
        String l2 = annen.hentNavn();
        return l1.compareTo(l2);
    }

    public Hvite skrivHvitResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift {

        Hvite h1 = new Hvite(legemiddel, this,  pasient, reit);
        utskrevendeResepter.leggTil(h1);
        pasient.leggTil(h1);
        return h1;

    }

    public P skrivPResept(Legemiddel legemiddel, Pasient pasient) throws UlovligUtskrift {

        P p1 = new P(legemiddel, this, pasient);
        utskrevendeResepter.leggTil(p1);
        pasient.leggTil(p1);
        return p1;


    }

    public Milliterresepter skrivMilliterResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift{

        Milliterresepter m1 = new Milliterresepter(legemiddel, this, pasient, reit);
        utskrevendeResepter.leggTil(m1);
        pasient.leggTil(m1);
        return m1;

    }


    public Blaa skrivBlaaResept(Legemiddel legemiddel, Pasient pasient, int reit) throws UlovligUtskrift {

        Blaa b1 = new Blaa(legemiddel, this, pasient, reit);
        utskrevendeResepter.leggTil(b1);
        pasient.leggTil(b1);
        return b1;
      
    }

}
