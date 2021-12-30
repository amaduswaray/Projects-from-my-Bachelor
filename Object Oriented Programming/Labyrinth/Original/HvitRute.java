//lager hvit rute
class HvitRute extends Rute{


  public HvitRute(int r, int k){
    super(r,k);
  }

  @Override
  public char tilTegn(){
    return '.';
  }

  public String toString(){
    return ".";
  }




}
