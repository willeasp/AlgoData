/*
    Assignment 4

    Author: William Asp
    September 15, 2020

    **What it is**
        a function in C which takes an array of integers 
        (both positive and negative) and orders the elements 
        in the array so that all negative elements come before 
        the positive.

    **How it works**
        The program will prompt for the number of inputs to add,
        then it will prompt for the integers to sort.
        The program will then move all negative numbers so that
        all negative numbers comes before the positive and 
        print the result.

    **Testing**
        Run file with -t as argument

*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// swaps the elements at index i and j in array a
void swap ( int a[], int i, int j ) {
    int tmp = a[i];
    a[i] = a[j];
    a[j] = tmp;
}

// prints the array a with negative elements before positive
// 
// if a positive integer comes before a negative integer, swap them.
void negativeBeforePositiveSort ( int a[], int size ) {
    for(int i = 0; i < size; i++) {
        if (a[i] < 0) {
            continue;
        }
        for(int j = i+1; j < size; j++) {
             if (a[j] < 0) {
                 swap(a, i, j);
                 break;
             }
        }
    }
}

static void test ( void ) {
    int a[] = {-2, 5, -7, 4, -11, 3, 6, -8, -9, -1};
    negativeBeforePositiveSort(a, 10);
    for(int i = 0; i < 10; i++) {
        if (a[i] > 0 && a[i + 1] < 0) {
            printf("Test failed.");
            exit(0);
        }
    }
    printf("Test successful.");
}

int main( int argc, char* argv[] ) {
    if (argc > 1) {
        if (strcmp(argv[1], "-t") == 0) {
            test();
            exit(0);
        }
    }
    // prompt user
    printf("How many numbers do you want to sort?\n");
    int N;
    scanf("%d", &N);
    int a[N];

    // collect array
    printf("Enter %d integers, both negative and positive.\n", N);
    for(int i = 0; i < N; i++) {
        scanf("%d", &a[i]);
    }
    int size = sizeof(a)/sizeof(int);

    // sort
    negativeBeforePositiveSort(a, size);

    // print sorted
    for(int i = 0; i < size; i++) {
        printf("%d ", a[i]);
    }
        
    printf("\n");
    return 0;
}