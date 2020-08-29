/*
	Author: William Asp
	August 26, 2020
	Last updated:
		August 28, 2020
			- Integrated "stack" to iterativeReverse function. This was done to not keep a global variable
			  which can be accessed by the entire program. 

	Reverses a string recursively or iteratively.

	The functions takes input from stdin in form of a character array,
	and outputs the reversed character array to stdout.

	The code is not based on any outside source.
*/

#include <stdio.h>
#include <string.h>
#include <stdbool.h>


// reverses a string iteratively
void iterativeReverse ( void ) {
	char stack[100], c;
	int index = 0;

	while((c = getchar()) != '\n') {
    	stack[index++] = c;
    }

    while (index > 0) {
    	c = stack[--index];
    	putchar(c);
    }
}

// reverses a string recursively
void recursiveReverse ( void ) {
	char c;
	c = getchar();
	if (c != '\n') {
		recursiveReverse();
		putchar(c);
	}	
	return;
}

// gets string from file
static void getString( char* dest, FILE* fp) {
	fgets(dest, 50, fp);
}

// removes trailing newline "\n"
static void removeNewline( char* str ) {
	int len;
	len = strlen(str);
	if( str[len-1] == '\n' )
	    str[len-1] = 0;
}

// tests the reverse algorithm for passed algorithm
static bool testReverse( void (*reverse)() ) {
	puts("TEST");

	FILE* expected;										// expected responses
	expected = fopen("a1expectedcase.txt", "r");

	for(int i = 0; i < 3; i++) {						// loops through each word in list, 3 words
		// create and read from files
		FILE* output;
		output = freopen("output.txt", "w", stdout);	// write stdout to output.txt
		
		// run tests
		reverse();										// run the algorithm

		fclose(output);
		output = fopen("output.txt", "r");

		// get string from output and expected
		char expectedString[100];						// string to compare to output
		getString(expectedString, expected);			
		removeNewline(expectedString);
		fprintf(stderr, "%s\n", expectedString);		// print the expected string
		
		char outputString[100];							// outputted string from algorithm
		getString(outputString, output);

		// compare strings
		if (strcmp(expectedString, outputString) ) {		// compare if strings are equal
			return false;
		}
		
	}
	return true;
}


static void test ( void ) {
	if(!(testReverse(iterativeReverse))) {
		fprintf(stderr, "Iterative reverse failed.\n");
	}
	else if(!(testReverse(recursiveReverse))) {
		fprintf(stderr, "Recursive reverse failed.\n");
	}
	else {
		fprintf(stderr, "Test successful.\n");
	}
}

int main ( int argc, char **argv ) {
	if (argc > 1) {
		if (strcmp(argv[1], "-t\n")) {
			test();
		}
	}
	else {
		puts("Iterative version. Enter your text then press enter");
	    iterativeReverse();
	    puts("\nRecursive version. Enter your text then press enter");
	    recursiveReverse();
	    puts("\n");
	}
	
    return 0;
} 

