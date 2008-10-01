#include <stdio.h>
#include <stdlib.h>
#include <stdarg.h>
#include <string.h>
#include <ctype.h>
#include <limits.h> /* for types conversion */

#include <assert.h>

#include <libmisc/trace.h>

void
rs_trace_syslog(int flags, const char *fn, char const *fmt, va_list va)
{
    /* NOTE NO TRAILING NUL */
    char buf[4090];

    /* you're never going to want program or pid in a syslog message,
 *      * because it's redundant. */
    rs_format_msg(buf, sizeof(buf),
                  flags | RS_LOG_NO_PROGRAM | RS_LOG_NO_PID,
                  fn, fmt,  va);
    syslog(flags & RS_LOG_PRIMASK, "%s", buf);
}

