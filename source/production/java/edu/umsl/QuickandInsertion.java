package edu.umsl;
import java.util.InputMismatchException;
import java.util.Scanner;

/* Created By Tyler Ziggas
Date of Creation: October 10, 2020
Date of Submission: October 2020

    This code asks you to choose between 3 different sizes for an array (1000, 10000, 100000)
and then asks which of the 3 types of array you would like (random, sorted, almost sorted)
then finally uses Quicksort with Insertion Sort.
    The four quicksort examples in this project are normal Quicksort, Quicksort that switches to Insertion Sort
when 2% of the array is left to sort, Quicksort with median-of-three partitioning, and Quicksort with
a random pivot.
*/

class QuickandInsertion {
     public static int arrayChoice() { // Choose the size of the array
        int sizeChoice = 0;
        boolean retry = false;

        do {
            do {
                try {
                    retry = false;
                    System.out.println("1) Array of size 1000");
                    System.out.println("2) Array of size 10000");
                    System.out.println("3) Array of size 100000");
                    Scanner input = new Scanner(System.in);
                    sizeChoice = input.nextInt(); // Choosing between the 3 different types
                } catch (InputMismatchException ex) { // Make sure the user doesn't give a character input
                    System.out.println("That is not an integer.");
                    retry = true;
                }
            } while (retry);
        } while (sizeChoice < 0 || sizeChoice > 3); // Keep it within the 3 digits it is allowed to be
        if (sizeChoice == 1) { // After, translate the choice to the size of the array
            sizeChoice = 1000;
        } else if (sizeChoice == 2) {
            sizeChoice = 10000;
        } else if (sizeChoice == 3) {
            sizeChoice = 100000;
        }
        return sizeChoice; // return it to be used as the size
    }

    public static int arrayType() { // Choose the type of array that is created
        int typeChoice = 0;
        boolean retry = false;

        do {
            do {
                try {
                    retry = false;
                    System.out.println("1) Random numbers");
                    System.out.println("2) Sorted numbers");
                    System.out.println("3) Almost sorted numbers, with every 10th number being random");
                    Scanner input = new Scanner(System.in);
                    typeChoice = input.nextInt(); // Input between the 3 different types of arrays
                } catch (InputMismatchException ex) { // Make sure the user doesn't give a character input
                    System.out.println("That is not an integer.");
                    retry = true;
                }
            } while (retry);
        } while (typeChoice < 0 || typeChoice > 3); // Keep it within the 3 digits it is allowed to be
        return typeChoice; // return so the array can be created
    }

    public static void initializeArray(int SIZE, int type, int [] quickSortArray) { // Initializing the array
        final int max = 10000;
        final int min = 1;
        if (type == 1) { // Creation of a random array
            for (int i = 0; i < SIZE; i++) {
                quickSortArray[i] = (int)(Math.random() * (max - min) + min);
            }
        } else if (type == 2) { // Creation of a sorted array
            for (int i = 0; i < SIZE; i++) {
                quickSortArray[i] = i+1;
            }
        } else if (type == 3) { // Creation of a sorted array with every tenth number being random
            for (int i = 0; i < SIZE; i++) {
                if ((i+1) % 10 == 0) {
                    quickSortArray[i] = (int)(Math.random() * (max - min + 1) + min);
                } else {
                    quickSortArray[i] = i+1;
                }
            }
        }
    }

   public static void quickAndInsertion(int[] quickAndInsertionArray, int left, int right, int cutOff) {
        if (left < right) {
            if (right - left <= cutOff) { // Check if the cutoff requirement has been met, insertion sort can be used if it is
                insertionSort(quickAndInsertionArray, left, right);
            } else {
                int split = partitionNormal(quickAndInsertionArray,left,right);
                quickAndInsertion(quickAndInsertionArray,left,split, cutOff);
                quickAndInsertion(quickAndInsertionArray,split+1,right, cutOff);
            }
        }
    }

    public static int partitionNormal(int[] quickSortArray, int left, int right) {
        int pivot = quickSortArray[left]; // Choose the leftmost element
        int i = left - 1;
        int j = right + 1;
        do { // Search for values i and j that are less than or more than to move around the pivot value
            do {
                i++;
            } while (quickSortArray[i] < pivot);
            do {
                j--;
            } while (quickSortArray[j] > pivot);
            swap(quickSortArray, i, j);
        } while (i < j);
    swap(quickSortArray, i, j);
    swap(quickSortArray, left, j);
    return j;
    }

    public static void insertionSort(int [] quickAndInsertionArray, int left, int right) { // Insertion sort
        for (int i = left; i <= right; i++) {
            int value = quickAndInsertionArray[i];
            int j = i;
            while (j > left && quickAndInsertionArray[j - 1] > value) {
                quickAndInsertionArray[j] = quickAndInsertionArray[j - 1];
                j--;
            }
            quickAndInsertionArray[j] = value;
        }
    }

    public static void swap (int[] quickSortArray, int i, int j) { // Swapping function
        int temp = quickSortArray[i];
        quickSortArray[i] = quickSortArray[j];
        quickSortArray[j] = temp;
    }

    public static void main (String args[]) {
        System.out.println("Choose how big the array is and what type of list it is below.");
        int SIZE = arrayChoice(); // Choose size
        int type = arrayType(); // Choose type
        int[] quickSortArray = new int[SIZE];
        initializeArray(SIZE, type, quickSortArray); // Initialize the array with this call
        int left = 0;
        int right = SIZE-1;
        double tempCutOff = quickSortArray.length * 0.02;
        final int cutOff = (int)tempCutOff;

        long insertionStart = System.nanoTime(); // Start timer
        quickAndInsertion(quickSortArray, left, right, cutOff); // The type of quicksort used is called here
        System.out.println("Quicksort with Insertion Time: " + (System.nanoTime() - insertionStart) + "ns");
    }
}
