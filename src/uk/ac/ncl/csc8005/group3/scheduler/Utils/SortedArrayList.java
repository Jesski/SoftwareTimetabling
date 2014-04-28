package uk.ac.ncl.csc8005.group3.scheduler.Utils;
import java.util.*;


/**
 * @author:  Denny S Antony & Luke McMahon 
 * Date: 28/04/2014
 */
public class SortedArrayList<E extends Comparable<? super E>> extends ArrayList<E>{

    /**
     *  Constructor for SortedArrayList, simply called the constructor of ArrayList
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
            int j;

            for (j=size;j>0;j--){
                if((this.get(j-1)).compareTo(element)<0) 
                {
                    // if array is less than element, found pos so break
                    break;
                }
            }
            super.add(j, element);
            passed = true;
        }catch (IndexOutOfBoundsException e){
            passed = false; 
        }
            return passed;
    }
}
