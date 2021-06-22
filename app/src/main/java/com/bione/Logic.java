package com.bione;

import java.util.Arrays;

public class Logic {

    public static void main(String[] arg) {

        int[] array = {10, 20, 30, 40, 50, 60};

//        // Print min and max value in array
//        System.out.println("----" + Arrays.toString(maxMinNumber(array)));
//
//        // Print array in reverse
//        System.out.println("----" + Arrays.toString(reverse(array)));
//
//        // Print array in cyclicRotateByOne
//        System.out.println("----" + Arrays.toString(cyclicRotateByOne(array)));

        // Print array so that move all zero at end
        array = new int[]{1, 0, 2, 0, 3, 2, 0, 0, 2, 3, 4, 5};
        System.out.println("----" + Arrays.toString(moveAllZero(array)));
    }

    /**
     * to find out min and max value in array
     *
     * @param array
     * @return
     */
    public static int[] maxMinNumber(int[] array) {
        int max = array[0];
        int min = array[0];

        for (int i = 0; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            } else if (array[i] < min) {
                min = array[i];
            }
        }

        System.out.println("Max Number is " + max);
        System.out.println("Min Number is " + min);
        int[] newArray = {min, max};
        return newArray;
    }

    /**
     * to reverse the array
     *
     * @param array
     * @return
     */
    public static int[] reverse(int[] array) {

        int a = 0;
        int n = array.length;

        for (int i = 0; i < n / 2; i++) {
            a = array[i];
            array[i] = array[n - i - 1];
            array[n - i - 1] = a;
        }

        return array;
    }

    /**
     * to cyclic rotate the array by one
     */
    public static int[] cyclicRotateByOne(int[] array) {
        int n = array.length;
        int a = array[0];
        // 10,20,30,40,50
        // 50,10,20,30,40
        for (int i = 0; i < n; i++) {
            a = array[i];
            array[i] = array[n - 1];
            array[n - 1] = a;
        }
        return array;
    }

    /**
     * to move all zero at end
     */
    public static int[] moveAllZero(int[] array) {
        int a = 0;
        int count = 0;
        int temp = 0;
//{1, 0, 2, 0, 3, 2, 0, 0, 2, 3, 4, 5};
        for (int i = 0; i < array.length; i++) {
            if ((array[i] != 0)) {
                temp = array[count];
                array[count] = array[i];
                array[i] = temp;
                count = count + 1;
            }
        }
        return array;
    }

}
