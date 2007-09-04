/* xmlrpc_config.h is generated from xmlrpc_config.h.in by 'configure'.

   This file just uses plain AC_SUBST substitution, the same as
   Makefile.config.  Wherever you see @XXX@, that gets replaced by the
   value of 'configure' variable XXX.

   Logical macros are 0 or 1 instead of the more traditional defined and
   undefined.  That's so we can distinguish when compiling code between
   "false" and some problem with the code.
*/


/* We hope to replace xmlrpc_amconfig.h some day with something that 
   doesn't require a whole special set of software to build, to make
   Xmlrpc-c approachable by dumber developers.
*/
#include "xmlrpc_amconfig.h"


#define HAVE_WCHAR_H 1
#define HAVE_SYS_FILIO_H 0
#define HAVE_SYS_IOCTL_H 1

#define VA_LIST_IS_ARRAY 1

#define HAVE_LIBWWW_SSL 0

#define ATTR_UNUSED __attribute__((__unused__))

#define DIRECTORY_SEPARATOR "/"

#define HAVE_UNICODE_WCHAR HAVE_WCHAR_H

/*  Xmlrpc-c code uses __inline__ to declare functions that should
    be compiled as inline code.  GNU C recognizes the __inline__ keyword.
    Others recognize 'inline' or '__inline' or nothing at all to say
    a function should be inlined.

    We could make 'configure' simply do a trial compile to figure out
    which one, but for now, this approximation is easier:
*/
#if (!defined(__GNUC__))
  #if (!defined(__inline__))
    #if (defined(__sgi) || defined(_AIX))
      #define __inline__ __inline
    #else   
      #define __inline__
    #endif
  #endif
#endif
