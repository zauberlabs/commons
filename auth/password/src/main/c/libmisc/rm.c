/*
 * Copyright (C) 2003 by Juan F. Codagnone
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>

#include <sys/types.h>
#include <sys/stat.h>
#ifdef WIN32
	#include <unix.h>
#else
	#include <unistd.h>
#endif                     
#include <dirent.h>
#include "rm.h"

typedef struct GSList
{	void *data;
	struct GSList *next;
} GSList;

static GSList* 
g_slist_append (GSList *list, void *data)
{	GSList *l;

	l = malloc(sizeof(*l));
	if( l == NULL )
		return NULL;

	l->data = data;
	l->next = list;

	return l;
}

#define ISDOT(dname)	((dname)[0]=='.' && (dname)[1]=='\0' )
#define ISDOTDOT(dname)	( (dname)[0]=='.' && (dname)[1]=='.' && \
                        (dname[2])=='\0' )
int
rrm( const char *pathname)
{	struct dirent *dentry;
	struct stat buf;
	GSList *l = NULL;
	GSList *ll = NULL;
	int fail = 0;
	DIR *dir;
	char *s;
	
	dir = opendir(pathname);
	if( dir == NULL )
		return -1;

	while( !fail && (dentry=readdir(dir)) )
	{
		if( ISDOT(dentry->d_name) || ISDOTDOT(dentry->d_name) )
			continue;
			
		s = malloc(strlen(pathname) + strlen(dentry->d_name) + 1 + 1 );
		if( s == NULL )
			fail = 1;
		else
		{	strcpy(s,pathname);
			strcat(s,"/");
			strcat(s,dentry->d_name);
			if( stat(s,&buf) == 0 )
			{
				if( S_ISDIR(buf.st_mode) )
					l = g_slist_append(l,s);
				else
					remove(s);
			}
		}
	}
	closedir(dir);
	
	for( ll = l ; ll ; ll = ll->next )
	{
		rrm(ll->data);
		
		free(ll->data);
		free(ll);
	}

	fail = remove(pathname);
	
	return fail ? -1 : 0;
}
