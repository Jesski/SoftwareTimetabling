package uk.ac.ncl.csc8005.group3.scheduler.Utils;
import java.util.*;


/**
 * @author:  Denny S Antony & Luke McMahon 
 * Date: 28/04/2014
 */
public class SortedArrayList<E extends Comparable<? super E>> extends ArrayList<E>{

    /**
     *  Constructor for SortedArrayList.
     *  Calls constructor of Arraylist.
     */
    public SortedArrayList(){
        super();
    }

    /**
     * Overrides the add method of ArryList, sorting the array to find the position of each element before the element is added
     * 
     * @return boolean, true if added successfully
     * @return boolean, false if not added successfully
     * 
     */
    public boolean add(E element){
        boolean passed = false;
        try{
            int size = this.size();
            int count;

            for (count=size;count>0;count--){
                if((this.get(count-1)).compareTo(element)<0) 
                {
                    // if array is less than element, found position so break
                    break;
                }
            }
            super.add(count, element);
            passed = true;
        }catch (IndexOutOfBoundsException e){
            passed = false; 
        }
            return passed;
    }
}
