package com.example.visualsorter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.Random;

public class MainActivity extends AppCompatActivity
{
    // These define how the list of numbers will be populated
    private final int NUMBER_OF_ELEMENTS = 100;
    private final int MAX_NUMBER = 500;
    private final int MIN_NUMBER = 0;

    // GUI elements and their associated objects
    private ListView sortedLV, unsortedLV;
    private ArrayAdapter<String> sortedAA, unsortedAA;
    private int[] sortedNumbers, unsortedNumbers;
    private String[] sortedStrings, unsortedStrings;

    private Button resetBtn, insertionBtn, mergeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize GUI
        sortedLV = this.findViewById(R.id.sortedLV);
        unsortedLV = this.findViewById(R.id.unsortedLV);

        sortedNumbers = new int[NUMBER_OF_ELEMENTS];
        unsortedNumbers = new int[NUMBER_OF_ELEMENTS];

        sortedStrings = new String[NUMBER_OF_ELEMENTS];
        unsortedStrings = new String[NUMBER_OF_ELEMENTS];

        sortedAA = new ArrayAdapter<String>(this, R.layout.simple_listview_row, sortedStrings);
        unsortedAA = new ArrayAdapter<String>(this, R.layout.simple_listview_row, unsortedStrings);

        sortedLV.setAdapter(sortedAA);
        unsortedLV.setAdapter(unsortedAA);

        // Fill both lists with random numbers and populate the list views
        this.initializeArrays();
    }

    private void insertionSort(int[] input)
    {
        int theFollower, swap;

        for(int currStart = 1; currStart < input.length; currStart++)
        {
            theFollower = currStart;

            while(theFollower > 0 && input[theFollower] < input[theFollower - 1])
            {
                // Keep swapping
                swap = input[theFollower];
                input[theFollower] = input[theFollower - 1];
                input[theFollower - 1] = swap;

                theFollower--;
            }
        }
    }

    public void insertionBtnPressed(View v)
    {
        // Perform an insertion sort on the unsorted array
        this.insertionSort(sortedNumbers);
        this.updateStringArrays();
    }

    private void mergeSort(int[] input)
    {
        if(input.length != 1)
        {
            // "Divide" phase
            int[] leftArray = this.splitArray(input, 0, input.length / 2);
            int[] rightArray = this.splitArray(input, input.length / 2, input.length);

            this.mergeSort(leftArray);
            this.mergeSort(rightArray);

            // "Conquer" phase
            this.merge(input, leftArray, rightArray);
        }
    }

    private void merge(int[] originalArray, int[] leftArray, int[] rightArray)
    {
        // Left counter and right counter (start1/start2 in example given in class)
        // -> used to check to see how far we've gotten in each of the divided arrays
        int lc = 0;
        int rc = 0;

        for(int ac = 0; ac < originalArray.length; ac++)
        {
            // Make sure we are not out of bounds. If we are out of bounds in one half of the
            // -> divided array, take from the half that is not out of bounds. Otherwise perform
            // -> a comparison and put the smaller one in.
            if(lc >= leftArray.length)
            {
                originalArray[ac] = rightArray[rc];
                rc++;
            }
            else if(rc >= rightArray.length)
            {
                originalArray[ac] = leftArray[lc];
                lc++;
            }
            else if(leftArray[lc] <= rightArray[rc])
            {
                originalArray[ac] = leftArray[lc];
                lc++;
            }
            else
            {
                originalArray[ac] = rightArray[rc];
                rc++;
            }
        }
    }

    // Creates a new array from startIndex to endIndex of the input array and returns it
    private int[] splitArray(int[] input, int startIndex, int endIndex)
    {
        int[] output = new int[endIndex - startIndex];

        int oc = 0;
        for (int ic = startIndex; ic < endIndex; ic++)
        {
            output[oc] = input[ic];
            oc++;
        }

        return output;
    }

    public void mergeBtnPressed(View v)
    {
        // Perform a merge sort on the unsorted array
        this.mergeSort(sortedNumbers);
        this.updateStringArrays();
    }

    private void initializeArrays()
    {
        this.fillRandomIntArray(unsortedNumbers, MIN_NUMBER, MAX_NUMBER);
        this.copyContentsOfIntArrays(unsortedNumbers, sortedNumbers);

        this.updateStringArrays();
    }

    public void resetBtnPressed(View v)
    {
        this.initializeArrays();
    }

    // Fills an array with random integers ranging from lowerBound to upperBound
    private void fillRandomIntArray(int[] inputArray, int lowerBound, int upperBound)
    {
        Random r = new Random();

        for(int ac = 0; ac < inputArray.length; ac++)
        {
            inputArray[ac] = lowerBound + r.nextInt(upperBound - lowerBound);
        }
    }

    private void updateStringArrays()
    {
        this.copyIntArrayToStringArray(unsortedNumbers, unsortedStrings);
        this.copyIntArrayToStringArray(sortedNumbers, sortedStrings);

        this.sortedAA.notifyDataSetChanged();
        this.unsortedAA.notifyDataSetChanged();
    }

    private void copyContentsOfIntArrays(int[] source, int[] destination)
    {
        for(int ac = 0; ac < source.length; ac++)
        {
            destination[ac] = source[ac];
        }
    }

    private void copyContentsOfStringArrays(String[] source, String[] destination)
    {
        for(int ac = 0; ac < source.length; ac++)
        {
            destination[ac] = source[ac];
        }
    }

    private void copyIntArrayToStringArray(int[] intArray, String[] strArray)
    {
        for(int ac = 0; ac < intArray.length; ac++)
        {
            strArray[ac] = String.format("%d", intArray[ac]);
        }
    }
}
