/*
 * ftw.c --  an ftw clone
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
#ifdef HAVE_CONFIG_H
  #include <config.h>
#endif

#include <stdlib.h>

#include <sys/types.h>
#include <sys/stat.h>

#ifndef HAVE_UNISTD_H
  #include <unix.h>
#endif

#include "dirstack.h"

int ftw_(const char *path, 
       int (*fn)(const char *file, const struct stat *sb, void *data),
       void *data)
{
	int ret = -1;
	char *file = 0;
	struct stat st;
	
	stackdir_t ds = dirstack_new();

	if( dirstack_is_valid(ds) )
	{
		dirstack_push(ds, path);
		ret = 0;
		do 
		{
			while( dirstack_get_file(ds, &file) == 1 && !ret )
			{       
				if( stat(file, &st) == 0 )
				{
					if( S_ISDIR(st.st_mode) )
						dirstack_push(ds, file);
					else
						ret = (*fn)(file, &st, data);
				}

				free(file);
				file = 0;
			}
		} while(!dirstack_is_empty(ds));
	}

	dirstack_destroy(ds);

	return ret;
}

#ifdef TEST_DRIVER_FTW_
#include <stdio.h>

int 
fn(const char *file, const struct stat *sb, void *data)
{
	puts(file);

	return 0;
}

int
main(int argc, char **argv)
{

	if( argc != 1 )
	{	
		ftw_(argv[1], fn, NULL);		
	}
	return 0;
}
#endif
