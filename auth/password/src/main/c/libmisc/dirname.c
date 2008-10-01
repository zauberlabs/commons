/*
 * dirname.c -- 
 *
 * $Id: dirname.c,v 1.4 2003/03/26 03:51:35 juam Exp $
 *
 * Copyright (C) 2001 by Juan F. Codagnone <juam@users.sourceforge.net>
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
#include "basename.h"

EXPORT char *
my_path_get_dirname(const char *path)
{	char *p, *q;

	p = strrchr(path, '/' ) + 1;
	if( p -1  == NULL )
		return strdup(".");

	for( ; *(p-1) =='/' && p-1!=path  ; p--)
		;
	q = malloc(p - path + 2);
	if( q )
	{	memcpy(q,path,p-path);
		q[p-path]=0;
	}
	
	return q;
}
