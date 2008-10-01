/*
 * mkrdir.c -- create a directory (and its parents)
 *
 * Copyright (C) 2003 by Juan F. Codagnone <juam@users.sourceforge.net>
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
#include <stdlib.h>
#include <string.h>
#include <sys/stat.h>
#include <sys/types.h> 
#include <errno.h>

#include "strdup.h"

#ifdef WIN32
	#include <unix.h>
#endif

/* TODO: make it more generic:
 * TODO: create the dir with mode 0755 and the chmod it to `mode'
 */
EXPORT int
mkrdir(const char *pathname, mode_t mode)
{	struct stat s;
	int ret = 0;
	char *p,*q;

	errno = 0;
	
	/* already exists ? */
	ret = stat( pathname, &s );
	if( ret == 0 )
	{	if( S_ISDIR(s.st_mode) )
			errno = EEXIST;
		else
			errno = ENOTDIR;
		return -1;	
	}

	p = strdup(pathname);
	if( p == NULL )
	{	errno = ENOMEM ;
		return -1;
	}
	
	if( p[strlen(p)-1] == '/' )
		p[strlen(p)-1] = '\0';

	ret = 0;
	for( q=p ; !ret && (q=strchr(q,'/')) ;  )
	{
		
		*q = 0;
		if( p[0] )
		{	ret = mkdir(p,mode);
			if( ret == -1 && (errno == EEXIST || errno == EACCES))
				ret = 0;
		}
		*q = '/';
		q++;
		while( *q=='/' )
			q++;
	}

	ret = mkdir(p,mode);

	free(p);
	
	return ret;
}

