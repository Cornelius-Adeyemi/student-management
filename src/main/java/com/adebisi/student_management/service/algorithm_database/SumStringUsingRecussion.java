package com.adebisi.student_management.service.algorithm_database;

public class SumStringUsingRecussion {




    public static int sumOfDigits(String input, int index) {

        if(input.isEmpty()){
            return 0;
        }

        // Base case: If index is out of bounds, return 0
        if (index >= input.length()) {
            return 0;
        }
        char ch = input.charAt(index);
        int intvalue = (int) ch;


        int currentDigit ;
        if(intvalue < 48 || intvalue > 57){
            currentDigit = 0;
        }else{
            currentDigit =  input.charAt(index) - '0';
        }

        // Convert the current character to an integer
       ;

        return currentDigit + sumOfDigits(input, index + 1);
    }
}
