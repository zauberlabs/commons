/*
 * $Id: newopt.h,v 1.2 2003/02/26 00:37:48 juam Exp $
 *
 * Command line options parser inspired in getopt(3) but unlike that,
 * this _is_ THREAD SAFE
 * 
 */

#ifndef _OPT_H_
#define _OPT_H_

#ifdef __cplusplus
extern "C" {
#endif

#include "dll.h"

/* different types of arguments
 */
enum optflagsT
{	OPT_NORMAL=0,		/* normal*/
	OPT_INVALID=2,		/* don't use it */
	OPT_OPTIONAL=2		/* optional argument (not yet) */
};

/* diferent types of variables
 */
enum optTypeT
{
	OPT_T_FLAG,		/* arg is a flag. is set to 1 if exist*/
	OPT_T_NFLAG,		/* arg is a flag. is set to 0 if exist*/
	OPT_T_FUNCT,		/* call a function */

	OPT_T_INT,		/* arg is an int */
	OPT_T_LONG,		/* arg is a long */
	OPT_T_REAL,		/* arg is a double */
	OPT_T_GENER,		/* don't treat argument, pass the char */
	OPT_T_INVALID		/* dont't use it */
};


typedef enum opt_flags
{	OPT_F_QUIET=1U,		/* dont print errors */
	/*
	 * Not Yet!
	 */
	OPT_F_ERROR=2U,		/* for error handling use reserved functions */
	OPT_F_FFILE=4U,		/* options can be read from a file
				 * set in param */
	OPT_F_INVALID
}optFlagsT;

/* option form
 * 
 * name:	if name the option is a long option this represents the
 *		name of the option. if is a short one all this are bind
 *		it to the param.
 *
 * arg:		one of these
 *			OPT_NORMAL:
 *			OPT_OPTIONAL: not yet
 *
 * short_opt:	true if it the option is a short option
 *
 * type:	class of the argument
 *
 * data:	pointer where to store the argument
 */		
typedef struct _opt
{	const char *name;
	enum optflagsT flags;
	int short_opt;		
	enum optTypeT type;
	void *data;
}optionT;

/*
 * GetOptions()
 * 
 *  Parse the comand line options.
 *
 *  Parameters.
 * 	argv	as recived from main
 *	opt	the options array termined, null terminated
 *	flags	set the flags. can be bitwised
 *		OPT_F_QUIET
 *	reserved some flags may use this. reserved for future.
 *
 *  Returns.
 *	>0 the index to the first non-option.
 *	-1 error.
 */
EXPORT int
GetOptions( char *const *argv, optionT *opt, unsigned flags, void *reserved );


#ifdef __cplusplus
}
#endif

#endif

