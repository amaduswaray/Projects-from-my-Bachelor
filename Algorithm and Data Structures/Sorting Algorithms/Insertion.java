diclass Insertion extends Sorter {

    void sort() {

      int n = A.length;

      for(int i=1; i < n; i++){
        int j = i;

        while(j>0 && gt(A[j-1], A[j])){
          swap(j,j-1);
          j--;
        }

      }

    }

    String algorithmName() {
        return "insertion";
    }

}
