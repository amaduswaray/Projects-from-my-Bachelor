class Quick1 extends Sorter {

  int low = 0;
  int high;


    void sort() {

      high = A.length - 1;
      quicksort(A, low, high);

    }

    String algorithmName() {
        return "quick";
    }

    public void quicksort(int[] a, int l, int h){

      if (l >= h){
        return;
      }

      int p = partition(a, l, h);
      quicksort(a, l, p-1);
      quicksort(a, p+1, h);
      return;
    }

    /*public int chosePivot(int[] a, int l, int h){
      int midt = (l+h)/2;

      return midt;
    }*/

    public int partition(int[] a, int l, int h){

      //int p = chosePivot(a, l, h);

      //swap(p, h);

      //a[p] = a[high];
      //a[high] = a[p];

      int pivot = a[h];

      int left = l;
      int right = h-1;

      while(leq(left, right)){

        while(leq(left, right) && leq(a[left], pivot)){
          left+=1;
        }

        while(geq(right, left) && leq(a[right], pivot)){
          right-=1;
        }

        if(lt(left, right)){

          swap(left, right);
        }


      }

      swap(left, right);

      return left;

    }
}
