/*
    Author: William Asp
    September 24, 2020

    **What it is**
        A filter to clean a text from all characters that
        are not alphabetic, blank or newline.

    **How it works**
        The program reads from stdin, and writes to the file filter.txt
        To filter a file, pipe it to the file.

    **Testing**
        Run the program with the -t flag, the expected output and real output is written 
        tp stdout
*/
#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>

void filter ( void ) {
    FILE* fp;
    fp = fopen("filter.txt", "w");
    char c;
    while((c = getchar()) != EOF) {
        if(isalpha(c) || c == '\n')
            fputc(c, fp);
        else
            fputc(' ', fp);
    }
    fclose(fp);
}


static void test ( void ) {
    FILE* fp;
    fp = fopen("test.txt", "r");
    char expected[] = "                             \nabcdefghijklmnopqrstuvxyzABCDEFGHIJKLMNOPQRSTUVXYZ";
    printf("expected:    %s\n", expected);
    printf("Test result: ");
    filter();
    FILE* res;
    res = fopen("filter.txt", "r");
    char c;
    while((c = fgetc(res)) != EOF) {
        putchar(c);
    }
}

int main ( int argc, char* argv[] ) {
    if(argc > 1 && strcmp(argv[1],"-t") == 0) {
        test();
    } else
        filter();
}