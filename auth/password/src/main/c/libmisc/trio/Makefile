# Generated automatically from Makefile.in by configure.
CC	= gcc
CFLAGS	= -g -O2 -DDEBUG
OBJS	= triostr.o trio.o trionan.o
TARGETLIB	= libtrio.a
TARGETINCS	= trio.h triop.h triodef.h trionan.h triostr.h
LDFLAGS	= -L. -ltrio -lm
AR	= ar
RANLIB	= ranlib
ERASE	= rm -f
MKDIR	= mkdir -p
GENDOC	= doxygen

PURIFY = purify
PURIFYOPTIONS = -chain-length=16 -first-only=YES

# Installation settings
INSTALL		= /usr/bin/ginstall -c
INSTALL_DATA	= ${INSTALL} -m 644
prefix		= /usr/local
exec_prefix	= ${prefix}
includedir	= ${prefix}/include
libdir		= ${exec_prefix}/lib

all: $(TARGETLIB) $(TARGET) regression

test:
	./regression

install:	$(TARGETLIB)
	$(MKDIR) $(libdir)
	$(MKDIR) $(includedir)
	$(INSTALL_DATA) $(TARGETLIB) $(libdir)/$(TARGETLIB)
	for i in $(TARGET_INCS);do \
	(set -x;$(INSTALL_DATA) $$i $(includedir)); \
	done

pure: $(TOBJS) $(OBJS)
	$(PURIFY) $(PURIFYOPTIONS) $(CC) $(CFLAGS) $^ $(LDFLAGS)

regression: regression.o $(TARGETLIB)
	$(CC) $(CFLAGS) regression.o $(LDFLAGS) -o $@

example: example.o $(TARGETLIB)
	$(CC) $(CFLAGS) example.o $(LDFLAGS) -o $@

compare: compare.o $(TARGETLIB)
	$(CC) $(CFLAGS) compare.o $(LDFLAGS) -o $@

userdef: userdef.o $(TARGETLIB)
	$(CC) $(CFLAGS) userdef.o $(LDFLAGS) -o $@

$(TARGETLIB): $(OBJS)
	$(AR) ruv $(TARGETLIB) $(OBJS)
	$(RANLIB) $(TARGETLIB)

doc::
	$(GENDOC) doc/trio.cfg

clean:
	$(ERASE) *~ core regression example $(TOBJS) $(OBJS) $(TARGET) $(TARGETLIB) example.o regression.o
