/*= -*- c-basic-offset: 4; indent-tabs-mode: nil; -*-
 *
 * librsync -- library for network deltas
 * $Id: trace.c,v 1.12 2003/05/01 15:32:39 juam Exp $
 *
 * Copyright (C) 2000, 2001 by Martin Pool <mbp@samba.org>
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

                                     /*
                                      | Finality is death.
                                      | Perfection is finality.
                                      | Nothing is perfect.
                                      | There are lumps in it.
                                      */



/*
 * @todo Have a bit in the log level that says not to include the
 * function name.
 *
 * @todo Always show program/pid in log files.
 */

#define _GNU_SOURCE


#include <stdio.h>
#include <string.h>
#include <errno.h>
#include <stdlib.h>
#include <assert.h>
#include <stdarg.h>

#include "trace.h"

#ifdef HAVE_CONFIG_H
 #include <config.h>
#endif

#ifdef HAVE_UNISTD_H
 #include <unistd.h>
#endif

#ifdef WIN32
	#include <process.h>
	#define vsnprintf trio_vsnprintf
#endif

rs_trace_fn_t  *rs_trace_impl = rs_trace_stderr;
int rs_trace_fd = 2;

int rs_trace_level = RS_LOG_INFO;

static void rs_log_va(int level, char const *fn, char const *fmt, va_list va);

#if SIZEOF_SIZE_T > SIZEOF_LONG
#  warning size_t is larger than a long integer, values in trace messages may be wrong
#endif


/**
 * Log severity strings, if any.  Must match ordering in
 * ::rs_loglevel.
 */
static const char *rs_severities[] = {
    "EMERGENCY! ", "ALERT! ", "CRITICAL! ", "ERROR: ", "Warning: ",
    "", "", ""
};



/**
 * \brief Set the destination of trace information.
 *
 * The callback scheme allows for use within applications that may
 * have their own particular ways of reporting errors: log files for a
 * web server, perhaps, and an error dialog for a browser.
 *
 * \todo Do we really need such fine-grained control, or just yes/no
 * tracing?
 */
EXPORT void
rs_trace_to(rs_trace_fn_t * new_impl)
{
    rs_trace_impl = new_impl;
}


/** 
 * Set the least important message severity that will be output.
 */
EXPORT void
rs_trace_set_level(rs_loglevel level)
{
    rs_trace_level = level;
}


static void
rs_log_va(int flags, char const *fn, char const *fmt, va_list va)


{
    int level = flags & RS_LOG_PRIMASK;
    
    if (rs_trace_impl && (level <= rs_trace_level)) {
        rs_trace_impl(flags, fn, fmt, va);
    }
}


void rs_format_msg(char *buf,
                   size_t buf_len,
                   int flags,
                   const char *fn,
                   const char *fmt,
                   va_list va)
{
    unsigned level = flags & RS_LOG_PRIMASK;
    int len;
    const char *sv;

    *buf = '\0';
    len = 0;

    if (!(flags & RS_LOG_NO_PROGRAM)) {
        strcpy(buf, rs_program_name);
        len = strlen(buf);
    }

    if ( 0 && !(flags & RS_LOG_NO_PID)) {
        sprintf(buf+len, "[%d] ", (int) getpid());
    } else if (~flags & RS_LOG_NO_PROGRAM) {
        strcat(buf+len, ": ");
    }
    len = strlen(buf);

    sv = rs_severities[level];
    if (*sv) {
        strcpy(buf + len, sv);
        len = strlen(buf);
    }

    if (!(flags & RS_LOG_NONAME) && fn) {
        sprintf(buf+len, "(%s) ", fn);
        len = strlen(buf);
    }

    vsnprintf(buf + len, buf_len - len, fmt, va);
}



/**
 * Called by a macro, used on platforms where we can't determine the
 * calling function name.
 */
EXPORT void
rs_log0_nofn(int level, char const *fmt, ...)
{
    va_list         va;

    va_start(va, fmt);
    rs_log_va(level, NULL, fmt, va);
    va_end(va);
}


EXPORT void rs_log0(int level, char const *fn, char const *fmt, ...)
{
    va_list         va;
    
    va_start(va, fmt);
    rs_log_va(level, fn, fmt, va);
    va_end(va);
}


EXPORT void
rs_trace_stderr(int flags, const char *fn, char const *fmt, va_list va)
{
    /* NOTE NO TRAILING NUL */
    char buf[4090];
    int len;

    rs_format_msg(buf, sizeof buf, flags, fn, fmt, va);

    len = strlen(buf);
    if (len > (int) sizeof buf - 2)
        len = (int) sizeof buf - 2;
    strcpy(&buf[len], "\n");
        
    (void) write(rs_trace_fd, buf, len+1);
}



/* ======================================================================== */
/* functions for handling compilers without varargs macros */

/* This is called directly if the machine doesn't allow varargs
 * macros. */
EXPORT void
rs_log_fatal_nofn(char const *s, ...) 
{
    va_list	va;

    va_start(va, s);
    rs_log_va(RS_LOG_CRIT, NULL, s, va);
    va_end(va);
}


/* This is called directly if the machine doesn't allow varargs
 * macros. */
EXPORT void
rs_log_error_nofn(char const *s, ...) 
{
    va_list	va;

    va_start(va, s);
    rs_log_va(RS_LOG_ERR, NULL, s, va);
    va_end(va);
}

/* This is called directly if the machine doesn't allow varargs
 * macros. */
EXPORT void
rs_log_warning_nofn(char const *s, ...) 
{
    va_list	va;

    va_start(va, s);
    rs_log_va(RS_LOG_WARNING, NULL, s, va);
    va_end(va);
}


/* This is called directly if the machine doesn't allow varargs
 * macros. */
EXPORT void
rs_log_critical_nofn(char const *s, ...) 
{
    va_list	va;

    va_start(va, s);
    rs_log_va(RS_LOG_CRIT, NULL, s, va);
    va_end(va);
}

/* This is called directly if the machine doesn't allow varargs
 * macros. */
EXPORT void
rs_log_info_nofn(char const *s, ...) 
{
    va_list	va;

    va_start(va, s);
    rs_log_va(RS_LOG_INFO, NULL, s, va);
    va_end(va);
}


/* This is called directly if the machine doesn't allow varargs
 * macros. */
EXPORT void
rs_log_notice_nofn(char const *s, ...) 
{
    va_list	va;

    va_start(va, s);
    rs_log_va(RS_LOG_NOTICE, NULL, s, va);
    va_end(va);
}


/* This is called directly if the machine doesn't allow varargs
 * macros. */
EXPORT void
rs_log_trace_nofn(char const *s, ...) 
{
    va_list	va;

    va_start(va, s);
    rs_log_va(RS_LOG_DEBUG, NULL, s, va);
    va_end(va);
}


/**
 * Return true if the library contains trace code; otherwise false.
 * If this returns false, then trying to turn trace on will achieve
 * nothing.
 */
EXPORT int
rs_supports_trace(void)
{
#ifdef DO_RS_TRACE
    return 1;
#else
    return 0;
#endif				/* !DO_RS_TRACE */
}


