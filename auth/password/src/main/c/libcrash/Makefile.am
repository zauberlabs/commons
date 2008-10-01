
EXTRA_DIST = Makefile.mine

noinst_LIBRARIES = libcrash.a

libcrash_a_SOURCES = sigsegv.c sigsegv.h
libcrash_a_CFLAGS  = -I../

noinst_PROGRAMS = test1 test2 test3

test1_SOURCES = test1.c
test1_CFLAGS  = -I..
test1_LDFLAGS = -L.
test1_LDADD   = -lcrash -lpthread
test1_DEPENDENCIES = libcrash.a

test2_SOURCES = test2.c
test2_CFLAGS  = -I..
test2_LDFLAGS = -L.
test2_LDADD   = -lcrash -lpthread
test2_DEPENDENCIES = libcrash.a

test3_SOURCES = test3.c
test3_CFLAGS  = -I..
test3_LDFLAGS = -L.
test3_LDADD   = -lcrash -lpthread
test3_DEPENDENCIES = libcrash.a
