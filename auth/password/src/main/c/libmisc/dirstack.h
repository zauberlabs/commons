/*
 * $Id: dirstack.h,v 0.0 2001/09/08 15:35:22 juam Exp $
 *
 *  Directory Listings tool
 *
 */
#ifndef _DIRSTACK_H_
#define _DIRSTACK_H_

typedef struct stackdir_t *stackdir_t;

stackdir_t dirstack_new( void );
int   dirstack_is_valid( stackdir_t ld );
int   dirstack_is_empty( stackdir_t ld );
void  dirstack_destroy( stackdir_t ld );

/*
 * dirstack_push()
 *
 * adds all the files/directories in `dirname' to the stack 
 *
 * <0 on error. See errno on error.
 */
int
dirstack_push( stackdir_t ld, const char *dirname );

/*
 * PopFile()
 *
 * Gets the next file in the directory and put it in '*name'
 *
 * Returns: 
 *   1 if ok
 *   0 if we reach the end of directory. More directories may be available
 *  <0 if was an error or reach the end.
 *
 */
int
dirstack_get_file( stackdir_t ld, char **name );

#endif
