package search;

import java.util.ArrayList;
import java.util.Random;

public class Main {

  public static void main(String[] args) throws InterruptedException {
    final int ARRAY_SIZE = 50000000;
    Random random = new Random();
    ArrayList<Integer> numbers = new ArrayList<Integer>();
    for (int i=0; i<ARRAY_SIZE; ++i) {
      numbers.add(random.nextInt(ARRAY_SIZE));
    }
//    long time1 = System.currentTimeMillis();
    System.out.println(searchArray(numbers.get(1), numbers));
    System.out.println(searchArray(numbers.get(5), numbers));
    System.out.println(searchArray(numbers.get(900), numbers));
    System.out.println(searchArray(numbers.get(3200), numbers));
    System.out.println(searchArray(numbers.get(7400), numbers));
    System.out.println(searchArray(numbers.get(9876), numbers));
    System.out.println(searchArray(2000000, numbers));
    System.out.println(searchArray(-45, numbers));
//    long time2 = System.currentTimeMillis();
//    System.out.println(time2 - time1);
  }

  private static boolean searchArray(int target, ArrayList<Integer> list) throws InterruptedException {
    // You can replace ThreadedSearch with LinearSearch to see this work with
    // the given linear search code.
    ThreadedSearch<Integer> searcher=new ThreadedSearch<Integer>();
    LinearSearch<Integer> searcher2 = new LinearSearch<>();
    // This specifies 4 threads for the tests. It would be a good idea to play
    // with this and see how that changes things. Keep in mind that your number
    // of threads *may* need to evenly divide the length of the list being
    // searched (ARRAY_SIZE in this case).

      long time1 = System.currentTimeMillis();
      boolean threadedSearcher = searcher.parSearch(1, target,list);

      long time2 = System.currentTimeMillis();
      boolean linearSearcher = searcher2.search(target,list);

      long time3 = System.currentTimeMillis();
      boolean threadedSearcher2 = searcher.parSearch(10, target,list);

      long time4 = System.currentTimeMillis();

      if(threadedSearcher == linearSearcher){
          System.out.println("Linear Time : " + (time3 - time2) + ", Threaded Time 1: " + (time2 - time1) + ", Threaded time 2: " + (time4 - time3));
          return threadedSearcher;
      }
      return false;
  }

}
