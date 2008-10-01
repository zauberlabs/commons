#include <stdlib.h>
#include "stack.h"

struct stack_node
{	void *data;
	struct stack_node *next;
};

struct stackCDT
{ 	struct stack_node *list;
};

stack_t
stack_new(void)
{	stack_t s;

	s    = malloc(sizeof(*s));
	if( s )
		s->list = NULL;

	return s;
}

int
stack_is_valid(stack_t stack)
{	
	return stack!=NULL;
}

void
stack_destroy(stack_t stack)
{	struct stack_node *n, *nn;

	if( stack_is_valid(stack) )
	{
		for( n = stack->list; n ; n = nn )
		{ 	nn = n->next; 
			free(n);
		}
		free(stack);
	}
}

int
stack_push(stack_t stack, void *data)
{	struct stack_node *n;
	int ret = 0;
	
	if( stack_is_valid(stack) )
	{	n = malloc(sizeof(*n));
		if( n )
		{ 	n->data = data;
			n->next = stack->list;
			stack->list = n;
		}
		else
			ret = -1;
	}
	else
		ret = -1;
    
	return ret;
}

/*
 * <0 en caso de error
 */
int
stack_pop(stack_t stack, void **d)
{	struct stack_node *n;
	int ret = 0;
	
	if( stack_is_valid(stack) )
	{	if( stack->list )
		{ 	n = stack->list;
			stack->list = stack->list->next;
			*d = n->data;
		}
		else
			ret = -1;
	}
	else
		ret = -1;
    
	return ret;
}

int
stack_is_empty(stack_t stack)
{
	return stack_is_valid(stack) ?  stack->list == NULL : 0;
}

int
stack_top(stack_t stack, void **data)
{	int ret = 0; 

	if( stack_is_empty(stack) )
		ret = -1;
	else
		*data = stack->list->data;

	return ret;
}

