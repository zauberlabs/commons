/*
 * $Id: strdup.c,v 1.3 2003/02/26 00:37:48 juam Exp $
 */
#include <stdlib.h>
#include <string.h>
#include <errno.h>

#include "strdup.h"
        
EXPORT char *
strdup(const char *s)
{	char *ptr;
        extern int errno;

	if( s == NULL)
	{	errno = EINVAL;
		return NULL;
	}
	ptr=malloc( strlen(s) + 1 );
	if( ! ptr )
	{	errno = ENOMEM ;
		return NULL;
	}

	return strcpy(ptr,s);
}

