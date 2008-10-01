#ifndef Z3A437113A1EBA13A9C397E185E80E7F7
#define Z3A437113A1EBA13A9C397E185E80E7F7

typedef struct
{	unsigned char *t; 
	int index;

}stackElementT;

typedef struct stackCDT *stack_t;

stack_t stack_new(void);
void    stack_destroy(stack_t stack);
int     stack_push (stack_t stack, void *data);
int     stack_pop (stack_t stack, void **data);
int     stack_is_empty(stack_t stack);
int     stack_top (stack_t stack, void **data);

#endif
