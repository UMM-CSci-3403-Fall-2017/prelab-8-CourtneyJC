package search;

import java.util.ArrayList;

public class ThreadedSearch<T> implements Runnable {

  private T target;
  private ArrayList<T> list;
  private int begin;
  private int end;
  private Answer answer;

  public ThreadedSearch() {

  }

  private ThreadedSearch(T target, ArrayList<T> list, int begin, int end, Answer answer) {
    this.target=target;
    this.list=list;
    this.begin=begin;
    this.end=end;
    this.answer=answer;
  }

  /**
  * Searches `list` in parallel using `numThreads` threads.
  *
  * You can assume that the list size is divisible by `numThreads`
  */
  public boolean parSearch(int numThreads, T target, ArrayList<T> list) throws InterruptedException {
    /*
    * First construct an instance of the `Answer` inner class. This will
    * be how the threads you're about to create will "communicate". They
    * will all have access to this one shared instance of `Answer`, where
    * they can update the `answer` field inside that instance.
    *
    * Then construct `numThreads` instances of this class (`ThreadedSearch`)
    * using the 5 argument constructor for the class. You'll hand each of
    * them the same `target`, `list`, and `answer`. What will be different
    * about each instance is their `begin` and `end` values, which you'll
    * use to give each instance the "job" of searching a different segment
    * of the list. If, for example, the list has length 100 and you have
    * 4 threads, you would give the four threads the ranges [0, 25), [25, 50),
    * [50, 75), and [75, 100) as their sections to search.
    *
    * You then construct `numThreads`, each of which is given a different
    * instance of this class as its `Runnable`. Then start each of those
    * threads, wait for them to all terminate, and then return the answer
    * in the shared `Answer` instance.
    */
    Thread[] threads = new Thread[numThreads];
    Answer answer = new Answer();
    int first = 0;
    int gapSize = list.size() / numThreads;
    for (int i = 0; i < numThreads; i++){
      threads[i] = new Thread(new ThreadedSearch<T>(target,list,first, first + gapSize, answer));
      threads[i].start();
      first = first + gapSize;
    }
    for(int i = 0; i < numThreads; ++i){
      threads[i].join();
    }
    System.out.println("threaded searched through " + answer.getCount() + " elements");
    return answer.getAnswer();
  }

  public void run() {
    int searched = 0;
    // for loop runs through the elements looking for a match
    for(int i = this.begin; i < this.end; i++){
      searched++; // I'm keeping track of how many elements it looks at
      // if answer is already true, break
      if(this.answer.getAnswer() == true){
          break;
      }
      // if it finds a match, set answer to true and break
      if (this.list.get(i).equals(this.target)) {
              this.answer.setAnswer(true);
              this.answer.incCount(searched);
              break;
      }
    }
    // answer keeps track of how many elements were looked at, so I know for each search, not each thread
    this.answer.incCount(searched);
  }

  private class Answer {
    private boolean answer = false;
    private int counted = 0;

    public boolean getAnswer() {
      return answer;
    }
    public int getCount() { return counted;}

    // This has to be synchronized to ensure that no two threads modify
    // this at the same time, possibly causing race conditions.
    public synchronized void setAnswer(boolean newAnswer) {
      answer = newAnswer;
    }
    public synchronized void incCount(int searched) { counted = counted + searched;}
  }

}
