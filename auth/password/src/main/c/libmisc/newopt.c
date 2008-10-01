/*
 * newopt.c -- Command line options parser inspired in getopt(3)(in behavior)
 *
 * $Id: newopt.c,v 1.6 2003/02/26 00:37:48 juam Exp $
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
#include <stdio.h>
#include <stdlib.h>
#include <stdarg.h>
#include <string.h>
#include <ctype.h>
#include <limits.h>	/* for types conversion */

#include <assert.h>
#include <trace.h>

#include <strdup.h>
#include "newopt.h"

static int getlong(const char *fmt,long *mylong);
static int getreal(const char *fmt,double *mylong);

/* for I18n (internationalization) and L10n (localization) */ 
#define _(String) (String)
#define N_(String) (String)
#define textdomain(Domain)
#define bindtextdomain(Package, Directory)

struct global
{	char * const * argv;	/* argv as main */
	const optionT *table;	/* option table */
	int i;			/* current position in argv    */
	const char *pt;		/* current position in argv[i] */
	unsigned flags;		/* flags from user */
	void *reserved;		/* reserved parameter from user */
	const char *arg;	/* argument for the current option */
};

/*
 * report something to the user
 */
static void 
report( unsigned flags, const char *fmt,...)
{	va_list ap;
	
	if( !(flags & OPT_F_QUIET) )
	{	
		va_start(ap,fmt);
		fprintf(stderr,_("%s: "), rs_program_name);
		vfprintf(stderr,fmt,ap);
		fprintf(stderr,"\n");
		va_end(ap); 
	}
}

/*
 * Initiliazation dependences 
 */

/*
 * is 'str' alphabetic?
 */
static int
buffIsValid( const char *str )
{	
	if( str == NULL || str[0]=='-' || str[0] =='_' )
		return 0;

	for( ; *str && (isalpha(*str)||*str=='-'||*str=='_') ;str++)
		;

	return !*str;
}

#define valid_type(f)	( (f)>=0 && (f)<OPT_T_INVALID)
#define valid_flag(t)	( (t)>=0 && (t)<OPT_INVALID)

/*
 * Checks:
 *    options are not duplicated
 * Returns:
 *    <0 on colition (error)
 */
static int
check_colitions( const optionT *opt )
{	/* TODO: not yet!
	 */
	return 0;
}

/*
 * Checks:
 *   name not null and alphabetic
 *   valid type of variable 
 *
 *  Returns:
 *	<0 if there is an error
 *
 *  NULL data is fine
 */
static int
check_table( const optionT * opt )
{	int i;

	assert( opt );

	for( i=0; opt[i].name && buffIsValid(opt[i].name) &&
	     valid_type(opt[i].type) && valid_flag(opt[i].flags) ; i++ )
		;

	if( opt[i].name == NULL )
		return -i;

	return check_colitions( opt );
}

static void
free_argv( char **argv)
{	int i;

	for( i=0 ; argv[i] ; i++ )
		 free(argv[i]); 
	free(argv);
}

/*
 * guess..
 */
static char **
clone_argv( char * const * argv)
{	char **new;
	int i,argc;
	int ok=1;

	for( argc = 0 ; argv[argc] ; argc++ )
                ;

	new=malloc( (1+argc) * sizeof(char *) );
	if( new==NULL )
		 return NULL;
 
	for( i=0; argv[i] && ok ; i++)
	{       new[i] = strdup( argv[i] );
		ok = new[i] != NULL;
	}
	new[argc] = NULL;
	
	if( !ok )
		 free_argv(new);

	return new;
}

/*
 * do sanity checks for GetOptions() and fills 'data'
 */
static int
do_sanity( struct global *data,char *const *argv,  const optionT *opt, 
                  unsigned flags, void *reserved )
{	int nRet=0;

	assert( data );

	if( !argv )
		nRet = -1;
	else if( opt==NULL || !check_table(opt)  )
		nRet = -1;
	else if( flags <0 && flags >= OPT_F_INVALID )
		nRet = -1;
	if( nRet < 0)
		return nRet;

	data->table = opt;
	data->i = 1;
	data->pt = NULL;
	data->flags = flags;
	data->reserved = reserved;
	data->argv = /*clone_argv(argv)*/ argv;

	if( data->argv == NULL)
		nRet = -1;

	return nRet;
}

/*
 * some useful bindings
 */
#define is_option(a) (  (a)[0]=='-' &&  \
		    ( (a)[1]=='-' ? (a)[2]!='\0' : (a)[1]!='\0' ) )

/* is the string 'a' a number?
 */
#define is_number(a)	( !getlong(a,NULL) || !getreal(a,NULL) )
#define force_end(str) ( !strcmp(str,"--" ) )

#define is_long_opt(s)  ( (s)[0]=='-' && (s)[1]=='-' && (s)[2]!='\0' )

#define current_arg(s) ( (s)->argv[(s)->i] )

/*
 * finds a short option in the table
 * 
 *  returns the index or -1
 */
static int
findOption( const optionT *table, int c , const char *str)
{	int i;
	int found;
	
	assert(table);

	/*
	 * TODO: extend search for incomplete strings
	 * TODO: ie: adapt process_tok() from command.c from patagon
	 * TODO: or quad-frontend projects
	 */
	for( found=i=0; table[i].name && !found ; i++ )
	{	if( str )
		{	if(!table[i].short_opt && !strcmp(str,table[i].name) )
				found=1;
		}
		else
		{	if( table[i].short_opt && strchr(table[i].name,c) )
				found = 1;
		}
	}
	
	return found? i-1 : -1;
}

#define findShortOption(table,c) ( findOption((table),(c),NULL) )
#define findLongOption(table,s)  ( findOption((table),0,s) )

enum
{	RET_UNKNOW=-1,	/* unknown option */
	RET_ENOUGH=-2,	/* not enought arguments */
	RET_END=-3	/* we reach the end */
};

#define has_no_arg(t)  ( (t).type<=OPT_T_FUNCT && (t).type>=OPT_T_FLAG)
#define has_one_arg(t) ( (t).type>=OPT_T_INT && (t).type<=OPT_T_GENER )

/*
 * gets aditionals parameters of an option if any.
 * <0 error. else the position in the table of the command
 */
static int
do_short_opt( struct global *data )
{	int c;
	int n;
	assert( data );

	c = *(data->pt++);
	if( !*data->pt )	/* we finish this arg. go to the next */
	{	data->pt = NULL;
		data->i++;
	}
	n = findShortOption( data->table,c);

	if( n < 0 )
	{	n = RET_UNKNOW;
		report(data->flags,_("unknow option '%c'"),c);
	}
	else if( has_one_arg( data->table[n] ) )
	{	if( data->pt )	/* inlined argument */
		{	data->arg = data->pt;
			data->pt = NULL;
			data->i++;
		}
		else if ( !current_arg(data) || force_end(current_arg(data)) ||
	  ( !is_number(current_arg(data)) && is_option(current_arg(data)) )  )
		{	n = RET_ENOUGH;
			report(data->flags,_("not enough parameters for `%c'"),
				c);
		}
		else
		{	data->arg = current_arg(data);
			data->i++;
		}
	}

	return n;
}


/*
 * handle the long options.
 * returs <0 on error.  
 * TODO: stinks
 */
static int
do_long_option ( struct global *data )
{	char *q,*arg ;
	int n;
	assert( data );

	arg =  current_arg(data)+2;
	data->i++;

	if( (q=strchr( arg , '='  )) )  /* separate  --argument=something */
	{	 q++;
		*(q-1)=0;
		if(!*q)
			q=NULL;
	}
	n = findLongOption ( data->table , arg);
	
	if( n<0 )
	{	n =  RET_UNKNOW;
		report(data->flags,_("unknow long option `%s'"),arg);
	}
	else if( has_one_arg( data->table[n] ) )
	{	if( q )		/* inline option */
			data->arg = q;
		else if( !current_arg(data) || force_end( current_arg(data) ) ||
 	( !is_number(current_arg(data)) && is_option(current_arg(data)) )  ) 
	{	n = RET_ENOUGH;
			report(data->flags,_("not enough parameters for `%s'"),
				arg);
		}
		else
		{	data->arg = current_arg(data);
			data->i++;
		}
	}
	else if( data->table[n].flags!=0)
		assert( 0 );

	return n;
}

/*
 * gets the next option. returns the index on data->table if the option
 *
 * Modifies internal counters (data->pt and data->i).
 */
static int
getNextOption(struct global *data)
{	int nRet;

	assert( data );
	data->arg = NULL;

	if( data->pt )
		nRet = do_short_opt( data );
	else if( !current_arg(data) )
		nRet = RET_END;
	else if( force_end( current_arg(data) ))
	{	data->i++;	/* the user don't want the "--" stuff */
		nRet = RET_END;
	}
	else if( !is_option( current_arg(data) ))
		nRet = RET_END;
	else if( is_long_opt( current_arg(data)  ))
		nRet = do_long_option(data);
	else
	{	data->pt = current_arg(data) + 1;
		nRet = do_short_opt( data );
	}

	return nRet;
}

static int
getreal(const char *fmt,double *myreal)
{	double r;
	char *error;

	if( fmt == NULL  )
		return -1;

	r = strtod(fmt,&error);
	/* we ignore (under|over)flown */
	if( (r==0.0 && error) || (error && *error) )
		return -1;

	if( myreal )
		*myreal = r;

	return 0;
}

static int
getlong(const char *fmt,long *mylong)
{	long int l;
	char *error;

	
	if( fmt==NULL || !*fmt)
		return -1;

	l = strtol(fmt,&error,0);
	if( error && *error )
		return -1;

	if( mylong )
		*mylong = l;
	return 0;
}

int
getint(const char *fmt,int *myint)
{	long l;
	int ret;

	ret = getlong(fmt,&l);
	if( ret ==-1 || l<INT_MIN || l>INT_MAX )
		return -1;

	if( myint )
		*myint = l; 
	return 0;
}

/*
 * process the option at 'argv[i]'
 * Params:
 * 	*table	the element to process
 *	arg	the argument if any
 *
 * returns <0 on error
 */
static int
process( optionT *table, const char *arg )
{	int nRet=0;
	typedef void (*callbackT)(void);

	assert( table );
	if( !table->data )
		return -1;

	/* TODO: next time do the questions on a table! */
	switch( table->type )
	{	case OPT_T_FLAG:
		case OPT_T_NFLAG:
			*((int *)table->data) = table->type == OPT_T_FLAG;
			break;
		case OPT_T_INT:
			nRet = getint(arg,table->data);
			break;
		case OPT_T_LONG:
			nRet = getlong(arg,table->data);
			break;
		case OPT_T_REAL:
			nRet = getreal(arg,table->data);
			break;
		case OPT_T_FUNCT:
			(*(callbackT)table->data)();
			break;
		case OPT_T_GENER:
			*((const char **)table->data) = arg;
			break;
		default:
			assert( 0 );
	}
	
	return nRet;	
}

EXPORT int
GetOptions( char *const *argv, optionT *table, unsigned flags, void *reserved )
{	struct global data;
	int i=0;
	int fail=0;

	if( do_sanity(&data,argv,table,flags,reserved) <0 )
	{	report(flags,_("invalid arguments") );
		return -1;
	}

	while( !fail && (i=getNextOption(&data)) >= 0 )
	{	if( process(table+i,data.arg) <0 )
		{	report(data.flags,_("format error in `%s'"), data.arg);
			fail=1;
		}
	}

	/*free_argv((char **)data.argv);*/
	return (fail || i != RET_END) ? -1:data.i;
}

