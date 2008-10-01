/* sigsegv.c -- sigsegv handlers 
 *
 * Copyright (c) 2003 Juan F. Codagnone <juam@users.sourceforge.net>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included 
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
#ifdef HAVE_CONFIG_H
  #include <config.h>
#endif

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include <errno.h>

#include <unistd.h>
#include <sys/wait.h>

#ifdef HAVE_PTHREAD
  #include <pthread.h>
#endif

/* 
 * ifdefs can be harmful...but not this time!!
 *
 * http://www.gnu.org/manual/glibc-2.2.3/html_chapter/libc_33.html
 */
#if defined(__GLIBC__) && defined(__GLIBC_MINOR__) && __GLIBC__ == 2 && \
    __GLIBC_MINOR__ >= 1
  #define HAVE_BACKTRACE
  #include <execinfo.h>
#endif


#include "sigsegv.h"

/** custom print function */
static int (* print)(const char *format, ...) = NULL;

/** do we need to print the control return at the end? */
static int needs_cr = 1;

void *
sigsegv_set_print( int (* fnc)(const char *format, ...), int _needs_cr)
{	void *ret;
	
	ret = &print;
	print = fnc;
	needs_cr = _needs_cr;

	return ret;
}

/**************************************************************************/

/**
 * lunch gdb, and print the backtrace
 */
static int
_sigsegv_dump_gdb_real(pid_t pid, const char *binary, int full_bt, 
                       int (* myprint)(const char *format, ...))
{	char tmp[]="/tmp/mrbug-crash-XXXXXX";
	int ret = 0;
	int fd;

	fd = mkstemp(tmp);
	if( fd == -1 )
	{	(*myprint)("opening gdb command (tempory) file `%s'%s", tmp,
		           needs_cr ? "\n" : "");
		ret = -1;
	}
	else
	{	char gdb_cmd[]="bt\nquit";
		char gdb_cmd_full[]="bt full\nquit";
		char cmd[128];
		FILE *fp;
		
		if( full_bt )
			write(fd, gdb_cmd_full, strlen(gdb_cmd_full));
		else
			write(fd, gdb_cmd, strlen(gdb_cmd));
		close(fd);
	
		/* i'm too lazy */
		snprintf(cmd, sizeof(cmd), "gdb -nw -n -batch -x \"%s\" %s %d",
		         tmp, binary, pid);
		cmd[sizeof(cmd)-1]=0;
		(*myprint)("trying to dump pid: %d (%s)...%s", pid, binary,
		            needs_cr ? "\n" : "");

		fflush(NULL);
		fp = popen(cmd, "r");
		if( fp == NULL )
		{	(*myprint)("err. couldn't exec `%s'%s", cmd,
			           needs_cr ? "\n" : "");
			ret = -1;
		}
		else
		{	char buff[4096];
			size_t len;

			while(fgets(buff, sizeof(buff), fp))
			{	len = strlen(buff);
				if( buff[len-1] == '\n')
					buff[len-1]=0;
					
				(*myprint)("%s%s", buff,needs_cr ? "\n" : "");
			}
			fclose(fp);
		}
		if( remove(tmp) == -1 )
			(*myprint)("removing `%s` (@;@)%s", tmp,
			           needs_cr ? "\n" : "");
	}

	return ret;
}

static int
_sigsegv_dump_gdb(pid_t pid, const char *binary, int full_bt )
{	pid_t mpid;
	int (* myprint)(const char *format, ...);

	myprint = print ? print : printf;

	/*
	 * clone the process, so we don't make the bt bigger.
	 */
	mpid = fork();
	if( mpid == 0 )
	{	_sigsegv_dump_gdb_real(pid, binary, full_bt,  myprint);
		exit(0);
	}
	else if( mpid == -1 )
		(*myprint)("lunching son: `%s' %s", strerror(errno),
		           needs_cr ? "\n" : "");
	else
	{	/* father */
		int status;

		alarm(0);
		waitpid(0, &status, 0);
		if( WIFEXITED(status) && WEXITSTATUS(status)==0 )
			;
	}
	
	return 0;
}

/** 
 * gets the pathname for the binary that has created the `pid' process
 *
 * \param buff     output buffer (where the path is written)
 * \param nbuff    size of the output buffer
 * \param pid      process id to search for
 *
 * \return buff param
 */
static char *
get_path_from_pid(char *buff, size_t nbuff, pid_t pid)
{	char proc[256];
	char *ret = NULL;
	int n;
	
	sprintf(proc, "/proc/%d/exe", pid);
	if( (n=readlink(proc, buff, nbuff)) == -1 )
		ret = NULL;
	else
	{	buff[n]=0;
		ret = buff;
	}

	return ret;
}


static void
_sigsegv_dump_libc( int (* myprint)(const char *format, ...) )
{
#ifdef HAVE_BACKTRACE
	void *array[48] = {0};
	unsigned short i;
	int n;
	char **res;


	(*myprint)("Backtrace:%c", needs_cr ? "\n" : "");
 	n  = backtrace(array, sizeof(array)/(sizeof(*array)));
	res =  backtrace_symbols(array, n);
	for (i = 0; i < n; i++)
		(*myprint)("%s%s", res[i], needs_cr ? "\n" : "");

	(*myprint)("Attempting to generate core file%s",
	           needs_cr ? "" : "");
#endif

}

static void 
_sigsegv_handler_generic(int signal, int full_bt)
{	char binary[2048];
	int pid = getpid();
	int (* myprint)(const char *format, ...);

	myprint = print ? print : printf;
	if( get_path_from_pid(binary, sizeof(binary), pid) == NULL)
               	(*myprint)("pid %d does not seems to exist", pid);
        else 
	{ 	(*myprint)("Segmentation Violation Detected.%s", 
		           needs_cr ? "\n" : "");
		_sigsegv_dump_gdb(pid, binary, full_bt);
		_sigsegv_dump_libc(myprint);
		
	}
	
	#ifdef HAVE_PTHREAD
	  /** in multithreads programs, chances are that you don't get a core 
	   *  dump unless you stop all the others threads. i don't remember the
	   *  exact cause.
	   */
	   pthread_kill_other_threads_np();
	#endif
	
	fflush(NULL);
	abort();
}

void
sigsegv_handler_fnc(int signal)
{
	_sigsegv_handler_generic(signal, 0);
}

void
sigsegv_handler_bt_full_fnc(int signal)
{
	_sigsegv_handler_generic(signal, 1);
}

