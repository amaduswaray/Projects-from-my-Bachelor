class Quick extends Sorter {


  int low =0;
  int high;
    void sort() {
        high = A.length-1;

        // Do insertion sort here. Use the Sorter's comparison- and swap
        // methods for automatically counting the swaps and comparisons.
        //
        // The object has a field A and n, where A is to be sorted and n is its
        // length.
        //
        // Alternatively, you can "manually" increment the fields `comparisons'
        // and `swaps' at your own leisure.
        quickSort(A,low, high);



    }

    private void quickSort(int[] a, int low, int high) {
        if (low>=high) {
        return;
      }

      int p = partition(a,low,high);
      quickSort(a,low, p-1);
      quickSort(a,p+1,high);
      return;


    }

    private  int partition(int[] a, int low, int high) {

    int pivot = a[high]; //tallet på indeks high deler på 2 setter vi til pivot (optional)


    int left = low;
    int right = high-1;


    while(leq(left,right)) {

      while(leq(left,right) && leq(a[left], pivot)) {
        left ++; //vi har noe som er mindre så vi skal ikkke bytte, vi går videre
      }
      while (geq(right,left) && geq(a[right],pivot)) {
        right --; //vi har ikke funnet noe som er større, vi  går videre
      }

      if (lt(left,right)) { //her har vi nå funnet elementer som ikke er på rikgit plass
        swap(left,right);

      }
    }

     swap(left,high);

    return left;



  }


    String algorithmName() {
        return "quick";
    }
}
