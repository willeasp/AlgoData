#include <stdio.h>

int main( void ) {
    int count = 0;
    char c = 0;
    while((c = getchar()) != EOF) {
        count++;
    }
    printf("%d", count);
}