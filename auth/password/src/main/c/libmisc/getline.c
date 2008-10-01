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

#include "getline_.h"

#define INIT_CHUNK	256
#define FACTOR_CHUNK	2

char *
getline_(FILE *fp)
{	unsigned size,i;
	int exit = 0;
	char *p;
	int c;
	
	size = INIT_CHUNK;
	p = (char *) malloc(size);
	if( p == NULL )
		return NULL;

	for( i=0; !exit && (c=getc(fp))!=EOF && c != '\n' ; i++ )
	{ 	/* resize */
		if( i + 2 == size )
		{	char *q;

			size *= FACTOR_CHUNK;
			q = (char *)realloc(p, size );
			if( q == NULL )
			{	free(p);
				exit = 1;

				/* eat line */
				while( (c=getc(fp))!=EOF && c!='\n')
					;
			}
		}
		if( !exit )
			p[i] = c;
	}
	
	if(  exit )
		free(p);
	else
	{
		p[i] = 0;
		if( c == EOF && i==0 )
		{	free(p);
			p = NULL;
		}
		/* strip  \r\n */
		else if( p[i-1] == '\n' )
		{	p[i-1] = '\0';
			if( p[i-2] == '\r' )
				p[i-2] = '\0';
		}
			
	}
	
	return exit ? NULL : p;
}
