/*= -*- c-basic-offset: 4; indent-tabs-mode: nil; -*-
 *
 * librsync -- generate and apply network deltas
 * $Id: trace.h,v 1.2 2003/02/26 00:37:48 juam Exp $
 * 
 * Copyright (C) 2000, 2001, 2002 by Martin Pool <mbp@samba.org>
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


/**
 * @file
 *
 * @todo A function like perror that includes strerror output.  Apache
 * does this by adding flags as well as the severity level which say
 * whether such information should be included.
 */

#include <stdarg.h>

#ifdef __cplusplus
extern "C" {
#endif

#include "dll.h"

/*
 * trace may be turned off.
 *
 * error is always on, but you can return and continue in some way
 *
 * fatal terminates the whole process
 */


/* unconditionally on */
#define DO_RS_TRACE

/**
 * \brief Log severity levels.
 *
 * These are the same as syslog, at least in glibc.
 *
 * \sa rs_trace_set_level()
 */
typedef enum {
    RS_LOG_EMERG         = 0,   /**< System is unusable */
    RS_LOG_ALERT         = 1,   /**< Action must be taken immediately */
    RS_LOG_CRIT          = 2,   /**< Critical conditions */
    RS_LOG_ERR           = 3,   /**< Error conditions */
    RS_LOG_WARNING       = 4,   /**< Warning conditions */
    RS_LOG_NOTICE        = 5,   /**< Normal but significant condition */
    RS_LOG_INFO          = 6,   /**< Informational */
    RS_LOG_DEBUG         = 7    /**< Debug-level messages */
} rs_loglevel;

enum {
    RS_LOG_PRIMASK       = 7,   /**< Mask to extract priority
                                   part. \internal */

    RS_LOG_NONAME        = 8,   /**< \b Don't show function name in
                                   message. */

    RS_LOG_NO_PROGRAM   = 16,
    RS_LOG_NO_PID       = 32
};


/**
 * \typedef rs_trace_fn_t
 * \brief Callback to write out log messages.
 * \param level a syslog level.
 * \param msg message to be logged.
 */
typedef void    rs_trace_fn_t(int flags, const char *fn,
                              char const *msg, va_list);

EXPORT void rs_format_msg(char *buf, size_t, int, const char *,
                   const char *fmt, va_list);

EXPORT void            rs_trace_set_level(rs_loglevel level);

/** Set trace callback. */
EXPORT void            rs_trace_to(rs_trace_fn_t *);

/** Default trace callback that writes to stderr.  Implements
 * ::rs_trace_fn_t, and may be passed to rs_trace_to(). */
EXPORT void rs_trace_stderr(int level, const char *fn, char const *fmt, va_list va);
EXPORT extern int rs_trace_fd;


EXPORT void
rs_trace_syslog(int level, const char *fn, char const *fmt, va_list va);

/** Check whether the library was compiled with debugging trace
 * suport. */
EXPORT int             rs_supports_trace(void);

EXPORT void rs_log0(int level, char const *fn, char const *fmt, ...)
#if defined(__GNUC__)
    __attribute__ ((format(printf, 3, 4)))
#endif /* __GNUC__ */
  ;

#if defined(HAVE_VARARG_MACROS)

#if 1 || defined(DO_RS_TRACE)
#  define rs_trace(fmt, arg...)                            \
    do { rs_log0(RS_LOG_DEBUG, __FUNCTION__, fmt , ##arg);  \
    } while (0)
#else
#  define rs_trace(s, str...)
#endif	/* !DO_RS_TRACE */

/*
 * TODO: Don't assume this is a gcc thing; rather test in autoconf for
 * support for __FUNCTION__ and varargs macros.  One simple way might
 * just be to try compiling the definition of one of these functions!
 *
 * TODO: Also look for the C9X predefined identifier `_function', or
 * whatever it's called.
 */

#define rs_log(l, s, str...) do {              \
     rs_log0((l), __FUNCTION__, (s) , ##str);  \
     } while (0)


#define rs_log_critical(s, str...) do {                         \
     rs_log0(RS_LOG_CRIT,  __FUNCTION__, (s) , ##str);          \
     } while (0)

#define rs_log_error(s, str...) do {                            \
     rs_log0(RS_LOG_ERR,  __FUNCTION__, (s) , ##str);           \
     } while (0)

#define rs_log_notice(s, str...) do {                           \
     rs_log0(RS_LOG_NOTICE,  __FUNCTION__, (s) , ##str);        \
     } while (0)

#define rs_log_warning(s, str...) do {                          \
     rs_log0(RS_LOG_WARNING,  __FUNCTION__, (s) , ##str);       \
     } while (0)

#define rs_log_info(s, str...) do {                             \
     rs_log0(RS_LOG_INFO,  __FUNCTION__, (s) , ##str);          \
     } while (0)


#define rs_fatal(s, str...) do {               \
     rs_log0(RS_LOG_CRIT,  __FUNCTION__,       \
	      (s) , ##str);                    \
     abort();                                  \
     } while (0)

#else /* not defined HAVE_VARARG_MACROS */

EXPORT void rs_log_trace_nofn(char const *s, ...);
EXPORT void rs_log_info_nofn(char const *, ...);
EXPORT void rs_log_notice_nofn(char const *, ...);
EXPORT void rs_log_warning_nofn(char const *s, ...);
EXPORT void rs_log_error_nofn(char const *s, ...);
EXPORT void rs_log_critical_nofn(char const *, ...);
EXPORT void rs_log_fatal_nofn(char const *s, ...);

EXPORT void rs_log0_nofn(int level, char const *fmt, ...);

/* If we don't have gcc vararg macros, then we fall back to making the
 * log routines just plain functions.  On platforms without gcc (boo
 * hiss!) this means at least you get some messages, but not the nice
 * function names etc. */
#define rs_log rs_log0_nofn

#define rs_trace        rs_log_trace_nofn
#define rs_log_info     rs_log_info_nofn
#define rs_log_notice   rs_log_notice_nofn
#define rs_log_warning  rs_log_warning_nofn
#define rs_log_error    rs_log_error_nofn
#define rs_log_critical rs_log_critical_nofn
#define rs_fatal        rs_log_fatal_nofn
#endif /* HAVE_VARARG_MACROS */



/**
 * \macro rs_trace_enabled()
 *
 * Call this before putting too much effort into generating trace
 * messages.
 */

EXPORT extern int rs_trace_level;

#ifdef DO_RS_TRACE
#  define rs_trace_enabled() ((rs_trace_level & RS_LOG_PRIMASK) >= RS_LOG_DEBUG)
#else
#  define rs_trace_enabled() 0
#endif


extern EXPORT const char *rs_program_name;

#ifdef __cplusplus
}
#endif

