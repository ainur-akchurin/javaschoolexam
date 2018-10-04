package com.tsystems.javaschool.tasks.subsequence;

import java.util.Iterator;
import java.util.List;

public class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")
    public boolean find(List x, List y) {
        validate(x,y);
        return isPossible(x,y);
    }

    /**
     *
     * @param x - first sequence
     * @param y - second sequence
     * @return true - if after all operations list x equals list y, otherwise false
     */
    private static boolean isPossible(List x, List y){

        if(y.size()<x.size())
            return false;

        Iterator iteratorY =y.iterator();

        for (Object objectX : x){
            while (iteratorY.hasNext() && !objectX.equals(iteratorY.next()))
                iteratorY.remove();
        }
        while (iteratorY.hasNext()){
            iteratorY.next();
            iteratorY.remove();}

        return x.equals(y);
    }

    /**
     * Checking reference for null.
     * If one of the parameters is null, IllegalArgumentException will be thrown.
     *
     * @param x - first sequence
     * @param y - second sequence
     */
    private void validate(List x, List y){
        if(x==null||y==null)
            throw new IllegalArgumentException();
    }
}
