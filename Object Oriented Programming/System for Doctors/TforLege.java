class TforLege{

  public static void main(String[] args) throws UlovligUtskrift{

    Pasient me = new Pasient("Amadu", "9876");
    Legemiddel xan = new Vanlig("Molly", 20.0, 40.0);

    Lege du = new Spesialist("Smart", 123);

    System.out.println(du.skrivHvitResept(xan, me, 3));
  }

}
