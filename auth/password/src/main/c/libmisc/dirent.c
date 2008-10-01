/*
 *
 * WIN32 Implementation of (POSIX) opendir, readdir, closedir 
 *
 * Thread safe
 *
 * (c) Copyright 2003  Juan F. Codagnone <juam@arnet.com.ar>-All rights reserved
 */
#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include <windows.h>
#include "dirent_.h"

struct DIR 
{
	HANDLE handle;	
	WIN32_FIND_DATA data;
	struct dirent dentry;
	int first;
};

DIR * 
opendir( const char *name )
{	DIR *dir;
	char *s;

	s = malloc(strlen(name) + 3 + 1);
	if( s == NULL )
		return NULL;
	strcpy(s,name);
	strcat(s,"\\*");

	dir = malloc(sizeof(*dir));
	if( dir == NULL )
	{	free(s);
		return NULL;
	}

	dir->first = 1;
	dir->handle = FindFirstFile(s,&(dir->data));
	if( dir->handle ==  INVALID_HANDLE_VALUE )
	{	free(dir);
		dir = NULL;
	}

	free(s);
	return dir;
}

int 
closedir( DIR * dir )
{	BOOL b;	
	errno = 0;

	if( dir == NULL )
		errno = EBADF;
	else
	{ 	b = FindClose(dir->handle);
		if( b == FALSE )
			errno = EBADF;
		free(dir);
	}
	return 0;
}

struct dirent *
readdir( DIR * dir )
{	BOOL b = 1;

	errno = 0;
	if( dir == NULL )
	{	errno = EBADF;
		return NULL;
	}

	if( dir->first )
		dir->first = 0;
	else
		b = FindNextFile(dir->handle, &(dir->data) );

	if( b )
	{	strncpy(dir->dentry.d_name, dir->data.cFileName, 
		        sizeof(dir->dentry.d_name)-1);
		dir->dentry.d_name[sizeof(dir->dentry.d_name)-1] = 0;
	}

	return b ? &(dir->dentry) : NULL ;
}

