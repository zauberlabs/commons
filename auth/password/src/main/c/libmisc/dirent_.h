/*
 * (c) Copyright 2003  Juan F. Codagnone <juam@arnet.com.ar>-All rights reserved
 */
#ifndef _DIRENT_H_
#define _DIRENT_H_

#ifndef NAME_MAX
	#define NAME_MAX 1024 
#endif

struct dirent
{
	char d_name[NAME_MAX+1];
};



typedef struct DIR DIR;

DIR * opendir( const char *name );
int closedir( DIR * dir );
struct dirent *readdir( DIR * dir );

#endif
