/* 
 * should print a simple backtrace with a custom print function (prints -> at
 * the begining of each line
 */
#include <stdio.h>
#include <stdarg.h>
#include <signal.h>
#include <libcrash/sigsegv.h>

static void bar(int a);

static void
foo(int a)
{	char *die = 0;

	if( a > 10 )
		*die = 0; /* die! die! */

	bar(a+1);
}

static void
bar(int a)
{
	foo(a+1);
}


int
my_print( const char *format, ...)
{	va_list ap;
	int ret;
	
	va_start(ap, format);
	printf("-> ");
	ret = vprintf(format, ap);
	printf("\n");
	va_end(ap);
	
	return ret;
}

int
main(int argc, char **argv)
{
	signal(SIGSEGV, sigsegv_handler_fnc);
	sigsegv_set_print(my_print, 0);
	foo(0); 
	return 0;
}
