package search;

import java.util.ArrayList;

public class LinearSearch<T> {
    
    public boolean search(T target, ArrayList<T> list) {
        for(int i=0; i<list.size(); i++){
            if(list.get(i).equals(target)) {
//                System.out.println("linear found at " + i);
                return true;
            }
        }
        return false;
    }
}
