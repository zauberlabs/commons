#include <stdlib.h>

#include "queue.h"

#define IS_QUEUE(t) (t!=NULL)

struct node 
{	void *data;
	struct node *link;	
};

struct queue
{	struct node *head;
	struct node *tail;
};

EXPORT queue_t
queue_new(void)
{	queue_t q;

	q = malloc(sizeof(*q));
	if( q )
	{	q->head = NULL;
		q->tail = NULL;
	}

	return q;
}

EXPORT void 
queue_free(queue_t queue)
{	struct node *node, *next;

	if( IS_QUEUE(queue) )
	{	node = queue->head;
		while( node!=NULL)
		{	next = node->link;
			free(node);
			node = next;
		}
		free(queue);
	}
}

EXPORT int
queue_is_empty(queue_t queue)
{
	return IS_QUEUE(queue) ? queue->head == NULL : 1;
}

EXPORT int
queue_enqueue(queue_t queue, void *data)
{	struct node *node;
	int ret = 0;

	if( IS_QUEUE(queue) )
	{	node = malloc(sizeof(*node));
		if( node == NULL )
			ret = -1;
		else
		{	node->data = data;
			node->link = NULL;
			if( queue->head == NULL )
				queue->head = node;
			else
				queue->tail->link = node;
			queue->tail = node;
		}
	}
	else 
		ret = -1;
		
	return ret;
}

EXPORT void *
queue_dequeue(queue_t queue)
{	struct node *node;
	void *ret = NULL;

	if( IS_QUEUE(queue) )
	{ 	node = queue->head;
		if( node != NULL )
		{	ret = node->data;
			queue->head = node->link;
			free(node);
		}
	}
		
	return ret;
}

