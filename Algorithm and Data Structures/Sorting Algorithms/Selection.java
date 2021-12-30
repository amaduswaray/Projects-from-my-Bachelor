class Selection extends Sorter{

  void sort(){

    int n = A.length;

    for(int i=0; i<n; i++){
      int k = i;

      for(int j=k+1; j<n; j++){

        if(lt(A[j], A[k])){
          k = j;
        }
      }

      if(!(eq(i,k))){
        swap(i,k);
      }

    }


  }


  String algorithmName() {
      return "selection";
  }



}
