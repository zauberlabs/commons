/*
 *
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
#include <limits.h>

#include <config.h>

#ifndef HAVE_ITOA
#ifdef HAVE_SNPRINTF
char *
itoa(int i,char *buf,size_t sizeofbuf)
{	int nret;

	nret = snprintf(buf, sizeofbuf,"%d",i);

	return nret ? buf : NULL;
}
#else

char *
itoa(int i,char *buf,size_t sizeofbuf)
{	char bigbigbigbuffer[4060]; /* :^) */
	size_t nRet;

	nRet = sprintf(bigbigbigbuffer,"%d",i);
	if( nRet +1 > sizeofbuf )
		return 0;
	strncpy(buf,bigbigbigbuffer,nRet+1);

	return buf;

}

#endif
#endif
