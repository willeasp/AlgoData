/*
	Author: William Asp
	August 26, 2020

	Reverses a string recursively or iteratively.
	The program takes input from stdin in form of a string,
	and outputs the reversed string to stdout.

	The code is not based on any outside source.
*/

#include <stdio.h>

//TODO change to use structs
char stack[100];	// stack for holding characters
int i;				// index for stack

// push character to stack
void push ( char c ) {
	stack[i++] = c;
}

// pop character from stack
char pop ( void ) {
	return stack[--i];
}

// reverses a string iteratively
void iterativeReverse ( void ) {
	char c;
	do {
    	c = getchar();
    	push(c);
    } while(c != '\n');

    while (i >= 0) {
    	c = pop();
    	putchar(c);
    }
}

// reverses a string recursively
void recursiveReverse ( void ) {
	char c;
	c = getchar();
	if (c != '\n') {
		recursive();
	}
	putchar(c);
	return;
}

//TODO 	make function printout, where a global variable test determines 
//		whether the output prints to stdout or returns as a "string"
// static void test ( void ) {
// 	char* testString = "Hello World!\n";
// 	char* expectedString = "\n!dlroW olleH";
// 	if ((strcmp(iterativeReverse(), expectedString))) {
// 		puts("Iterative reverse failed.");
// 	}
// 	if ((strcmp(recursiveReverse(), expectedString))) {
// 		puts("Recursive reverse failed.")
// 	}
// }

int main (void) {
	puts("Iterative version. Enter your text then press enter");
    iterativeReverse();
    puts("\nRecursive version. Enter your text then press enter");
    recursiveReverse();
    puts("\n")

    return 0;
} 

