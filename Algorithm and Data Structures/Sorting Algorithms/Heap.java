class Heap extends Sorter {


  void sort() {
    buildMaxHeap(A,A.length);

    int n = A.length;
    for (int i=n-1; i>=0; i--) {
      swap(0,i);
      bubbleDown(A,0,i);
    }



  }






  private  void buildMaxHeap(int[] arr, int n) {

    for (int i=n/2; i>=0; i--) {
      bubbleDown(arr,i,n);
    }
  }

  private  void bubbleDown(int[] a, int i, int n ) {
    int largest = i;
    int left = (2*i) +1;
    int right = (2*i) +2;

    if (lt(left,n) && lt(a[largest],a[left])) {
      //swap(left,largest);
      int tmp = left;
      left = largest;
      largest = tmp;
      swaps++;
      //we dont use the swap method from sorter
      //because it swaps the placec in the Array
      //here we only want to swap the variabel
      //poting at each other
    }

    if (lt(right,n) &&  lt(a[largest],a[right])) {
      //swap(right,largest);
      int tmp = right;
      right = largest;
      largest = tmp;
      swaps++;
    }

    if (!eq(largest,i)) {

      swap(i,largest);

      bubbleDown(a,largest,n);
    }
  }


  String algorithmName() {
      return "heap";
  }
}
