package com.letschat.master;

/**
 * @author jarvis.yuen
 * @version 1.0.0
 * @ClassName CommonTest.java
 * @createTime 2021年01月05日 11:03:00
 */
public class CommonTest {

    private int binarySearch(int[] arr,int target){
        int l=0,r=arr.length-1;
        while (l<=r){
            int mid = l+(r-1)/2;
            if(arr[mid] == target){
                return arr[mid];
            }
            if(arr[mid]>target){
                r = mid-1;
            }else{
                l = mid+1;
            }

        }
        return -1;
    }


    public void algTest(){

    }


}
