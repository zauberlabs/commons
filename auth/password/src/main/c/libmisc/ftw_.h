#ifndef Z878A77898552592460F0F0A74D090F86
#define Z878A77898552592460F0F0A74D090F86
/*
 * ftw_()  walks  through  the  directory  tree starting from the indicated
 * directory dir.  For each found entry in the tree, it  calls  fn()  with
 * the  full pathname of the entry, a pointer to the stat(2) structure for
 * the entry.
 *
 * To  stop the tree walk, fn() returns a non-zero value; this value will
 * become the return value of ftw().  Otherwise, ftw() will continue until
 * it has traversed the entire tree, in which case it will return zero, or
 * until it hits an error other than EACCES (such as a malloc(3) failure),
 * in which case it will return -1.
 *
 * Because	ftw()  uses dynamic data structures, the only safe way to exit
 * out of a tree walk is to return a non-zero  value.   To	handle	inter-
 * rupts,  for example, mark that the interrupt occurred and return a non-
 * zero value--don't use longjmp(3) unless the program is going to termi-
 * nate.
 *
 */

int ftw_(const char *path, 
       int (*fn)(const char *file, const struct stat *sb, void *data),
       void *data);

#endif
