/*
 * should print a simple backtrace
 */
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
main(int argc, char **argv)
{

	signal(SIGSEGV, sigsegv_handler_fnc);
	foo(0);
	return 0;
}
