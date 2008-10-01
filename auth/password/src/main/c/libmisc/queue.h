#ifndef ZD0E437531E4E79A568A44500452B941A
#define ZD0E437531E4E79A568A44500452B941A

#include "dll.h"

typedef struct queue *queue_t;

EXPORT queue_t queue_new(void);
EXPORT void    queue_free(queue_t queue);

EXPORT int     queue_is_empty(queue_t queue);
EXPORT int     queue_enqueue(queue_t queue, void *data);
EXPORT void *  queue_dequeue(queue_t queue);

#endif
