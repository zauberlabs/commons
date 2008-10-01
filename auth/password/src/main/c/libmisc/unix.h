/*
 * (c) Copyright 2003  Juan F. Codagnone <juam@arnet.com.ar>-All rights reserved */
#ifndef _UNIX_H_
#define _UNIX_H_

/* missing mode */

#include <config.h>

#define	R_OK	4		/* Test for read permission.  */
#define	W_OK	2		/* Test for write permission.  */
#define	X_OK	1		/* Test for execute permission.  */
#define	F_OK	0		/* Test for existence.  */

typedef unsigned int size_t;

/*  
 *  MSVC++ sys/stat doesn't have the struct stat::st_mode macros and definitions *
 *  Extracted from stat(2) man page
 */
#if 0
#define S_IFMT	  0170000   /* bitmask for the file type bitfields */
#define S_IFSOCK  0140000   /* socket */
#define S_IFLNK	  0120000   /* symbolic link */
#define S_IFREG	  0100000   /* regular file */
#define S_IFBLK	  0060000   /* block device */
#define S_IFDIR	  0040000   /* directory */
#define S_IFCHR	  0020000   /* character device */
#define S_IFIFO	  0010000   /* fifo */
#define S_ISUID	  0004000   /* set UID bit */
#define S_ISGID	  0002000   /* set GID bit (see below) */
#define S_ISVTX	  0001000   /* sticky bit (see below) */
#define S_IRWXU	  00700	    /* mask for file owner permissions */
#define S_IRUSR	  00400	    /* owner has read permission */
#define S_IWUSR	  00200	    /* owner has write permission */
#define S_IXUSR	  00100	    /* owner has execute permission */
#define S_IRWXG	  00070	    /* mask for group permissions */
#define S_IRGRP	  00040	    /* group has read permission */
#define S_IWGRP	  00020	    /* group has write permission */
#define S_IXGRP	  00010	    /* group has execute permission */
#define S_IRWXO	  00007	    /* mask for permissions for others (not in group)*/
#define S_IROTH	  00004	    /* others have read permission */ 
#define S_IWOTH	  00002	    /* others have write permisson */
#define S_IXOTH	  00001	    /* others have execute permission */
#endif

#define S_ISREG(m)	 ( (m) & S_IFREG )
#define S_ISDIR(m)	 ( (m) & S_IFDIR )
#define S_ISCHR(m)	 ( (m) & S_IFCHR )
#define S_ISBLK(m)	 ( (m) & S_ISBLK )
#define S_ISFIFO(m)	 ( (m) & S_IFIFO )

int access(const char *pathname, int mode);
int mkdir(const char *path, mode_t mode);
int write(int fd, void *data, size_t len);
mode_t umask(mode_t mask);
unsigned int sleep(unsigned int seconds);
int _isatty ( int desc );
#define isatty(m) (_isatty(m))
#endif
