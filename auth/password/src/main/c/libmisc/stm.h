/**
 * \file State Machine Executor. Give him a bunch of tables and an input and 
 *  he will do the job
 */
#ifndef ZB6923F700E91E7D577BDE4A599C4851E
#define ZB6923F700E91E7D577BDE4A599C4851E

#define ELSE	NULL

typedef struct parse
{ 	int function;
	int (*cmpfnc)(int );
	int next_state;
	int (*action)( int , void *);
} ST_PARSE;

typedef struct stCDT * stm_t;

/** creates a new parser object 	*/
stm_t stm_new(ST_PARSE **sp, unsigned num_states, int init_state, void *data);

/**
 * parses input c for parser object pd
 * returns whatever returns action routine or 0 if no action routine is given
 * or debug mode
 */
int stm_parse( stm_t pd, int c );

/** Obtains parser state of parser object pd  */
int stm_get_state( stm_t pd );

/** Sets parser object pd to its initial state */
int stm_reinit( stm_t pd );

/** closes parser object */
int stm_destroy( stm_t pd );

/** \brief enable/disable debug 
 *
 *  callback format: old_state, new_state, char
 */
int stm_set_debug( stm_t st, void (*fnc) (int, int, int) );

/**
 * Outputs a graphviz digraph
 */
int
stm_print_digraph( void *fp, ST_PARSE **sp, unsigned nstates, 
                   const char *(state)(int c), const char *(fnc)(void *) );
enum
{ 	EST_OK,
	EST_NO_SLOT, 
	EST_BAD_PD,
	EST_PD_NOT_OPENED,
	EST_BAD_STATE,
	EST_NULL_PTR
};

enum {
	ST_CHAR,	/**< cmp c with letter   */
	ST_LCHAR,	/**< cmp lower(c) with letter */
	ST_UCHAR,	/**< cmp upper(c) with letter */
	ST_FUNC,	/**< cmp with function */
	ST_RFUNC,	/**< the function returns an state */
	ST_MAX   	/**< invalid, don't use it */
};
#endif
