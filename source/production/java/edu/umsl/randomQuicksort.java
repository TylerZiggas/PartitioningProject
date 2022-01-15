package edu.umsl;
import java.util.InputMismatchException;
import java.util.Scanner;

/* Created By Tyler Ziggas
Date of Creation: October 10, 2020
Date of Submission: October 2020

    This code asks you to choose between 3 different sizes for an array (1000, 10000, 100000)
and then asks which of the 3 types of array you would like (random, sorted, almost sorted)
then finally uses Quicksort with a random pivot.
    The four quicksort examples in this project are normal Quicksort, Quicksort that switches to Insertion Sort
when 2% of the array is left to sort, Quicksort with median-of-three partitioning, and Quicksort with
a random pivot.
*/

public class randomQuicksort {
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

    public static void quickRandomPivot(int[] quickRandomPivotArray, int left, int right) {
        if (left < right) { // Checking if we need to do more quicksort
           int pivot = randomPartition(quickRandomPivotArray,left,right); // grabbing another random pivot value and partitioning
            quickRandomPivot(quickRandomPivotArray,left,pivot);
            quickRandomPivot(quickRandomPivotArray,pivot+1,right);
        }
    }

    public static int partition(int[] quickRandomPivotArray, int left, int right) { // Hoare's Partitioning with that random element
        int pivot = quickRandomPivotArray[left]; // Choose the leftmost elment (which is now random)
        int i = left - 1;
        int j = right + 1;
        do { // Search for values i and j that are less than or more than to move around the pivot value
            do {
                i++;
            } while (quickRandomPivotArray[i] < pivot);
            do {
                j--;
            } while (quickRandomPivotArray[j] > pivot);
            swap(quickRandomPivotArray, i, j);
        } while (i < j);
        swap(quickRandomPivotArray, i, j);
        swap(quickRandomPivotArray, left, j);
        return j;
    }

    public static int randomPartition(int[] quickRandomPivotArray, int left, int right) { // Getting a random value for the pivot
        int pivot = (int)(Math.random() * (right - left) + left);
        swap(quickRandomPivotArray, pivot, left); // Swapping the random element with the leftmost element
        return partition(quickRandomPivotArray, left, right);
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
        int right = SIZE-1;

        long randomStart = System.nanoTime(); // Start timer
        quickRandomPivot(quickSortArray, 0, right); // The type of quicksort used is called here
        System.out.println("Random Pivot Time: " + (System.nanoTime() - randomStart) + "ns");
    }
}
