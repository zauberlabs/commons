/*
 * Dir listing implementig stacks.
 *
 * This is UGLY, and OLD
 *
 * $Id: dirstack.c,v 0.0 2001/09/08 15:35:52 juam Exp $
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
 *
 ****
 *
 * The idea of this module was to create a generic interface for
 * directories listing to memory. (to avoid the problem of finite
 * file descriptors or the problems that ocasionates the reuse of
 * them). 
 *
 * It is coded using a stack (implemented with linked lists). With garbage
 * collector.
 *
 * TODO: Sat,  8 Sep 2001 12:12:43 -0300 
 * TODO: may be it would be convenient to keep in diferents files the stack 
 * TODO: part and the directories for future reuse of the stack,
 */
#ifdef HAVE_CONFIG_H
 #include <config.h>
#endif 

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include <errno.h>
#ifdef HAVE_DIRENT_H
  #include <dirent.h>
#else
  #include <dirent_.h>
#endif

#include "dirstack.h"

/*
 * how many members hold in each list member?
 */
#define NODE_BUFF_SIZE	512

#define NELEM(array)	( sizeof(array) / sizeof(*array) )

#define ISDOT(dname)	((dname)[0]=='.' && (dname)[1]=='\0' )
#define ISDOTDOT(dname)	( (dname)[0]=='.' && (dname)[1]=='.' && \
			(dname[2])=='\0' )

typedef struct nodeT
{	int index;	/* up to were is filled ? */
	struct
	{	char *name;
	}bucket[NODE_BUFF_SIZE];
	struct nodeT *next;
	struct nodeT *back;
}* nodeT;

struct stackdir_t
{	nodeT data;	/* useful data */
	nodeT unused;	/* unused nodes */
};

/*
 * allocate a new node and place at the top of the list
 * 
 * returns <0 on error.
 */
static int
new_node( stackdir_t ld )
{	nodeT node;

	assert( ld );
	if( ld->unused )
	{	node = ld->unused;
		ld->unused = ld->unused->next;
	}
	else
	{	node = malloc( sizeof(*node)  );
		if( node == NULL )
			return -1;
	}
	node->index = 0;
	node->next = ld->data;
	ld->data = node;
	
	return 0;
}

/*
 * put in the repository the current node
 */
int
remove_node( stackdir_t ld )
{	nodeT act;

	assert(ld);
	if( dirstack_is_empty( ld ) )
		return 0;
	
	act = ld->data;
	ld->data = ld->data->next;
	act->next = ld->unused;
	ld->unused = act;

	return 0;
}

stackdir_t
dirstack_new( void )
{	stackdir_t ld;
	
	ld = malloc( sizeof(*ld) );
	if( ld == NULL )
	{	errno = ENOMEM;
		return NULL;
	}
	ld->data =
	ld->unused = NULL;
	return ld;
}

int
dirstack_is_valid( stackdir_t ld )
{
	return ld!=NULL;
}

int
dirstack_is_empty( stackdir_t ld )
{
	return ld->data==NULL;
}

void
dirstack_destroy( stackdir_t ld )
{	nodeT node,aux;
	int i;
	
	if( ld == NULL )
		return;

	for( node = ld->data ; node ; node = aux )
	{	aux = node->next;
		for( i=0 ; i< node->index ; i++ )
			free( node->bucket[i].name );
		free( node );
	}
	for( node = ld->unused ; node ; node = aux )
	{	aux = node->next;
		free( node);
	}

	free( ld );
}

/* adds a new file to the listings
 * resize if needed
 * 
 * 'path' is the path to 'file'
 *
 *  if path & file are null is interpreted as a directory separator
 */
static int
add_item( stackdir_t ld, const char *path, const char *file )
{	int i;
	void *ptr;
	int special=0;
	
	assert( ld );
	if( path == NULL && file == NULL )
		special = 1;
	else if( !(path && file) )
		assert(NULL);

	if( ld->data->index == NELEM(ld->data->bucket) )
		new_node( ld );
	
	
	if( special )
		ptr = NULL;
	else
	{	ptr = malloc(strlen(path) + strlen(file) + 2  );
		sprintf( ptr,"%s/%s",path,file );

		if( ptr == NULL )
		{	errno = ENOMEM;
			return -1;
		}
	}
	i = ld->data->index++;
	ld->data->bucket[i].name = ptr;
	
	return 0;
}

/*
 * removes the elements with the same keys
 */
void
remove_key( stackdir_t ld)
{	char *s;
	
	assert( ld && ld->data );
	
	while( dirstack_get_file(ld,&s)==1 )
		free(s);
}

int
dirstack_push( stackdir_t ld, const char *dirname )
{	DIR *dir;
	struct dirent *d;
	int fail;

	if( ld == NULL || dirname == NULL )
	{	errno = EINVAL;
		return -1;
	}
	else if( (dir = opendir (dirname)) == NULL )
		return -2;
	if( dirstack_is_empty( ld ) )
	{	if( new_node( ld ) < 0 )
		{	closedir(dir);
			return -1;
		}
	}

	fail = add_item( ld , NULL , NULL )<0;
	while( !fail && (d=readdir(dir)) )
	{	if( !ISDOT(d->d_name) && !ISDOTDOT(d->d_name) )
				fail = add_item( ld, dirname, d->d_name) < 0;
	}
	
	if( fail)
		remove_key(ld);

	closedir(dir);
		
	return fail? -3 : 1;
}

int
dirstack_get_file( stackdir_t ld , char **nRet)
{	int ret = 0;
	
	if( dirstack_is_empty(ld) || nRet == NULL)
		return -1;

	if( ld->data->index == 0 )
		 remove_node( ld );
	if( dirstack_is_empty(ld) )
		return  -1;
		
	else if( ld->data->bucket[--ld->data->index].name )
	{	*nRet = ld->data->bucket[ld->data->index].name;
		ld->data->bucket[ld->data->index].name = NULL;
		ret = 1;
	}
	else 
		ret = 0;
	
	return ret;
}

#ifdef _TEST_DIRSTACK_

int
main(int argc,char *argv[])
{	stackdir_t ld;
	int key;
	char *s;
	
	if( argc==1 )
		return 1;

	ld = dirstack_new();
	printf("valid stack?...");
	if( !dirstack_is_valid(ld) )
	{	puts("no");
		return 2;
	}
	puts("yes");
	printf("is empty? %s\n",dirstack_is_empty(ld) ? "yes":"no");
	printf("could pop empty? %d\n",dirstack_get_file(ld,&s) );
	printf("pushing dir '%s'...",argv[1]);
	key = dirstack_push( ld , argv[1] );
	if( key < 0 )
	{	printf("%d\n",key);
		perror("");
	}
	else
	{	printf("ok. key = %d\n",key);
	}
	puts("poping all the dir");
	while( dirstack_get_file(ld,&s)==1 )
	{	assert(s);
		puts( s );
		free( s );
	}
	printf("is empty? %s\n",dirstack_is_empty(ld) ? "yes":"no");
	printf("could pop empty? %d\n",dirstack_get_file(ld,&s) );

	key = dirstack_push( ld , "/net" );
	key = dirstack_push( ld , "/tmp" );
	while( (key=dirstack_get_file(ld,&s))>=0)
	{	if( !key )
			puts("----------------");
		else
		{
			assert(s);
			puts( s );
			free( s );
		}
	}

	dirstack_destroy( ld );
	return 0;
}

#endif


