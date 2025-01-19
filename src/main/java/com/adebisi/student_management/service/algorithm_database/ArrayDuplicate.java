package com.adebisi.student_management.service.algorithm_database;

import org.springframework.stereotype.Component;

import java.util.Arrays;


@Component
public class ArrayDuplicate {





    public static int[][] removeDuplicate(int[][] array){

         int outterLength = array.length;

         for(int i= 0; i< outterLength; i++){
             int[] inner = array[i];
             int innerLength = inner.length;

             boolean[] checker = new boolean[500001];

             for(int y=0; y< innerLength; y++){

                 if(checker[inner[y]]){

                     inner[y]= 0;
                 }else {

                     checker[inner[y]] =true;
                 }



             }


         }

         return array;

    }

    public static boolean checker(int[] array, int index, int value){

        for(int i =0; i< index; i++){
            if(array[i] == value){
                return true;
            }

        }
      return false;
    }



}
