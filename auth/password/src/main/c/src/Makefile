CC = gcc

OUT = password_d
OBJS = main.o syslog.o ../libdaemon/daemon.o ../libmisc/trace.o ../libmisc/newopt.o ../libmisc/strdup.o ../libcrash/sigsegv.o
LDDFLAGS = -L../libdaemon 
CFLAGS = -ansi -pedantic -I.. -I../libmisc -g
LIBS = -lcrack -lxmlrpc_server  -lxmlrpc_server_abyss  -lxmlrpc -lxmlrpc_util -lxml2 -lz -lm -lpthread 

all: $(OBJS)
	$(CC) -o $(OUT) $(CFLAGS) $(LDDFLAGS) $(LIBS)  $(OBJS)  
clean:
	$(RM) *.o $(OUT)
