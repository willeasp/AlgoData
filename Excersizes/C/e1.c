#include <stdio.h>

int main( void ) {
	char c;
	do {
		c = getchar();
		if (c == 'a')
			putchar('X');
		else
			putchar(c);
	} while (c != EOF);
}