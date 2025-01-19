package com.adebisi.student_management.service.algorithm_database;


import org.springframework.stereotype.Component;

@Component
public class ConvertTime {






    public static void convertTime(int h, int m){
     try {

         if (h <= 0 || m < 0) { // Check if the value provided is lower than

             throw new RuntimeException("Time values should be greater than 0");
         }

         String nums[] = { "zero", "one", "two", "three", "four",
                 "five", "six", "seven", "eight", "nine",
                 "ten", "eleven", "twelve", "thirteen",
                 "fourteen", "fifteen", "sixteen", "seventeen",
                 "eighteen", "nineteen", "twenty", "twenty one",
                 "twenty two", "twenty three", "twenty four",
                 "twenty five", "twenty six", "twenty seven",
                 "twenty eight", "twenty nine",
         };
         if(m != 0) {
             m = m % 60;

             h = m > 0 ? h : h == 12 ? 1 : h + 1;
         }

         if(m == 0){
             System.out.println(nums[h] + " o' clock ");

         } else if(m < 30 ){
            if(m== 15 ){

                System.out.println("quarter past " + nums[h]);
            }else{

                String minutes = m == 1 ? "minute" : "minutes";

                System.out.println( nums[m] + " " +  minutes    + " past " +
                        nums[h]);
            }

         }else if( m == 30){

             System.out.println("half past " + nums[h]);
         }else if(m > 30){

            if (m == 45 ){

                System.out.println("quarter to " +
                        nums[(h % 12) + 1]);
            }else {

                String minutes  = (60 - m) == 1 ? "minute" : "minutes";

                System.out.println( nums[60 - m] + " " + minutes + " to " +
                        nums[(h % 12) + 1]);
            }


         }



     }catch (Exception e){
        throw e;
     }

    }
}
