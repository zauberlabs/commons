
#ifndef BDC7C661334E19A615237078D89B9954
#define BDC7C661334E19A615237078D89B9954

#ifdef __cplusplus
extern "C" {
#endif

/**
 * read a whole line from a file streem
 *
 * Use #free() to free the pointer that is returned
 *
 * Strips CR, LF, CRLF
 *
 * \returns NULL on EOF
 */
char * getline_(FILE *fp);


#ifdef __cplusplus
}
#endif

#endif
